package fr.mmg.mmquizz.Metier;

import java.util.ArrayList;

/**
 * Created by marie on 07/02/2018.
 */

public class Question
{
    // **** DECLARATION DES ATTRIBUTS ****//
    private int id;
    private String libelle;
    private Theme themeSelected;
    private ArrayList<Reponse> reponses;

    // **** CONSTRUCTEURS ****//
    public Question()
    {

    }

    public Question(int id, String libelle)
    {
        this.id = id;
        this.libelle = libelle;
        themeSelected = new Theme();
        reponses = new ArrayList<Reponse>();
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

    public Theme getThemeSelected() {
        return themeSelected;
    }

    public void setThemeSelected(Theme themeSelected) {
        this.themeSelected = themeSelected;
    }

    public ArrayList<Reponse> getReponses() {
        return reponses;
    }

    public void setReponses(ArrayList<Reponse> reponses) {
        this.reponses = reponses;
    }

    // **** METHODE ****//
    @Override
    public String toString() {
        return libelle;
    }
}
