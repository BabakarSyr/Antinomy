package Modele;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Jeu
{
     Plateau plateau;
    
    
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
    
    public void definir_ordres_joueurs(Plateau plateau, String dejaVuJoueur1, String dejaVuJoueur2)
    {
      if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) 
      {
        Random rand = new Random();
        int num_joueur_actif = rand.nextInt(2)+1 ;  // [1-2]
        plateau.setJoueurActif(num_joueur_actif);  // [1-2]
        if (num_joueur_actif==1)
        {
          plateau.getJoueur(1).sorcier.setSensDuTemps(true);
          plateau.getJoueur(2).sorcier.setSensDuTemps(false);
        }
        else
        {
          plateau.getJoueur(1).sorcier.setSensDuTemps(false);
          plateau.getJoueur(2).sorcier.setSensDuTemps(true);
        }
      }
      else if (dejaVuJoueur1.equals("oui")) 
      {
        int num_joueur_actif = 1;
        plateau.setJoueurActif(num_joueur_actif);
        plateau.getJoueur(1).sorcier.setSensDuTemps(true);
        plateau.getJoueur(2).sorcier.setSensDuTemps(false);
      }
      else 
      {
        int num_joueur_actif = 2;
        plateau.setJoueurActif(num_joueur_actif);
        plateau.getJoueur(1).sorcier.setSensDuTemps(false);
        plateau.getJoueur(2).sorcier.setSensDuTemps(true);
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
            System.out.print("Carte " + j  +"="+ plateau.joueurActif.getMain().get(i).toString() + " ");
            System.out.println();
        }
    }

    ////////

    public boolean estParadoxe() {
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        ArrayList<Carte> cartes = joueurActif().getMain();
    
        boolean memeCouleur = true;
        boolean memeForme = true;
        boolean memeValeur = true;

        Carte carte = cartes.get(0);
        if (carte.getCouleur() == couleurInterdite) {
            return false;
        }
        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getCouleur() == couleurInterdite) {
                return false;
            }
            memeCouleur = memeCouleur&(cartes.get(i).getCouleur() == carte.getCouleur());
            memeForme = memeForme&(cartes.get(i).getForme() == carte.getForme());
            memeValeur = memeValeur&(cartes.get(i).getValeur() == carte.getValeur());
            
        }
        return memeCouleur || memeForme || memeValeur;
    }
    

    public void paradoxe()
    {
      try (Scanner scanner = new Scanner(System.in)) 
      {
        if(estParadoxe())
        {
          System.out.println("Vous avez un paradoxe");
        
          //Afficher les cartes du joueur actif
          afficher_cartes_main();
        
          //Le joueur actif gagne 1 cristal
          plateau.joueurActif.ajouterCristaux(1);
                    
          System.out.println("Récapitulatif :");
          System.out.println("Le joueur "+plateau.joueur1.getNom()+" a en sa possession "+plateau.joueur1.getNombreCristaux()+" cristaux");
          System.out.println("Le joueur "+plateau.joueur2.getNom()+" a en sa possession "+plateau.joueur2.getNombreCristaux()+" cristaux");
                        
          //Le joueur melange les cartes entre ses mains
          plateau.joueurActif.melangerMain();
        
          System.out.print("Vous choississez de mettre vos 3 cartes mélangé a gauche ou a droite de votre baguette magique ?(gauche ou droite) : ");
          String direction = scanner.next().toLowerCase();
          boolean futur = true;
          if(direction == "gauche"){
            futur = false;
          }
          if(direction == "droite"){
            futur = true;
          }
          while(!estPossibleEchangerParadoxe(futur))
          {
            System.out.println("Vous ne pouvez pas placer vos 3 cartes à "+direction+" car il n'y a pas assez de cartes. Choisir la direction opposée :");
            direction = scanner.next().toLowerCase();
          }
          //TODO faire jouer 3 cartes
          //plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
          plateau.codex.changerCouleurInterdite();
          System.out.println("La nouvelle couleur interdite est :"+ (String)plateau.codex.getCouleurInterdite().getCode());
          
          
          System.out.println("Voici le plateau apres votre coup :");
          if(plateau.getJoueurActif().getNom().equals(plateau.getJoueur(1).getNom()))
          {
            plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(1),plateau.getPositionSorcier(1));
          }
          else
          {
            plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(2),plateau.getPositionSorcier(2));
          }
        }
      }
    }

    public boolean partieTerminee() {
        return plateau.joueur1.getNombreCristaux() == 3 || plateau.joueur2.getNombreCristaux() == 3;
    }

    //On a duel si positionSorcierJoueur1 == positionSorcierJoueur2
    public boolean estDuel(){
        return plateau.joueur1.sorcier.getPositionSorcier() == plateau.joueur2.sorcier.getPositionSorcier();
    }

    public void jouerCarte(int indiceCarteMain, int indiceContinuum){
        deplacerSorcier(indiceContinuum);
        echangerCarte(indiceCarteMain, indiceContinuum);
    }

    public boolean estDeplacementPossible(int indiceCarteMain, int indiceContinuum){
        return plateau.cartesAccessibles(joueurActif().getMain().get(indiceCarteMain)).contains(indiceContinuum);
    }

    public boolean estPossibleEchangerParadoxe(int indiceContinuum){
        int sorcier=joueurActif().sorcier.getPositionSorcier();
            if(indiceContinuum>sorcier){
                return sorcier<6;
            }
            if(indiceContinuum<sorcier){
                return sorcier>2;
            }
        return false;
    }
    public boolean estPossibleEchangerParadoxe(boolean futur){
        int sorcier=joueurActif().sorcier.getPositionSorcier();
        if(futur){
            return sorcier<6;
        }
        else{
            return sorcier>2;
        }   
    }

    public void deplacerSorcier(int positionSorcier) {
        joueurActif().sorcier.positionSorcier = positionSorcier;
    }

    //Equivalent echanger carte
    public void echangerCarte(int indiceCarteMain, int indiceContinuum) {//indiceCarteMain=[0-2]
            Joueur joueur = joueurActif();
            Carte carte = joueur.getMain().get(indiceCarteMain);
            joueur.getMain().remove(indiceCarteMain);
        
            // Échange de la carte avec la carte à la position du sorcier dans le continuum
            Carte carteContinuum = continuum().get(indiceContinuum);
            continuum().set(indiceContinuum, carte);
        
            // Ajouter la carte du continuum à la main du joueur
            joueur.getMain().add(indiceCarteMain, carteContinuum);
    }

    // echange les 3 cartes en main avec 3 cartes du plateau suite à un paradoxe
    public void echangerParadoxe(boolean futur) {
        Joueur joueur = joueurActif();
        int indexSorcier = joueur.sorcier.getPositionSorcier();
        int j = 0;
        if(!futur){
            for (int i = indexSorcier - 3; i < indexSorcier; i++) {
                echangerCarte(j, i);
                j++;
            }
        }
        else{
            for (int i = indexSorcier + 1; i < indexSorcier + 4; i++) {
                echangerCarte(j, i);
                j++;
            }
        }
    }

    public Joueur joueurActif(){
        return plateau.getJoueurActif();
    }

    public ArrayList<Carte> continuum(){
        return plateau.getContinuum();
    }

    public Couleur couleurInterdite(){
        return plateau.couleurInterdite();
    }

    public void duel() 
    {
      if (estDuel())
      {
        System.out.println("C'est l'heure du Duel!");
        System.out.println("Rappel,la couleur interdite est :"+ (String)plateau.codex.getCouleurInterdite().getCode());
        int valeurMainJoueur1= 0;
        int valeurMainJoueur2 = 0;


        //Les 2 jouerurs affichent leur main
        System.out.println("La main du joueur 1 est : ");
        for(int i=0;i<3;i++){
           
            System.out.println("Carte "+i+" : "+plateau.joueur1.getMain().get(i).toString());
            
        }
       System.out.println();
        System.out.println("La main du joueur 2 est : ");
        for(int i=0;i<3;i++){
            
            System.out.println("Carte "+i+" : "+plateau.joueur2.getMain().get(i).toString());
        }
        System.out.println();
    


        
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        System.out.println("couleur interdite :" + couleurInterdite);
        for (Carte carte : plateau.joueur1.getMain()) 
        {
            valeurMainJoueur1 += carte.getValeur(couleurInterdite);
            System.out.println("carte : " + carte.getForme() + carte.getCouleur() + carte.getValeur());
        }
    
        for (Carte carte : plateau.joueur2.getMain()) {
            valeurMainJoueur2 += carte.getValeur(couleurInterdite);
            System.out.println("carte : " + carte.getForme() + carte.getCouleur() + carte.getValeur());
        }
        System.out.println("Le total de points du joueur " +plateau.joueur1.getNom()+ " est :" +valeurMainJoueur1);
        System.out.println("Le total de points du joueur " +plateau.joueur2.getNom()+ " est :" +valeurMainJoueur2);


        if (valeurMainJoueur1 > valeurMainJoueur2) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            if(plateau.joueur1.volerCristal(plateau.joueur2)){
                System.out.println(plateau.joueur1.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur2.getNom());
                System.out.println("Récapitulatif des cristaux :");
                System.out.println(plateau.joueur1.getNom() + " : " + plateau.joueur1.getNombreCristaux());
                System.out.println(plateau.joueur2.getNom() + " : " + plateau.joueur2.getNombreCristaux());
                
                plateau.codex.changerCouleurInterdite();
                return;
            }
            else
                System.out.println("Impossible de voler un cristal à " + plateau.joueur1.getNom() + " car il n'en a plus  ou n'en a pas.");
                return;
        }
        if (valeurMainJoueur1 < valeurMainJoueur2) {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            if(plateau.joueur2.volerCristal(plateau.joueur1)){
                System.out.println(plateau.joueur2.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur1.getNom());
                System.out.println("Récapitulatif des cristaux :");
                System.out.println(plateau.joueur1.getNom() + " : " + plateau.joueur1.getNombreCristaux());
                System.out.println(plateau.joueur2.getNom() + " : " + plateau.joueur2.getNombreCristaux());
                
                plateau.codex.changerCouleurInterdite();
                return;
                
            }
            else
                System.out.println("Impossible de voler un cristal à " + plateau.joueur2.getNom() + " car il n'en a plus  ou n'en a pas.");
                return;
        } 
        // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
        Random random = new Random();
            
            
        plateau.joueur1.melangerMain();
        plateau.joueur2.melangerMain();
            
       // Sélectionner une carte au hasard pour chaque joueur
        int indexCarteJoueur1 = random.nextInt(3);
        int indexCarteJoueur2 = random.nextInt(3);

        Carte carteJoueur1 = plateau.joueur1.getMain().get(indexCarteJoueur1);
        System.out.println(plateau.joueur1.getNom() + " tire la carte " + carteJoueur1.toString());
        Carte carteJoueur2 = plateau.joueur2.getMain().get(indexCarteJoueur2);

        System.out.println(plateau.joueur2.getNom() + " tire la carte " + carteJoueur2.toString());
        // On compare  les valeurs des cartes en tenant compte de la couleur interdite
        int valeurCarteJoueur1 = carteJoueur1.getValeur(plateau.codex.getCouleurInterdite());
        int valeurCarteJoueur2 = carteJoueur2.getValeur(plateau.codex.getCouleurInterdite());
            
        if (valeurCarteJoueur1 > valeurCarteJoueur2) {
            plateau.joueur1.volerCristal(plateau.joueur2);
            System.out.println(plateau.joueur1.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur2.getNom());
            
            plateau.codex.changerCouleurInterdite();
            return;
        } else if (valeurCarteJoueur1 < valeurCarteJoueur2) {
            plateau.joueur2.volerCristal(plateau.joueur1);
            System.out.println(plateau.joueur2.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur1.getNom());
            
            plateau.codex.changerCouleurInterdite();
            return;
        } else {
            System.out.println("Égalité!");
            return;
        }
    }
  }
}
