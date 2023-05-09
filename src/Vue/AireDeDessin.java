package Vue;


import javax.swing.*;

import Modele.Carte;
import Modele.Jeu;
import Modele.ZoneClic;

import java.awt.*;
import java.util.ArrayList;

public class AireDeDessin extends JComponent {
	int compteur;
	Point position;
	ArrayList<Image> image;
	Jeu jeu;
	int hauteurCarte, largeurCarte, 
		debutContinuumX, debutContinuumY, finContinuumX, finContinuumY, hauteurContinuum, largeurContinuum,
		debutMainJoueurActifX, debutMainJoueurActifY, finMainJoueurActifX, finMainJoueurActifY,
		debutMainJoueurSecondaireX, debutMainJoueurSecondaireY, finMainJoueurSecondaireX, finMainJoueurSecondaireY;
	int width, height;
	ImageJeu anneau_bleu, anneau_rouge, anneau_vert, anneau_violet,
		cle_bleu, cle_rouge, cle_vert, cle_violet,
		crane_bleu, crane_rouge, crane_vert, crane_violet,
		plume_bleu, plume_rouge, plume_vert, plume_violet,
		codex;
	Graphics2D drawable;

	public AireDeDessin(Jeu j) {
		
		jeu = j;
		compteur = 1;
		chargerImages();
		initialisationCoordonnées();
	}

	private void initialisationCoordonnées() {
		// Dimension Carte
		hauteurCarte = 0;
		largeurCarte = 0;

		// Coordonnées continuum
		debutContinuumX = 0;
		debutContinuumY = 0;
		hauteurContinuum = 0;
		largeurContinuum = 0;

		//Coordonnées Main Joueur actif
		debutMainJoueurActifX = 0;
		debutMainJoueurActifY = 0;
		finMainJoueurActifX = 0;
		finMainJoueurActifY = 0;

		//Coordonnées Main Joueur secondaire
		debutMainJoueurSecondaireX = 0;
		debutMainJoueurSecondaireY = 0;
		finMainJoueurSecondaireX = 0;
		finMainJoueurSecondaireY = 0;
	}

	public void chargerImages(){
		
		// Chargement de l'image de la même manière que le fichier de niveaux
		anneau_bleu = new ImageJeu("anneau_bleu");
		anneau_rouge = new ImageJeu("anneau_rouge");
		anneau_vert = new ImageJeu("anneau_vert");
		anneau_violet = new ImageJeu("anneau_violet");
		cle_bleu = new ImageJeu("cle_bleu");
		cle_rouge = new ImageJeu("cle_rouge");
		cle_vert = new ImageJeu("cle_vert");
		cle_violet = new ImageJeu("cle_violet");
		crane_bleu = new ImageJeu("crane_bleu");
		crane_rouge = new ImageJeu("crane_rouge");
		crane_vert = new ImageJeu("crane_vert");
		crane_violet = new ImageJeu("crane_violet");
		plume_bleu = new ImageJeu("plume_bleu");
		plume_rouge = new ImageJeu("plume_rouge");
		plume_vert = new ImageJeu("plume_vert");
		plume_violet = new ImageJeu("plume_violet");
		codex = new ImageJeu("codex");
	}
	void fixePosition(int x, int y) {
		position = new Point(x, y);
	}
	public ZoneClic getZoneClic(){
		int x = (int)position.getX();
		int y = (int)position.getY();
		if(((x >= debutContinuumX) && (x <= largeurContinuum) && (y >= debutContinuumY) && (y <= finContinuumY))){
			return ZoneClic.CONTINUUM;
		}
		if(((x >= debutMainJoueurActifX) && (x <= finMainJoueurActifX) && (y >= debutMainJoueurActifY) && (y <= finMainJoueurActifY))){
			return ZoneClic.MAIN_JOUEUR_COURANT;
		}
		return ZoneClic.HORS_ZONE;
	}
	//TODO à améliorer pour récuperer l'indice de la carte d'une meilleure maniere
	public int getCarte(ZoneClic zoneCarte){
		switch(zoneCarte){
			case MAIN_JOUEUR_COURANT:
				return (int)(position.getX()-debutMainJoueurActifX)/(largeurCarte);
			case CONTINUUM:
				return (int)position.getX()/largeurCarte;
			default:
				return -1;
		}
	}

