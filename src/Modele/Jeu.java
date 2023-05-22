package Modele;

import java.util.ArrayList;
import java.util.Random;






public class Jeu
{
    public Plateau plateau;
    
    
    public Jeu()
    {
		this.plateau = new Plateau();
    }

    public Jeu(Plateau p)
    {
        this.plateau = p;
    }

    ////////
    public Plateau plateau()
    {
        return this.plateau;
    }

    public void definirJoueur1(Joueur j)
    {
        if (j==plateau.joueur1)
        {
            plateau.joueur1.setSensDuTemps(true);
            plateau.joueur2.setSensDuTemps(false);
            plateau.joueurActif=1;
        }
        else
        {
            plateau.joueur1.setSensDuTemps(false);
            plateau.joueur2.setSensDuTemps(true);
            plateau.joueurActif=2;
        }
    }
    
    public void definirOrdresJoueurs(String dejaVuJoueur1, String dejaVuJoueur2)
    {
      if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) 
      {
        Random rand = new Random();
        int num_joueur_actif = rand.nextInt(2)+1 ;  // [1-2]
        plateau.setJoueurActif(num_joueur_actif);  // [1-2]
        if (num_joueur_actif==1)
        {
          plateau.getJoueur(1).setSensDuTemps(true);
          plateau.getJoueur(2).setSensDuTemps(false);
        }
        else
        {
          plateau.getJoueur(1).setSensDuTemps(false);
          plateau.getJoueur(2).setSensDuTemps(true);
        }
      }
      else if (dejaVuJoueur1.equals("oui")) 
      {
        int num_joueur_actif = 1;
        plateau.setJoueurActif(num_joueur_actif);
        plateau.getJoueur(1).setSensDuTemps(true);
        plateau.getJoueur(2).setSensDuTemps(false);
      }
      else 
      {
        int num_joueur_actif = 2;
        plateau.setJoueurActif(num_joueur_actif);
        plateau.getJoueur(1).setSensDuTemps(false);
        plateau.getJoueur(2).setSensDuTemps(true);
      }
    }

    public ArrayList<Integer>positionsDepart(){
        return plateau.positionsDepart();
    }

    public void afficher_cartes_main()
    {
        for (int i =0 ;i < 3; i++) 
        {
            int j=i+1;
            System.out.print("Carte " + j  +"="+ plateau.joueurActif().getMain().get(i).toString() + " ");
            System.out.println();
        }
    }

    ////////
    public boolean estParadoxe() {
        return plateau.estParadoxe();
    }

    public boolean estParadoxe(int indiceContinuum, int indiceMain) {
        return plateau.estParadoxe(indiceContinuum, indiceMain);
    }

    public void paradoxe(int indiceCarteContinuum)
    {
        if(indiceCarteContinuum>joueurActif().getPositionSorcier()){
            echangerParadoxe(true);
        }
        else{
            echangerParadoxe(false);
        }
        joueurActif().ajouterCristal(1);
        plateau.codex.changerCouleurInterdite();
    }

    public boolean partieTerminee() {
        return plateau.joueur1.getNombreCristaux() == 5 || plateau.joueur2.getNombreCristaux() == 5;
    }

    public String nomVainqueur()
    {
        if (partieTerminee() && plateau.joueur1.getNombreCristaux() == 5)
        {
            return plateau.joueur1.getNom();
        }
        else if (partieTerminee() && plateau.joueur2.getNombreCristaux() == 5)
        {
            return plateau.joueur2.getNom();
        }
        else
        {
            return null;
        }
    }

    //On a duel si positionSorcierJoueur1 == positionSorcierJoueur2
    public boolean estDuel(){
        return plateau.joueur1.getPositionSorcier() == plateau.joueur2.getPositionSorcier();
    }

    public void jouerCarte(int indiceCarteMain, int indiceContinuum){
        deplacerSorcier(indiceContinuum);
        echangerCarte(indiceCarteMain, indiceContinuum);
    }

    public boolean estDeplacementPossible(int indiceCarteMain, int indiceContinuum){
        return plateau.cartesAccessibles(joueurActif().getMain().get(indiceCarteMain)).contains(indiceContinuum);
    }




    //Stocker toutes les positions des cartes ou il est possible de placer son paradoxe
    public ArrayList<Integer> positionsParadoxe(){
        ArrayList<Integer> positionsParadoxe=new ArrayList<Integer>();
       
            if(estPossibleEchangerParadoxe(true)){
                positionsParadoxe.add(joueurActif().getPositionSorcier()+1);
                positionsParadoxe.add(joueurActif().getPositionSorcier()+2);
                positionsParadoxe.add(joueurActif().getPositionSorcier()+3);
            }
            if(estPossibleEchangerParadoxe(false)){
                positionsParadoxe.add(joueurActif().getPositionSorcier()-1);
                positionsParadoxe.add(joueurActif().getPositionSorcier()-2);
                positionsParadoxe.add(joueurActif().getPositionSorcier()-3);
            }
        return positionsParadoxe;
  
    }
    public boolean estPossibleEchangerParadoxe(int indiceContinuum){
        int sorcier=joueurActif().getPositionSorcier();
            if(indiceContinuum>sorcier){
                return sorcier<6;
            }
            if(indiceContinuum<sorcier){
                return sorcier>2;
            }
        return false;
    }
    public boolean estPossibleEchangerParadoxe(boolean futur){
        return plateau.estPossibleEchangerParadoxe(futur);
    }

    public void deplacerSorcier(int positionSorcier) {
        joueurActif().setPositionSorcier(positionSorcier);
    }

    //Equivalent echanger carte
    public void echangerCarte(int indiceCarteMain, int indiceContinuum) {//indiceCarteMain=[0-2]
        plateau.echangerCarte(indiceCarteMain, indiceContinuum);
    }

    // echange les 3 cartes en main avec 3 cartes du plateau suite à un paradoxe
    public void echangerParadoxe(boolean futur) {
        plateau.echangerParadoxe(futur);
    }

    public Joueur joueurActif(){
        return plateau.joueurActif();
    }

    public ArrayList<Carte> continuum(){
        return plateau.getContinuum();
    }

    public Couleur couleurInterdite(){
        return plateau.couleurInterdite();
    }

    public Joueur meilleurMain()
    {
        int valeurMainJoueur1 = plateau().valeurMain(plateau.joueurActif());
        int valeurMainJoueur2 = plateau().valeurMain(plateau.joueurInactif());
        
        if (valeurMainJoueur1 > valeurMainJoueur2) 
        {
            return plateau.joueurActif();
        }
        else if (valeurMainJoueur1 < valeurMainJoueur2)
        {
            return plateau.joueurInactif();
        }
        return null;
    }

    public Joueur duel() 
    {
        Joueur j1 = plateau.joueurActif();
        Joueur j2 = plateau.joueurInactif();

        Joueur gagnantDuel = meilleurMain();
        if (gagnantDuel==j1) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            if(j1.volerCristal(j2)){
                System.out.println(j1.getNom() + " gagne le duel et vole un cristal à " + j2.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j1.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return gagnantDuel;

        }
        if (gagnantDuel==j2) 
        {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            if(j2.volerCristal(j1))
            {
                System.out.println(j2.getNom() + " gagne le duel et vole un cristal à " + j1.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j2.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return gagnantDuel;
        }
        return null;
    }

    public Joueur duelEgalite()
    {
        Joueur j1 = plateau.joueurActif();
        Joueur j2 = plateau.joueurInactif();
        // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
                        
        j1.melangerMain();
        j2.melangerMain();

        Carte carteJoueur1 = j1.getMain().get(0);
        Carte carteJoueur2 = j2.getMain().get(0);

        // On compare  les valeurs des cartes en tenant compte de la couleur interdite
        int valeurCarteJoueur1 = carteJoueur1.getValeur(plateau.codex.getCouleurInterdite());
        int valeurCarteJoueur2 = carteJoueur2.getValeur(plateau.codex.getCouleurInterdite());
                
        if (valeurCarteJoueur1 > valeurCarteJoueur2) 
        {
            if(j1.volerCristal(j2))
            {
                System.out.println(j1.getNom() + " gagne le duel et vole un cristal à " + j2.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j1.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j1;
        }
        if (valeurCarteJoueur1 < valeurCarteJoueur2) 
        {
            if(j2.volerCristal(j1))
            {
                System.out.println(j2.getNom() + " gagne le duel et vole un cristal à " + j1.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j2.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j2;
        }
        System.out.println("Égalité!");
        return null;
    }


    public Joueur duelEgalite(int indiceCarteJ1, int indicieCarteJ2)
    {
        Joueur j1 = plateau.joueurActif();
        Joueur j2 = plateau.joueurInactif();
        // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
                        
        j1.melangerMain();
        j2.melangerMain();

        Carte carteJoueur1 = j1.getMain().get(indiceCarteJ1);
        Carte carteJoueur2 = j2.getMain().get(indicieCarteJ2);

        // On compare  les valeurs des cartes en tenant compte de la couleur interdite
        int valeurCarteJoueur1 = carteJoueur1.getValeur(plateau.codex.getCouleurInterdite());
        int valeurCarteJoueur2 = carteJoueur2.getValeur(plateau.codex.getCouleurInterdite());
                
        if (valeurCarteJoueur1 > valeurCarteJoueur2) 
        {
            if(j1.volerCristal(j2))
            {
                System.out.println(j1.getNom() + " gagne le duel et vole un cristal à " + j2.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j1.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j1;
        }
        if (valeurCarteJoueur1 < valeurCarteJoueur2) 
        {
            if(j2.volerCristal(j1))
            {
                System.out.println(j2.getNom() + " gagne le duel et vole un cristal à " + j1.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j2.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j2;
        }
        System.out.println("Égalité!");
        return null;
    }

    public void jouerCoup(Coup coup){
        plateau.jouerCoup(coup);
    }

    public Coup creerCoup(Plateau plateau){
        return plateau.creerCoup(plateau);
    }

    public boolean peutAnnuler() {
		return plateau.peutAnnuler();
	}

	public boolean peutRefaire() {
		return plateau.peutRefaire();
	}

	public Coup annuler() {
		Coup coup = plateau.annuler();
		return coup;
	}

	public Coup refaire() {
		Coup coup = plateau.refaire();
		return coup;
	}
    
}

