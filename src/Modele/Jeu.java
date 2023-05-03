package Modele;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import Modele.Carte.Couleur;


public class Jeu {
     Plateau plateau;
    
    
    public Jeu(Plateau plateau) {
        this.plateau =plateau; 
       
    }
    ////////
    public Plateau getPlateau()
    {
        return this.plateau;
    }
    ////////

    public boolean est_Possible_Placer_3cartes(String Direction) {
        int posSorcier=plateau.joueurActif.sorcier.getPositionSorcier();
        boolean possible=false;
        int nbCarte=0;
        switch(Direction) {
            case "gauche":
                for(int i=posSorcier-1;i>=0;i--){
                    nbCarte++;
                }
                break;
                
            case "droite":
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
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        ArrayList<Carte> cartes = mainJoueur.getCartes();
    
        boolean memeCouleur = true;
        boolean memeForme = true;
        boolean memeValeur = true;
        boolean differentCouleurInterdite = true;
    
        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getForme() != cartes.get(0).getForme()) {
                memeForme = false;
                break;
            }
        }
    
        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getValeur() != cartes.get(0).getValeur()) {
                memeValeur = false;
                break;
            }
        }
    
        for (Carte carte : cartes) {
            if (carte.getCouleur() == couleurInterdite) {
                differentCouleurInterdite = false;
                break;
            }
        }

        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getCouleur() != cartes.get(0).getCouleur()) {
                memeCouleur = false;
                break;
            }
        }
    

        // Il y a un paradoxe si toutes les cartes ont la même couleur ou la même forme ou la même valeur
        // et toutes les cartes sont de couleur différente de la couleur interdite
        return (memeCouleur || memeForme || memeValeur) && differentCouleurInterdite;
    }
    

    public void paradoxe(){
      

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Voulez-vous échanger vos cartes en main avec les cartes à gauche ou à droite du sorcier ? (Entrez gauche ou droite)");

            String direction = scanner.next().toLowerCase();
            while(!est_Possible_Placer_3cartes(direction)){
                System.out.println("Vous ne pouvez pas placer vos 3 cartes à "+direction+" car il n'y a pas assez de cartes. Choisissez une autre direction : gauche / droite");

                direction = scanner.next().toLowerCase();
            }

    
            switch(direction){
                case "gauche":
                    plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
                    break;
                case "droite":
                    plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
                
                    break;
            }
        }
       
  
    
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
        System.out.println("C'est l'heure du Duel!");
        System.out.println("Rappel,la couleur interdite est :"+ (String)plateau.codex.getCouleurInterdite().getCode());
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
        System.out.println();
    


        
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
        System.out.println("Le total de points du joueur " +plateau.joueur1.getNom()+ " est :" +totalJoueur1);
        System.out.println("Le total de points du joueur " +plateau.joueur2.getNom()+ " est :" +totalJoueur2);


        if (totalJoueur1 > totalJoueur2) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            if(plateau.joueur1.volerCristal(plateau.joueur2)){
                System.out.println(plateau.joueur1.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur2.getNom());
                System.out.println("Récapitulatif des cristaux :");
                System.out.println(plateau.joueur1.getNom() + " : " + plateau.joueur1.getNombreCristaux());
                System.out.println(plateau.joueur2.getNom() + " : " + plateau.joueur2.getNombreCristaux());
                return;
            }
            else
                System.out.println("Impossible de voler un cristal à " + plateau.joueur1.getNom() + " car il n'en a plus  ou n'en a pas.");
        } else if (totalJoueur1 < totalJoueur2) {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            if(plateau.joueur2.volerCristal(plateau.joueur1)){
                System.out.println(plateau.joueur2.getNom() + " gagne le duel et vole un cristal à " + plateau.joueur1.getNom());
                System.out.println("Récapitulatif des cristaux :");
                System.out.println(plateau.joueur1.getNom() + " : " + plateau.joueur1.getNombreCristaux());
                System.out.println(plateau.joueur2.getNom() + " : " + plateau.joueur2.getNombreCristaux());
                return;
                
            }
            else
                System.out.println("Impossible de voler un cristal à " + plateau.joueur2.getNom() + " car il n'en a plus  ou n'en a pas.");
                return;
        } else {
            // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
            Random random = new Random();
            boolean egalite = true;
            
            
            plateau.joueur1.getMain().melangerCartes();
            plateau.joueur2.getMain().melangerCartes();
            
            while (egalite) {
               // Sélectionner une carte au hasard pour chaque joueur
                int indexCarteJoueur1 = random.nextInt(3);
                int indexCarteJoueur2 = random.nextInt(3);

                Carte carteJoueur1 = plateau.joueur1.getMain().getCarte(indexCarteJoueur1);
                System.out.println(plateau.joueur1.getNom() + " tire la carte " + carteJoueur1.toString());
                Carte carteJoueur2 = plateau.joueur2.getMain().getCarte(indexCarteJoueur2);
                System.out.println(plateau.joueur2.getNom() + " tire la carte " + carteJoueur2.toString());

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
