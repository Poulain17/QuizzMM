package fr.mmg.mmquizz.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fr.mmg.mmquizz.Metier.Score;

/**
 * Created by marie on 21/02/2018.
 */

public class DAOScore
{
    private static final int VERSION = 19;
    private static final String NOM_BDD = "mmquizz.sqlite";

    private static final String TABLE = "score";
    private static final String COL_ID = "id_score";
    private static final String COL_PSEUDO = "pseudo";
    private static final String COL_RESULTAT = "resultat";

    private SQLiteDatabase bdd;

    private MMQuizzSQLite mmQuizzSQLite;

    public DAOScore(Context context)
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

    //Récupération d'une liste de score en fonction du pseudo saisi par la joueur
    public ArrayList<Score> getScoreByPseudo (String pseudo)
    {
        openForRead();
        Cursor c = bdd.query(TABLE, new String[]{COL_ID, COL_PSEUDO, COL_RESULTAT},
                COL_PSEUDO + " = \"" + pseudo + "\"",null,null,null,null);
        if(c.getCount() == 0){
            c.close();
            return null;
        }
        ArrayList<Score> scoreArrayList = new ArrayList<>();
        while (c.moveToNext())
        {
            Score score = cursorToScore(c);
            scoreArrayList.add(score);
        }
        c.close();
        return scoreArrayList;
    }

    //Enregistre le premier score d'un pseudo saisi dans la base données
    public Score setScore (String pseudo, int resultat)
    {
        openForWrite();

        Score score = new Score();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PSEUDO,pseudo);
        contentValues.put(COL_RESULTAT, resultat);

        bdd.insert(TABLE,null,contentValues);

        return score;
    }

    //Modifie le score
    public Score updateScore (String pseudo, int resultat)
    {
        openForWrite();

        Score score = new Score();

        String strSQL = "UPDATE " + TABLE + " SET " + COL_RESULTAT+ "=" + resultat + " WHERE " + COL_PSEUDO + "=" +"'" + pseudo + "'";

        bdd.execSQL(strSQL);
        return score;
    }

    private Score cursorToScore(Cursor c)
    {
        return new Score(c.getInt(0),c.getString(1),c.getInt(2));
    }

    //Modifie le score, lorsqu'il y a deja un score avec ce pseudo sinon il l'enregistre
    public Score modifyScore (Score newValue)
    {
        openForRead();
        openForWrite();

        ArrayList<Score> scores = new ArrayList<>();
        Score highScore = new Score();

        //Récupère le score précédent en fonction du pseudo saisi
        scores = getScoreByPseudo(newValue.getPseudo());

        //Si aucun score n'est enregistré à ce pseudo, on enregistre le score dans la base de donnée
        if (scores == null)
        {
            newValue = setScore(newValue.getPseudo(),newValue.getResultat());
        }
        else
        {
             Score oldValue = scores.get(0);
            //Si le dernier score est supérieur à l'ancien, on le met à jour dans la base de donnée
            if (oldValue.getResultat() < newValue.getResultat())
            {
                highScore.setHighScore(oldValue.getResultat(), newValue.getResultat());
                updateScore(newValue.getPseudo(),highScore.getResultat());
            }
            else
            {
                highScore = oldValue;
            }
        }
        return highScore;
    }
}
