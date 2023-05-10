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
        changerEtatJeu(EtatJeu.DEBUT_PARTIE);
        carteSelectionnee = -1;
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
                changerEtatJeu(EtatJeu.CARTE_SELECTIONNEE);
                previsualisationDeplacement();
                break;
            case CARTE_SELECTIONNEE:
                if(indiceCarte != carteSelectionnee){
                    carteSelectionnee = indiceCarte;
                    previsualisationDeplacement();
                }else{
                    carteSelectionnee = -1;
                    annulerPrevisualisation();
                }
                break;
            case PARADOXE:
                break;
            case DUEL:
                break;
            case DEBUT_PARTIE:
                break;
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
                        changerEtatJeu(EtatJeu.PARADOXE);
                        carteSelectionnee = -1;
                    }
                    else if(jeu.estDuel()){
                        changerEtatJeu(EtatJeu.DUEL);
                        carteSelectionnee = -1;
                    }else{
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                        carteSelectionnee = -1;
                    }
                }
                break;
            case PARADOXE:
                if(jeu.estPossibleEchangerParadoxe(indiceCarteContinuum)){
                    if(indiceCarteContinuum>jeu.joueurActif().sorcier.getPositionSorcier()){
                        jeu.echangerParadoxe(true);
                    }
                    else{
                        jeu.echangerParadoxe(false);
                    }
                    jeu.joueurActif().ajouterCristaux(1);
                    changerEtatJeu(EtatJeu.DUEL);
                    carteSelectionnee = -1;
                }
                else{
                    System.out.println("choisir position valide !");
                }
                break;
            case DEBUT_PARTIE:
                if(jeu.positionsDepart().contains(indiceCarteContinuum)){
                    jeu.deplacerSorcier(indiceCarteContinuum);
                }
                changerEtatJeu(EtatJeu.DEBUT_TOUR);
                break;
            default:
                break;
        }
    }
    public int carteSelectionnee(){
        return this.carteSelectionnee;
    }

    //TODO Compléter méthode prévisualisationDeplacement
    public void previsualisationDeplacement(){
        //changerEtatJeu(EtatJeu.CARTE_SELECTIONNEE);
    }

    private void annulerPrevisualisation() {
        //changerEtatJeu(EtatJeu.DEBUT_TOUR);
    }

    private void changerEtatJeu(EtatJeu etat) {
        etatJeu = etat;
    }
    
}
