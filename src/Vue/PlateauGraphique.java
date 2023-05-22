package Vue;


import javax.swing.*;

import Modele.Carte;
import Modele.Jeu;
import Modele.Joueur;

import Modele.ZoneClic;

import java.awt.*;
import java.util.ArrayList;

import Modele.EtatJeu;

public class PlateauGraphique extends JComponent {
	final boolean J1 = true;
	final boolean J2 = false;
	int compteur;
	Point position;
	ArrayList<Image> image;
	Jeu jeu;
	EtatJeu etatJeu;
	int hauteurCarte, largeurCarte, 
		debutContinuumX, debutContinuumY, finContinuumX, finContinuumY, hauteurContinuum, largeurContinuum,
		debutMainJoueur1X, debutMainJoueur1Y, finMainJoueur1X, finMainJoueur1Y,
		debutMainJoueur2X, debutMainJoueur2Y, finMainJoueur2X, finMainJoueur2Y;
	int width, height;
	boolean voirMainAdversaire;
	Aspects aspects;
	Graphics2D drawable;
	CollecteurEvenements c;

	int carteSelectionne;
	int indiceCarteSurbrillance;

	public PlateauGraphique(Jeu j, CollecteurEvenements c) {
		jeu = j;
		this.c = c;
		compteur = 1;
		//chargerImages2();
		aspects = new Aspects(2);
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
		debutMainJoueur1X = 0;
		finMainJoueur1X = 0;
		debutMainJoueur1Y = 0;
		finMainJoueur1Y = 0;

		//Coordonnées Main Joueur secondaire
		debutMainJoueur2X = 0;
		finMainJoueur2X = 0;
		debutMainJoueur2Y = 0;
		finMainJoueur2Y = 0;

		carteSelectionne=-1;
		indiceCarteSurbrillance=-1;

		voirMainAdversaire = false;
	}
	void fixePosition(int x, int y) {
		position = new Point(x, y);
	}

	public void haloJaune(){
		if (indiceCarteSurbrillance >= 0 && indiceCarteSurbrillance < jeu.plateau().getContinuum().size()) {
			// Dessiner le halo de surbrillance autour de la carte
		
			drawable.setStroke(new BasicStroke(5));
			drawable.setColor(Color.YELLOW);
	
			int x = indiceCarteSurbrillance * (largeurCarte)+6;
			int y = debutContinuumY;
			drawable.drawRect(x, y, largeurCarte, hauteurCarte-3);
		}
	}


	public void surbrillance(){
		Carte carte = jeu.plateau().joueurActif().getMain().get(c.carteSelectionnee());
		ArrayList<Integer> cartesAccessibles = jeu.plateau().cartesAccessibles(carte);
		//Mettre en surbrillance les cartes accessibles
		for(int i = 0; i < cartesAccessibles.size(); i++){
			indiceCarteSurbrillance = cartesAccessibles.get(i);
			haloJaune();
		
		}

	}

	public void surbrillancePositionAccesibleSorcier(){
		ArrayList <Integer>pos= jeu.plateau().positionsDepart();
		for(int i = 0; i < pos.size(); i++){
			indiceCarteSurbrillance = pos.get(i);
			haloJaune();
			
		}
		
	}

