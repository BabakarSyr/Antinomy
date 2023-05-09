
import java.util.Scanner;

import Modele.Carte;
import Modele.Partie;


import java.util.List;
import java.util.Random;
import Modele.IADifficile;
public class TestPlateauDifficile{
    public static void main(String[] args) {
        int nbTours = 0;
        int num_joueur_actif ;
        String in;

        Random rand = new Random();


        Partie partie = new Partie();
      
        System.out.println("Bienvenue dans le jeu de l'antinomy !");
        System.out.println();



       
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Entrez le nom du joueur 1 : ");
            //le joueur1 est en rose
            String nomJoueur1 = "\033[95m\033[7m"+sc.next()+"\033[0m";
            System.out.println();
            
            //System.out.print("Entrez le nom du joueur 2 : ");
            //le joueur2 est en orange
            String nomJoueur2 = "\033[38;5;208m\033[7mMerlin\033[0m";
            System.out.println();
           
          

                    
            partie.getJoueur(1).setNom(nomJoueur1);
            partie.getJoueur(2).setNom(nomJoueur2);

          
            System.out.println(partie.joueur1.getNom()+",avez-vous ressenti du déjà-vu récemment ? : "); 
            System.out.print("Entrez oui ou non : ");
            String dejaVuJoueur1 = sc.next().toLowerCase();

            System.out.println();

            System.out.println(partie.joueur2.getNom()+",avez-vous ressenti du déjà-vu récemment ? : "); 
            System.out.print("Entrez oui ou non : ");
            String dejaVuJoueur2 = rand.nextBoolean() ? "oui" : "non";

            if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) {
               
                num_joueur_actif = rand.nextInt(2)+1 ;  // [1-2]
                partie.setJoueurActif(num_joueur_actif );  // [1-2]
            }
            else if (dejaVuJoueur1.equals("oui")) {
                num_joueur_actif = 1;
            
                partie.setJoueurActif(num_joueur_actif);
            }
           else {
                num_joueur_actif = 2;
                partie.setJoueurActif(num_joueur_actif);
           }
           int num_joueur_inactif =partie.getJoueurActif() == partie.getJoueur(1) ? 2 : 1;




            System.out.println("Le joueur " + partie.getJoueurActif().getNom() + " commence la partie !");
            System.out.println();

            System.out.println("Voici le partie de jeu :");
            partie.afficher_continuum();
            System.out.println();
    
            
            
            System.out.println("La  couleur interdite indiqué par le codex pour ce tour est:"+(String)partie.codex.getCouleurInterdite().getCode() );
            System.out.println();
    
    
            
            
    
            List<Integer> possible =  partie.pos_carte_couleur_interdite() ;
            System.out.println("A quel emplacement sur le partie veux tu placer ta baguette magique ?");
            System.out.println("Les indices des cartes portant la couleur interdite sont :");
            for(int i=0;i< possible.size();i++){
                System.out.print(possible.get(i));
                System.out.println();
            }
            
          
           
