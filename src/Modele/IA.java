package Modele;

import java.util.Random;

import Structures.Noeud;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public abstract class IA implements Joueur
{
    //Attributs liées au joueur de l'IA
    String nom;
    ArrayList<Carte> main;
    int nombreCristaux;
    boolean sensDuTemps; // si sensDuTemps=true, le futur est vers la droite du plateau
    int positionSorcier;

    //Attributs liées à l'IA
    Random r;
    Jeu jeu;
    Plateau plateau;
    ArrayList<Integer> emplacementsAccessibles;
    ArrayList<Integer> paradoxPositions;
    int ordreIA;
    int ordreAdversaire;
    Noeud treeConfig;


    public IA()
    {
        setNom("IA");
        this.nombreCristaux = 0;
        this.main = new ArrayList<>();
        this.sensDuTemps = true;
        this.positionSorcier = -1;
    }

    public IA(boolean sensDuTemps)
    {
        setNom("IA");
        this.nombreCristaux = 0;
        this.main = new ArrayList<>();
        this.sensDuTemps = sensDuTemps;
        this.positionSorcier = -1;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public String getNom()
    {
        return this.nom;
    }

    public void setMain(ArrayList<Carte> main)
    {
        this.main = main;
    }

    public ArrayList<Carte> getMain()
    {
        return this.main;
    }
    
    public void melangerMain()
    {
        Collections.shuffle(main);
    }

    public void setPositionSorcier(int position)
    {
        this.positionSorcier = position;
    }

    public int getPositionSorcier()
    {
        return this.positionSorcier;
    }

    public void setSensDuTemps(boolean sensDuTemps)
    {
        this.sensDuTemps = sensDuTemps;
    }
    
    public boolean getSensDuTemps()
    {
        return this.sensDuTemps;
    }

    public int getNombreCristaux()
    {
        return this.nombreCristaux;
    }

    public void ajouterCristaux()
    {
        this.nombreCristaux++;
    }
   
    public void ajouterCristaux(int nombreCristaux)
    {
        this.nombreCristaux += nombreCristaux;
    }

    public void retirerCristaux()
    {
        this.nombreCristaux--;
    }

    public void retirerCristaux(int nombreCristaux)
    {
        this.nombreCristaux -= nombreCristaux;
    }

    public boolean volerCristal(Joueur autreJoueur)
    {
        if (autreJoueur.getNombreCristaux() > 0)
        {
            //On ajoute un cristal au joueur actuel 
            this.ajouterCristaux();
            //on en retire un à l'autre joueur
            autreJoueur.retirerCristaux();
            return true;
        }
        else
        {
            return false;
        }
    }

    public int getIndiceCarte(Carte c)
    {
        for (int i = 0; i < this.main.size(); i++)
        {
            if (this.main.get(i) == c)
            {
                return i;
            }
        }
        return -1;
    }

    public void initIA(Plateau p)
    {
        this.r = new Random();
        this.jeu = new Jeu (p);
        this.plateau = p;
        emplacementsAccessibles = new ArrayList<>();
        paradoxPositions = new ArrayList<>();
        if (this.plateau.getJoueur(1).getNom().equals(this.nom))
        {
            this.ordreIA = 1;
            this.ordreAdversaire = 2;
        }
        else
        {
            this.ordreIA = 2;
            this.ordreAdversaire = 1;
        }
        calculPosInitiale();
    }

    public int calculPosInitiale()
    {
        return this.plateau.positionsDepart().get(0);
    }

    //Equivalent echanger carte
    public Carte jouerCarte(int index, List<Carte> continuum)
    {
        Carte carte = main.get(index);
        main.remove(index);
    
        // Échange de la carte avec la carte à la position du sorcier dans le continuum
        Carte carteContinuum = continuum.get(positionSorcier);
        continuum.set(positionSorcier, carte);
    
        // Ajouter la carte du continuum à la main du joueur
        main.add(index, carteContinuum);
        return carte;
    }

    //Retourne vrai si tous les cartes de la main du joueur sont de la meme forme
    public boolean mainMemeForme(ArrayList<Carte> copieMain)
    {
        return (copieMain.get(0).getForme() == copieMain.get(1).getForme() && copieMain.get(1).getForme()== copieMain.get(2).getForme());
    }

    //Retourne vrai si tous les cartes de la main du joueur sont de la meme couleur
    public boolean mainMemeCouleur(ArrayList<Carte> copieMain)
    {
        return (copieMain.get(0).getCouleur() == copieMain.get(1).getCouleur() && copieMain.get(1).getCouleur()== copieMain.get(2).getCouleur());
    }

    //Retourne vrai si tous les cartes de la main du joueur sont de la meme valeur
    public boolean mainMemeValeur(ArrayList<Carte> copieMain)
    {
        return (copieMain.get(0).getValeur() == copieMain.get(1).getValeur() && copieMain.get(1).getValeur()== copieMain.get(2).getValeur());
    }

    //Retourne vrai si tous les cartes de la main du joueur ne sont pas de la couleur bannie
    public boolean mainCouleurBannie(ArrayList<Carte> copieMain)
    {
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        return copieMain.get(0).getCouleur() !=  couleurInterdite && copieMain.get(1).getCouleur() != couleurInterdite && copieMain.get(3).getCouleur() != couleurInterdite;
    }

    public int valeurMain()
    {
        int valeurMain = 0;
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        for (Carte carte : main)
        {
            valeurMain += carte.getValeur(couleurInterdite);
        }
        return valeurMain;
    }

    public ArrayList<Integer> peutFormerParadoxe (Carte carte, ArrayList<Integer> accessibles)
    {
        ArrayList<Integer> paradoxes = new ArrayList<>();
        ArrayList<Carte> copieMain = new ArrayList<>();

        for (Carte c : main)
        {
            copieMain.add(c);
        }
        copieMain.remove(carte);
        for (Integer e : accessibles)
        {
            Carte but = plateau.getContinuum().get(e);
            copieMain.add(but);
            if (mainCouleurBannie(copieMain) && (mainMemeCouleur(copieMain) || mainMemeForme(copieMain) || mainMemeValeur(copieMain)))
            paradoxes.add(e);
            copieMain.remove(but);
        }
        return paradoxes;
    }

    public int simulerMouvement(int indexCarteChoisie, int indexContinuum)
    {
        if (plateau.getJoueur(ordreAdversaire).getPositionSorcier() == indexContinuum)
        {
            Joueur gagnant = jeu.meilleurMain();
            if (gagnant.getNom().equals(nom))
            {
                return 1;
            }
            else if (gagnant.getNom().equals(plateau.getJoueur(ordreAdversaire).getNom()))
            {
                return -1;
            }
            return 0;
        }
        return 1;
    }

    public ArrayList<Integer> joue()
    {
        return null;
    }

    public boolean estIA()
    {
        return true;
    }

    public int choisirSens()
    {
        return 0;
    }

    public int calculScore()
    {
        String gagnant = jeu.nomVainqueur();
        if (gagnant != null)
        {
            if (gagnant.equals(this.nom))
            {
                return +1000;
            }
            else
            {
                return -1000;
            }
        }
        int score = 0;
        for (Carte c : main)
        {
            score += c.getValeur(plateau.codex.couleurInterdite);
        }
        if (jeu.estDuel())
        {
            if (score > plateau.valeurMain(plateau.joueurInactif()))
            {
                score += 200;
            }
            else
            {
                score -= 200;
            }
        }
        if (jeu.estParadoxe())
        {
            score += 100;
        }
        return score;
    }

    public Noeud minimax(int profondeur, boolean maxJoueur, int alpha, int beta)
    {
        if (profondeur == 0)
        {
            return new Noeud(plateau, )
                calculScore(), )
        }













        if (profondeur == 0)
        {
            return;
        }
        for (Carte c : plateau.joueurActif().getMain())
        {
            for (int pos : plateau.cartesAccessibles(c))
            {
                Plateau plateauClone = plateau.clone();
                Joueur joueurClone = plateauClone.getJoueurParNom(jou)
            }
        }
    }
}