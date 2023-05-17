package Structures;

import java.util.ArrayList;

import Modele.Carte;
import Modele.Plateau;

public class Noeud
{
    private int Score;
    private Plateau etat;
    private Carte carteJoue;
    private int positionJoue;
    private ArrayList<Noeud> fils;

    public Noeud(Plateau p, Carte c, int position, int score)
    {
        this.etat = p;
        this.carteJoue = c;
        this.positionJoue = position;
        this.fils = new ArrayList<>();
        this.Score = score;
    }

    public int getScore()
    {
        return Score;
    }

    public void setScore(int score)
    {
        this.Score =  score;
    }

    public Plateau getEtatPlateau()
    {
        return etat;
    }

    public Carte getCarteJouee()
    {
        return carteJoue;
    }

    public int getPositionJouee()
    {
        return positionJoue;
    }

    public ArrayList<Noeud> getFils()
    {
        return fils;
    }

    public void ajouterFils(Noeud n)
    {
        fils.add(n);
    }
}