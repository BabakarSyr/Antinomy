package Controleur;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Modele.Coup;
import Modele.EtatJeu;
import Modele.Jeu;
import Modele.Joueur;
import Modele.Plateau;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;


public class ControleurMediateur implements CollecteurEvenements {

    String infoPlateau, nomJ1, nomJ2, typeJ1, typeJ2;
    Random random;
    boolean IAActive;
    Jeu jeu;
    EtatJeu etatJeu;
    int carteSelectionnee;
    boolean voirMainAdversaire, voirMainJoueurActif;
    InterfaceGraphique interfaceGraphique;
    Plateau plateauDebutTour;
    Timer timer;
    ScheduledExecutorService timerV2;
 
    
    public ControleurMediateur(Jeu j) {
        this.jeu = j;
        changerEtatJeu(EtatJeu.DEBUT_PARTIE);
        carteSelectionnee = -1;
        infoPlateau = "Placez votre sorcier !";
        voirMainAdversaire = false;
        voirMainJoueurActif = true;
        random = new Random();
        plateauDebutTour = jeu.plateau().clone();
        this.timer = new Timer();
        timerV2 = Executors.newSingleThreadScheduledExecutor();
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

    public void init(String nomJ1, String nomJ2, String typeJ1, String typeJ2){
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
            case "Valider":
                init("Joueur 1", "Joueur 2", typeJ1, typeJ2);
                interfaceGraphique.afficherPanel("Plateau");
                interfaceGraphique.miseAjour();
                break;
            case "Regles":
                interfaceGraphique.afficherPanel("Regles");
                break;
            case "Humain1":
                typeJ1 = "Humain";
                break;
            case "IAFacile1":
                typeJ1 = "IAFacile";
                break;
            case "IADifficile1":
                typeJ1 = "IADifficileV1";
                break;
            case "Humain2":
                typeJ2 = "Humain";
                break;
            case "IAFacile2":
                typeJ2 = "IAFacile";
                break;
            case "IADifficile2":
                typeJ2 = "IADifficileV1";
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
                        //timer.schedule(paradoxe, 2500);
                        timerV2.schedule(paradoxe, 2500, TimeUnit.MILLISECONDS);
                        
                    }
                    else if(jeu.estDuel()){
                        carteSelectionnee = -1;
                        infoPlateau = "duel!";
                        duel();
                        //interfaceGraphique.setDuelEgalite(random.nextInt(3), random.nextInt(3));
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
            case PARADOXE2:
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
                        //interfaceGraphique.setDuelEgalite(random.nextInt(3), random.nextInt(3));
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
    public void nouveauTimer()
    {
        if (this.timer != null)
        {
            this.timer.cancel();
            this.timer=null;
        }
        this.timer = new Timer();
        System.out.println("\n\n\n\n\n\n\n\n nouveau timer initialise \n\n\n\n\n\n\n\n");
    }

    private void changerTour( ) {
        //nouveauTimer();
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

    /*TimerTask finTour = new TimerTask() {
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

    TimerTask paradoxe = new TimerTask() {
        @Override
        public void run()
        {
            changerEtatJeu(EtatJeu.PARADOXE2);
            interfaceGraphique.miseAjour();
        }
    };*/

    Runnable paradoxe = () -> 
    {
        changerEtatJeu(EtatJeu.PARADOXE2);
        interfaceGraphique.miseAjour();
    };

    Runnable finTour = () -> 
    {
        changerEtatJeu(EtatJeu.DEBUT_TOUR);
        changerTour();
        voirMainJoueurActif = true;
        voirMainAdversaire = false;
        interfaceGraphique.miseAjour();
        interfaceGraphique.clearDuelEgalite();
    };


    public void duel()
    {
        voirMainJoueurActif = true;
        voirMainAdversaire = true;
        Joueur J1= jeu.plateau.joueur1;
        Joueur J2= jeu.plateau.joueur2;
        int valeurMainJ1 = jeu.plateau.valeurMain(J1);
        int valeurMainJ2 = jeu.plateau.valeurMain(J2);
        Joueur joueur = jeu.duel();
        if(joueur != null){
            String resultatDuel;
            if (joueur==J1)
            {
                resultatDuel = "vainqueur: "+J1.getNom()+" "+valeurMainJ1+"points / perdant: "+J2.getNom()+" "+valeurMainJ2+"points";
            }
            else
            {
                resultatDuel = "vainqueur: "+J2.getNom()+" "+valeurMainJ2+"points / perdant: "+J1.getNom()+" "+valeurMainJ1+"points";
            }
            infoPlateau = resultatDuel;
            interfaceGraphique.miseAjour();
            //timer.schedule(finTour, 3000);
            timerV2.schedule(finTour, 3000, TimeUnit.MILLISECONDS);

        }
        else{
            infoPlateau = "Egalité une carte va etre tiree au hasard";
            changerEtatJeu(EtatJeu.DUEL_EGALITE);

            int indiceAdversaire = random.nextInt(3);
            int indiceJoueurActif = random.nextInt(3);
            interfaceGraphique.setDuelEgalite(indiceJoueurActif, indiceAdversaire);
            interfaceGraphique.miseAjour();
            joueur = jeu.duelEgalite(indiceJoueurActif, indiceAdversaire);
            if(joueur!= null){
                infoPlateau = joueur.getNom() + " à remporté le duel apres egalité!";
            }
            else{
                infoPlateau = "Egalité le jeu continue !";
            }
            //timer.schedule(finTour, 3000);
            timerV2.schedule(finTour, 2500, TimeUnit.MILLISECONDS);
            if(jeu.partieTerminee())
            {
                changerEtatJeu(EtatJeu.FIN_PARTIE);
                infoPlateau = jeu.nomVainqueur()+ " REMPORTE LA PARTIE !";
            }
        }
    }
}