package Modele;
import java.util.*;
import Modele.Plateau;
import Modele.Jeu;

public class IAFacile extends IA 
{
    Random r;
    Jeu jeu;
    public IAFacile(Jeu j) 
    {
        this.jeu = j;
		r = new Random();
	}


    //@Override
	public void joue() 
    {
        Plateau p = jeu.getPlateau();
        String temps;
        int indexCarteChoisie = r.nextInt(3); // Choisir une carte au hasard  
        Carte carteChoisie = p.joueurActif.getMain().getCartes().get(indexCarteChoisie);
        //choisis une autre carte de la main si pas de déplacements dans le passe ni le futur
        while (p.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, p.getContinuum()) == p.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, p.getContinuum()) == false)
        {
            indexCarteChoisie = r.nextInt(3); // Choisir une carte au hasard  
            carteChoisie = p.joueurActif.getMain().getCartes().get(indexCarteChoisie);
        }
        if (!(p.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, p.getContinuum())))
        {
            temps = "passe";
        }
        else if (!(p.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, p.getContinuum())))
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
                if (p.joueurActif.sorcier.est_possible_aller_futur(carteChoisie, p.getContinuum()))
                {
                    p.joueurActif.sorcier.deplacerFutur(carteChoisie, p.getContinuum());
                    p.joueurActif.jouerCarte(indexCarteChoisie, p.getContinuum());
                }
                break;
            case "passe":
                if (p.joueurActif.sorcier.est_possible_aller_passe(carteChoisie, p.getContinuum())) 
                {                        
                    List<Integer> positionsPossibles = p.joueurActif.sorcier.Position_Possible_Passe(carteChoisie, p.getContinuum());
                    int position = positionsPossibles.get(r.nextInt(positionsPossibles.size()));
                    //mettre à jour la position du sorcier
                    p.joueurActif.sorcier.setPositionSorcier(position);
                    //jouer la carte
                    p.joueurActif.jouerCarte(indexCarteChoisie, p.getContinuum());
                }
                break;
        }
    }

}
