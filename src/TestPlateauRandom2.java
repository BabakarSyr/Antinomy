
import Modele.Carte;
import Modele.Jeu;
import Modele.Plateau;

import java.util.List;
import java.util.Random;

public class TestPlateauRandom2{
    public static void main(String[] args) {
        int num_joueur_actif ;
        Random rand = new Random();

        Jeu jeu = new Jeu();

        Plateau plateau = jeu.plateau();
        System.out.println("Bienvenue dans le jeu de l'antinomy !");
        System.out.println();

            
            //le joueur1 est en rose
            String nomJoueur1 = "\033[95m\033[7mIA_aleatoire_1\033[0m";
            System.out.println();
            
           
            //le joueur2 est en orange
            String nomJoueur2 = "\033[38;5;208m\033[7mIA_aleatoire_2\033[0m";
            System.out.println();
           

                    
            plateau.getJoueur(1).setNom(nomJoueur1);
            plateau.getJoueur(2).setNom(nomJoueur2);

          
            System.out.println(plateau.joueur1.getNom()+",avez-vous ressenti du déjà-vu récemment ? : "); 
            System.out.print("Entrez oui ou non : ");
            String dejaVuJoueur1 = rand.nextBoolean() ? "oui" : "non";
            System.out.println();

            System.out.println(plateau.joueur2.getNom()+",avez-vous ressenti du déjà-vu récemment ? : "); 
            System.out.print("Entrez oui ou non : ");

         
            String dejaVuJoueur2 = rand.nextBoolean() ? "oui" : "non";

            if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) {
               
                num_joueur_actif = rand.nextInt(2)+1 ;  // [1-2]
                plateau.setJoueurActif(num_joueur_actif );  // [1-2]
            }
            else if (dejaVuJoueur1.equals("oui")) {
                num_joueur_actif = 1;
            
                plateau.setJoueurActif(num_joueur_actif);
            }
           else {
                num_joueur_actif = 2;
                plateau.setJoueurActif(num_joueur_actif);
           }
           int num_joueur_inactif =plateau.getJoueurActif() == plateau.getJoueur(1) ? 2 : 1;




            System.out.println("Le joueur " + plateau.getJoueurActif().getNom() + " commence la partie !");
            System.out.println();

            System.out.println("Voici le plateau de jeu :");
            plateau.afficherContinuum();
            System.out.println();
    
            
            
            System.out.println("La  couleur interdite indiqué par le codex pour ce tour est:"+(String)plateau.codex.getCouleurInterdite().getCode() );
            System.out.println();
    
    
            
            
    
            List<Integer> possible =  plateau.positionsDepartPossible() ;
            System.out.println("A quel emplacement sur le plateau veux tu placer ta baguette magique ?");
            System.out.println("Les indices des cartes portant la couleur interdite sont :");
            for(int i=0;i< possible.size();i++){
                System.out.print(possible.get(i));
                System.out.println();
            }
            
          
           
           // Lecture de la carte à jouer
          System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
           for (int i =0 ;i < 3; i++) {
               int j=i+1;
               System.out.print("Carte " + j  +"="+ plateau.joueurActif.getMain().getCartes().get(i).toString() + " ");
               System.out.println();
           }
           System.out.print("\nTu dois choisir de le placer au niveau d'une carte sur le continuum portant la couleur interdite : ");
    
          
           
