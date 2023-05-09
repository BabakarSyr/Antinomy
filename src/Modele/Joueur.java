package Modele;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import Modele.Carte.Couleur;



public class Joueur {
    String nom;
    MainDeCartes main;
    int nombreCristaux;
    public Sorcier sorcier;
    Codex codex;


    public Joueur(String nom, int nombreCristaux) {
        this.nom = nom;
        this.nombreCristaux = nombreCristaux;
        this.sorcier = new Sorcier(true);
        this.main = new MainDeCartes();
        this.codex = new Codex(null);
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
    public void setMain(MainDeCartes main) {
        this.main = main;
    }


    public Sorcier getSorcier() {
        return sorcier;
    }
    public void setSorcier(Sorcier sorcier) {
        this.sorcier = sorcier;
    }




    //Equivalent echanger carte
    public Carte jouerCarte(int index, List<Carte> continuum) {//index=[0-2]
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
        //Main du joueur
        
        
        int positionSorcier = sorcier.getPositionSorcier();
        switch(Direction){
            case "gauche":
                for(int j=0;j<3;j++){

                    for (int i = positionSorcier - 3; i < positionSorcier; i++) {
                        
                       
                            jouerCarte(j, continuum);
                        
                    }
                }
                
                
                break;
            case "droite":
                for(int j=0;j<3;j++){
              
                    for (int i = positionSorcier + 1; i <= positionSorcier + 3; i++) {
                        
                      
                            jouerCarte(j, continuum);
                    }
                }
                break;
        }
    }
    
    public int totalValeurMain() {
        int total = 0;
        for (Carte carte : main.getCartes()) {
            total += carte.getValeur();
        }
        return total;
    }


    //Trie les acrte dans la main du joeur selon leur valeur
    public void trierCartes(MainDeCartes main) {
         // Trier la main par valeur
            Collections.sort(main.getCartes(), new Comparator<Carte>() {
                @Override
                public int compare(Carte carte1, Carte carte2) {
                    return carte1.getValeur() - carte2.getValeur();
                }
            });
    }


   /* 
    public Carte getCarteNonValeurMin() {
        int valeurMin = 0;
        Carte carteUnique = null;
        for (Carte carte : main.getCartes()) {
            if (carte.getValeur() < valeurMin) {
                valeurMin = carte.getValeur();
            }
        }
        if(main.getCarte(0).getValeur()==main.getCarte(1).getValeur() && main.getCarte(0).getValeur()==valeurMin){
            carteUnique=main.getCarte(2);
        }
        else if(main.getCarte(0).getValeur()==main.getCarte(2).getValeur() && main.getCarte(0).getValeur()==valeurMin){
            carteUnique=main.getCarte(1);
        }
        else if(main.getCarte(1).getValeur()==main.getCarte(2).getValeur() && main.getCarte(1).getValeur()==valeurMin){
            carteUnique=main.getCarte(0);
        }
        
        return carteUnique;
       
    }

   
    public Carte getCarteFormeDifferente() {
        Carte carteUnique = null;
        if (main.getCarte(0).getForme() == main.getCarte(1).getForme()) {
            carteUnique = main.getCarte(2);
        }
        else if (main.getCarte(0).getForme() == main.getCarte(2).getForme()) {
            carteUnique = main.getCarte(1);
        }
        else if (main.getCarte(1).getForme() == main.getCarte(2).getForme()) {
            carteUnique = main.getCarte(0);
        }
        return carteUnique;
    }

    
    public Carte getCarteCouleurDifferente() {
        Carte carteUnique = null;
       
        if (main.getCarte(0).getCouleur() == main.getCarte(1).getCouleur() && main.getCarte(0).getCouleur() != codex.getCouleurInterdite()) 
            carteUnique = main.getCarte(2);
           
        else if (main.getCarte(0).getCouleur() == main.getCarte(2).getCouleur() && main.getCarte(0).getCouleur() != codex.getCouleurInterdite()) 
            carteUnique = main.getCarte(1);
            
        
        else if (main.getCarte(1).getCouleur() == main.getCarte(2).getCouleur() && main.getCarte(1).getCouleur() != codex.getCouleurInterdite()) 
            carteUnique = main.getCarte(0);

        return carteUnique;
           
    }*/

    public List<Couleur> getAllCouleurMain(){
        List<Couleur> couleurs = new ArrayList<>();
        for (Carte carte : main.getCartes()) {
            couleurs.add(carte.getCouleur());
        }
        return couleurs;
    }

    public List<Integer> getAllValeurMain(){
        List<Integer> valeurs = new ArrayList<>();
        for (Carte carte : main.getCartes()) {
            valeurs.add(carte.getValeur());
        }
        return valeurs;
       
    }
    public List<Forme> getAllFormeMain(){
        List<Forme> formes = new ArrayList<>();
        for (Carte carte : main.getCartes()) {
            formes.add(carte.getForme());
        }
        return formes;
       
    }



    public Joueur copy() {
        Joueur clone = new Joueur(this.nom, this.nombreCristaux);
        clone.sorcier = this.sorcier;
        clone.main = new MainDeCartes(new ArrayList<>(this.main.getCartes()));
        clone.codex = this.codex;
        return clone;

    }



}

