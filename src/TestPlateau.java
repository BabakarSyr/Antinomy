
import java.util.Scanner;

import Modele.Carte;
import Modele.Jeu;
import Modele.Plateau;

import java.util.ArrayList;
import java.util.List;

public class TestPlateau {
    public static void main(String[] args) {
        int num_joueur_actif = 0;
        String in;
        Jeu jeu = new Jeu();
        Plateau plateau = jeu.plateau();
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
            
            
            jeu.definir_ordres_joueurs(plateau, dejaVuJoueur1, dejaVuJoueur2);

            int num_joueur_inactif = plateau.getJoueurActif() == plateau.getJoueur(1) ? 2 : 1;




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
            
            plateau.getJoueur(num_joueur_actif).sorcier.getSensDuTemps();

            plateau.changerJoueurActif();
            System.out.println();
            
            System.out.println(plateau.joueurActif.getNom());
            // Lecture de la carte à jouer
            System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
            jeu.afficher_cartes_main();
            System.out.print("A toi de placer ton sorcier : ");
            in= sc.next();
            int pos2 = Integer.parseInt(in);
            
            plateau.changerJoueurActif();
            jeu.deplacerSorcier(pos2);
            plateau.changerJoueurActif();
            
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_inactif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_inactif));
    
            //Afficher le plateau et colorer position sorcier
            plateau.afficher_colorSorcier_continuum(pos,pos2);
            plateau.changerJoueurActif();
    
    
            System.out.println("Que la partie commence:");
    
                
            System.out.println("\nTest jouer");
            int i=0;
            //Tant que la partie n'est pas termin
            while( !jeu.partieTerminee() ){
    
                if(i>=1){
                    System.out.println("Voici votre main pour vous aider à choisir le bon emplacement:");
                    jeu.afficher_cartes_main();
                }
                System.out.println("Rappel : La  couleur interdite indiqué par le codex pour ce tour est:"+(String)plateau.codex.getCouleurInterdite().getCode() );
                System.out.println("la position de ton sorcier joueur "+ plateau.getJoueurActif().getNom() + " est :"+plateau.getPositionSorcier(num_joueur_actif));
                System.out.println();

               
               
      
                
                System.out.print("Choisissez la carte à jouer dans votre main(1, 2 ou 3) : "+"\n");
                in= sc.next();
                int indexCarteChoisie = Integer.parseInt(in)-1;

                Carte carteChoisie = plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
                boolean pasDeDeplacements = true;
                plateau.cartesAccessibles(carteChoisie);
                ArrayList<Integer> positionsPossibles = plateau.cartesAccessibles(carteChoisie);
                if (positionsPossibles.size()>0)
                {
                    do
                    {
                    plateau.afficherCartesAcceccibles(positionsPossibles);
                    System.out.print("Choisir une position sur le plateau parmi les choix possible qui s'offre a vous : ");
                    in = sc.next();
                    int position = Integer.parseInt(in);
                    while (!positionsPossibles.contains(position)) 
                    {
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
                
                //Si le nom du joeur actif est le meme que celui du joueur 1
                System.out.println("Voici le plateau apres votre coup :");
                if(plateau.getJoueurActif().getNom().equals(plateau.getJoueur(1).getNom()))
                    plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_actif),plateau.getPositionSorcier(num_joueur_inactif));
                else
                    plateau.afficher_colorSorcier_continuum(plateau.getPositionSorcier(num_joueur_inactif),plateau.getPositionSorcier(num_joueur_actif));

                jeu.paradoxe();
                jeu.duel();

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
