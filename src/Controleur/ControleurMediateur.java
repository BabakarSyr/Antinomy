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
    

    // Actions à effectuer en cas de clic sur une des cartes du continuum
    @Override
    public void clicCarteContinuum(int indiceCarteContinuum) {
        System.out.println(etatJeu);
        switch(etatJeu){
            case CARTE_SELECTIONNEE:
                //Jouer coup
                if(jeu.estDeplacementPossible(carteSelectionnee, indiceCarteContinuum)){
                    jeu.jouerCarte(carteSelectionnee, indiceCarteContinuum);
                    if(jeu.estParadoxe()){
                        etatJeu = EtatJeu.PARADOXE;
                    }
                }
            case PARADOXE:
                if(jeu.estPossibleEchangerParadoxe(indiceCarteContinuum)){
                    if(indiceCarteContinuum>jeu.joueurActif().sorcier.getPositionSorcier()){
                        jeu.echangerParadoxe(true);
                    }
                    else{
                        jeu.echangerParadoxe(false);
                    }
                    jeu.joueurActif().ajouterCristaux(1);
                    etatJeu = EtatJeu.DUEL;
                }
                else{
                    System.out.println("choisir position valide !");
                }
                break;
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
