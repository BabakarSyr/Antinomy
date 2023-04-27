package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class PlateauDeJeu {
     int joueurActif;

     List<Carte> cartesReliques=new ArrayList<>();
    List<Carte> cartesSorciers= new ArrayList<>();
     List<Carte> continuum= new ArrayList<>();

     Sorcier sorcierJoueur1;
        Sorcier sorcierJoueur2;
    // Carte codex;
     int positionSorcierJoueur1;
     int positionSorcierJoueur2;
    int positionCodex;
     
    MainDeCartes mainJoueur1;
    MainDeCartes mainJoueur2;
  
   
        
    




    public PlateauDeJeu() {
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.VERT,1));
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.VIOLET,2));
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.BLEU,3));
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.ROUGE,4));

        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.VIOLET,1));
        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.BLEU,2));
        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.ROUGE,3));
        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.VERT,4));

        cartesReliques.add(new Carte(Forme.CLE, Couleur.ROUGE,1));
        cartesReliques.add(new Carte(Forme.CLE, Couleur.VERT,2));
        cartesReliques.add(new Carte(Forme.CLE, Couleur.VIOLET,3));
        cartesReliques.add(new Carte(Forme.CLE, Couleur.BLEU,4));

        cartesReliques.add(new Carte(Forme.CRANE, Couleur.BLEU,1));
        cartesReliques.add(new Carte(Forme.CRANE, Couleur.ROUGE,2));
        cartesReliques.add(new Carte(Forme.CRANE, Couleur.VERT,3));
        cartesReliques.add(new Carte(Forme.CRANE, Couleur.VIOLET,4));
        

        // Mélanger les cartes Reliques
        Collections.shuffle(cartesReliques);

        



         /**Couleur interdite au debut du jeu
          ** On place un cristal sur la couleur du Codex correspondant à la couleur de la carte la plus à gauche du continuum. 
          **Ce sera la couleur interdite.
          */
          Couleur couleurInterdite = cartesReliques.get(0).getCouleur();
         // Créer les cartes Sorcier pour chaque joueur
        
         sorcierJoueur1 = new Sorcier( true);
         sorcierJoueur2 = new Sorcier(false);
      



          try (// Lire l'entrée de l'utilisateur
             Scanner scanner = new Scanner(System.in)) {
            // Demander aux joueurs s'ils ont ressenti du déjà-vu récemment
              System.out.println("Joueur 1, avez-vous ressenti du déjà-vu récemment ? (Entrez oui ou non)");
              String dejaVuJoueur1 = scanner.next();//toLowerCase 
  
              System.out.println("Joueur 2, avez-vous ressenti du déjà-vu récemment ? (Entrez oui ou non)");

              String dejaVuJoueur2 = scanner.next();
  
              // Si aucun ou les 2 joueur(s) n'a(ont) ressenti du déjà-vu, on choisi le joueur actif au hasard
              if (  (dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) ||(dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui")) ){
                  Random rand = new Random();
                  joueurActif = rand.nextInt(2) + 1; // [1-2]
              } else if (!dejaVuJoueur1.equals("non")) {
                  joueurActif = 1;
              } else {
                  joueurActif = 2;
              }
        }

           
        // sa main et on met les 2 cartes sorciers dans le plateau et la derniere cartes relique restante sera le codex

        // Distribuer les cartes RElique aux joueurs
        for(int i = 0; i < 3; i++) {
          
            mainJoueur1.ajouterCarte(cartesReliques.get(0));
            cartesReliques.remove(0);
            mainJoueur2.ajouterCarte(cartesReliques.get(0));
            cartesReliques.remove(0);
        }
       
        //Ajouter toutes les cartes dans carte_sur_plateau sauf la derniere carte relique qui sera le codex
        for(int i = 0; i < cartesReliques.size()-1; i++) {
            continuum.add(cartesReliques.get(i));
            cartesReliques.remove(i);
        }





        //Ajouter la derniere carte relique qui sera le codex.Il ne reste normalement qu'une seule carte relique dans cartesReliques,donc cette cartes sera le codex
       // codex=cartesReliques.get(0);

        //Supprimer cartesReliques
        //cartesReliques=null;












         // Placer les cartes sorciers sur la couleur interdite
         for (int i = 0; i < continuum.size(); i++) {
            if (continuum.get(i).getCouleur() == couleurInterdite) {
                positionSorcierJoueur1 = i;
                break;
            }
        }

        for (int i = continuum.size() - 1; i >= 0; i--) {
            if (continuum.get(i).getCouleur() == couleurInterdite) {
                positionSorcierJoueur2 = i;
                break;
            }
        }












    }

    public boolean est_possible_aller_futur(Carte carteChoisie) {
        int valeurCarte = carteChoisie.getValeur();
        Sorcier sorcierActif = (joueurActif == 1) ? sorcierJoueur1 : sorcierJoueur2;
        int positionSorcier = (joueurActif == 1) ? positionSorcierJoueur1 : positionSorcierJoueur2;
        
        //Si le futur du sorcier est a droite
        if (sorcierActif.getSensDuTemps()) 
            return (positionSorcier + valeurCarte < continuum.size());
        
        // le futur est a gauche
        else 
            return (positionSorcier - valeurCarte >= 0);
        
    }

    public boolean est_possible_aller_passe(Carte carteChoisie) {
        Sorcier sorcierActif = (joueurActif == 1) ? sorcierJoueur1 : sorcierJoueur2;
        int positionSorcier = (joueurActif == 1) ? positionSorcierJoueur1 : positionSorcierJoueur2;
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();
    
        //Si le passé du sorcier est à gauche
        if (sorcierActif.getSensDuTemps()) {
            for (int i = positionSorcier - 1; i >= 0; i--) {
                if( continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) 
                    return true;       
            }
        } 
        //Le passé du sorcier est à droite
        else {
            for (int i = positionSorcier + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) 
                    return true;
                
            }
        }
    
        return false;
    }
    
    
    public void deplacerFutur(Carte carteChoisie) {
        if (!est_possible_aller_futur(carteChoisie)) {
            System.out.println("Impossible de se déplacer vers le futur avec cette carte");
            return;
        }
        
        int valeurCarte = carteChoisie.getValeur();
        Sorcier sorcierActif = (joueurActif == 1) ? sorcierJoueur1 : sorcierJoueur2;
        
        if (sorcierActif.getSensDuTemps()) {
            // Si le futur du sorcier est à droite
            positionSorcierJoueur1 += valeurCarte;
        } else {
            // Pour ce sorcier, le futur est à gauche
            positionSorcierJoueur1 -= valeurCarte;
        }
    }

    
    
    
    public void deplacerPasse(Carte carteChoisie) {
        if (!est_possible_aller_passe(carteChoisie)) {
            System.out.println("Impossible de se déplacer vers le passé avec cette carte");
            return;
        }
    
        Sorcier sorcierActif = (joueurActif == 1) ? sorcierJoueur1 : sorcierJoueur2;
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();
        
        if (sorcierActif.getSensDuTemps()) {
            // Si le passé du sorcier est à gauche
            for (int i = positionSorcierJoueur1 - 1; i >= 0; i--) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) {
                    positionSorcierJoueur1 = i;
                    break;
                }
            }
        } else {
            // Pour ce sorcier, le passé est à droite
            for (int i = positionSorcierJoueur1 + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) {
                    positionSorcierJoueur1 = i;
                    break;
                }
            }
        }
    }
    
    

    public int getJoueurActif() {
        return joueurActif;
    }
    public int getCodexPosition() {
        return positionCodex;
    }
    
    

}
