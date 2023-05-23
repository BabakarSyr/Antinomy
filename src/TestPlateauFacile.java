
import java.util.Scanner;

import Modele.Carte;

import Modele.IAFacile;
import Modele.Jeu;
import Modele.Plateau;

import java.util.ArrayList;
import java.util.List;

public class TestPlateauFacile {
    public static void main(String[] args) {
        int num_joueur_actif = 0;
        String in;
        Jeu jeu = new Jeu();
        Plateau plateau = jeu.plateau();
        System.out.println("Bienvenue dans le jeu de l'antinomy !");
        System.out.println();



       
        try (Scanner sc = new Scanner(System.in)) {
            
            //le joueur1 est en rose
            String nomJoueur1 = "IAFACILE";
            
            System.out.println();
            
            System.out.print("Entrez le nom du joueur 2 : ");
            //le joueur2 est en orange
            String nomJoueur2 = "\033[95m\033[7m"+sc.next()+"\033[0m";
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
            
            
            jeu.definirOrdresJoueurs( dejaVuJoueur1, dejaVuJoueur2);

            int num_joueur_inactif = plateau.joueurActif() == plateau.getJoueur(1) ? 2 : 1;




            System.out.println("Le joueur " + plateau.joueurActif().getNom() + " commence la partie !");
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
            jeu.afficher_cartes_main();
            System.out.print("\nTu dois choisir de le placer au niveau d'une carte sur le continuum portant la couleur interdite : ");
    
          
            in= sc.next();
            int pos=Integer.parseInt(in); 
            while(!possible.contains(pos)){
                System.out.println("La position que tu as choisi n'est pas valide");
                System.out.print("Tu dois choisir de le placer au niveau d'une carte sur le continuum portant la couleur interdite : ");
                in= sc.next();
                pos=Integer.parseInt(in);
            }
            jeu.deplacerSorcier(pos);
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_actif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_actif));
            
            plateau.getJoueur(num_joueur_actif).getSensDuTemps();

            plateau.changerJoueurActif();
            System.out.println();
            
