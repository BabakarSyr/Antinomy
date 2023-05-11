package Modele;

import java.util.Random;
import java.util.ArrayList;

public abstract class IA
{
    Random r;
    Jeu jeu;
    Plateau plateau;
    ArrayList<Integer> emplacementsAccessibles;
    ArrayList<Integer> paradoxPositions;
    Joueur joueurIA;
    int ordreIA;
    int positionIA;

    public IA(Jeu j)
    {
        this.r = new Random();
        this.jeu = j;
        this.plateau = this.jeu.plateau();
        emplacementsAccessibles = new ArrayList<>();
        paradoxPositions = new ArrayList<>();
        this.joueurIA = this.plateau.getJoueurParNom("IA");
        if (this.plateau.getJoueur(1).getNom().equals(new String("IA")))
        {
            this.ordreIA = 1;
        }
        else
        {
            this.ordreIA = 2;
        }
        setPosInitiale();
    }

    public void setPosInitiale()
    {
        ArrayList<Integer> positions =  this.plateau.positionsDepart();
        this.positionIA = positions.get(0);
        if (this.plateau.getJoueurActif() != this.joueurIA)
        {
            this.plateau.changerJoueurActif();
            this.jeu.deplacerSorcier(positionIA);
            this.plateau.changerJoueurActif();
        }
        else
        {
            this.jeu.deplacerSorcier(positionIA);
        }
    }

    public ArrayList<Integer> peutFormerParadoxe (Carte carte)
    {
        Jeu jeuClone = new Jeu(this.plateau);
        Plateau plateauClone = jeuClone.plateau();
        Joueur joueurIAClone = plateauClone.getJoueur(this.ordreIA);
        int indiceCarte = joueurIAClone.getIndiceCarte(carte);

        ArrayList<Integer> accessibles = plateauClone.cartesAccessibles(carte);
        ArrayList<Integer> paradoxes = new ArrayList<>();
        for (Integer i : accessibles)
        {
            jeuClone.deplacerSorcier(i);
            jeuClone.echangerCarte(indiceCarte, i);
            if (jeuClone.estParadoxe())
            {
                paradoxes.add(i);
            }
            jeuClone.echangerCarte(indiceCarte, i);
        }
        return paradoxes;
    }

    public void joue()
    {}
}