
import java.util.Scanner;


import Modele.Partie;
import Modele.Plateau;

import java.util.Random;

public class TestPlateau {
    public static void main(String[] args) {
        int num_joueur_actif ;
        Plateau plateau = new Plateau();
        System.out.println("Bienvenue dans le jeu de l'antinomy !");
        System.out.println();



        System.out.println("Entrez le nom du joueur 1 :");
        try (Scanner sc = new Scanner(System.in)) {
            String nomJoueur1 = sc.next();

            System.out.println("Entrez le nom du joueur 2 :");
            String nomJoueur2 = sc.next();
                    
            plateau.getJoueur(1).setNom(nomJoueur1);
            plateau.getJoueur(2).setNom(nomJoueur2);

            System.out.println("avez-vous ressenti du déjà-vu récemment ? (Entrez oui ou non)");
            String dejaVuJoueur1 = sc.next().toLowerCase();

            System.out.println("Joueur 2, avez-vous ressenti du déjà-vu récemment ? (Entrez oui ou non)");
            String dejaVuJoueur2 = sc.next().toLowerCase();

            if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) {
                Random rand = new Random();
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
            plateau.afficher_continuum();
            System.out.println();
    
            
            
            System.out.println("La  couleur interdite indiqué par le codex pour ce tour est:"+plateau.codex.getCouleurInterdite()    );
            System.out.println();
    
    
            
            
    
            
            System.out.println("A quel emplacement sur le plateau veux tu placer ta baguette magique ?");
            System.out.println("Les indices des cartes portant la couleur interdite sont :");
            plateau.pos_carte_couleur_interdite() ;
            System.out.println("Tu dois choisir de le placer au niveau d'une carte sur le continuum portant la couleur interdite :");
    
           
            int pos = sc.nextInt() ;
            plateau.setPositionSorcier(pos,1);
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_actif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_actif));
            
            
           plateau.getJoueur(num_joueur_actif).sorcier.getSensDuTemps();


            System.out.println();

            System.out.println("\nA toi de choisir  "+plateau.getJoueur(2).getNom());
            int pos2 = sc.nextInt() ;
            plateau.setPositionSorcier(pos2,2);
            
            System.out.println("\nTu as choisi joueur  "+plateau.getJoueur(num_joueur_inactif).getNom()+" de le placer a la position :"+plateau.getPositionSorcier(num_joueur_inactif));
                
            plateau.setTempsSorcier(num_joueur_inactif);
    
            
    
    
            System.out.println("Que la partie commence:");
            Partie partie = new Partie(plateau);
    
                
            System.out.println("\nTest jouer");
            //Tant que la partie n'est pas termin
            while( !partie.partieTerminee() ){
    
                //NOM DU JOUEUR ACTIF

                System.out.println("la position de ton sorcier joueur "+ plateau.getJoueurActif().getNom() + " est :"+plateau.getPositionSorcier(num_joueur_actif));
                System.out.println();
                // Lecture de la carte à jouer
                System.out.print("Choisissez la carte à jouer dans votre main(1, 2 ou 3) :\n ");
        
                for (int i =0 ;i < 3; i++) {
                    int j=i+1;
                    System.out.print("Carte " + j  +"="+ plateau.joueurActif.getMain().getCartes().get(i).toString() + " ");
                    System.out.println();
                }
             
                int carte = sc.nextInt() - 1;

                // Lecture du temps
                System.out.print("Choisissez le temps (futur ou passe) : ");
                String temps = sc.next().toLowerCase();

                // Jouer la carte choisie
                partie.jouer(carte, temps);
                System.out.println();
                System.out.println("Ton sorcier est maintenant situé à la position :"+plateau.getPositionSorcier(num_joueur_actif));
                System.out.println("Voici le nouveau plateau de jeu :");
                plateau.afficher_continuum();

                System.out.println();
                System.out.println("Le joueur actif est maintenant le joueur " + plateau.getJoueurActif().getNom() );
                
            }

        }
    }
    
}