            System.out.println(plateau.joueurActif().getNom());
            // Lecture de la carte à jouer
            System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
            jeu.afficher_cartes_main();
            System.out.print("A toi de placer ton sorcier : ");
            in= sc.next();
            int pos2 = Integer.parseInt(in);
            
           
            jeu.deplacerSorcier(pos2);
            plateau.changerJoueurActif();
            
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_inactif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_inactif));
    
            //Afficher le plateau et colorer position sorcier
            plateau.afficher_colorSorcier_continuum(pos,pos2);
           
    
    
            System.out.println("Que la partie commence:");
    
                
            System.out.println("\nTest jouer");

            IAFacile ia = new IAFacile();
            int i=0;



            //Tant que la partie n'est pas termin
            while( !jeu.partieTerminee() ){
    
                if(i>=1){
                    System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
                    jeu.afficher_cartes_main();
                }
                System.out.println("Rappel : La  couleur interdite indiqué par le codex pour ce tour est:"+(String)plateau.codex.getCouleurInterdite().getCode() );
                System.out.println("la position de ton sorcier joueur "+ plateau.joueurActif().getNom() + " est :"+plateau.getPositionSorcier(num_joueur_actif));
                System.out.println();

               
               
            
            if (plateau.joueurActif().getNom().equals(nomJoueur2)) { 
                System.out.print("Choisissez la carte à jouer dans votre main(1, 2 ou 3) : "+"\n");
                in= sc.next();
                int indexCarteChoisie = Integer.parseInt(in)-1;

                Carte carteChoisie = plateau.joueurActif().getMain().get(indexCarteChoisie);
                boolean pasDeDeplacements = true;
                plateau.cartesAccessibles(carteChoisie);
                ArrayList<Integer> positionsPossibles = plateau.cartesAccessibles(carteChoisie);
                if (positionsPossibles.size()>0)
                {
                    do{
                    plateau.afficherCartesAcceccibles(positionsPossibles);
                    System.out.print("Choisir une position sur le plateau parmi les choix possible qui s'offre a vous : ");
                    in = sc.next();
                    int position = Integer.parseInt(in);
                    while (!positionsPossibles.contains(position)) {
                        System.out.print("La position que vous avez choisi n'est pas valide, veuillez choisir une autre position : ");
                        in = sc.next();
                        position = Integer.parseInt(in);
                    }
                    jeu.echangerCarte(indexCarteChoisie, position);
                    jeu.deplacerSorcier(position);
                    }
                    while(pasDeDeplacements);
                    pasDeDeplacements = false;
                }
            }
            else{
                ia.joue();
            }





                //Si le nom du joeur actif est le meme que celui du joueur 1
                System.out.println("Voici le plateau apres votre coup :");
                if(plateau.joueurActif().getNom().equals(plateau.getJoueur(1).getNom()))
                    plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_actif),plateau.getPositionSorcier(num_joueur_inactif));
                else
                    plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_inactif),plateau.getPositionSorcier(num_joueur_actif));

                    if(jeu.estParadoxe())
                    {
                        System.out.println("Vous avez un paradoxe");
                        
                        //Afficher les cartes du joueur actif
                        jeu.afficher_cartes_main();
                        
                        //Le joueur actif gagne 1 cristal
                        //plateau.joueurActif().ajouterCristaux(1);
                                    
                        System.out.println("Récapitulatif :");
                        System.out.println("Le joueur "+plateau.joueur1.getNom()+" a en sa possession "+plateau.joueur1.getNombreCristaux()+" cristaux");
                        System.out.println("Le joueur "+plateau.joueur2.getNom()+" a en sa possession "+plateau.joueur2.getNombreCristaux()+" cristaux");
                                        
                        //Le joueur melange les cartes entre ses mains
                        plateau.joueurActif().melangerMain();
                        String direction;
                        if (jeu.estPossibleEchangerParadoxe(true) && !jeu.estPossibleEchangerParadoxe(false))
                        {
                            jeu.echangerParadoxe(true);
                        }
                        else if (!jeu.estPossibleEchangerParadoxe(true) && jeu.estPossibleEchangerParadoxe(false))
                        {
                            jeu.echangerParadoxe(false);
                        }
                        else
                        {
                            System.out.print("Vous choississez de mettre vos 3 cartes mélangé a gauche ou a droite de votre baguette magique ?(gauche ou droite) : ");
                            do
                            {
                                direction = sc.next().toLowerCase();
                                if (direction!="gauche" || direction!="droite")
                                {
                                    System.out.print("choisissez gauche ou droite : ");
                                }
                            }
                            while(direction!="gauche" || direction!="droite");
                            boolean futur = true;
                            if(direction == "gauche"){
                                futur = false;
                            }
                            if(direction == "droite"){
                                futur = true;
                            }
                            jeu.echangerParadoxe(futur);
                        }
                        plateau.codex.changerCouleurInterdite();
                        System.out.println("La nouvelle couleur interdite est :"+ (String)plateau.codex.getCouleurInterdite().getCode());
                        System.out.println("Voici le plateau apres votre coup :");
                        if(plateau.joueurActif().getNom().equals(plateau.getJoueur(1).getNom()))
                        {
                            plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(1),plateau.getPositionSorcier(1));
                        }
                        else
                        {
                            plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(2),plateau.getPositionSorcier(2));
                        }
                    }
    
                if(jeu.estDuel())
                {
                    System.out.println("C'est l'heure du Duel!");
                    System.out.println("Rappel,la couleur interdite est :"+ (String)plateau.codex.getCouleurInterdite().getCode());
                    //Les 2 joueurs affichent leur main
                    System.out.println("La main du joueur 1 est : ");
                    for(int k =0;i<3;i++){
                    
                        System.out.println("Carte "+k+" : "+plateau.joueur1.getMain().get(i).toString());
                        
                    }
                    System.out.println();
                    System.out.println("La main du joueur 2 est : ");
                    for(int k =0;i<3;i++){
                        
                        System.out.println("Carte "+k+" : "+plateau.joueur2.getMain().get(i).toString());
                    }
                    System.out.println();
                }
                    jeu.duel();
    
                    System.out.println();
                    System.out.println("Ton sorcier "+plateau.joueurActif().getNom() +" est maintenant situé à la position :"+plateau.getPositionSorcier(num_joueur_actif));
    
                   
                    
                     // Une fois que le joueur a terminé son tour, on change de joueur actif au niveau du plateau
                    plateau.changerJoueurActif();
                    System.out.println();
                    System.out.println("Le joueur actif est maintenant le joueur " + plateau.joueurActif().getNom() );
    
                   
                    //le numero du joueur actif est changé dans le plateau et celui du joueur inactif est changé aussi
                    num_joueur_actif = plateau.joueurActif() == plateau.getJoueur(1) ? 1 : 2;
                    num_joueur_inactif = plateau.joueurActif() == plateau.getJoueur(1) ? 2 : 1;
    
             
                    
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
    

    