package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlateauDeJeu {
     List<Carte> cartesReliques;
     List<Carte> cartesSorciers;
     List<Carte> cartes_sur_plateau;
     Carte codex;
     int positionSorcierJoueur1;
     int positionSorcierJoueur2;
    MainDeCartes mainJoueur1;
    MainDeCartes mainJoueur2;




    public PlateauDeJeu() {
        cartesReliques = new ArrayList<>();
        cartes_sur_plateau = new ArrayList<>();


        cartesReliques.add(new Carte(Forme.PLUME, Couleur.VERT,1,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.VIOLET,2,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.BLEU,3,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.PLUME, Couleur.ROUGE,4,TypeCarte.RELIQUE, Couleur.VERT));

        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.VIOLET,1,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.BLEU,2,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.ROUGE,3,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.ANNEAU, Couleur.VERT,4,TypeCarte.RELIQUE, Couleur.VERT));

        cartesReliques.add(new Carte(Forme.CLE, Couleur.ROUGE,1,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.CLE, Couleur.VERT,2,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.CLE, Couleur.VIOLET,3,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.CLE, Couleur.BLEU,4,TypeCarte.RELIQUE, Couleur.VERT));

        cartesReliques.add(new Carte(Forme.CRANE, Couleur.BLEU,1,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.CRANE, Couleur.ROUGE,2,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.CRANE, Couleur.VERT,3,TypeCarte.RELIQUE, Couleur.VERT));
        cartesReliques.add(new Carte(Forme.CRANE, Couleur.VIOLET,4,TypeCarte.RELIQUE, Couleur.VERT));

        // Mélanger les cartes Reliques
        Collections.shuffle(cartesReliques);

        
        // Créer les cartes Sorcier pour chaque joueur
        Carte sorcierJoueur1 = new Carte(null, null, 0, TypeCarte.SORCIER,null);
        Carte sorcierJoueur2 = new Carte(null, null, 0, TypeCarte.SORCIER, null);
        cartesSorciers.add(sorcierJoueur1);
        cartesSorciers.add(sorcierJoueur2);

            
        //On met mainetant prend les cartes de la pochette et on les met dans le plateau de jeu et on avant de poser les cartes sur le plateau,chaque joueur pioche 3 cartes et les met dans
        // sa main et on met les 2 cartes sorciers dans le plateau et la derniere cartes relique restante sera le codex

        // Distribuer les cartes RElique aux joueurs
        for(int i = 0; i < 3; i++) {
            mainJoueur1.ajouterCarte(cartesReliques.get(0));
            cartesReliques.remove(0);
            mainJoueur2.ajouterCarte(cartesReliques.get(0));
            cartesReliques.remove(0);
        }
       
        //Ajouter toutes les cartes de cartes dans carte_sur_plateau sauf la derniere carte relique qui sera le codex
        for(int i = 0; i < cartesReliques.size()-1; i++) {
            cartes_sur_plateau.add(cartesReliques.get(i));
            cartesReliques.remove(i);
        }
        //Ajouter la derniere carte relique qui sera le codex.Il ne reste normalement qu'une seule carte relique dans cartesReliques,donc cette cartes sera le codex
        codex=cartesReliques.get(0);


        // Initialisez les positions des cartes sorcier
        positionSorcierJoueur1 = 0;
        positionSorcierJoueur2 = 0;
    }

    // ... Autres méthodes et fonctionnalités pour la classe PlateauDeJeu
}
