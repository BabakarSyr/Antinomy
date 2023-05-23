package Vue;

import java.awt.event.MouseMotionListener;

import Modele.ZoneClic;
import Vue.CollecteurEvenements;
import Vue.PlateauGraphique;

import java.awt.event.MouseEvent;

public class AdaptateurSourisMouvement implements MouseMotionListener {

    // =====================
    // ===== ATTRIBUTS =====
    // =====================
    CollecteurEvenements c;
    PlateauGraphique plateauGraphique;

    /////////////////////////////////////////////////////////////////////////

    // ========================
    // ===== CONSTRUCTEUR =====
    // ========================
    public AdaptateurSourisMouvement(PlateauGraphique pGraphique, CollecteurEvenements cEvenements) {
        plateauGraphique = pGraphique;
        c = cEvenements;
    }

    // ===========================
    // ===== DEPLACER SOURIS =====
    // ===========================
    @Override
    public void mouseMoved(MouseEvent e) {

        int positionSourisX = e.getX();
        int positionSourisY = e.getY();

		plateauGraphique.fixePosition(positionSourisX, positionSourisY);
		int indiceCarte;
		switch(plateauGraphique.getZoneClic()){
			case CONTINUUM:
				break;
			case MAIN_JOUEUR_1:
				//Jouer une carte
				System.out.println("clic MAIN_JOUEUR_1");
                survolMain(c.joueurActif(1), ZoneClic.MAIN_JOUEUR_1);
				//plateauGraphique.repaint();
				break;
			case MAIN_JOUEUR_2:
				//Jouer une carte
				System.out.println("clic MAIN_JOUEUR_2");
                survolMain(c.joueurActif(2), ZoneClic.MAIN_JOUEUR_2);
				//plateauGraphique.repaint();
				break;
			default:
                plateauGraphique.setEstZoneMainAdverse(false);
				System.out.println("X=" + positionSourisX + " Y=" +positionSourisY);
				break;
		};
    }

    void survolMain(boolean actif, ZoneClic zone){
		int indiceCarte;
		if(actif){
            plateauGraphique.setEstZoneMainAdverse(false);
			indiceCarte = plateauGraphique.getCarte(zone);
			System.out.println("carte " + indiceCarte);
			//c.clicCarteMain(indiceCarte);
		}else{
            plateauGraphique.setEstZoneMainAdverse(true);
		}
	}

    // =======================================
    // ===== MAINTENIR + DEPLACER SOURIS =====
    // =======================================
    public void mouseDragged(MouseEvent e) {
        
    }
}