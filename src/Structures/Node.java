package Structures;

import java.util.ArrayList;

import Modele.Carte;
import Modele.Plateau;

public class Node
{
    private Plateau etat;
    private Carte carteJoue;
    private int positionJoue;
    private ArrayList<Node> fils;

    public Node(Plateau p, Carte c, int position)
    {
        this.etat = p;
        this.carteJoue = c;
        this.positionJoue = position;
        this.fils = new ArrayList<>();
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

    public ArrayList<Node> getFils()
    {
        return fils;
    }

    public void ajouterFils(Node n)
    {
        fils.add(n);
    }
}