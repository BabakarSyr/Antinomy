package Modele;

import java.util.List;

public class Joueur {
    String nom;
    MainDeCartes main;
    int nombreCristaux;
    public Sorcier sorcier;


    public Joueur(String nom, int nombreCristaux) {
        this.nom = nom;
        this.nombreCristaux = nombreCristaux;
        this.sorcier = new Sorcier(true);
        this.main = new MainDeCartes();
    }



    public int getNombreCristaux() {
        return nombreCristaux;
    }
   
    public void ajouterCristaux(int nombreCristaux) {
        this.nombreCristaux += nombreCristaux;
    }
    public void retirerCristaux(int nombreCristaux) {
        this.nombreCristaux -= nombreCristaux;
    }
    
    //Voler un cristal à un autre joueur
    public boolean volerCristal(Joueur autreJoueur) {
        if (autreJoueur.getNombreCristaux() > 0) {
            //On ajoute un cristal au joueur actuel 
            this.ajouterCristaux(1);
            //on en retire un à l'autre joueur
            autreJoueur.retirerCristaux(1);
            return true;
        }
        else {
            return false;
        }
    }


   
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public MainDeCartes getMain() {
        return main;
    }


    public Sorcier getSorcier() {
        return sorcier;
    }
    public void setSorcier(Sorcier sorcier) {
        this.sorcier = sorcier;
    }




    //Equivalent echanger carte
    public Carte jouerCarte(int index, List<Carte> continuum) {
        int positionSorcier = sorcier.getPositionSorcier();
        Carte carte = main.getCarte(index);
        main.retirerCarte(index);
    
        // Échange de la carte avec la carte à la position du sorcier dans le continuum
        Carte carteContinuum = continuum.get(positionSorcier);
        continuum.set(positionSorcier, carte);
    
        // Ajouter la carte du continuum à la main du joueur
        main.ajouterCarte(carteContinuum,index);
        return carte;

    
    }
    public void jouer3Cartes(List<Carte> continuum,String Direction) {
        int positionSorcier = sorcier.getPositionSorcier();
        switch(Direction){
            case "Gauche":
                // Vérifier si on peut jouer 3 cartes à gauche
                if (positionSorcier >= 3) {
                    for (int i = positionSorcier - 3; i < positionSorcier; i++) {
                        jouerCarte(i, continuum);
                    }
                }
                break;
            case "Droite":
                // Vérifier si on peut jouer 3 cartes à droite
                if (positionSorcier + 3 < continuum.size()) {
                    for (int i = positionSorcier + 1; i <= positionSorcier + 3; i++) {
                        jouerCarte(i, continuum);
                    }
                }
                break;
        }
    }




 
    

}

