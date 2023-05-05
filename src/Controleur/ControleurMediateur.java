package Controleur;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


import Global.Configuration;
import Modele.Carte;
import Modele.EtatJeu;
import Modele.Jeu;
import Structures.Iterateur;
import Structures.Sequence;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {

    boolean IAActive;
    Jeu jeu;
    EtatJeu etatJeu;
    int carteSelectionnee;
    
    public ControleurMediateur(Jeu j) {
        this.jeu = j;
        etatJeu = EtatJeu.DEBUT_TOUR;
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
    public void clicCarteMain(int indiceCarte) {
        switch(etatJeu){
            case DEBUT_TOUR:
                carteSelectionnee = indiceCarte;
                previsualisationDeplacement();
                break;
            case CARTE_SELECTIONNEE:
                if(indiceCarte != carteSelectionnee){
                    carteSelectionnee = indiceCarte;
                    previsualisationDeplacement();
                }else{
                    annulerPrevisualisation();
                }
            default:
                break;
        }
    }
    

    //TODO Compléter méthode clicCarteContinuum
    @Override
    public void clicCarteContinuum(int indiceCarte) {
        System.out.println(etatJeu);
        switch(etatJeu){
            case CARTE_SELECTIONNEE:
                //Jouer coup
                jeu.getPlateau().joueurActif.jouerCarte(indiceCarte, jeu.getPlateau().getContinuum());
            default:
                break;
        }
    }

    //TODO Compléter méthode prévisualisationDeplacement
    public void previsualisationDeplacement(){
        etatJeu = EtatJeu.CARTE_SELECTIONNEE;
    }

    private void annulerPrevisualisation() {
        etatJeu = EtatJeu.DEBUT_TOUR;
    }
    
}