           // Lecture de la carte à jouer
          System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
           for (int i =0 ;i < 3; i++) {
               int j=i+1;
               System.out.print("Carte " + j  +"="+ partie.joueurActif.getMain().getCartes().get(i).toString() + " ");
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
            partie.setPositionSorcier(pos,1);
            System.out.println("\nTu as choisi joueur  "+partie.getJoueur(num_joueur_actif).getNom()+" de le placer a la position :"+partie.getPositionSorcier(num_joueur_actif));
            
            
           partie.getJoueur(num_joueur_actif).sorcier.getSensDuTemps();


            System.out.println();
            
            System.out.println(partie.getJoueur(num_joueur_inactif).getNom());
            // Lecture de la carte à jouer
          System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
          for (int i =0 ;i < 3; i++) {
              int j=i+1;
              System.out.print("Carte " + j  +"="+ partie.getJoueur(num_joueur_inactif).getMain().getCartes().get(i).toString() + " ");
              System.out.println();
          }
            System.out.print("A toi de placer ton sorcier : ");

           
            //les positions possibles sont dans la liste possible.On choisit une position au hasard
            int pos2 = possible.get(rand.nextInt(possible.size()));
            
          
            partie.setPositionSorcier(pos2,2);
            
            System.out.println("\nTu as choisi joueur  "+partie.getJoueur(num_joueur_inactif).getNom()+" de le placer a la position :"+partie.getPositionSorcier(num_joueur_inactif));
                
            partie.setTempsSorcier(num_joueur_inactif);
    
            //Afficher le partie et colorer position sorcier
            partie.afficher_colorSorcier_continuum(pos,pos2);
    
    


            IADifficile ia = new IADifficile("Merlin",0,partie);
            System.out.println("Que la partie commence:");
           
                
            System.out.println("\nTest jouer");
            
            //Tant que la partie n'est pas termin
            while( !partie.partieTerminee() ){
    
                if(nbTours>=1){
                    System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
                    for (int j =0 ;j < 3; j++) {
                        int k=j+1;
                        System.out.print("Carte " + k  +"="+ partie.joueurActif.getMain().getCartes().get(j).toString() + " ");
                        System.out.println();
                    }
                }
                System.out.println("Rappel : La  couleur interdite indiqué par le codex pour ce tour est:"+(String)partie.codex.getCouleurInterdite().getCode() );
                System.out.println("la position de ton sorcier joueur "+ partie.getJoueurActif().getNom() + " est :"+partie.getPositionSorcier(num_joueur_actif));
                System.out.println();

                ia.set_Plateau_avant_Coup_Adversaire(partie.copie());
                if (partie.getJoueurActif().getNom().equals(nomJoueur1)) {
                    System.out.print("Choisissez la carte à jouer dans votre main(1, 2 ou 3) : "+"\n");
                    in= sc.next();
                    int indexCarteChoisie = Integer.parseInt(in)-1;
                
                    Carte carteChoisie = partie.joueurActif.getMain().getCartes().get(indexCarteChoisie);
                
                    

                    boolean actionReussie = false;
                    do {
                        System.out.print("Choisissez le temps (futur ou passe) : ");
                        String temps = sc.next().toLowerCase();
                        switch (temps) {
                            case "futur":
                                if (partie.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, partie.getContinuum())) {
                                    partie.joueurActif.sorcier.deplacerFutur(carteChoisie, partie.getContinuum());
                                    partie.joueurActif.jouerCarte(indexCarteChoisie, partie.getContinuum());
                                    actionReussie = true; // L'action a réussi, on peut sortir de la boucle
                                    ia.Coup_jouer_Par_Adversaire(partie);
                                } else {
                                    System.out.println("Vous ne pouvez pas aller dans le futur .Vous ne pouvez aller que dans le passe avec cette carte");
                                }
                                break;
                            case "passe":
                                if (partie.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, partie.getContinuum())) {
                                    List<Integer> positionsPossibles = partie.joueurActif.sorcier.Position_Possible_Passe(carteChoisie, partie.getContinuum());
                                    System.out.print("Choisir une position sur le partie parmi les choix possible qui s'offre a vous : ");
                                    in = sc.next();
                                    int position = Integer.parseInt(in);
                                    while (!positionsPossibles.contains(position)) {
                                        System.out.print("La position que vous avez choisi n'est pas valide, veuillez choisir une autre position : ");
                                        in = sc.next();
                                        position = Integer.parseInt(in);
                                    }
                                    //mettre à jour la position du sorcier
                                    partie.joueurActif.sorcier.setPositionSorcier(position);
                                    //jouer la carte
                                    partie.joueurActif.jouerCarte(indexCarteChoisie, partie.getContinuum());
                                    actionReussie = true; // L'action a réussi, on peut sortir de la boucle
                                    ia.Coup_jouer_Par_Adversaire(partie);
                                } else {
                                    System.out.println("Vous ne pouvez pas aller dans le passe .Vous ne pouvez aller que dans le futur avec cette carte");
                                }
                                break;
                            default:
                                System.out.println("Saisie incorrecte. Veuillez saisir futur ou passe");
                        }
                    } while (!actionReussie);


                } 
                else {
                    //L'IA joue

                    ia.jouer(partie);
                   
                }


                //Si le nom du joeuur actif est le meme que celui du joueur 1
                System.out.println("Voici le partie apres votre coup :");
                if(partie.getJoueurActif().getNom().equals(partie.getJoueur(1).getNom()))
                    partie.afficher_colorSorcier_continuum(partie.getPositionSorcier(num_joueur_actif),partie.getPositionSorcier(num_joueur_inactif));
                else
                    partie.afficher_colorSorcier_continuum(partie.getPositionSorcier(num_joueur_inactif),partie.getPositionSorcier(num_joueur_actif));



