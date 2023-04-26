package Modele;
public class Joueur {
    String nom;
    int points;
    MainDeCartes main;
    int nombreCristaux;

    public Joueur(String nom, int nombreCristaux) {
        this.nom = nom;
        points = 0;
        main = new MainDeCartes();
        this.nombreCristaux = nombreCristaux;
    }

    public int getNombreCristaux() {
        return nombreCristaux;
    }
    public void setNombreCristaux(int nombreCristaux) {
        this.nombreCristaux = nombreCristaux;
    }
    public void ajouterCristaux(int nombreCristaux) {
        this.nombreCristaux += nombreCristaux;
    }
    public void retirerCristaux(int nombreCristaux) {
        this.nombreCristaux -= nombreCristaux;
    }
    
    //Voler un cristal à un autre joueur
    public void volerCristal(Joueur autreJoueur) {
        if (autreJoueur.getNombreCristaux() > 0) {
            //On ajoute un cristal au joueur actuel 
            this.ajouterCristaux(1);
            //on en retire un à l'autre joueur
            autreJoueur.retirerCristaux(1);
        }
        else {
            System.out.println("Impossible de voler un cristal à " + autreJoueur.getNom() + " car il n'en a plus  ou n'en a pas.");
        }
    }


   
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public MainDeCartes getMain() {
        return main;
    }

    public void setMain(MainDeCartes main) {
        this.main = main;
    }

    public void ajouterPoints(int points) {
        this.points += points;
    }

    public void piocherCarte(Carte carte) {
        main.ajouterCarte(carte);
    }

    public Carte jouerCarte(int index) {
        Carte carte = main.getCarte(index);
        main.retirerCarte(index);
        return carte;
    }
}

