package Structures;

import java.util.ArrayList;

import Modele.Carte;
import Modele.Plateau;

import Global.Configuration;

public class Noeud implements Cloneable
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

    public void setCarte(Carte c)
    {
        this.carteJoue = c;
    }

    public void setPositionJouee(int pos)
    {
        this.positionJoue = pos;
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

    public void setFils (ArrayList<Noeud> a)
    {
        this.fils = a;
    }

    @Override
    public Noeud clone()
    {
        try
        {
            Noeud cloneNoeud = (Noeud) super.clone();
            cloneNoeud.Score = this.Score;
            cloneNoeud.etat = this.etat.clone();
            cloneNoeud.carteJoue = this.carteJoue.clone();
            cloneNoeud.positionJoue = this.positionJoue;
            cloneNoeud.fils = new ArrayList<>();
            for (Noeud n : this.fils)
            {
                cloneNoeud.fils.add(n.clone());
            }
            return cloneNoeud;
        }
        catch (CloneNotSupportedException e)
        {
            Configuration.erreur("Bug interne, noeud non clonable");
            return null;
        }
    }
}