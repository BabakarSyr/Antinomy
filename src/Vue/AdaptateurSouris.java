package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.ZoneClic;

public class AdaptateurSouris extends MouseAdapter {
	PlateauGraphique plateauGraphique;
	CollecteurEvenements c;

	public AdaptateurSouris(PlateauGraphique p, CollecteurEvenements c) {
		plateauGraphique = p;
		this.c = c;
	}

	//TODO ajouter action à faire si on clique dans la ZONE CONTINUUM
	@Override
	public void mousePressed(MouseEvent e) {
		plateauGraphique.fixePosition(e.getX(), e.getY());
		int indiceCarte;
		switch(plateauGraphique.getZoneClic()){
			case CONTINUUM:
				//Faire quelque chose
				System.out.println("clic CONTINUUM");
				indiceCarte = plateauGraphique.getCarte(ZoneClic.CONTINUUM);
				System.out.println("carte " + indiceCarte);
				c.clicCarteContinuum(indiceCarte);
				plateauGraphique.setCarteSelectionne(-1);
				plateauGraphique.repaint();
				break;
			case MAIN_JOUEUR_COURANT:
				//Jouer une carte
				System.out.println("clic MAIN_JOUEUR_COURANT");
				indiceCarte = plateauGraphique.getCarte(ZoneClic.MAIN_JOUEUR_COURANT);
				System.out.println("carte " + indiceCarte);
				c.clicCarteMain(indiceCarte);
				plateauGraphique.repaint();
				break;
			case MAIN_ADVERSAIRE:
				//Jouer une carte
				System.out.println("clic MAIN_ADVERSAIRE");
				//indiceCarte = plateauGraphique.getCarte(ZoneClic.MAIN_ADVERSAIRE);
				//System.out.println("carte " + indiceCarte);
				//c.clicCarteMain(indiceCarte);
				plateauGraphique.voirMainAdversaire();
				plateauGraphique.repaint();
				break;
			default:
				System.out.println("clic plateau");
				break;
		};
	}
}