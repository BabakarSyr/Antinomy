package Modele;
import java.util.ArrayList;

public class IAFacile extends IA
{
    public IAFacile(Plateau p) 
    {
        super(p);
	}

    @Override
    public int calculPosInitiale()
    {
        ArrayList<Integer> positions =  this.plateau.positionsDepart();
        int positionChoisie = r.nextInt(positions.size());
        positionSorcier = positions.get(positionChoisie);
        return positionSorcier;
    }

    @Override
    public int choisirSens()
    {
        //Si on ne peut pas placer des cartes à gauche
        if (positionSorcier < 3)
        {
            //On les place à droite
            return 1;
        }
        //Sinon, si on ne peut pas placer des cartes à droite
        else if (positionSorcier > 5)
        {
            //On les place à gauche
            return 0;
        }
        //Sinon, on choisi où les placer àleatoirement
        else
        {
            return r.nextInt(2);
        }
    }

    @Override
	public ArrayList<Integer> joue() 
    {
        //Etape 1. Recuperer la position de l'adversaire
        int posAdversaire = plateau.getPositionSorcier(ordreAdversaire);

        ArrayList<Integer> cartes = new ArrayList<>();
        for (int i = 0; i < 3; i++)
        {
            cartes.add(i);
        }
        
        int mouvementChoisi;
        int carteChoisie;
        ArrayList<Integer> positions;
        ArrayList<Integer> choice = new ArrayList<Integer>();
        int resultatDuel;

        //Tant qu'il y a des cartes qu'on n'a pas encore testé
        while (cartes.size() !=  0)
        {
            //Etape 2. Choisir une carte aleatoirement
            carteChoisie = r.nextInt(cartes.size());

            //Etape 3. Obtenir tous les positions valides pour la carte actuelle
            positions = plateau.cartesAccessibles(main.get(carteChoisie));

            //Etape 4. Regarder si la position de l'adversaire est dans ce tableau
            if (positions.contains(posAdversaire))
            {
                //Etape 4a. Si oui, choisir ce mouvement
                mouvementChoisi = posAdversaire;
                resultatDuel = simulerMouvement(carteChoisie, mouvementChoisi);
                //Etape 4b. Si on gagne pas le duel, on l'enleve des positions valides
                if (resultatDuel != 1)
                {
                    positions.remove(posAdversaire);
                }
            }

            //Etape 5. Parmi les positions valides, recuperer celles qui entrainnent la formation d'un paradoxe
            ArrayList<Integer> paradoxPositions = peutFormerParadoxe(main.get(carteChoisie), positions);

            //Etape 6. S'il y a au moins une position valide qui entrainne la formation d'un paradoxe
            if (paradoxPositions.size() >= 1)
            {
                //Choisir aleatoirement une position et jouer ça
                mouvementChoisi = r.nextInt(paradoxPositions.size());
                mouvementChoisi = paradoxPositions.get(mouvementChoisi);
                choice.add(carteChoisie);
                choice.add(mouvementChoisi);
                choice.add(choisirSens());
                return choice;
            }
            //Etape 7. Sinon on passe à la carte suivante
            cartes.remove(carteChoisie);
        }
        
        //Etape 8. si aucune position ne satisfait pas nos contraintes choisir une carte et une position aleatoirement et joue ça
        carteChoisie = r.nextInt(3);
        positions = plateau.cartesAccessibles(main.get(carteChoisie));
        mouvementChoisi = r.nextInt(positions.size());
        mouvementChoisi = positions.get(mouvementChoisi);
        choice.add(carteChoisie);
        choice.add(mouvementChoisi);
        choice.add(choisirSens());
        return choice;
    }
}
