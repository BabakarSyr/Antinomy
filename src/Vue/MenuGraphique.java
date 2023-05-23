package Vue;


import javax.swing.*;

import Modele.Carte;
import Modele.Jeu;
import Modele.Joueur;

import Modele.ZoneClic;

import java.awt.*;
import java.util.ArrayList;

import Modele.EtatJeu;

public class MenuGraphique extends JPanel {

	int width, height;
	Point position;
	ImageJeu menuBackground;

	Graphics2D drawable;
	CollecteurEvenements c;


	public MenuGraphique(CollecteurEvenements c) {
		menuBackground = new ImageJeu("fond3");
	}

	public void paintComponent(Graphics g) {
		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		drawable = (Graphics2D) g;

		// On reccupere quelques infos provenant de la partie JComponent
		width = getSize().width;
		height = getSize().height;
		// Si la position n'est pas fixée, on calcule le centre de la zone et un rayon
		if (position == null)
			position = new Point(width/2, height/2);

		// On efface tout
		drawable.clearRect(0, 0, width, height);

		Aspects aspects = new Aspects(2);
		drawable.drawImage(aspects.fond.image(), 0, 0, width, height, null);

	}
}