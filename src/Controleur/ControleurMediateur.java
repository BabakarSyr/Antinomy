package Controleur;


import java.util.ArrayList;
import java.util.Random;

import Modele.Coup;
import Modele.EtatJeu;
import Modele.Jeu;
import Modele.Joueur;
import Modele.Plateau;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;


public class ControleurMediateur implements CollecteurEvenements {

    String infoPlateau;
    Random random;
    boolean IAActive;
    Jeu jeu;
    EtatJeu etatJeu;
    int carteSelectionnee;
    boolean voirMainAdversaire, voirMainJoueurActif;
    Coup coup;
    InterfaceGraphique interfaceGraphique;
 
    
    public ControleurMediateur(Jeu j) {
        this.jeu = j;
        changerEtatJeu(EtatJeu.DEBUT_PARTIE);
        carteSelectionnee = -1;
        infoPlateau = "Placez votre sorcier !";
        voirMainAdversaire = false;
        voirMainJoueurActif = true;
        random = new Random();
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
    public boolean commande(String commande) {
        switch (commande) {
            case "Quitter":
                System.exit(0);
                break;
            case "Annuler":
                annule();
                break;
            case "Refaire":
                refaire();
                break;
            case "MenuPrincipal":
            default:
                return false;
        }
        return true;
    }

    void annule() {
        if(etatJeu != EtatJeu.DEBUT_PARTIE) {
            jeu.annuler();
            interfaceGraphique.repaint();
            /*if(jeu.plateau().peutAnnuler()){
                interfaceUtilisateur.setBoutonHistoriqueArriere(true);
            }else{
                interfaceUtilisateur.setBoutonHistoriqueArriere(false);
            }
            if(jeu.plateau().peutRefaire()){
                interfaceUtilisateur.setBoutonHistoriqueAvant(true);
            }else{
                interfaceUtilisateur.setBoutonHistoriqueAvant(false);
            }*/
        }
    }

    void refaire() {
        if(etatJeu != EtatJeu.DEBUT_PARTIE) {
            jeu.refaire();
            interfaceGraphique.repaint();
            /* 
            if(jeu.plateau().peutAnnuler()){
                interfaceUtilisateur.setBoutonHistoriqueArriere(true);
            }else{
                interfaceUtilisateur.setBoutonHistoriqueArriere(false);
            }
            if(jeu.plateau().peutRefaire()){
                interfaceUtilisateur.setBoutonHistoriqueAvant(true);
            }else{
                interfaceUtilisateur.setBoutonHistoriqueAvant(false);
            }
            */
        }
    }




    public EtatJeu etatJeu(){
        return etatJeu;
    }
    
    public void interfaceGraphique(InterfaceGraphique i){
        this.interfaceGraphique = i;
    }
    public Coup creerCoup(Plateau plateau){
        return jeu.creerCoup(plateau);
    }

    public void jouerCoup(Coup coup){
        if(coup != null){
            jeu.jouerCoup(coup);
        }else{
            System.out.println("Coup null fourni !");
        }
    }

    public void joue(Plateau plateau){
        Coup coup = creerCoup(plateau);
        System.out.println("JAI BIEN CREE MON COUP!");
        jouerCoup(coup);
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
                changerEtatJeu(EtatJeu.DEBUT_TOUR);
                changerTour();
                break;
            case DEBUT_PARTIE:
                break;
            default:
                break;
        }
    }

    public void clicCarteMainAdverse( ) {
        switch(etatJeu){
            case DUEL:
                voirMainAdversaire = true;
                Joueur joueur = jeu.duel();
                if(joueur != null){
                    infoPlateau = joueur.getNom() + " à remporté le duel !";
                    changerEtatJeu(EtatJeu.DEBUT_TOUR);
                    changerTour();
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
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                        infoPlateau = "";
                        carteSelectionnee = -1;
                        changerTour();
                    }
                }
                else
                {
                    infoPlateau = "mouvement impossible";
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
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                        infoPlateau = "";
                        changerTour();
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
                    if(jeu.plateau().joueurInactif().getPositionSorcier() != -1){
                        changerEtatJeu(EtatJeu.DEBUT_TOUR);
                        infoPlateau = "";
                    }
                    changerTour();
                }
                break;
            default:
                break;
        }
    }

    private void changerTour( ) {
        //Coup coup = jeu.creerCoup(carteSelectionnee, indiceCarteContinuum, indiceParadoxe);
        //jouerCoup(coup);
        jeu.plateau().changerJoueurActif();
        if (estTourIA())
        {
            //tourIA (jeu.joueurActif().joue());
            tourIA (jeu.joueurActif().joueCoup());
        }
        else
        {
            voirMainJoueurActif=true;
            //voirMainAdversaire=false;
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

    //joue le tour de l'IA
    public void tourIA (ArrayList<Integer> coupIA )
    {
        if(coupIA==null)
        {
            System.out.println("\n\n\n\n\n\n\n\n\n\n\n\nla liste de coups est vide grosse banane\n\n\n\n\n\n\n\n\n\n\n\n");
            return;
        }
        if (etatJeu == EtatJeu.DEBUT_PARTIE)
        {
            clicCarteContinuum(coupIA.get(0));
            System.out.println("sorcier du joueur "+ jeu.plateau().joueurActif +" a la position"+jeu.plateau().joueurActif().getPositionSorcier());

        }
        else
        {
            voirMainJoueurActif = false;
            clicCarteMain(coupIA.get(0));
            clicCarteContinuum(coupIA.get(1));
            if (coupIA.size()>2)
            {
                clicCarteContinuum(coupIA.get(2));
            }
        }
    }

    public void tourIA (Coup coupIA )
    {
        if (etatJeu == EtatJeu.DEBUT_PARTIE)
        {
            clicCarteContinuum(coupIA.indiceCarteContinuum());
        }
        else
        {
            voirMainJoueurActif = false;
            clicCarteMain(coupIA.indiceCarteJouee());
            clicCarteContinuum(coupIA.indiceCarteContinuum());
            if (coupIA.indiceParadoxe()!= -1)
            {
                clicCarteContinuum(coupIA.indiceParadoxe());
            }
        }
    }

    public boolean estTourIA ()
    {
        return jeu.joueurActif().estIA();
    }

}