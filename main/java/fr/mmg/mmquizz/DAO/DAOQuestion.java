package fr.mmg.mmquizz.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

import fr.mmg.mmquizz.Metier.Question;
import fr.mmg.mmquizz.Metier.Theme;

/**
 * Created by marie on 07/02/2018.
 */

public class DAOQuestion {

    private static final int VERSION = 19;
    private static final String NOM_BDD = "mmquizz.sqlite";

    private static final String TABLE = "question";
    private static final String COL_ID = "id_question";
    private static final String COL_LIBELLE = "libelle_question";
    private static final String COL_ID_THEME = "id_theme";

    private SQLiteDatabase bdd;

    private MMQuizzSQLite mmQuizzSQLite;

    public DAOQuestion (Context context)
    {
        mmQuizzSQLite = MMQuizzSQLite.getInstance(context);
    }

    public void openForWrite()
    {
        bdd = mmQuizzSQLite.getWritableDatabase();
    }

    public void openForRead()
    {
        bdd = mmQuizzSQLite.getReadableDatabase();
    }

    public void close()
    {
        bdd.close();
    }

    //Récupération d'une liste de questions aléatoires en fonction du thème
    public ArrayList<Question> getAllQuestionsFromTheme(int id_theme)
    {
        openForRead();
        Cursor c = bdd.query(TABLE, new String[]{COL_ID,COL_LIBELLE, COL_ID_THEME}, COL_ID_THEME + " = \"" + id_theme + "\"", null, null, null,"Random() Limit 10" );
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Question> questionsList = new ArrayList<>();
        while (c.moveToNext())
        {
            Question theme = cursorToQuestion(c);
            questionsList.add(theme);
        }
        c.close();
        return questionsList;
    }

    private Question cursorToQuestion(Cursor c)
    {
        return new Question(c.getInt(0),c.getString(1));
    }
}
