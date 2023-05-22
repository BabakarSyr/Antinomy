package Modele;

import java.util.ArrayList;
import java.util.List;

public interface Joueur
{
    public int getNombreCristaux();
    
    public void setCristal(int nb);
    public void ajouterCristal(int nb);
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
    public Coup joueCoup();
    public boolean estIA();
}