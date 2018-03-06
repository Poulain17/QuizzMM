package fr.mmg.mmquizz.Metier;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by marie on 07/02/2018.
 */

public class Theme implements Parcelable
{
    // **** DECLARATION DES ATTRIBUTS ****//
    private int id;
    private String libelle;
    private String icone;
    private ArrayList<Question> questions;

    // **** CONSTRUCTEURS ****//
    public Theme()
    {

    }

    public Theme(int id, String libelle)
    {
        this.id = id;
        this.libelle = libelle;
    }

    public Theme(int id, String libelle, String icone)
    {
        this.id = id;
        this.libelle = libelle;
        this.icone = icone;
        questions =  new ArrayList<Question>();
    }

    // **** CONSTRUCTEUR - PARCELABLE ****//
    protected Theme(Parcel in)
    {
        id = in.readInt();
        libelle = in.readString();
        icone = in.readString();
    }

    // ********** METHODES GETTERS ET SETTERS **********//
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    // **** METHODES ****//
    @Override
    public String toString()
    {
        return libelle;
    }

    // **** METHODES - PARCELABLE ****//
    public static final Creator<Theme> CREATOR = new Creator<Theme>()
    {
        @Override
        public Theme createFromParcel(Parcel in)
        {
            return new Theme(in);
        }

        @Override
        public Theme[] newArray(int size)
        {
            return new Theme[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(libelle);
        parcel.writeString(icone);
    }
}
