package Modele;

import java.util.ArrayList;
import java.util.Collections;

public class MainDeCartes {
    ArrayList<Carte> cartes;

    public MainDeCartes() {
        this.cartes = new ArrayList<>();
    }

    public MainDeCartes(ArrayList<Carte> arrayList) {
        this.cartes = arrayList;
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

    //Fonction qui ajoute une carte au debut de la main du joueur
    public void ajouterCarte(Carte carte, int index) {
        cartes.add(index, carte);
    }

    public void retirerCarte(int index) {
        cartes.remove(index);
    }

    //melanger les cartes de la main du joueur
    public void melangerCartes() {
        Collections.shuffle(cartes);
    }


    //Fonction qui recherche la valeur la plus faible des cartes entre les main du joueur et renvoie toutes les cartes avec cette valeur
    public ArrayList<Carte> getCartesPlusFaibleValeur() {
        ArrayList<Carte> cartesPlusFaibleValeur = new ArrayList<>();
        int valeurMin = 0;
        for (Carte carte : cartes) {
            if (carte.getValeur() < valeurMin) {
                valeurMin = carte.getValeur();
            }
        }
        for (Carte carte : cartes) {
            if (carte.getValeur() == valeurMin) {
                cartesPlusFaibleValeur.add(carte);
            }
        }
        return cartesPlusFaibleValeur;
    }


}
