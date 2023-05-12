package Modele;
import java.util.*;

public class IAFacile extends IA
{
    public IAFacile(Jeu j) 
    {
        super(j);
	}

    @Override
    public void setPosInitiale()
    {
        ArrayList<Integer> positions =  this.plateau.positionsDepart();
        int positionChoisie = r.nextInt(positions.size());
        positionIA = positions.get(positionChoisie);
        jeu.deplacerSorcier(positionIA); 
    }

    @Override
	public ArrayList<Integer> joue() 
    {
        int mouvementChoisi;
        int carteChoisie;
        ArrayList<Integer> positions;
        this.setPosInitiale();
        ArrayList<Integer> choice = new ArrayList<Integer>();
        for (Carte c : joueurIA.getMain())
        {
            positions =  plateau.cartesAccessibles(c);
            ArrayList<Integer> positionsCopy = new ArrayList<>(positions);
            if (positions.size() != 0)
            {
                ArrayList<Integer> paradoxPositions = peutFormerParadoxe(c, positions);
                while (paradoxPositions.size() != 0)
                {
                    mouvementChoisi = r.nextInt(paradoxPositions.size());
                    mouvementChoisi = paradoxPositions.get(mouvementChoisi);
                    int resultatDuel = simulerMouvement(joueurIA.getIndiceCarte(c), mouvementChoisi);
                    if (resultatDuel == 1)
                    {
                        choice.add(joueurIA.getIndiceCarte(c));
                        choice.add(mouvementChoisi);
                        return choice;
                    }
                    else
                    {
                        positions.remove(paradoxPositions.get(mouvementChoisi));
                        paradoxPositions.remove(mouvementChoisi);
                    }
                }
                //paradoxPositions est vidée
                while (positionsCopy.size() != 0)
                {
                    mouvementChoisi = r.nextInt(positions.size());
                    mouvementChoisi = positions.get(mouvementChoisi);
                    int resultatDuel = simulerMouvement(joueurIA.getIndiceCarte(c), mouvementChoisi);
                    if (resultatDuel == 1)
                    {
                        choice.add(joueurIA.getIndiceCarte(c));
                        choice.add(mouvementChoisi);
                        return choice;
                    }
                    else
                    {
                        positions.remove(mouvementChoisi);
                    }
                }
            }
        }
        carteChoisie = r.nextInt(3);
        positions =  plateau.cartesAccessibles(joueurIA.getMain().get(carteChoisie));
        mouvementChoisi = r.nextInt(positions.size());
        mouvementChoisi = positions.get(mouvementChoisi);
        choice.add(carteChoisie);
        choice.add(mouvementChoisi);
        return choice;
    }
}
