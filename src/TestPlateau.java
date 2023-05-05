
import java.util.Scanner;

import Modele.Carte;
import Modele.Jeu;
import Modele.Plateau;

import java.util.List;
import java.util.Random;

public class TestPlateau {
    public static void main(String[] args) {
        int num_joueur_actif ;
        String in;
        Plateau plateau = new Plateau();
        System.out.println("Bienvenue dans le jeu de l'antinomy !");
        System.out.println();



       
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Entrez le nom du joueur 1 : ");
            //le joueur1 est en rose
            String nomJoueur1 = "\033[95m\033[7m"+sc.next()+"\033[0m";
            System.out.println();
            
            System.out.print("Entrez le nom du joueur 2 : ");
            //le joueur2 est en orange
            String nomJoueur2 = "\033[38;5;208m\033[7m"+sc.next()+"\033[0m";
            System.out.println();
           

                    
            plateau.getJoueur(1).setNom(nomJoueur1);
            plateau.getJoueur(2).setNom(nomJoueur2);

          
            System.out.println(plateau.joueur1.getNom()+",avez-vous ressenti du déjà-vu récemment ? : "); 
            System.out.print("Entrez oui ou non : ");
            String dejaVuJoueur1 = sc.next().toLowerCase();

            System.out.println();

            System.out.println(plateau.joueur2.getNom()+",avez-vous ressenti du déjà-vu récemment ? : "); 
            System.out.print("Entrez oui ou non : ");
            String dejaVuJoueur2 = sc.next().toLowerCase();

            if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) {
                Random rand = new Random();
                num_joueur_actif = rand.nextInt(2)+1 ;  // [1-2]
                plateau.setJoueurActif(num_joueur_actif );  // [1-2]
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
            else if (dejaVuJoueur1.equals("oui")) {
                num_joueur_actif = 1;
            
                plateau.setJoueurActif(num_joueur_actif);
                plateau.getJoueur(1).sorcier.setSensDuTemps(true);
                plateau.getJoueur(2).sorcier.setSensDuTemps(false);
            }
           else {
                num_joueur_actif = 2;
                plateau.setJoueurActif(num_joueur_actif);
                plateau.getJoueur(1).sorcier.setSensDuTemps(false);
                plateau.getJoueur(2).sorcier.setSensDuTemps(true);
           }
           int num_joueur_inactif = plateau.getJoueurActif() == plateau.getJoueur(1) ? 2 : 1;




            System.out.println("Le joueur " + plateau.getJoueurActif().getNom() + " commence la partie !");
            System.out.println();

            System.out.println("Voici le plateau de jeu :");
            plateau.afficher_continuum();
            System.out.println();
    
            
            
            System.out.println("La  couleur interdite indiqué par le codex pour ce tour est:"+(String)plateau.codex.getCouleurInterdite().getCode() );
            System.out.println();
    
    
            
            
    
            List<Integer> possible =  plateau.pos_carte_couleur_interdite() ;
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
    
          
            in= sc.next();
            int pos=Integer.parseInt(in); 
            while(!possible.contains(pos)){
                System.out.println("La position que tu as choisi n'est pas valide");
                System.out.print("Tu dois choisir de le placer au niveau d'une carte sur le continuum portant la couleur interdite : ");
                in= sc.next();
                pos=Integer.parseInt(in);
               
                

            }
            plateau.setPositionSorcier(pos,1);
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

             in= sc.next();
            int pos2 = Integer.parseInt(in);
            
          
            plateau.setPositionSorcier(pos2,2);
            
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_inactif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_inactif));
                
            plateau.setTempsSorcier(num_joueur_inactif);
    
            //Afficher le plateau et colorer position sorcier
            plateau.afficher_colorSorcier_continuum(pos,pos2);
    
    
            System.out.println("Que la partie commence:");
            Jeu jeu = new Jeu(plateau);
    
                
            System.out.println("\nTest jouer");
            int i=0;
            //Tant que la partie n'est pas termin
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

               
               
      
                
                System.out.print("Choisissez la carte à jouer dans votre main(1, 2 ou 3) : "+"\n");
                in= sc.next();
                int indexCarteChoisie = Integer.parseInt(in)-1;

                Carte carteChoisie = plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
                 
        
                boolean actionReussie = false;
                do {
                    System.out.print("Choisissez le temps (futur ou passe) : ");
                    String temps = sc.next().toLowerCase();
                    switch (temps) {
                        case "futur":
                            if (plateau.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, plateau.getContinuum())) {
                                plateau.joueurActif.sorcier.deplacerFutur(carteChoisie, plateau.getContinuum());
                                plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.getContinuum());
                                actionReussie = true; // L'action a réussi, on peut sortir de la boucle
                            } else {
                                System.out.println("Vous ne pouvez pas aller dans le futur .Vous ne pouvez aller que dans le passe avec cette carte");
                            }
                            break;
                        case "passe":
                            if (plateau.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, plateau.getContinuum())) {
                                List<Integer> positionsPossibles = plateau.joueurActif.sorcier.Position_Possible_Passe(carteChoisie, plateau.getContinuum());
                                System.out.print("Choisir une position sur le plateau parmi les choix possible qui s'offre a vous : ");
                                in = sc.next();
                                int position = Integer.parseInt(in);
                                while (!positionsPossibles.contains(position)) {
                                    System.out.print("La position que vous avez choisi n'est pas valide, veuillez choisir une autre position : ");
                                    in = sc.next();
                                    position = Integer.parseInt(in);
                                }
                                //mettre à jour la position du sorcier
                                plateau.joueurActif.sorcier.setPositionSorcier(position);
                                //jouer la carte
                                plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.getContinuum());
                                actionReussie = true; // L'action a réussi, on peut sortir de la boucle
                            } else {
                                System.out.println("Vous ne pouvez pas aller dans le passe .Vous ne pouvez aller que dans le futur avec cette carte");
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



                //Le paradoxe est prioritaire sur le duel si le joueur a laz possibilité de faire un paradoxe et qu'il ya duel en meme temps


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
                        String direction = sc.next().toLowerCase();
                        while(!jeu.est_Possible_Placer_3cartes(direction)){
                            System.out.println("Vous ne pouvez pas placer vos 3 cartes à "+direction+" car il n'y a pas assez de cartes. Choisir la direction opposée :");
            
                            direction = sc.next().toLowerCase();
                        }
                        switch(direction){
                            case "gauche":
                                plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
                                break;
                            case "droite":
                                plateau.joueurActif.jouer3Cartes(plateau.getContinuum(), direction);
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
    
                if(jeu.isDuel()){
                    System.out.println("couleur interdite :");
                    if (jeu.duel())
                    {
                        //Changer la couleur interdite
                        plateau.codex.changerCouleurInterdite();
                    }
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
            }
            int score1 = plateau.getJoueur(1).getNombreCristaux();
            int score2 = plateau.getJoueur(2).getNombreCristaux();
            if(score1 > score2)
                System.out.println("Le joueur "+plateau.getJoueur(1).getNom()+" a gagné avec un score de "+score1+" contre "+score2+" pour le joueur "+plateau.getJoueur(2).getNom());
            else
                System.out.println("Le joueur "+plateau.getJoueur(2).getNom()+" a gagné avec un score de "+score2+" contre "+score1+" pour le joueur "+plateau.getJoueur(1).getNom());
            
        }
    }
    
}
