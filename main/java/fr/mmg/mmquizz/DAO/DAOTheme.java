package fr.mmg.mmquizz.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import fr.mmg.mmquizz.Metier.Theme;

/**
 * Created by marie on 07/02/2018.
 */

public class DAOTheme
{
    private static final int VERSION = 19;
    private static final String NOM_BDD = "mmquizz.sqlite";

    private static final String TABLE = "theme";
    private static final String COL_ID = "id_theme";
    private static final String COL_LIBELLE = "libelle_theme";
    private static final String COL_ICONE = "icone_theme";

    private SQLiteDatabase bdd;

    private MMQuizzSQLite mmQuizzSQLite;


    public DAOTheme(Context context)
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
    //Récupération du thème en fonction de l'id
    public Theme getThemeByID (int id_theme)
    {
        openForRead();
        Cursor c = bdd.query(TABLE, new String[]{COL_ID, COL_LIBELLE, COL_ICONE}, COL_ID + " = \"" + id_theme + "\"",
                null,null,null,COL_LIBELLE);
        Theme theme = cursorToTheme(c);
        c.close();
        return theme;
    }

    //Récupération de tous les thèmes
    public ArrayList<Theme> getAllThemes()
    {
        openForRead();
        Cursor c = bdd.query(TABLE, new String[]{COL_ID,COL_LIBELLE, COL_ICONE}, null, null, null, null, COL_LIBELLE);
        if (c.getCount() == 0) {
            c.close();
            return null;
        }
        ArrayList<Theme> themesList = new ArrayList<Theme>();
        while (c.moveToNext())
        {
            Theme theme = cursorToTheme(c);
            themesList.add(theme);
        }
        c.close();
        return themesList;
    }


    private Theme cursorToTheme(Cursor c)
    {
        return new Theme(c.getInt(0), c.getString(1), c.getString(2));
    }
}
