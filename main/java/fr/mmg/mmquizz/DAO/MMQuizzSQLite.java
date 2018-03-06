package fr.mmg.mmquizz.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.mmg.mmquizz.Metier.Theme;

/**
 * Created by marie on 07/02/2018.
 */

public class MMQuizzSQLite extends SQLiteOpenHelper
{
    // **** DECLARATION DES ATTRIBUTS ****//
    private final Context mycontext;
    private static MMQuizzSQLite sInstance;

    private static final int DATABASE_VERSION = 19;
    private String DATABASE_PATH;
    private static final String DATABASE_NAME = "mmquizz.sqlite";

    public static synchronized MMQuizzSQLite getInstance(Context context)
    {
        if (sInstance == null)
        {
            sInstance = new MMQuizzSQLite(context);
        }
        return sInstance;
    }

    // **** CONSTRUCTEUR ****//
    public MMQuizzSQLite(Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.mycontext = context;
        String filesDir = context.getFilesDir().getPath();
        DATABASE_PATH = filesDir.substring(0, filesDir.lastIndexOf("/")) + "/databases/";
        //Si la bdd n'existe pas dans le dossier de l'app
        if (!checkdatabase())
        {
            //Copie db de 'assets' vers DATABASE_PATH
            copydatabase();
        }
    }

    // **** METHODES ****//

    //Retourne true/false si la bdd existe dans le dossier de l'app
    private boolean checkdatabase()
    {
        File dbfile = new File(DATABASE_PATH + DATABASE_NAME);
        return dbfile.exists();
    }

    //Copie la base de "assets" vers "/data/data/com.package.nom/databases"
    //Fait au premier lancement de l'application
    private void copydatabase() {
        final String outFileName = DATABASE_PATH + DATABASE_NAME;

        InputStream myInput;
        try {
            //Ouvre la bdd de 'assets' en lecture
            myInput = mycontext.getAssets().open(DATABASE_NAME);

            //Dossier de destination
            File pathFile = new File(DATABASE_PATH);
            if (!pathFile.exists()) {
                if (!pathFile.mkdirs()) {
                    Toast.makeText(mycontext, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //Ouverture en écriture du fichier bdd de destination
            OutputStream myOutput = new FileOutputStream(outFileName);

            //Transfert de inputfile vers outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            //Fermeture
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mycontext, "Erreur : copydatabase()", Toast.LENGTH_SHORT).show();
        }
        //On greffe le numéro de version
        try {
            SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
            checkdb.setVersion(DATABASE_VERSION);
        } catch (SQLiteException e) {
            // bdd n'existe pas
        }
    } // copydatabase()

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion)
        {
            Log.d("debug","onUgrade() : oldVersion=" + oldVersion + ",newVersion=" + newVersion);
            mycontext.deleteDatabase(DATABASE_NAME);
            copydatabase();
        }
    }
}
