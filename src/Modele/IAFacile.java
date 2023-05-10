package Modele;
import java.util.*;

public class IAFacile 
{
    Random r;
    Jeu jeu;
    Plateau plateau;
    ArrayList<Integer> emplacementsAccessibles;
    Joueur joueurIA;
    int ordreJoueur;
    int positionIA;

    public IAFacile(Jeu j) 
    {
		r = new Random();
        this.jeu = j;
        this.plateau = this.jeu.plateau();
        this.emplacementsAccessibles = new ArrayList<>();
        this.joueurIA = plateau.getJoueurParNom(new String("IA Facile"));
        this.positionIA = 0;
        if (plateau.getJoueur(1).getNom() == this.joueurIA.getNom())
        {
            this.ordreJoueur = 1;
        }
        else
        {
            this.ordreJoueur = 2;
        }
	}

    public void setPosInitiale()
    {
        List<Integer> positions = this.plateau.positionsDepartPossible();
        int numPositions = positions.size();
        int positionChoisie = r.nextInt(numPositions);
        this.positionIA = positions.get(positionChoisie);
        this.jeu.deplacerSorcier(positionIA); 
    }


	public void joue() 
    {
        int indexCarteChoisie = 0; 
        Carte carteChoisie;
        
        if (plateau.getJoueurActif() == this.joueurIA)
        {
            this.emplacementsAccessibles.clear();
            while (this.emplacementsAccessibles.isEmpty())
            {
                indexCarteChoisie = r.nextInt(3);
                carteChoisie = this.joueurIA.getMain().getCartes().get(indexCarteChoisie);
                plateau.cartesAccessibles(carteChoisie);
            }
        
            int emplacementChoisi = emplacementsAccessibles.get(r.nextInt(emplacementsAccessibles.size()));
            //TODO verifier que ca fonctionne
            this.jeu.deplacerSorcier(emplacementChoisi);
            this.positionIA = emplacementChoisi;
        }
    }

}
