package Controleur;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

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
    InterfaceGraphique interfaceGraphique;
    Plateau plateauDebutTour;
    Timer timer;
 
    
    public ControleurMediateur(Jeu j) {
        this.jeu = j;
        changerEtatJeu(EtatJeu.DEBUT_PARTIE);
        carteSelectionnee = -1;
        infoPlateau = "Placez votre sorcier !";
        voirMainAdversaire = false;
        voirMainJoueurActif = true;
        random = new Random();
        plateauDebutTour = jeu.plateau().clone();
        timer = new Timer();
	}

    public void init(){
        this.jeu.plateau = new Plateau();
        this.jeu.definirJoueur1(jeu.plateau.joueur1);
        changerEtatJeu(EtatJeu.DEBUT_PARTIE);
        carteSelectionnee = -1;
        infoPlateau = "Placez votre sorcier !";
        voirMainAdversaire = false;
        voirMainJoueurActif = true;
        plateauDebutTour = jeu.plateau();
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
            case "Recommencer":
                init();
                interfaceGraphique.miseAjour();
                break;
            case "MenuPrincipal":
                interfaceGraphique.afficherPanel("MenuPrincipal");
                break;
            case "Plateau":
                interfaceGraphique.afficherPanel("Plateau");
                break;
            case "ParametrePartie":
                interfaceGraphique.afficherPanel("ParametrePartie");
                break;
            default:
                return false;
        }
        return true;
    }

    void annule() {
        if(etatJeu != EtatJeu.DEBUT_PARTIE) {
            jeu.annuler();
            interfaceGraphique.miseAjour();
            plateauDebutTour = jeu.plateau().clone();

            interfaceGraphique.setBoutonHistoriqueAnnuler(jeu.plateau().peutAnnuler());
            interfaceGraphique.setBoutonHistoriqueRefaire(jeu.plateau().peutRefaire());
        }
    }

    void refaire() {
        if(etatJeu != EtatJeu.DEBUT_PARTIE) {
            jeu.refaire();
            interfaceGraphique.miseAjour();
            plateauDebutTour = jeu.plateau().clone();
            
            interfaceGraphique.setBoutonHistoriqueAnnuler(jeu.plateau().peutAnnuler());
            interfaceGraphique.setBoutonHistoriqueRefaire(jeu.plateau().peutRefaire());
        }
    }

    public void regle(){

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
                        carteSelectionnee = -1;
                        infoPlateau = "duel!";
                        duel();
                        interfaceGraphique.setDuelEgalite(random.nextInt(3), random.nextInt(3));
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
                    if(jeu.partieTerminee()){
                        changerEtatJeu(EtatJeu.FIN_PARTIE);
                        infoPlateau = jeu.nomVainqueur()+ " REMPORTE LA PARTIE !";
                    }
                    else if(jeu.estDuel()){
                        changerEtatJeu(EtatJeu.DUEL);
                        carteSelectionnee = -1;
                        infoPlateau = "duel!";
                        duel();
                        interfaceGraphique.setDuelEgalite(random.nextInt(3), random.nextInt(3));
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
        joue(plateauDebutTour);
        jeu.plateau().changerJoueurActif();
        if (estTourIA())
        {
            tourIA (jeu.joueurActif().joueCoup());
        }
        else
        {
            voirMainJoueurActif=true;
            voirMainAdversaire=false;
        }
        plateauDebutTour = jeu.plateau().clone();
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

    public void duel()
    {
        TimerTask finTour = new TimerTask() {
            @Override
            public void run()
            {
                changerEtatJeu(EtatJeu.DEBUT_TOUR);
                changerTour();
                voirMainJoueurActif = true;
                voirMainAdversaire = false;
                interfaceGraphique.miseAjour();
                interfaceGraphique.clearDuelEgalite();
            }
        };



        voirMainJoueurActif = true;
        voirMainAdversaire = true;
        Joueur joueur = jeu.duel();
        if(joueur != null){
            String resultatDuel;
            if (joueur==jeu.plateau.joueur1)
            {
                resultatDuel = "vainqueur: "+joueur.getNom()+" "+jeu.plateau.valeurMain(joueur)+"points / perdant: "+jeu.plateau.joueur2.getNom()+" "+jeu.plateau.valeurMain(jeu.plateau.joueur2)+"points";
            }
            else
            {
                resultatDuel = "vainqueur: "+joueur.getNom()+" "+jeu.plateau.valeurMain(joueur)+"points / perdant: "+jeu.plateau.joueur1.getNom()+" "+jeu.plateau.valeurMain(jeu.plateau.joueur1)+"points";
            }
            infoPlateau = resultatDuel;
            interfaceGraphique.miseAjour();
            timer.schedule(finTour, 3000);
        }
        else{
            infoPlateau = "Egalité une carte va etre tiree au hasard";
            changerEtatJeu(EtatJeu.DUEL_EGALITE);

            int indiceAdversaire = random.nextInt(3);
            int indiceJoueurActif = random.nextInt(3);
            //TODO ajouter une methode pour afficher duel egalite
            interfaceGraphique.setDuelEgalite(indiceJoueurActif, indiceAdversaire);
            interfaceGraphique.miseAjour();
            joueur = jeu.duelEgalite(indiceJoueurActif, indiceAdversaire);
            if(joueur!= null){
                infoPlateau = joueur.getNom() + " à remporté le duel apres egalité!";
            }
            else{
                infoPlateau = "Egalité le jeu continue !";
            }
            timer.schedule(finTour, 4000);
            if(jeu.partieTerminee())
            {
                changerEtatJeu(EtatJeu.FIN_PARTIE);
                infoPlateau = jeu.nomVainqueur()+ " REMPORTE LA PARTIE !";
            }
        }
    }
}