package Modele;

import java.util.Random;
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
    public boolean mainMemeForme()
    {
        return (main.get(0).getForme() == main.get(1).getForme() && main.get(1).getForme()== main.get(2).getForme());
    }

    //Retourne vrai si tous les cartes de la main du joueur sont de la meme couleur
    public boolean mainMemeCouleur()
    {
        return (main.get(0).getCouleur() == main.get(1).getCouleur() && main.get(1).getCouleur()== main.get(2).getCouleur());
    }

    //Retourne vrai si tous les cartes de la main du joueur sont de la meme valeur
    public boolean mainMemeValeur()
    {
        return (main.get(0).getValeur() == main.get(1).getValeur() && main.get(1).getValeur()== main.get(2).getValeur());
    }

    //Retourne vrai si tous les cartes de la main du joueur ne sont pas de la couleur bannie
    public boolean mainCouleurBannie()
    {
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        return main.get(0).getCouleur() !=  couleurInterdite && main.get(1).getCouleur() != couleurInterdite && main.get(3).getCouleur() != couleurInterdite;
    }

    public ArrayList<Integer> peutFormerParadoxe (Carte carte, ArrayList<Integer> accessibles)
    {
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        ArrayList<Carte> cartes = this.getMain();
        Carte c;
    
        boolean memeCouleur = true;
        boolean memeForme = true;
        boolean memeValeur = true;
        boolean memeCouleur2, memeForme2, memeValeur2;
        ArrayList<Integer> paradoxes = new ArrayList<>();

        if (cartes.get(0)==carte)
        {
            c=cartes.get(1);
        }
        else
        {
            c=cartes.get(0);
        }
        for (int i = 0; i < cartes.size(); i++) {
            if (cartes.get(i)!=carte && cartes.get(i).getCouleur() == couleurInterdite) {
                return null;
            }
            memeCouleur = memeCouleur&(cartes.get(i).getCouleur() == c.getCouleur());
            memeForme = memeForme&(cartes.get(i).getForme() == c.getForme());
            memeValeur = memeValeur&(cartes.get(i).getValeur() == c.getValeur());
            
        }
        for (int j : accessibles)
        {
            memeCouleur2=memeCouleur& (jeu.plateau.continuum.get(j).getCouleur()==c.getCouleur());
            memeForme2=memeForme&(jeu.plateau.continuum.get(j).getValeur()==c.getValeur());
            memeValeur2=memeValeur&(jeu.plateau.continuum.get(j).getCouleur()==c.getCouleur());
            if (memeCouleur2 || memeForme2 || memeValeur2)
            {
                paradoxes.add(j);
            }
        }
        return paradoxes;
    }

    public int simulerMouvement(int indexCarteChoisie, int indexContinuum)
    {
        Plateau plateauClone = null;
        try
        {
            plateauClone = (Plateau) this.plateau.clone();
        }
        catch (CloneNotSupportedException e)
        {
            e.printStackTrace();
        }
        Jeu jeuClone = new Jeu(plateauClone);
        Joueur joueurIAClone = plateauClone.getJoueur(this.ordreIA);
        jeuClone.jouerCarte(indexCarteChoisie, indexContinuum);
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
            else if (gagnant ==  null)
            {
                return 0;
            }
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
}