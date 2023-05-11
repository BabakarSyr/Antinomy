package Modele;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;

public class Joueur {
    String nom;
    ArrayList<Carte> main;
    int nombreCristaux;
    public Sorcier sorcier;


    public Joueur(String nom, int nombreCristaux) {
        this.nom = nom;
        this.nombreCristaux = nombreCristaux;
        this.sorcier = new Sorcier(true);
        this.main = new ArrayList<>();
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

    public ArrayList<Carte> getMain() {
        return main;
    }


    public Sorcier sorcier() {
        return sorcier;
    }
    public void setSorcier(Sorcier sorcier) {
        this.sorcier = sorcier;
    }

    public void melangerMain(){
        Collections.shuffle(main);
    }


    //Equivalent echanger carte
    public Carte jouerCarte(int index, List<Carte> continuum) {//index=[0-2]
        int positionSorcier = sorcier.getPositionSorcier();
        Carte carte = main.get(index);
        main.remove(index);
    
        // Échange de la carte avec la carte à la position du sorcier dans le continuum
        Carte carteContinuum = continuum.get(positionSorcier);
        continuum.set(positionSorcier, carte);
    
        // Ajouter la carte du continuum à la main du joueur
        main.add(index, carteContinuum);
        return carte;

    
    }


    public int totalValeurMain() {
        int total = 0;
        for (Carte carte : main) {
            total += carte.getValeur();
        }
        return total;
    }

    public boolean DeuxCarteMemeProp() {
        boolean mainValide = false;
        int val_carte1 = main.get(0).getValeur();
        Forme forme_carte1 = main.get(0).getForme();
        Couleur couleur_carte1 = main.get(0).getCouleur();
        for(int i=1;i<3;i++){
            if(main.get(i).getValeur()==val_carte1 || main.get(i).getForme()==forme_carte1 || main.get(i).getCouleur()==couleur_carte1){
                mainValide=true;
            }
        }
        return mainValide;
    }

    public Joueur copie() {
        Joueur clone = new Joueur(nom,nombreCristaux);
        clone.sorcier =sorcier.copie();
        clone.main = new ArrayList<>(main);
        return clone;

    }


 
    

}

