package Modele;

import java.util.ArrayList;
import java.util.List;

public interface Joueur
{
    public int getNombreCristaux();
    public void ajouterCristaux();
   
    public void ajouterCristaux(int nombreCristaux);
    public void retirerCristaux();
    public void retirerCristaux(int nombreCristaux);
    public boolean volerCristal(Joueur autreJoueur);
    public String getNom();
    public void setNom(String nom);

    public ArrayList<Carte> getMain();
    public void setMain(ArrayList<Carte> main);
    
    public void melangerMain();
    public int getIndiceCarte(Carte c);

    //Equivalent echanger carte
    public Carte jouerCarte(int index, List<Carte> continuum);
    public int getPositionSorcier();
    public void setPositionSorcier(int position);

    public boolean getSensDuTemps();
    public void setSensDuTemps(boolean sensDuTemps);
    public ArrayList<Integer> joue();
    public boolean estIA();
}

