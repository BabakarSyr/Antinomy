package Controleur;


import java.util.Random;

import Modele.EtatJeu;
import Modele.Jeu;
import Modele.Joueur;
import Modele.ZoneClic;
import Vue.CollecteurEvenements;

public class ControleurMediateur implements CollecteurEvenements {

    String infoPlateau;
    Random random;
    boolean IAActive;
    Jeu jeu;
    EtatJeu etatJeu;
    int carteSelectionnee;
    boolean voirMainAdversaire, voirMainJoueurActif;
    
    public ControleurMediateur(Jeu j) {
        this.jeu = j;
        changerEtatJeu(EtatJeu.DEBUT_PARTIE);
        carteSelectionnee = -1;
        infoPlateau = "Placez votre sorcier !";
        voirMainAdversaire = false;
        voirMainJoueurActif = true;
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
            case DUEL_EGALITE:
                int indiceAdversaire = random.nextInt(3);
                Joueur joueur = jeu.duelEgalite(indiceCarte, indiceAdversaire);
                if(joueur!= null){
                    infoPlateau = joueur.getNom() + " à remporté le duel !";
                }
                else{
                    infoPlateau = "Egalité le jeu continue !";
                }
                changerTour();
                break;
            case DEBUT_PARTIE:
                break;
            default:
                break;
        }
    }

    public void clicCarteMainAdverse() {
        switch(etatJeu){
            case DUEL:
                voirMainAdversaire = true;
                Joueur joueur = jeu.duel();
                if(joueur != null){
                    infoPlateau = joueur.getNom() + " à remporté le duel !";
                    changerTour();
                    changerEtatJeu(EtatJeu.DEBUT_TOUR);
                }
                else{
                    infoPlateau = "Egalité selectionnez une carte pour départager !";
                    changerEtatJeu(EtatJeu.DUEL_EGALITE);
                    voirMainJoueurActif = false;
                    voirMainAdversaire = false;
                }
                break;
            case DUEL_EGALITE:
                break;
            default:
                voirMainAdversaire = !voirMainAdversaire;
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
                        infoPlateau = "Placez votre paradoxe sur le plateau !";
                        carteSelectionnee = -1;
                    }
                    else if(jeu.estDuel()){
                        changerEtatJeu(EtatJeu.DUEL);
                        infoPlateau = "";
                        carteSelectionnee = -1;
                        voirMainAdversaire = false;
                    }else{
                        changerTour();
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                        infoPlateau = "";
                        carteSelectionnee = -1;
                    }
                }
                else
                {
                    System.out.println("mouvement impossible");
                }
                break;
            case PARADOXE:
                if(jeu.estPossibleEchangerParadoxe(indiceCarteContinuum)){
                    jeu.paradoxe(indiceCarteContinuum);
                    if(jeu.estDuel()){
                        changerEtatJeu(EtatJeu.DUEL);
                        infoPlateau = "Duel ! Cliquez pour voir la main de l'adversaire.";
                    }
                    else{
                        changerTour();
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                    }
                    carteSelectionnee = -1;
                    voirMainAdversaire = false;
                }
                else{
                    infoPlateau = "choisir position valide !";
                }
                break;
            case DEBUT_PARTIE:
                if(jeu.positionsDepart().contains(indiceCarteContinuum)){
                    jeu.deplacerSorcier(indiceCarteContinuum);
                    if(jeu.plateau().joueurInactif().getPositionSorcier() == -1){
                        changerTour();
                    }else{
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                        infoPlateau = "";
                        changerTour();
                    }
                }
                break;
            default:
                break;
        }
    }

    //TODO implémenter methode changerTour
    private void changerTour() {
        jeu.plateau().changerJoueurActif();
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

    @Override
    public String infoPlateau() {
        return infoPlateau;
    }

    @Override
    public boolean voirMainAdversaire() {
        return voirMainAdversaire;
    }
    @Override
    public boolean voirMainJoueurActif() {
        return voirMainJoueurActif;
    }

    @Override
    public boolean joueurActif(int numJoueur){
        if(numJoueur==1){
            return(jeu.joueurActif()==jeu.plateau().joueur1);
        }
        if(numJoueur==2){
            return(jeu.joueurActif()==jeu.plateau().joueur2);
        }
        return false;
        
    }
    
}
