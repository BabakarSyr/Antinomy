package Modele;

public class Partie {
     PlateauDeJeu plateau;
     Joueur joueur1;
     Joueur joueur2;
     Joueur joueurActuel;
    
      MainDeCartes mainJoueur1;
     MainDeCartes mainJoueur2;

     Couleur couleurInterdite;
    //Codex codex;

    public Partie(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.plateau = new PlateauDeJeu();
        this.joueurActuel = joueur1;

        mainJoueur1 = new MainDeCartes();
        mainJoueur2 = new MainDeCartes();
        //plateau = new Plateau();
    }
    /* 
    public void jouer() {
        while (!partieTerminee()) {
            // Phase 1 : Déplacement du sorcier
            Carte carteJouee = joueurActuel.choisirCarte();
            plateau.deplacerSorcier(joueurActuel, carteJouee);

            // Phase 2 : Vérification du Paradoxe
            if (joueurActuel.aParadoxe()) {
                joueurActuel.gagnerCristal();
                plateau.echangerCartesParadoxe(joueurActuel);
            }

            // Phase 3 : Duel (si les sorciers sont sur la même case)
            if (plateau.sorciersSurMemeCase()) {
                Joueur autreJoueur = joueurActuel == joueur1 ? joueur2 : joueur1;
                Joueur gagnantDuel = duel(joueurActuel, autreJoueur);
                if (gagnantDuel != null) {
                    gagnantDuel.volerCristal(autreJoueur);
                    plateau.deplacerCristalCodex();
                }
            }

            changerJoueurActuel();
        }
    }*/
    public boolean partieTerminee() {
        return joueur1.getNombreCristaux() == 5 || joueur2.getNombreCristaux() == 5;
    }

    private void changerJoueurActuel() {
        /*
         * Si le joueur actuel est le joueur 1, alors le joueur actuel devient le joueur 2.et vice versa
         */
        joueurActuel = joueurActuel == joueur1 ? joueur2 : joueur1;
    }

    /* 
    public void duel() {
        int totalJoueur1 = 0;
        int totalJoueur2 = 0;
    
        for (Carte carte : mainJoueur1.getCartes()) {
            if (carte.getCouleur() != codex.getCouleurInterdite()) {
                totalJoueur1 += carte.getValeur();
            }
        }
    
        for (Carte carte : mainJoueur2.getCartes()) {
            if (carte.getCouleur() != codex.getCouleurInterdite()) {
                totalJoueur2 += carte.getValeur();
            }
        }
    
        if (totalJoueur1 > totalJoueur2) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            joueur1.volerCristal(joueur2);
            System.out.println(joueur1.getNom() + " gagne le duel et vole un cristal à " + joueur2.getNom());
        } else if (totalJoueur1 < totalJoueur2) {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            joueur2.volerCristal(joueur1);
            System.out.println(joueur2.getNom() + " gagne le duel et vole un cristal à " + joueur1.getNom());
        } else {
            // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
            Random random = new Random();
            boolean egalite = (totalJoueur1 == totalJoueur2);
            // Récupérez la couleur interdite à partir du Codex
            Couleur couleurInterdite = codex.getCouleurInterdite(); 

            joueur1.getMain().melangerCartes();
            joueur2.getMain().melangerCartes();
            
            while (egalite) {
               // Sélectionner une carte au hasard pour chaque joueur
                int indexCarteJoueur1 = random.nextInt(joueur1.getMain().taille());
                int indexCarteJoueur2 = random.nextInt(joueur2.getMain().taille());

                Carte carteJoueur1 = joueur1.getMain().getCarte(indexCarteJoueur1);
                Carte carteJoueur2 = joueur2.getMain().getCarte(indexCarteJoueur2);

                // On compare  les valeurs des cartes en tenant compte de la couleur interdite
                int valeurCarteJoueur1 = carteJoueur1.getValeur(couleurInterdite);
                int valeurCarteJoueur2 = carteJoueur2.getValeur(couleurInterdite);
            
                if (valeurCarteJoueur1 > valeurCarteJoueur2) {
                    joueur1.volerCristal(joueur2);
                    System.out.println(joueur1.getNom() + " gagne le duel et vole un cristal à " + joueur2.getNom());
                    egalite = false;
                } else if (valeurCarteJoueur1 < valeurCarteJoueur2) {
                    joueur2.volerCristal(joueur1);
                    System.out.println(joueur2.getNom() + " gagne le duel et vole un cristal à " + joueur1.getNom());
                    egalite = false;
                } else {
                    System.out.println("Égalité à nouveau ! Les joueurs tirent de nouvelles cartes.");
                    egalite = true;
                }
            }
        }
        
    }*/
    

    /* 
    public static void main(String[] args) {
        Joueur joueur1 = new Joueur("Alice");
        Joueur joueur2 = new Joueur("Bob");

        Partie partie = new Partie(joueur1, joueur2);
        partie.jouer();

        System.out.println("Le gagnant est : " + (joueur1.getNombreCristaux() >= 5 ? joueur1.getNom() : joueur2.getNom()));
    }*/
}
