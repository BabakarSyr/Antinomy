package Modele;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Partie {
     Plateau plateau;
    
    
     
     
    public Partie(Plateau plateau) {
        this.plateau =plateau; 
       
    }


    public boolean est_Possible_Placer_3cartes(String Direction) {
        int posSorcier=plateau.joueurActif.sorcier.getPositionSorcier();
        boolean possible=false;
        int nbCarte=0;
        switch(Direction) {
            case "Gauche":
                for(int i=posSorcier-1;i>=0;i--){
                    nbCarte++;
                }
                break;
                
            case "Droit":
                for(int i=posSorcier+1;i<plateau.continuum.size();i++){
                    nbCarte++;
                }
                break;
        }
        if(nbCarte>=3)
            possible= true;
        return possible;
    }
    
    public boolean isParadoxe(MainDeCartes mainJoueur) {
        Couleur couleurInterdite =plateau.codex.getCouleurInterdite();
        ArrayList<Carte> cartes = mainJoueur.getCartes();
        
        Couleur couleurPremiereCarte = cartes.get(0).getCouleur();
        Forme formePremiereCarte = cartes.get(0).getForme();
        int valeurPremiereCarte = cartes.get(0).getValeur();
    
        //carte.size=3 toujours
        for (int i = 1; i < cartes.size(); i++) {
            Carte carte = cartes.get(i);
            if (carte.getCouleur() != couleurPremiereCarte) {
               return false;
            }
            if (carte.getForme() != formePremiereCarte) {
                return false;
            }
            if (carte.getValeur() != valeurPremiereCarte) {
               return false;
            }
            if (carte.getCouleur() == couleurInterdite) {
                return false;
            }
        }
    
    
        
    
        // Il y a un paradoxe si toutes les cartes ont la même couleur ou  la même forme ou  la même valeur
        // et toutes les cartes sont de couleur différente de la couleur interdite
        return true;
    }
    

    public void jouer(int indexCarteChoisie,String temps) {
    
        Carte carteChoisie = plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
        
        switch(temps){
            case "futur":
                // On effectue l'action correspondante à la carte choisie
                    if( plateau.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, plateau.continuum)){
                        plateau.joueurActif.sorcier.deplacerFutur(carteChoisie, plateau.continuum);
                        plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.continuum);
                    }
                    else{
                        System.out.println("Vous ne pouvez pas aller dans le futur avec cette carte");
                    }
                    
                break;
            case "passe": 
            if(plateau.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, plateau.continuum)) {  
                plateau.joueurActif.sorcier.deplacerPasse(carteChoisie, plateau.continuum);
                plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.continuum);
            }
            else{
                System.out.println("Vous ne pouvez pas aller dans le passe avec cette carte");
            }
            break;
            
        }
    
        // Vérifier si le joueur actif a un paradoxe
        if(isParadoxe(plateau.joueurActif.getMain())){
            System.out.println("Vous avez un paradoxe");

            //Le joeur actif gagne 1 cristal
            plateau.joueurActif.ajouterCristaux(1);
           
            //Le joueur melange les cartes entre ses mains
            plateau.joueurActif.main.melangerCartes();

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Voulez-vous échanger vos cartes en main avec les cartes à gauche ou à droite du sorcier ? (Entrez gauche ou droite)");
      
                String direction = scanner.next().toLowerCase();
                while(!est_Possible_Placer_3cartes(direction)){
                    System.out.println("Vous ne pouvez pas placer vos 3 cartes à "+direction+" car il n'y a pas assez de cartes. Choisissez une autre direction : gauche / droite");
   
                    direction = scanner.next().toLowerCase();
                }

         
                switch(direction){
                    case "gauche":
                        plateau.joueurActif.jouer3Cartes(plateau.continuum, direction);
                        break;
                    case "droite":
                        plateau.joueurActif.jouer3Cartes(plateau.continuum, direction);
                    
                        break;
                }
            }
            plateau.codex.changerCouleurInterdite();
          
            
        }

        if(isDuel())
            duel();
        
        // Une fois que le joueur a terminé son tour, on change de joueur actif au niveau du plateau
        plateau.changerJoueurActif();
        
    }
    
    
    public boolean partieTerminee() {
        return plateau.joueur1.getNombreCristaux() == 5 || plateau.joueur2.getNombreCristaux() == 5;
    }

    
    //On a duel si positionSorcierJoueur1 == positionSorcierJoueur2
    public boolean isDuel(){
        int positionSorcierJoueur1 = plateau.joueur1.sorcier.getPositionSorcier();
        int positionSorcierJoueur2 = plateau.joueur2.sorcier.getPositionSorcier();
        return positionSorcierJoueur1 == positionSorcierJoueur2;
    }


    public void duel() {

        int totalJoueur1= plateau.joueur1.getNombreCristaux();
        int totalJoueur2 = plateau.joueur2.getNombreCristaux();


        //Les 2 jouerurs affichent leur main
        System.out.println("La main du joueur 1 est : ");
        for(int i=0;i<3;i++){
           
            System.out.println("Carte "+i+" : "+plateau.joueur1.getMain().getCartes().get(i).toString());
            
        }
       System.out.println();
        System.out.println("La main du joueur 2 est : ");
        for(int i=0;i<3;i++){
            
            System.out.println("Carte "+i+" : "+plateau.joueur2.getMain().getCartes().get(i).toString());
        }




        
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        for (Carte carte : plateau.joueur1.getMain().getCartes()) {
            if (carte.getCouleur() !=couleurInterdite) {
                totalJoueur1 += carte.getValeur(couleurInterdite);
            }
        }
    
        for (Carte carte : plateau.joueur2.getMain().getCartes()) {
            if (carte.getCouleur() != couleurInterdite) {
                totalJoueur2 += carte.getValeur(couleurInterdite);
            }
        }
    
        if (totalJoueur1 > totalJoueur2) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            plateau.joueur1.volerCristal(plateau.joueur2);
            System.out.println(plateau.joueur1.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur2.getNom());
        } else if (totalJoueur1 < totalJoueur2) {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            if(plateau.joueur2.volerCristal(plateau.joueur1))
                System.out.println(plateau.joueur2.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur1.getNom());
            else
                System.out.println("Impossible de voler un cristal à " + plateau.joueur2.getNom() + " car il n'en a plus  ou n'en a pas.");
        } else {
            // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
            Random random = new Random();
            boolean egalite = (totalJoueur1 == totalJoueur2);
            
            
            plateau.joueur1.getMain().melangerCartes();
            plateau.joueur1.getMain().melangerCartes();
            
            while (egalite) {
               // Sélectionner une carte au hasard pour chaque joueur
                int indexCarteJoueur1 = random.nextInt(3);
                int indexCarteJoueur2 = random.nextInt(3);

                Carte carteJoueur1 = plateau.joueur1.getMain().getCarte(indexCarteJoueur1);
                Carte carteJoueur2 = plateau.joueur2.getMain().getCarte(indexCarteJoueur2);

                // On compare  les valeurs des cartes en tenant compte de la couleur interdite
                int valeurCarteJoueur1 = carteJoueur1.getValeur(plateau.codex.getCouleurInterdite());
                int valeurCarteJoueur2 = carteJoueur2.getValeur(plateau.codex.getCouleurInterdite());
            
                if (valeurCarteJoueur1 > valeurCarteJoueur2) {
                    plateau.joueur1.volerCristal(plateau.joueur2);
                    System.out.println(plateau.joueur1.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur2.getNom());
                    egalite = false;
                } else if (valeurCarteJoueur1 < valeurCarteJoueur2) {
                    plateau.joueur2.volerCristal(plateau.joueur1);
                    System.out.println(plateau.joueur2.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur1.getNom());
                    egalite = false;
                } else {
                    System.out.println("Égalité à nouveau ! Les joueurs tirent de nouvelles cartes.");
                    egalite = true;
                }
            }
        }
        
    }

}
