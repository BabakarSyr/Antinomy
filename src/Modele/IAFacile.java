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
        List<Integer> positions = this.plateau.pos_carte_couleur_interdite();
        int numPositions = positions.size();
        int positionChoisie = r.nextInt(numPositions);
        this.positionIA = positions.get(positionChoisie);
        this.plateau.setPositionSorcier(positionIA, this.ordreJoueur); 
    }

    public void calculerEmplacementsAccessibles(Carte a)
    {
        if (this.joueurIA.sorcier.est_possible_aller_futur(a, plateau.getContinuum()))
        {
            emplacementsAccessibles.add(positionIA + a.getValeur());
        }
        if (this.joueurIA.sorcier.est_possible_aller_passe(a, plateau.getContinuum())) 
        {                        
            List<Integer> positionsPossibles = plateau.getJoueur(this.ordreJoueur).sorcier.Position_Possible_Passe(a, plateau.getContinuum());

            for (Integer e : positionsPossibles)
            {
                emplacementsAccessibles.add(e);
            }
        }
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
                calculerEmplacementsAccessibles(carteChoisie);
            }
        
            int emplacementChoisi = emplacementsAccessibles.get(r.nextInt(emplacementsAccessibles.size()));
            this.plateau.setPositionSorcier(emplacementChoisi, this.ordreJoueur);
            this.positionIA = emplacementChoisi;
            //Carte f = plateau.getJoueur(this.ordreJoueur).jouerCarte(indexCarteChoisie, this.plateau.getContinuum());
        }
    }

}
