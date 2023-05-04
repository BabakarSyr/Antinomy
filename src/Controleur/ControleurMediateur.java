package Controleur;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import Global.Configuration;
import Modele.EtatJeu;
import Modele.Jeu;
import Structures.Iterateur;
import Structures.Sequence;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {

    boolean IAActive;
    Jeu jeu;
    EtatJeu etatJeu;
    
    public ControleurMediateur(Jeu j) {
        this.jeu = j;
	}

    // ============ Clic Souris ================
    @Override
    public void clicSouris(int coupX, int coupY) {

    }

    // ============ Mouvement Souris ================
    @Override
    public void traqueSouris(int coupX, int coupY) {
        
    }


    @Override
    public boolean commande(String com) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'commande'");
    }

    //TODO Compléter méthode clicCarteMain
    @Override
    public void clicCarteMain() {
        switch(etatJeu){
            case DEBUT_TOUR:
                previsualisationDeplacement();
                break;
            case CARTE_SELECTIONNEE:
                previsualisationDeplacement();
            default:
                break;
        }
    }
    //TODO Compléter méthode clicCarteContinuum
    @Override
    public void clicCarteContinuum() {
        switch(etatJeu){
            case CARTE_SELECTIONNEE:
                //Jouer coup
            default:
                break;
        }
    }

    //TODO Compléter méthode prévisualisationDeplacement
    public void previsualisationDeplacement(){
        etatJeu = EtatJeu.CARTE_SELECTIONNEE;
    }
    
}
