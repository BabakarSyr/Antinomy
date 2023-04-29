package Modele;

import java.util.ArrayList;
import java.util.Collections;

public class MainDeCartes {
    ArrayList<Carte> cartes;

    public MainDeCartes() {
        this.cartes = new ArrayList<>();
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

}