                //Le duel est prioritaire sur le paradoxe
                if(partie.isDuel())
                    if(partie.duel())
                         //Changer la couleur interdite
                         partie.codex.changerCouleurInterdite();


                     // Vérifier si le joueur actif a un paradoxe apres qu'il ait joué
                if(partie.isParadoxe(partie.joueurActif.getMain())){
                    System.out.println("Vous avez un paradoxe");

                    //Afficher les cartes du joueur actif
                    for(int j=0; j<3; j++){
                        System.out.println("Carte "+(j+1)+" : "+partie.joueurActif.getMain().getCartes().get(j).toString());
                    }

                    //Le joueur actif gagne 1 cristal
                    partie.joueurActif.ajouterCristaux(1);
            
                    System.out.println("Récapitulatif :");
                    System.out.println("Le joueur "+partie.joueur1.getNom()+" a en sa possession "+partie.joueur1.getNombreCristaux()+" cristaux");
                    System.out.println("Le joueur "+partie.joueur2.getNom()+" a en sa possession "+partie.joueur2.getNombreCristaux()+" cristaux");
                
                    //Le joueur melange les cartes entre ses mains
                    partie.joueurActif.getMain().melangerCartes();

                    System.out.print("Vous choississez de mettre vos 3 cartes mélangé a gauche ou a droite de votre baguette magique ?(gauche ou droite) : ");
                    String direction;
                    if (partie.getJoueurActif().getNom().equals(nomJoueur1)){
                         direction = sc.next().toLowerCase();
                        while(!partie.est_Possible_Placer_3cartes(direction)){
                            System.out.println("Vous ne pouvez pas placer vos 3 cartes à "+direction+" car il n'y a pas assez de cartes. Choisir la direction opposée :");
            
                            direction = sc.next().toLowerCase();
                        }
                    }
                    else{
                         direction = rand.nextBoolean() ? "gauche" : "droite";
                        while(!partie.est_Possible_Placer_3cartes(direction)){
                            direction = rand.nextBoolean() ? "gauche" : "droite";
                        }
                    }

                    
                    switch(direction){
                        case "gauche":
                            partie.joueurActif.jouer3Cartes(partie.getContinuum(), direction);
                            break;
                        case "droite":
                            partie.joueurActif.jouer3Cartes(partie.getContinuum(), direction);
                            break;
                    }
                    System.out.println("Voici le partie apres votre coup :");
                    if(partie.getJoueurActif().getNom().equals(partie.getJoueur(1).getNom()))
                        partie.afficher_colorSorcier_continuum(partie.getPositionSorcier(num_joueur_actif),partie.getPositionSorcier(num_joueur_inactif));
                    else
                        partie.afficher_colorSorcier_continuum(partie.getPositionSorcier(num_joueur_inactif),partie.getPositionSorcier(num_joueur_actif));
                        
                    partie.codex.changerCouleurInterdite();
                    System.out.println("La couleur interdite est maintenant :"+ (String)partie.codex.getCouleurInterdite().getCode());
                }
                     
               


                System.out.println();
                System.out.println("Ton sorcier "+partie.getJoueurActif().getNom() +" est maintenant situé à la position :"+partie.getPositionSorcier(num_joueur_actif));

               
                
                 // Une fois que le joueur a terminé son tour, on change de joueur actif au niveau du partie
                partie.changerJoueurActif();
                System.out.println();
                System.out.println("Le joueur actif est maintenant le joueur " + partie.getJoueurActif().getNom() );

               
                //le numero du joueur actif est changé dans le partie et celui du joueur inactif est changé aussi
                num_joueur_actif = partie.getJoueurActif() == partie.getJoueur(1) ? 1 : 2;
                num_joueur_inactif = partie.getJoueurActif() == partie.getJoueur(1) ? 2 : 1;

         
                
                nbTours++;
            }
            int score1 = partie.getJoueur(1).getNombreCristaux();
            int score2 = partie.getJoueur(2).getNombreCristaux();
            if(score1 > score2)
                System.out.println("Le joueur "+partie.getJoueur(1).getNom()+" a gagné avec un score de "+score1+" contre "+score2+" pour le joueur "+partie.getJoueur(2).getNom());
            else
                System.out.println("Le joueur "+partie.getJoueur(2).getNom()+" a gagné avec un score de "+score2+" contre "+score1+" pour le joueur "+partie.getJoueur(1).getNom());
            
        }
    }
    
}
