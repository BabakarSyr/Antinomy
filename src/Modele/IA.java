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
    int ordreAdversaire;
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
            this.ordreAdversaire = 2;
        }
        else
        {
            this.ordreIA = 2;
            this.ordreAdversaire = 1;
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

    public ArrayList<Integer> peutFormerParadoxe (Carte carte, ArrayList<Integer> accessibles)
    {
        Jeu jeuClone = new Jeu(this.plateau);
        Plateau plateauClone = jeuClone.plateau();
        Joueur joueurIAClone = plateauClone.getJoueur(this.ordreIA);
        int indiceCarte = joueurIAClone.getIndiceCarte(carte);

        ArrayList<Integer> paradoxes = new ArrayList<>();
        for (Integer i : accessibles)
        {
            jeuClone.jouerCarte(indiceCarte, i);
            if (jeuClone.estParadoxe())
            {
                paradoxes.add(i);
            }
            jeuClone.echangerCarte(indiceCarte, i);
        }
        return paradoxes;
    }

    public int simulerMouvement(int indexCarteChoisie, int intexContinuum)
    {
        Jeu jeuClone = new Jeu(this.plateau);
        Plateau plateauClone = jeuClone.plateau();
        Joueur joueurIAClone = plateauClone.getJoueur(this.ordreIA);

        jeuClone.jouerCarte(indexCarteChoisie, intexContinuum);
        if (jeuClone.estDuel())
        {
            Joueur gagnant = jeuClone.meilleurMain();
            if (gagnant == joueurIAClone)
            {
                return 1;
            }
            else if (gagnant == plateauClone.getJoueur(this.ordreAdversaire))
            {
                return -1;
            }
        }
        return 0;
    }

    public void joue()
    {}
}