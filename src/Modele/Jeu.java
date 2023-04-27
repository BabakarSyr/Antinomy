package Modele;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Jeu {
    Plateau plateau;
    Joueur joueur1;
    Joueur joueur2;
    Joueur joueurActuel;
    
    MainDeCartes mainJoueur1;
    MainDeCartes mainJoueur2;

    Couleur couleurInterdite;
    Codex codex;

    public Jeu(Joueur joueur1, Joueur joueur2) {
        this.joueur1 = joueur1;
        this.joueur2 = joueur2;
        this.plateau = new Plateau();
        this.joueurActuel = joueur1;

        mainJoueur1 = new MainDeCartes();
        mainJoueur2 = new MainDeCartes();
        plateau = new Plateau();
    }
}
