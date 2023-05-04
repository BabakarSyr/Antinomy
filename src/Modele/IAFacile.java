package Modele;
import java.util.*;
import Modele.Plateau;
import Modele.Jeu;

public class IAFacile extends IA 
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
        this.plateau = this.jeu.getPlateau();
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
        
    }

	public void joue() 
    {
        String temps;
        int indexCarteChoisie = r.nextInt(3); // Choisir une carte au hasard  
        Carte carteChoisie = this.joueurIA.getMain().getCartes().get(indexCarteChoisie);
        
        calculerEmplacementsAccessibles(carteChoisie);

        //choisis une autre carte de la main si pas de déplacements dans le passe ni le futur
        while (plateau.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, plateau.getContinuum()) == plateau.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, plateau.getContinuum()) == false)
        {
            indexCarteChoisie = r.nextInt(3); // Choisir une carte au hasard  
            carteChoisie = plateau.joueurActif.getMain().getCartes().get(indexCarteChoisie);
        }
        if (!(plateau.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, plateau.getContinuum())))
        {
            temps = "passe";
        }
        else if (!(plateau.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, plateau.getContinuum())))
        {
            temps = "futur";
        }
        else
        {
            temps = r.nextBoolean() ? "futur" : "passe"; // Choisir un temps au hasar
        }
        switch (temps) 
        {
            case "futur":
                if (plateau.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, plateau.getContinuum()))
                {
                    plateau.joueurActif.sorcier.deplacerFutur(carteChoisie, plateau.getContinuum());
                    plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.getContinuum());
                }
                break;
            case "passe":
                if (plateau.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, plateau.getContinuum())) 
                {                        
                    List<Integer> positionsPossibles = plateau.joueurActif.sorcier.Position_Possible_Passe(carteChoisie, plateau.getContinuum());
                    int position = positionsPossibles.get(r.nextInt(positionsPossibles.size()));
                    //mettre à jour la position du sorcier
                    plateau.joueurActif.sorcier.setPositionSorcier(position);
                    //jouer la carte
                    plateau.joueurActif.jouerCarte(indexCarteChoisie, plateau.getContinuum());
                }
                break;
        }
    }

}