            //les positions possibles sont dans la liste possible.On choisit une position au hasard
            int pos = possible.get(rand.nextInt(possible.size()));
            jeu.deplacerSorcier(pos);
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_actif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_actif));
           plateau.getJoueur(num_joueur_actif).sorcier.getSensDuTemps();


            System.out.println();
            

            System.out.println(plateau.getJoueur(num_joueur_inactif).getNom());
            // Lecture de la carte à jouer
          System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
          for (int i =0 ;i < 3; i++) {
              int j=i+1;
              System.out.print("Carte " + j  +"="+ plateau.getJoueur(num_joueur_inactif).getMain().getCartes().get(i).toString() + " ");
              System.out.println();
          }
            System.out.print("A toi de placer ton sorcier : ");

            //les positions possibles sont dans la liste possible.On choisit une position au hasard
            int pos2 = possible.get(rand.nextInt(possible.size()));
            
          
            plateau.changerJoueurActif();
            jeu.deplacerSorcier(pos2);
            plateau.changerJoueurActif();
            
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_inactif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_inactif));
                
            plateau.setTempsSorcier(num_joueur_inactif);
    



            //Afficher le plateau et colorer position sorcier
            plateau.afficher_colorSorcier_continuum(pos,pos2);
    
    








            System.out.println("Que la partie commence:");
    
                
            System.out.println("\nTest jouer");
            int i=0;
            //TODO decommenter et modifier
            //Tant que la partie n'est pas termin
            /* 
            while( !jeu.partieTerminee() ){
    
                if(i>=1){
                    System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
                    for (int j =0 ;j < 3; j++) {
                        int k=j+1;
                        System.out.print("Carte " + k  +"="+ plateau.joueurActif.getMain().getCartes().get(j).toString() + " ");
                        System.out.println();
                    }
                }
                System.out.println("Rappel : La  couleur interdite indiqué par le codex pour ce tour est:"+(String)plateau.codex.getCouleurInterdite().getCode() );
                System.out.println("la position de ton sorcier joueur "+ plateau.getJoueurActif().getNom() + " est :"+plateau.getPositionSorcier(num_joueur_actif));
                System.out.println();

               
              
                int indexCarteChoisie = rand.nextInt(3); // Choisir une carte au hasard
                Carte carteChoisie = plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
               

                boolean actionReussie = false;
                do {
                    System.out.print("Choisissez le temps (futur ou passe) : ");
                    String temps = rand.nextBoolean() ? "futur" : "passe"; 
                    switch (temps) {
                        case "futur":
                            if (plateau.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, plateau.getContinuum())) {
                                plateau.joueurActif.sorcier.deplacerFutur(carteChoisie, plateau.getContinuum());
                                plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.getContinuum());
                                actionReussie = true; // L'action a réussi, on peut sortir de la boucle
                            }
                            else {
                                System.out.println("Vous ne pouvez pas aller dans le futur .Vous ne pouvez aller que dans le passe avec cette carte");
                                
                                indexCarteChoisie = rand.nextInt(3);
                                carteChoisie =plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
                            
                            }
                        
                            break;
                        case "passe":
                            if (plateau.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, plateau.getContinuum())) {
                                List<Integer> positionsPossibles = plateau.joueurActif.sorcier.Position_Possible_Passe(carteChoisie, plateau.getContinuum());
                               
                                int position = positionsPossibles.get(rand.nextInt(positionsPossibles.size()));
                                //mettre à jour la position du sorcier
                                //plateau.joueurActif.sorcier.setPositionSorcier(position);
                                //jouer la carte
                                plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.getContinuum());
                                actionReussie = true; // L'action a réussi, on peut sortir de la boucle
                            } 
                            else {
                                System.out.println("Vous ne pouvez pas aller dans le passe .Vous ne pouvez aller que dans le futur avec cette carte");
                               
                                indexCarteChoisie = rand.nextInt(3);
                                carteChoisie =plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
                            
                            }
                            break;
                        default:
                            System.out.println("Saisie incorrecte. Veuillez saisir futur ou passe");
                    }
                } while (!actionReussie);


                
                    
                
                //Si le nom du joeuur actif est le meme que celui du joueur 1
                System.out.println("Voici le plateau apres votre coup :");
                if(plateau.getJoueurActif().getNom().equals(plateau.getJoueur(1).getNom()))
                    plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_actif),plateau.getPositionSorcier(num_joueur_inactif));
                else
                    plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_inactif),plateau.getPositionSorcier(num_joueur_actif));



                //Le duel est prioritaire sur le paradoxe
                if(jeu.isDuel())
                    jeu.duel();

                     // Vérifier si le joueur actif a un paradoxe apres qu'il ait joué
                if(jeu.isParadoxe(plateau.joueurActif.getMain())){
                    System.out.println("Vous avez un paradoxe");

                    //Afficher les cartes du joueur actif
                    for(int j=0; j<3; j++){
                        System.out.println("Carte "+(j+1)+" : "+plateau.joueurActif.getMain().getCartes().get(j).toString());
                    }

                    //Le joueur actif gagne 1 cristal
                    plateau.joueurActif.ajouterCristaux(1);
            
                    System.out.println("Récapitulatif :");
                    System.out.println("Le joueur "+plateau.joueur1.getNom()+" a en sa possession "+plateau.joueur1.getNombreCristaux()+" cristaux");
                    System.out.println("Le joueur "+plateau.joueur2.getNom()+" a en sa possession "+plateau.joueur2.getNombreCristaux()+" cristaux");
                
                    //Le joueur melange les cartes entre ses mains
                    plateau.joueurActif.getMain().melangerCartes();

                    System.out.print("Vous choississez de mettre vos 3 cartes mélangé a gauche ou a droite de votre baguette magique ?(gauche ou droite) : ");
                    String direction;
                    
                      
                         direction = rand.nextBoolean() ? "gauche" : "droite";
                        while(!jeu.est_Possible_Placer_3cartes(direction)){
                            
                            direction = rand.nextBoolean() ? "gauche" : "droite";
                        }
                    

                    
                    switch(direction){
                        case "gauche":
                            //plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
                            break;
                        case "droite":
                            //plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
                            break;
                    }
                    System.out.println("Voici le plateau apres votre coup :");
                    if(plateau.getJoueurActif().getNom().equals(plateau.getJoueur(1).getNom()))
                        plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_actif),plateau.getPositionSorcier(num_joueur_inactif));
                    else
                        plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_inactif),plateau.getPositionSorcier(num_joueur_actif));
                        
                    plateau.codex.changerCouleurInterdite();
                    System.out.println("La couleur interdite est maintenant :"+ (String)plateau.codex.getCouleurInterdite().getCode());
                }
                     
               


                System.out.println();
                System.out.println("Ton sorcier "+plateau.getJoueurActif().getNom() +" est maintenant situé à la position :"+plateau.getPositionSorcier(num_joueur_actif));

               
                
                 // Une fois que le joueur a terminé son tour, on change de joueur actif au niveau du plateau
                plateau.changerJoueurActif();
                System.out.println();
                System.out.println("Le joueur actif est maintenant le joueur " + plateau.getJoueurActif().getNom() );

               
                //le numero du joueur actif est changé dans le plateau et celui du joueur inactif est changé aussi
                num_joueur_actif = plateau.getJoueurActif() == plateau.getJoueur(1) ? 1 : 2;
                num_joueur_inactif = plateau.getJoueurActif() == plateau.getJoueur(1) ? 2 : 1;

         
                
                i++;
            }*/
            int score1 = plateau.getJoueur(1).getNombreCristaux();
            int score2 = plateau.getJoueur(2).getNombreCristaux();
            if(score1 > score2)
                System.out.println("Le joueur "+plateau.getJoueur(1).getNom()+" a gagné avec un score de "+score1+" contre "+score2+" pour le joueur "+plateau.getJoueur(2).getNom());
            else
                System.out.println("Le joueur "+plateau.getJoueur(2).getNom()+" a gagné avec un score de "+score2+" contre "+score1+" pour le joueur "+plateau.getJoueur(1).getNom());
            
        
    }
    
}

