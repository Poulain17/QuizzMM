package fr.mmg.mmquizz.IHM;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import fr.mmg.mmquizz.DAO.DAOQuestion;
import fr.mmg.mmquizz.DAO.DAOReponse;
import fr.mmg.mmquizz.DAO.DAOScore;
import fr.mmg.mmquizz.Metier.Question;
import fr.mmg.mmquizz.Metier.Quizz;
import fr.mmg.mmquizz.Metier.Reponse;
import fr.mmg.mmquizz.Metier.Score;
import fr.mmg.mmquizz.Metier.Theme;
import fr.mmg.mmquizz.R;


/**
 * Created by marie on 12/02/2018.
 */

public class QuizzActivity extends Activity implements View.OnClickListener
{
    int indexQ = 0;
    int resultatFinal = 0;
    int highScore = 0;
    Quizz monQuizz = new Quizz();
    Score monScore  = new Score();
    Score ancienScore = new Score();

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);

        //Récupère le thème choisi dans MainActivty
        Intent intent = getIntent();
        final Theme themeSelectionne =intent.getParcelableExtra("theme");
        monQuizz.setTheme(themeSelectionne);

        //---------Création d'une liste de question aléatoire--------//

        //Ouvre un accès à la DAOQuestion
        DAOQuestion daoQuestion = new DAOQuestion(this);
        daoQuestion.openForRead();

        //Récupère une liste de questions qui correspondent au thème selectionné
        final ArrayList<Question> questions = daoQuestion.getAllQuestionsFromTheme(monQuizz.getTheme().getId());
        monQuizz.setQuestions(questions);

        lancer();

        //---------Gestion du Bouton--------//
        Button buttonNext = (Button) findViewById(R.id.button);
        buttonNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(monQuizz.getReponse() == null)
        {
            //Met en place le message au cas où l'utilisateur n'a pas saisi de reponse
            Toast myToast = Toast.makeText(getApplicationContext(), monQuizz.setMessage(monQuizz.getReponse()), Toast.LENGTH_SHORT);
            myToast.setGravity(Gravity.BOTTOM, 0, 0);
            myToast.show();
        }
        else
        {
            //Changement de la couleur du bouton si le réponse est bonne ou pas
            Button buttonNext = (Button) findViewById(R.id.button);
            if (monQuizz.getReponse().getIsCorrect() != 0) {
                buttonNext.setBackgroundColor(Color.parseColor("#013127"));
                buttonNext.setTextColor(Color.parseColor("#FFFFFF"));
            }
            else {
                buttonNext.setBackgroundColor(Color.parseColor("#4d0111"));
                buttonNext.setTextColor(Color.parseColor("#FFFFFF"));
            }

            //Augmente le score en cas de bonne réponse
            resultatFinal = monScore.setResultat(monQuizz.getReponse());

            //Passe à la question suivante
            indexQ++;

            if (indexQ < monQuizz.getQuestions().size()) {
                Reponse noResponse = null;
                monQuizz.setReponse(noResponse);
                lancer();
            } else {
                //Fermeture de la fenêtre de dialogue de fin de quiz
                endDialog();

            }
        }
    }

    public void lancer()
    {
        //---------Gestion de la partie Question--------//

        //Récupère la question qui correspond à l'index dans la liste des questions
        monQuizz.setQuestion(monQuizz.getQuestions().get(indexQ));

        //Associe le TextView de la question à la classe QuizzActivity
        TextView questionView = (TextView) findViewById(R.id.textView);
        //Recupere le texte de la question
        questionView.setText(monQuizz.getQuestion().getLibelle());

        //---------Gestion de la partie Reponse--------//

        //Ouvre un accès à la DAOReponse
        DAOReponse daoReponse = new DAOReponse(this);
        daoReponse.openForRead();

        //Récupère la liste de réponses qui correspondent à la question affichée
        final ArrayList<Reponse> reponses = daoReponse.getAllReponsesFromQuestion(monQuizz.getQuestion().getId());

        //Associe le GridView des réponses à la classe QuizzActivity
        GridView lvReponses = (GridView) findViewById(R.id.listView);

        //Instancie un adapter qui va chercher la liste reponses
        ReponsesAdapter adapter = new ReponsesAdapter(this, R.layout.reponsesadapter, reponses);
        //Applique l'adapter à la GridView
        lvReponses.setAdapter(adapter);
        //Definit les actions lorsque l'utilisateur sélectionne une réponse
        lvReponses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reponse selectedReponse = (Reponse) parent.getItemAtPosition(position);
                monQuizz.setReponse(selectedReponse);
                view.setSelected(true);
            }
        });
    }

    public void endDialog() {
        //Mise en place de la fenêtre de dialogue de fin de quizz
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        builder.setTitle("Merci d'avoir jouer")
                .setView(inflater.inflate(R.layout.dialogend, null))
                .setMessage("\nTon Score est de " + resultatFinal + " / 10")
                .setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) ((AlertDialog) dialog).findViewById(R.id.saisiePseudo);
                        String saisie = editText.getText().toString();
                        monScore.setPseudo(saisie);
                        highScoreDialog();
                        //System.exit(0);

                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }

    public void highScoreDialog(){
        //Mise en place de la fenêtre de dialogue de fin de quizz
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();

        //Ouvre un accès à la DAOScore
        DAOScore daoScore = new DAOScore(this);
        daoScore.openForWrite();
        daoScore.openForRead();

        daoScore.modifyScore(monScore);

        builder.setTitle("Scores")
                .setMessage(monScore.getPseudo() + "\n" + monScore.setTextAlertDialogHighScore(ancienScore.getResultat(),resultatFinal) + monScore.setHighScore(ancienScore.getResultat(), resultatFinal)).setPositiveButton("Rejouer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }
}
