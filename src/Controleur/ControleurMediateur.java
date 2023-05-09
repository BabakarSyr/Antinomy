package Controleur;


import Modele.EtatJeu;
import Modele.Jeu;
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
    public void clicCarteContinuum(int indiceCarteContinuum) {
        System.out.println(etatJeu);
        switch(etatJeu){
            case CARTE_SELECTIONNEE:
                //Jouer coup
                jeu.jouerCarte(carteSelectionnee, indiceCarteContinuum);
                etatJeu = EtatJeu.DEBUT_TOUR;
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
