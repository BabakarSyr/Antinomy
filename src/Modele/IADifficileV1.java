package Modele;

import java.util.ArrayList;

public class IADifficileV1 extends IA
{
    public IADifficileV1()
    {
        super();
    }

    @Override
    public int calculPosInitiale()
    {
        //Etape 0: Initialisation
        ArrayList<ArrayList<Integer>> eligibles = new ArrayList<>();

        //Etape 1: Recuperer les positions de depart
        ArrayList<Integer> positions = this.plateau.positionsDepart();

        //Etape 2: Faire une copie du plateau qui servira aux calculs
        Plateau clonePlateau = plateau.clone();

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

    @Override
    public Coup joueCoup()
    {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joueCoup'");
    }
}