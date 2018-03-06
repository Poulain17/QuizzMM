package fr.mmg.mmquizz.IHM;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.mmg.mmquizz.DAO.DAOTheme;
import fr.mmg.mmquizz.Metier.Theme;
import fr.mmg.mmquizz.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ouvre un accès à la DAOTheme
        DAOTheme daoTheme = new DAOTheme(this);
        daoTheme.openForRead();

        //Instancie une liste themes dans lequel on récupère tous les thèmes
        final ArrayList<Theme> themes = daoTheme.getAllThemes();

        //Associe la GridView des thèmes à la classe MainActivty
        GridView lvThemes = (GridView) findViewById(R.id.listView);

        //Instancie un adapter qui va chercher la liste themes
        ThemesAdapter adapter = new ThemesAdapter(this, R.layout.themesadapter,themes);
        //Applique l'adapter à la GridView
        lvThemes.setAdapter(adapter);

        //Definit les actions lorsque l'utilisateur sélectionne un thème
        lvThemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Definit le thème selectionné par l'utilisateur
                Theme selectedTheme = (Theme) parent.getItemAtPosition(position);

                //Affiche un toast avec le libellé du thème selectionné
                Toast myToast = Toast.makeText(getApplicationContext(), selectedTheme.getLibelle(), Toast.LENGTH_SHORT);
                myToast.setGravity(Gravity.CENTER,0,0);
                myToast.show();

                //Definit le passage de l'activité MainActivity à QuizzActivity
                Intent myIntent = new Intent(MainActivity.this,QuizzActivity.class);

                //Récupère le thème selectionné
                myIntent.putExtra("theme",selectedTheme);

                //Démarre l'activité avec le thème selectionné
                startActivity(myIntent);

            }
        });

    }
}
