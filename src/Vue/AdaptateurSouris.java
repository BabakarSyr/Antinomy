package Vue;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import Modele.ZoneClic;

public class AdaptateurSouris extends MouseAdapter {
	AireDeDessin aire;
	CollecteurEvenements c;

	public AdaptateurSouris(AireDeDessin a) {
		aire = a;
	}

	//TODO ajouter action à faire si on clique dans la ZONE CONTINUUM
	@Override
	public void mousePressed(MouseEvent e) {
		aire.fixePosition(e.getX(), e.getY());
		switch(aire.getZoneClic()){
			case CONTINUUM:
				//Faire quelque chose
			case MAIN_JOUEUR_COURANT:
				//Jouer une carte
				c.clicCarteMain();
			default:
				break;
		};
	}
}