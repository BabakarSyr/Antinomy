package Modele;

import java.util.ArrayList;

import Structures.Noeud;

public class IADifficileV1 extends IA
{
    public IADifficileV1()
    {
        super("IA Difficile");
    }

    public IADifficileV1(Joueur j)
    {
        super(j);
    }

    @Override
    public int calculPosInitiale()
    {
        //Etape 0: Initialisation
        ArrayList<ArrayList<Integer>> eligibles = new ArrayList<>();

        //Etape 1: Recuperer les positions de depart
        ArrayList<Integer> positions = this.plateau.positionsDepart();

        //Etape 2: Faire une copie du plateau qui servira aux calculs
        Plateau clonePlateau = new Plateau(plateau);

        //Pour chaque position de depart:
        for (Integer a : positions)
        {
            //Etape 3: Positioner le sorcier à cette position
            clonePlateau.getJoueurParNom("IA").setPositionSorcier(a);

            //Pour chaque carte dans la main
            for (Carte c : this.main)
            {
                //Etape 4: Recuperer toutes les cases accessibles à partir de la position a avec la carte c
                ArrayList<Integer> accessibles = plateau.cartesAccessibles(c);

                //Etape 5: Parmi ces positions, recuperer celles qui permettent de former un paradoxe
                ArrayList<Integer> paradoxe = peutFormerParadoxe(c, accessibles);

                //S'il y a des positions qui forment des paradoxes
                if (!paradoxe.isEmpty())
                {
                    //Etape 6: Marque la combinaison (a,c,paradoxes) comme eligible
                    ArrayList<Integer> e = new ArrayList<>();
                    for (Integer i : paradoxe)
                    {
                        e.add(a);
                        e.add(main.indexOf(c));
                        e.add(i);
                        eligibles.add(e);
                        e.clear();
                    }
                }
            }
        }

        int positionChoisie;
        //Etape 7: Choisir une position eligible et le renvoyer
        switch (eligibles.size())
        {
            case 0:
                positionChoisie = r.nextInt(positions.size());
                return positions.get(positionChoisie);
            case 1:
                return eligibles.get(0).get(0);
            default:
                positionChoisie = r.nextInt(eligibles.size());
                return eligibles.get(positionChoisie).get(0);
        }
    }

    public int choisirSens(Plateau old, Plateau nouv)
    {
        int droite;
        //Si on ne peut pas placer des cartes à gauche
        if (positionSorcier < 3)
        {
            //On les place à droite
            return 8;
        }
        //Sinon, si on ne peut pas placer des cartes à droite
        else if (positionSorcier > 5)
        {
            //On les place à gauche
            return 0;
        }
        //Sinon, on choisi où les placer en fonction de l'ancienne àleatoirement
        else
        {
            Carte a = old.getContinuum().get(positionSorcier - 1);
            Carte b = nouv.getContinuum().get(positionSorcier - 1);
            if (a == b)
            {
                return positionSorcier - 1;
            }
            else
            {
                return positionSorcier + 1;
            }
        }
    }

    @Override
	public ArrayList<Integer> joue() 
    {
        ArrayList<Integer> choix = new ArrayList<>();

        if (positionSorcier==-1)
        {
            System.out.println("positionSorcier = " + positionSorcier);
            choix.add(calculPosInitiale());
            return choix;
        }
        else
        {
            //Step 1: Creer une arbre des configurations de profondeur 3
            Noeud arbreMinMax = minimax(this.plateau, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);

            //Step 2: Choisir le meuilleur movement en fonction du score stocké dans chque noeud
            ArrayList <Noeud> noeudsFils = arbreMinMax.getFils();

           int maxScore = Integer.MIN_VALUE;

            ArrayList<Noeud> meiulleursMovements = new ArrayList<>();
            for (Noeud n : noeudsFils)
            {
                int score = n.getScore();
                if (score > maxScore)
                {
                    maxScore = score;
                    meiulleursMovements.clear();
                    meiulleursMovements.add(n);
                }
                else if (score == maxScore)
                {
                    meiulleursMovements.add(n);
                }
            }

            Noeud selection = meiulleursMovements.get(r.nextInt(meiulleursMovements.size()));

            //Step 3: Retourner le movement choisi
            choix.add(this.getMain().indexOf(selection.getCarteJouee()));
            choix.add(selection.getPositionJouee());
            if (selection.getEtatPlateau().estParadoxe())
            {
                choix.add(choisirSens(this.plateau, selection.getEtatPlateau()));
            }
            return choix;
        }
    }

    @Override
    public Coup joueCoup()
    {
        return null;
    }
}