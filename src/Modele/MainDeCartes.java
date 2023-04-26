package Modele;

import java.util.ArrayList;
import java.util.Collections;

public class MainDeCartes {
    ArrayList<Carte> cartes;

    public MainDeCartes() {
        cartes = new ArrayList<>();
    }

    public ArrayList<Carte> getCartes() {
        return cartes;
    }

    public void setCartes(ArrayList<Carte> cartes) {
        this.cartes = cartes;
    }

    public Carte getCarte(int index) {
        return cartes.get(index);
    }

    public void ajouterCarte(Carte carte) {
        cartes.add(carte);
    }

    public void retirerCarte(int index) {
        cartes.remove(index);
    }

    public int taille() {
        return cartes.size();
    }

    //melanger les cartes de la main du joueur
    public void melangerCartes() {
        Collections.shuffle(cartes);
    }


    /* 
    public void afficherCartes() {
        for (Carte carte : cartes) {
            System.out.println(carte);
        }
    }
    
    public void afficherCarte(int index) {
        System.out.println(cartes.get(index));
    }
    public void modifierCarte(int index, Carte carte) {
        cartes.set(index, carte);
    }

    public boolean possedeCarte(Carte carteRecherchee) {
        return cartes.contains(carteRecherchee);
    }*/
}