	public void paintComponent(Graphics g) {
		System.out.println("Entree dans paintComponent : " + compteur++);
		

		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		drawable = (Graphics2D) g;

		// On reccupere quelques infos provenant de la partie JComponent
		width = getSize().width;
		height = getSize().height;
		hauteurCarte = getHeight()/5;
		largeurCarte = getWidth()/10;

		// Si la position n'est pas fixée, on calcule le centre de la zone et un rayon
		if (position == null)
			position = new Point(width/2, height/2);

		// On efface tout
		drawable.clearRect(0, 0, width, height);

		// Tracer Continuum au milieu du plateau
		tracerContinuum();
		tracerMainJoueurActif();
		tracerMainJoueurSecondaire(false);
	}

	void tracerContinuum(){
		debutContinuumY = 2*hauteurCarte;
		hauteurContinuum = hauteurCarte;
		largeurContinuum = getWidth();
		finContinuumX = debutContinuumX + largeurContinuum;
		finContinuumY = debutContinuumY + hauteurContinuum;
		
		ArrayList<Carte> continuum = jeu.getPlateau().getContinuum();
		for(int i =0; i< continuum.size(); i++){
			drawable.drawImage(imageCarte(continuum.get(i)), largeurCarte*i, debutContinuumY, largeurCarte, hauteurCarte, null);
		}
		drawable.drawImage(codex.image(), largeurCarte*continuum.size(), debutContinuumY, largeurCarte, hauteurCarte, null);
	}

	// Trace les cartes du joueur actif (joueur du bas)
	void tracerMainJoueurActif(){

		debutMainJoueurActifX = 3*largeurCarte;
		debutMainJoueurActifY = height-hauteurCarte;
		finMainJoueurActifX = 6*largeurCarte;
		finMainJoueurActifY = height;

		ArrayList<Carte> main = jeu.getPlateau().getJoueurActif().getMain().getCartes();
		for(int i =0; i< main.size(); i++){
			drawable.drawImage(imageCarte(main.get(i)), debutMainJoueurActifX+largeurCarte*i, debutMainJoueurActifY, largeurCarte, hauteurCarte, null);
		}
		
	}

	// Trace les cartes du joueur secondaire (joueur du haut)
	// mainOuverte : True si on veut voir le jeu du joueur secondaire
	//				 False sinon, trace le dos des cartes
	void tracerMainJoueurSecondaire(boolean mainOuverte){
		
		debutMainJoueurSecondaireX = 3*largeurCarte;
		debutMainJoueurSecondaireY = 0;
		finMainJoueurSecondaireX = 6*largeurCarte;
		finMainJoueurSecondaireY = hauteurCarte;
		if(mainOuverte){
			ArrayList<Carte> main = jeu.getPlateau().getJoueur(2).getMain().getCartes();
			for(int i =0; i< main.size(); i++){
				drawable.drawImage(imageCarte(main.get(i)), debutMainJoueurSecondaireX+largeurCarte*i, debutMainJoueurSecondaireY, largeurCarte, hauteurCarte, null);
			}
		}else{
			for(int i =0; i< 3; i++){
				drawable.drawImage(codex.image(), debutMainJoueurSecondaireX+largeurCarte*i, debutMainJoueurSecondaireY, largeurCarte, hauteurCarte, null);
			}
		}
	}

	//TODO créer méthode mettant à jour le booleen mainOuverte

	private Image imageCarte(Carte carte) {
		switch (carte.getCarte()){
			case "CRANE_BLEU":
				return crane_bleu.image();
			case "CRANE_ROUGE":
				return crane_rouge.image();
			case "CRANE_VERT":
				return crane_vert.image();
			case "CRANE_VIOLET":
				return crane_violet.image();
			case "ANNEAU_BLEU":
				return anneau_bleu.image();
			case "ANNEAU_ROUGE":
				return anneau_rouge.image();
			case "ANNEAU_VERT":
				return anneau_vert.image();
			case "ANNEAU_VIOLET":
				return anneau_violet.image();
			case "CLE_BLEU":
				return cle_bleu.image();
			case "CLE_ROUGE":
				return cle_rouge.image();
			case "CLE_VERT":
				return cle_vert.image();
			case "CLE_VIOLET":
				return cle_violet.image();
			case "PLUME_BLEU":
				return plume_bleu.image();
			case "PLUME_ROUGE":
				return plume_rouge.image();
			case "PLUME_VERT":
				return plume_vert.image();
			case "PLUME_VIOLET":
				return plume_violet.image();
			default:
				return null;
		}
	}

	void tracerImage(Carte carte, int position){
	}
}