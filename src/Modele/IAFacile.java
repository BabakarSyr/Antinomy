package Modele;
import java.util.ArrayList;

public class IAFacile extends IA
{
    public IAFacile() 
    {
        super("IA Facile");
	}

    public IAFacile(Joueur j)
    {
        super(j);
    }

    @Override
    public int calculPosInitiale()
    {
        ArrayList<Integer> positions =  this.plateau.positionsDepartPossible();
        System.out.println("nombre de positions accessibles : "+positions.size());
        int positionChoisie = r.nextInt(positions.size());
        return positions.get(positionChoisie);
    }

    @Override
    public int choisirSens()
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
        //Sinon, on choisi où les placer àleatoirement
        else
        {
            droite = r.nextInt(2);
            if (droite == 1)
            {
                return 8;
            }
            else
            {
                return 0;
            }
        }
    }

    @Override
	public ArrayList<Integer> joue() 
    {
        int mouvementChoisi;
        int carteChoisie;
        ArrayList<Integer> positions;
        ArrayList<Integer> tourIA = new ArrayList<Integer>();
        boolean resultatDuel;
        ArrayList<Integer> paradoxPositions = new ArrayList<Integer>();


        if (positionSorcier==-1)
        {
            System.out.println("positionSorcier = "+positionSorcier);
            tourIA.add(calculPosInitiale());
        }
        else
        {
            //Etape 1. Recuperer la position de l'adversaire
            Integer posAdversaire = plateau.getPositionSorcier(ordreAdversaire);

            ArrayList<Integer> cartes = new ArrayList<>();
            for (int i = 0; i < 3; i++)
            {
                cartes.add(i);
            }
            

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
                    resultatDuel=plateau.valeurMain(plateau.getJoueur(ordreIA))<jeu.plateau.valeurMain(plateau.getJoueur(ordreAdversaire));
                    //resultatDuel = simulerMouvement(carteChoisie, mouvementChoisi);
                    //Etape 4b. Si on gagne pas le duel, on l'enleve des positions valides
                    if (!resultatDuel)
                    {
                        positions.remove(posAdversaire);
                    }
                }

                //Etape 5. Parmi les positions valides, recuperer celles qui entrainnent la formation d'un paradoxe
                paradoxPositions = peutFormerParadoxe(main.get(carteChoisie), positions);

                //Etape 6. S'il y a au moins une position valide qui entrainne la formation d'un paradoxe
                if (paradoxPositions!=null)
                {

                    if (!paradoxPositions.isEmpty())
                    {
                        //Choisir aleatoirement une position et jouer ça
                        mouvementChoisi = r.nextInt(paradoxPositions.size());
                        mouvementChoisi = paradoxPositions.get(mouvementChoisi);
                        tourIA.add(carteChoisie);
                        tourIA.add(mouvementChoisi);
                        tourIA.add(choisirSens());
                        return tourIA;
                    }
                }
                //Etape 7. Sinon on passe à la carte suivante
                cartes.remove(carteChoisie);
            }
            
            //Etape 8. si aucune position ne satisfait pas nos contraintes choisir une carte et une position aleatoirement et joue ça
            carteChoisie = r.nextInt(3);
            positions = plateau.cartesAccessibles(main.get(carteChoisie));
            mouvementChoisi = r.nextInt(positions.size());
            mouvementChoisi = positions.get(mouvementChoisi);
            tourIA.add(carteChoisie);
            tourIA.add(mouvementChoisi);
            tourIA.add(choisirSens());
        }
        return tourIA;
    }

    @Override
	public Coup joueCoup() 
    {
        int mouvementChoisi;
        int carteChoisie;
        ArrayList<Integer> positions;
        Coup choix = new Coup();
        boolean resultatDuel;
        ArrayList<Integer> paradoxPositions = new ArrayList<Integer>();


        if (positionSorcier==-1)
        {
            System.out.println("positionSorcier = "+positionSorcier);
            choix.indiceCarteContinuum=calculPosInitiale();
        }
        else
        {
            //Etape 1. Recuperer la position de l'adversaire
            Integer posAdversaire = plateau.getPositionSorcier(ordreAdversaire);

            ArrayList<Integer> cartes = new ArrayList<>();
            for (int i = 0; i < 3; i++)
            {
                cartes.add(i);
            }
            

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
                    resultatDuel=plateau.valeurMain(plateau.getJoueur(ordreIA))<jeu.plateau.valeurMain(plateau.getJoueur(ordreAdversaire));
                    //resultatDuel = simulerMouvement(carteChoisie, mouvementChoisi);
                    //Etape 4b. Si on gagne pas le duel, on l'enleve des positions valides
                    if (!resultatDuel)
                    {
                        positions.remove(posAdversaire);
                    }
                }

                //Etape 5. Parmi les positions valides, recuperer celles qui entrainnent la formation d'un paradoxe
                paradoxPositions = peutFormerParadoxe(main.get(carteChoisie), positions);

                //Etape 6. S'il y a au moins une position valide qui entrainne la formation d'un paradoxe
                if (paradoxPositions!=null)
                {

                    if (!paradoxPositions.isEmpty())
                    {
                        //Choisir aleatoirement une position et jouer ça
                        mouvementChoisi = r.nextInt(paradoxPositions.size());
                        mouvementChoisi = paradoxPositions.get(mouvementChoisi);
                        choix.indiceCarteJouee=carteChoisie;
                        choix.indiceCarteContinuum=mouvementChoisi;
                        choix.indiceParadoxe=choisirSens();
                        return choix;
                    }
                }
                //Etape 7. Sinon on passe à la carte suivante
                cartes.remove(carteChoisie);
            }
            
            //Etape 8. si aucune position ne satisfait pas nos contraintes choisir une carte et une position aleatoirement et joue ça
            carteChoisie = r.nextInt(3);
            positions = plateau.cartesAccessibles(main.get(carteChoisie));
            mouvementChoisi = r.nextInt(positions.size());
            mouvementChoisi = positions.get(mouvementChoisi);
            choix.indiceCarteJouee=carteChoisie;
            choix.indiceCarteContinuum=mouvementChoisi;
            choix.indiceParadoxe=choisirSens();
        }
        return choix;
    }
}
