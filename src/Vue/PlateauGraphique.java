package Vue;


import javax.swing.*;

import Modele.Carte;
import Modele.Jeu;
import Modele.Joueur;
import Modele.Plateau;
import Modele.ZoneClic;

import java.awt.*;
import java.util.ArrayList;

public class PlateauGraphique extends JComponent {
	int compteur;
	Point position;
	ArrayList<Image> image;
	Jeu jeu;
	int hauteurCarte, largeurCarte, 
		debutContinuumX, debutContinuumY, finContinuumX, finContinuumY, hauteurContinuum, largeurContinuum,
		debutMainJoueurActifX, debutMainJoueurActifY, finMainJoueurActifX, finMainJoueurActifY,
		debutMainJoueurSecondaireX, debutMainJoueurSecondaireY, finMainJoueurSecondaireX, finMainJoueurSecondaireY;
	int width, height;
	aspects aspects;
	Graphics2D drawable;

	int carteSelectionne;

	public PlateauGraphique(Jeu j) {
		jeu = j;
		compteur = 1;
		//chargerImages2();
		aspects = new aspects(2);
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

		carteSelectionne=-1;
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
		
		ArrayList<Carte> continuum = jeu.plateau().getContinuum();
		for(int i =0; i< continuum.size(); i++){
			drawable.drawImage(imageCarte(continuum.get(i)), largeurCarte*i, debutContinuumY, largeurCarte, hauteurCarte, null);
		}
		tracerCodex(continuum);
		tracerSorcierActif(continuum);
		tracerSorcierPassif(continuum);
	}

	void tracerSorcierActif(ArrayList<Carte> continuum){
		int posSorcier=jeu.plateau().getPositionSorcier(1);
		drawable.drawImage(aspects.sorcier1.image(), posSorcier*largeurCarte+largeurCarte/4, debutContinuumY+hauteurCarte, largeurCarte/2 , hauteurCarte/2, null);

	}

	void tracerSorcierPassif(ArrayList<Carte> continuum){
		int posSorcier=jeu.plateau().getPositionSorcier(2);
		drawable.drawImage(aspects.sorcier2.image(), posSorcier*largeurCarte+largeurCarte/4, debutContinuumY-hauteurCarte/2, largeurCarte/2 , hauteurCarte/2, null);
	}

	void tracerCodex(ArrayList<Carte> continuum){
		switch (jeu.couleurInterdite()){
			case ROUGE :
				drawable.drawImage(aspects.codex_rouge.image(), largeurCarte*continuum.size(), debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case VERT :
				drawable.drawImage(aspects.codex_vert.image(), largeurCarte*continuum.size(), debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case VIOLET:
				drawable.drawImage(aspects.codex_violet.image(), largeurCarte*continuum.size(), debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case BLEU :
				drawable.drawImage(aspects.codex_bleu.image(), largeurCarte*continuum.size(), debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			default:
				break;
		}
	}

	// Trace les cartes du joueur actif (joueur du bas)
	void tracerMainJoueurActif(){

		debutMainJoueurActifX = 3*largeurCarte;
		debutMainJoueurActifY = height-hauteurCarte;
		finMainJoueurActifX = 6*largeurCarte;
		finMainJoueurActifY = height;

		ArrayList<Carte> main = jeu.plateau().getJoueurActif().getMain();
		for(int i =0; i< main.size(); i++){
			if (i == carteSelectionne){
				drawable.drawImage(imageCarte(main.get(i)), debutMainJoueurActifX+largeurCarte*i, debutMainJoueurActifY-hauteurCarte/4, largeurCarte, hauteurCarte, null);
			}
			else {
				drawable.drawImage(imageCarte(main.get(i)), debutMainJoueurActifX+largeurCarte*i, debutMainJoueurActifY, largeurCarte, hauteurCarte, null);
			}
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
			ArrayList<Carte> main = jeu.plateau().getJoueur(2).getMain();
			for(int i =0; i< main.size(); i++){
				drawable.drawImage(imageCarte(main.get(i)), debutMainJoueurSecondaireX+largeurCarte*i, debutMainJoueurSecondaireY, largeurCarte, hauteurCarte, null);
			}
		}else{
			for(int i =0; i< 3; i++){
				drawable.drawImage(aspects.carte_dos.image(), debutMainJoueurSecondaireX+largeurCarte*i, debutMainJoueurSecondaireY, largeurCarte, hauteurCarte, null);
			}
		}
	}

	//TODO créer méthode mettant à jour le booleen mainOuverte

	private Image imageCarte(Carte carte) {
		switch (carte.getCarte()){
			case "CRANE_BLEU":
				return aspects.crane_bleu.image();
			case "CRANE_ROUGE":
				return aspects.crane_rouge.image();
			case "CRANE_VERT":
				return aspects.crane_vert.image();
			case "CRANE_VIOLET":
				return aspects.crane_violet.image();
			case "ANNEAU_BLEU":
				return aspects.anneau_bleu.image();
			case "ANNEAU_ROUGE":
				return aspects.anneau_rouge.image();
			case "ANNEAU_VERT":
				return aspects.anneau_vert.image();
			case "ANNEAU_VIOLET":
				return aspects.anneau_violet.image();
			case "CLE_BLEU":
				return aspects.cle_bleu.image();
			case "CLE_ROUGE":
				return aspects.cle_rouge.image();
			case "CLE_VERT":
				return aspects.cle_vert.image();
			case "CLE_VIOLET":
				return aspects.cle_violet.image();
			case "PLUME_BLEU":
				return aspects.plume_bleu.image();
			case "PLUME_ROUGE":
				return aspects.plume_rouge.image();
			case "PLUME_VERT":
				return aspects.plume_vert.image();
			case "PLUME_VIOLET":
				return aspects.plume_violet.image();
			default:
				return null;
		}
	}

	public void setCarteSelectionne(int carteSelectionne) {
		if (carteSelectionne!=this.carteSelectionne){
			this.carteSelectionne = carteSelectionne;
		}
		else{
			this.carteSelectionne=-1;
		}
		
	}

	void tracerImage(Carte carte, int position){
	}
}