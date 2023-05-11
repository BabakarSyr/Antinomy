package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Joueur {
    String nom;
    ArrayList<Carte> main;
    int nombreCristaux;
    boolean sensDuTemps; // si sensDuTemps=true, le futur est vers la droite du plateau
    int positionSorcier;

    //////////////////////
    //// CONSTRUCTEUR ////
    //////////////////////

    public Joueur() {
        this.nom = "";
        this.nombreCristaux = 0;
        this.main = new ArrayList<>();
        this.sensDuTemps = true;
        this.positionSorcier=-1;
    }

    public Joueur(String nom) {
        this.nom = nom;
        this.nombreCristaux = 0;
        this.main = new ArrayList<>();
        this.sensDuTemps = true;
        this.positionSorcier=-1;
    }

    public Joueur(String nom, int nombreCristaux) {
        this.nom = nom;
        this.nombreCristaux = nombreCristaux;
        this.main = new ArrayList<>();
        this.sensDuTemps = true;
        this.positionSorcier=-1;
    }

    public Joueur(String nom, int nombreCristaux, boolean sensDuTemps, int posSorcier) {
        this.nom = nom;
        this.nombreCristaux = nombreCristaux;
        this.main = new ArrayList<>();
        this.sensDuTemps = sensDuTemps;
        this.positionSorcier=posSorcier;
    }

    ///////////////////////////
    ////  methodes joueur  ////
    ///////////////////////////

    public int getNombreCristaux() {
        return nombreCristaux;
    }

    public void ajouterCristaux() {
        this.nombreCristaux ++;
    }
   
    public void ajouterCristaux(int nombreCristaux) {
        this.nombreCristaux += nombreCristaux;
    }

    public void retirerCristaux() {
        this.nombreCristaux --;
    }

    public void retirerCristaux(int nombreCristaux) {
        this.nombreCristaux -= nombreCristaux;
    }
    
    //Voler un cristal à un autre joueur
    public boolean volerCristal(Joueur autreJoueur) {
        if (autreJoueur.getNombreCristaux() > 0) {
            //On ajoute un cristal au joueur actuel 
            this.ajouterCristaux();
            //on en retire un à l'autre joueur
            autreJoueur.retirerCristaux();
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

    public void melangerMain(){
        Collections.shuffle(main);
    }

    public int getIndiceCarte(Carte c)
    {
        for (int i = 0; i < this.main.size(); i++)
        {
            if (this.main.get(i) == c)
            {
                return i;
            }
        }
        return -1;
    }


    //Equivalent echanger carte
    public Carte jouerCarte(int index, List<Carte> continuum) {//index=[0-2]
        int positionSorcier = getPositionSorcier();
        Carte carte = main.get(index);
        main.remove(index);
    
        // Échange de la carte avec la carte à la position du sorcier dans le continuum
        Carte carteContinuum = continuum.get(positionSorcier);
        continuum.set(positionSorcier, carte);
    
        // Ajouter la carte du continuum à la main du joueur
        main.add(index, carteContinuum);
        return carte;

    
    }


    ///////////////////////////
    //// methodes sorcier  ////
    ///////////////////////////

    public int getPositionSorcier() 
    {
        return this.positionSorcier;
    }
 
    public boolean getSensDuTemps() 
    {
        return this.sensDuTemps;
    }
 
    public void setSensDuTemps(boolean sensDuTemps) 
    {
        this.sensDuTemps = sensDuTemps;
    }

}

