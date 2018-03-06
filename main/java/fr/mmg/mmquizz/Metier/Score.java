package fr.mmg.mmquizz.Metier;

import java.util.ArrayList;

import fr.mmg.mmquizz.DAO.DAOScore;

/**
 * Created by marie on 20/02/2018.
 */

public class Score {

    // **** DECLARATION DES ATTRIBUTS ****//
    int id;
    int resultat;
    String pseudo;
    Theme theme;
    Question question;
    Reponse reponse;

    // **** CONSTRUCTEURS ****//
    public Score() {

    }

    public Score(int id, String pseudo, int resultat) {
        this.id = id;
        this.pseudo = pseudo;
        this.resultat = resultat;
        Theme theme = new Theme();
        Question question = new Question();
        Reponse reponse = new Reponse();
    }

    // ********** METHODES GETTERS ET SETTERS **********//
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public int getResultat() {
        return resultat;
    }

    public void setResultat(int resultat) {
        this.resultat = resultat;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // **** METHODES ****//
    public int setResultat(Reponse reponseChoisi) {
        if (reponseChoisi.getIsCorrect() != 0)
            resultat++;
        return resultat;

    }

    //Methode qui détermine le texte de la fenêtre de dialogue en fonction du score du joueur
    public String setTextAlertDialog(int resultat) {
        String message = "";

        switch (resultat) {
            case 0:
                message = "Il est grand temps de lire/relire ce manga.";
                break;
            case 1:
            case 2:
            case 3:
                message = "Des lacunes, pas brillant...";
                break;
            case 4:
            case 5:
                message = "Pas terrible, à revoir...";
                break;
            case 6:
            case 7:
                message = "J'imagine que je dois te féliciter";
                break;
            case 8:
            case 9:
                message = "Podium en vue, Bravo !";
                break;
            case 10:
                message = "Un sans faute, mes Félicitations !";
                break;
            default:
                break;
        }
        return message;
    }

    //Methode qui détermine le meilleur score
    public int setHighScore(int previousResultat, int resultat) {
        if (previousResultat < resultat) {
            return resultat;
        } else return previousResultat;
    }

    //Methode qui détermine le texte de la fenêtre de dialogue
    //en fonction du meilleur score, si il est atteint
    public String setTextAlertDialogHighScore(int resultat, int highScore) {
        String messageScore;

        if (resultat < highScore) {
            messageScore = "Bravo New HighScore : ";
        } else {
            messageScore = "Votre score : ";
        }
        return messageScore;
    }

}
