package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import Modele.ZoneClic;

public class AdaptateurSouris extends MouseAdapter {
	AireDeDessin aire;
	CollecteurEvenements c;

	public AdaptateurSouris(AireDeDessin a, CollecteurEvenements c) {
		aire = a;
		this.c = c;
	}

	//TODO ajouter action à faire si on clique dans la ZONE CONTINUUM
	@Override
	public void mousePressed(MouseEvent e) {
		aire.fixePosition(e.getX(), e.getY());
		int indiceCarte;
		switch(aire.getZoneClic()){
			case CONTINUUM:
				//Faire quelque chose
				System.out.println("clic CONTINUUM");
				indiceCarte = aire.getCarte(ZoneClic.CONTINUUM);
				System.out.println("carte " + indiceCarte);
				c.clicCarteContinuum(indiceCarte);
				break;
			case MAIN_JOUEUR_COURANT:
				//Jouer une carte
				System.out.println("clic MAIN_JOUEUR_COURANT");
				indiceCarte = aire.getCarte(ZoneClic.MAIN_JOUEUR_COURANT);
				System.out.println("carte " + indiceCarte);
				c.clicCarteMain(indiceCarte);
				break;
			default:
				System.out.println("clic plateau");
				break;
		};
	}
}