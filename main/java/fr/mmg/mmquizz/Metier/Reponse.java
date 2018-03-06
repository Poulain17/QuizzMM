package fr.mmg.mmquizz.Metier;

/**
 * Created by marie on 07/02/2018.
 */

public class Reponse
{
    // **** DECLARATION DES ATTRIBUTS ****//
    private int id;
    private String libelle;
    private int isCorrect;
    private Question questionSelected;

    // **** CONSTRUCTEURS ****//
    public Reponse()
    {

    }

    public Reponse(int id, String libelle, int isCorrect)
    {
        this.id = id;
        this.libelle = libelle;
        this.isCorrect = isCorrect;
        questionSelected = new Question();
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

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Question getQuestionSelected() {
        return questionSelected;
    }

    public void setQuestionSelected(Question questionSelected) {
        this.questionSelected = questionSelected;
    }

    // **** METHODES ****//
    @Override
    public String toString()
    {
        return libelle;
    }
}
