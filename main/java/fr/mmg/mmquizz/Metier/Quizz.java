package fr.mmg.mmquizz.Metier;
import java.util.ArrayList;


/**
 * Created by marie on 13/02/2018.
 */

public class Quizz
{
    // **** DECLARATION DES ATTRIBUTS ****//
    private Theme theme;
    private Question question;
    private Reponse reponse;
    private ArrayList<Question> questions;

    // **** CONSTRUCTEUR ****//
    public Quizz()
    {

    }

    // ********** METHODES GETTERS ET SETTERS **********//
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Reponse getReponse() {
        return reponse;
    }

    public void setReponse(Reponse reponse) {
        this.reponse = reponse;
    }

    // **** METHODES ****//

    //Méthode qui determine le texte du toast si le joueur
    //n'a pas sélectionné de réponse avant de passer à la question suivante
    public String setMessage (Reponse reponseChoisi)
    {
        String message;

        if (reponseChoisi == null)
        {
            message = "Veuillez sélectionner une réponse !";
        }
        else {

            if (reponse.getIsCorrect() != 0) {
                message = "Bonne Reponse !";
            } else {
                message = "Mauvaise Reponse !";
            }

        }
        return message;
    }

}