	//Mettre en surbrillance  la carte selectionnée lorsque l'on fait un paradoxe
	public void surbrillanceParadoxe(){
		ArrayList<Integer>pos=jeu.positionsParadoxe();
		for(int i = 0; i < pos.size(); i++){
			indiceCarteSurbrillance = pos.get(i);
			haloJaune();
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
		if(jeu.joueurActif() == jeu.plateau().joueur1){
			tracerMainJoueur(c.voirMainJoueurActif(), J1);
			tracerMainJoueur(c.voirMainAdversaire(), J2);
		}else{
			tracerMainJoueur(c.voirMainAdversaire(), J1);
			tracerMainJoueur(c.voirMainJoueurActif(), J2);
		}

		//Si on est au debut du jeu, on affiche les cartes accessibles au sorcier
		if(c.etatJeu() == EtatJeu.DEBUT_PARTIE){
			surbrillancePositionAccesibleSorcier();
		}
		if(c.etatJeu()==EtatJeu.PARADOXE){
			surbrillanceParadoxe();	
		}
	
				
	
		if(c.carteSelectionnee() != -1){
			surbrillance();
			
		}
	}
	
	public ZoneClic getZoneClic(){
        int x = (int)position.getX();
        int y = (int)position.getY();
        if(((x >= debutContinuumX) && (x <= largeurContinuum) && (y >= debutContinuumY) && (y <= finContinuumY))){
            return ZoneClic.CONTINUUM;
        }
        if(((x >= debutMainJoueur1X) && (x <= finMainJoueur1X) && (y >= debutMainJoueur1Y) && (y <= finMainJoueur1Y))){
            return ZoneClic.MAIN_JOUEUR_1;
        }
        if(((x >= debutMainJoueur2X) && (x <= finMainJoueur2X) && (y >= debutMainJoueur2Y) && (y <= finMainJoueur2Y))){
            return ZoneClic.MAIN_JOUEUR_2;
        }
        return ZoneClic.HORS_ZONE;
    }
    //TODO à améliorer pour récuperer l'indice de la carte d'une meilleure maniere
    public int getCarte(ZoneClic zoneCarte){
        switch(zoneCarte){
            case MAIN_JOUEUR_1:
                return (int)(position.getX()-debutMainJoueur1X)/(largeurCarte);
            case MAIN_JOUEUR_2:
                return (int)(position.getX()-debutMainJoueur2X)/(largeurCarte);
            case CONTINUUM:
                return (int)position.getX()/largeurCarte;
            default:
                return -1;
        }
    }

	void tracerContinuum(){
		tracerFond();
		debutContinuumY = 2*hauteurCarte;
		//debutContinuumY = height/2-hauteurCarte/2;
		hauteurContinuum = hauteurCarte;
		largeurContinuum = width;
		finContinuumX = debutContinuumX + largeurContinuum;
		finContinuumY = debutContinuumY + hauteurContinuum;
		
		ArrayList<Carte> continuum = jeu.plateau().getContinuum();
		for(int i =0; i< continuum.size(); i++){
			drawable.drawImage(imageCarte(continuum.get(i)), largeurCarte*i+10, debutContinuumY, largeurCarte-5, hauteurCarte-5, null);
		}
		tracerCodex(continuum);
		tracerFleche(continuum);
		tracerSorcier(continuum, true);
		tracerSorcier(continuum, false);
		tracerScore();
		//tracerMessage();
		
	}

	void tracerFond(){
		drawable.drawImage(aspects.fond.image(), 0, 0,width, height, null);
	}

	void tracerFleche(ArrayList<Carte> continuum){
		if (joueurActif()){

			drawable.drawImage(aspects.fleche2.image(), debutContinuumX+10, hauteurCarte/4*11, finContinuumX-largeurCarte, hauteurCarte+5, null);
		}
		else{
			drawable.drawImage(aspects.fleche1.image(), debutContinuumX+10, hauteurCarte/4*6, finContinuumX-largeurCarte, hauteurCarte+5, null);
		}
	}

	// Trace le jeton du sorcier passé en paramètre:
	// sorcier: true, trace le sorcier du joueur1
	//			false, trace le sorcier du joueur2
	void tracerSorcier(ArrayList<Carte> continuum, boolean sorcier){
		if(sorcier){
			int posSorcier=jeu.plateau().getPositionSorcier(1);
			if (posSorcier!=-1){
				drawable.drawImage(aspects.sorcier1.image(), posSorcier*largeurCarte+largeurCarte/4, debutContinuumY+hauteurCarte+15, largeurCarte/2 , hauteurCarte/2, null);
			}
		}else{
			int posSorcier=jeu.plateau().getPositionSorcier(2);
			if (posSorcier!=-1){
				drawable.drawImage(aspects.sorcier2.image(), posSorcier*largeurCarte+largeurCarte/4, debutContinuumY-hauteurCarte/2-18, largeurCarte/2 , hauteurCarte/2, null);
			}
			
		}
	}
	

	void tracerCodex(ArrayList<Carte> continuum){
		switch (jeu.couleurInterdite()){
			case ROUGE :
				drawable.drawImage(aspects.codex_rouge.image(), largeurCarte*continuum.size()+5, debutContinuumY, largeurCarte-5, hauteurCarte-8, null);
				break;
			case VERT :
				drawable.drawImage(aspects.codex_vert.image(), largeurCarte*continuum.size()+5, debutContinuumY, largeurCarte-5, hauteurCarte-8, null);
				break;
			case VIOLET:
				drawable.drawImage(aspects.codex_violet.image(), largeurCarte*continuum.size()+5, debutContinuumY, largeurCarte-5, hauteurCarte-8, null);
				break;
			case BLEU :
				drawable.drawImage(aspects.codex_bleu.image(), largeurCarte*continuum.size()+5, debutContinuumY, largeurCarte-5, hauteurCarte-8, null);
				break;
			default:
				break;
		}
	}

	void tracerScore() {
		int cristaux = jeu.plateau().joueur1.getNombreCristaux();
		int cristaux2 = jeu.plateau().joueur2.getNombreCristaux();
		int hauteur=getHeight()/10;
		int longueur=getWidth()/8*5;
		Font fonte = new Font("Serif", Font.BOLD, getHeight()/35);
        drawable.setFont(fonte);
		drawable.drawString("Joueur 2 : " + cristaux2, longueur, hauteur);
		drawable.drawString("Joueur 1 : " + cristaux, longueur, hauteur*9);
	}

	void tracerMessage(){
		String msg = c.infoPlateau();
		if (joueurActif()){
			int hauteurPrevisualisation = hauteurCarte/4;
			int hauteur=getHeight()/10*8-hauteurPrevisualisation;
			int longueur=3*largeurCarte;
			Font fonte = new Font("Serif", Font.BOLD, getHeight()/40);
			drawable.setFont(fonte);
			drawable.drawString(msg, longueur, hauteur);
		}
		else{
			int hauteurPrevisualisation = hauteurCarte/4;
			int hauteur=getHeight()/3-hauteurPrevisualisation;
			int longueur=3*largeurCarte;
			Font fonte = new Font("Serif", Font.BOLD, getHeight()/40);
			drawable.setFont(fonte);
			drawable.drawString(msg, longueur, hauteur);
		}
	}

	// Trace les cartes d'un joueur
	// mainOuverte : True si on veut voir le jeu du joueur
	//				 False sinon, trace le dos des cartes
	// joueur : joueur1 si true
	//			joueur2 si false
	void tracerMainJoueur(boolean mainOuverte, boolean joueur){
		int XDepart, YDepart;
		int hauteurPrevisualisation;
		ArrayList<Carte> main;

		if(joueur){

			debutMainJoueur1X = 3*largeurCarte;
			finMainJoueur1X = 6*largeurCarte;
			debutMainJoueur1Y = height-hauteurCarte;
			finMainJoueur1Y = height;
			XDepart = debutMainJoueur1X;
			YDepart = debutMainJoueur1Y-20;

			hauteurPrevisualisation = hauteurCarte/4;
			main = jeu.plateau().getJoueur(1).getMain();
		}
		else{
			debutMainJoueur2X = 3*largeurCarte;
			finMainJoueur2X = 6*largeurCarte;

			debutMainJoueur2Y = 0;
			finMainJoueur2Y = hauteurCarte;
			XDepart = debutMainJoueur2X;
			YDepart = debutMainJoueur2Y+20;

			hauteurPrevisualisation = -(hauteurCarte/4);
			main = jeu.plateau().getJoueur(2).getMain();
		}
		
		carteSelectionne = c.carteSelectionnee();
		
		if(mainOuverte){
			for(int i =0; i< main.size(); i++){
				if (i == carteSelectionne && joueur == joueurActif()){
					drawable.drawImage(imageCarte(main.get(i)), XDepart+largeurCarte*i, YDepart-hauteurPrevisualisation, largeurCarte, hauteurCarte, null);
				}
				else {
					drawable.drawImage(imageCarte(main.get(i)), XDepart+largeurCarte*i, YDepart, largeurCarte, hauteurCarte, null);
				}
			}
		}
		else{
			for(int i =0; i< main.size(); i++){
				drawable.drawImage(aspects.carte_dos.image(), XDepart+largeurCarte*i, YDepart, largeurCarte, hauteurCarte, null);
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

	boolean joueurActif(){
		return jeu.joueurActif()==jeu.plateau().joueur1;
	}

	boolean estJoueur1(Joueur j)
	{
		return j==jeu.plateau().joueur1;
	}
	void tracerImage(Carte carte, int position){
	}

	public void voirMainAdversaire(boolean bool){
        voirMainAdversaire = bool;
    }
}