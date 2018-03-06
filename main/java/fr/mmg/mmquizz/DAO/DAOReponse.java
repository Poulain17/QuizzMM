package fr.mmg.mmquizz.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fr.mmg.mmquizz.Metier.Reponse;

/**
 * Created by marie on 07/02/2018.
 */

public class DAOReponse
{
    private static final int VERSION = 19;
    private static final String NOM_BDD = "mmquizz.sqlite";

    private static final String TABLE = "reponse";
    private static final String COL_ID = "id_reponse";
    private static final String COL_LIBELLE = "libelle_reponse";
    private static final String COL_ISCORRECT = "iscorrect";
    private static final String COL_ID_QUESTION = "id_question";

    private SQLiteDatabase bdd;

    private MMQuizzSQLite mmQuizzSQLite;

    public DAOReponse(Context context)
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

    //
    public Reponse getReponseByID (int id_reponse)
    {
        openForRead();
        Cursor c = bdd.query(TABLE, new String[]{COL_ID, COL_LIBELLE, COL_ISCORRECT, COL_ID_QUESTION},
                COL_ID + " = \"" + id_reponse + "\"",null,null,null,COL_LIBELLE);
        Reponse reponse = cursorToReponse(c);
        c.close();
        return reponse;
    }

    //Récupération d'une liste de reponses en fonction du thème
    public ArrayList<Reponse> getAllReponsesFromQuestion(int id_question)
    {
        openForRead();
        Cursor c = bdd.query(TABLE, new String[]{COL_ID, COL_LIBELLE, COL_ISCORRECT, COL_ID_QUESTION},
                COL_ID_QUESTION + " = \"" + id_question + "\"",null,null,null,COL_LIBELLE);
        if (c.getCount() == 0)
        {
            c.close();
            return null;
        }

        ArrayList<Reponse> reponsesList = new ArrayList<Reponse>();
        while (c.moveToNext())
        {
            Reponse reponse = cursorToReponse(c);
            reponsesList.add(reponse);
        }
        c.close();
        return reponsesList;
    }

    public Reponse cursorToReponse(Cursor c)
    {
        return new Reponse(c.getInt(0), c.getString(1), c.getInt(2));
    }
}
