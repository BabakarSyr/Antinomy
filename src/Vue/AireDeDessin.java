package Vue;


import javax.swing.*;

import Modele.Carte;
import Modele.Jeu;

import java.awt.*;
import java.util.ArrayList;

public class AireDeDessin extends JComponent {
	int compteur;
	Point position;
	ArrayList<Image> image;
	Jeu jeu;
	int hauteurCarte, largeurCarte, debutContinuumY, hauteurContinuum, largeurContinuum;
	int width, height;
	ImageJeu anneau_bleu, anneau_rouge, anneau_vert, anneau_violet,
		cle_bleu, cle_rouge, cle_vert, cle_violet,
		crane_bleu, crane_rouge, crane_vert, crane_violet,
		plume_bleu, plume_rouge, plume_vert, plume_violet;
	Graphics2D drawable;

	public AireDeDessin(Jeu j) {
		
		jeu = j;

		String image1 = "anneau_bleu";
		String image2 = "anneau_rouge";
		String image3 = "anneau_vert";
		String image4 = "anneau_violet";
		String image5 = "cle_bleu";
		String image6 = "cle_rouge";
		String image7 = "cle_vert";
		String image8 = "cle_violet";
		String image9 = "crane_bleu";
		String image10 = "crane_rouge";
		String image11 = "crane_vert";
		String image12 = "crane_violet";
		String image13 = "plume_bleu";
		String image14 = "plume_rouge";
		String image15 = "plume_vert";
		String image16 = "plume_violet";

		// Chargement de l'image de la même manière que le fichier de niveaux
		anneau_bleu = new ImageJeu(image1);
		anneau_rouge = new ImageJeu(image2);
		//anneau_vert = new ImageJeu(image3);
		anneau_violet = new ImageJeu(image4);
		cle_bleu = new ImageJeu(image5);
		cle_rouge = new ImageJeu(image6);
		cle_vert = new ImageJeu(image7);
		cle_violet = new ImageJeu(image8);
		crane_bleu = new ImageJeu(image9);
		crane_rouge = new ImageJeu(image10);
		crane_vert = new ImageJeu(image11);
		crane_violet = new ImageJeu(image12);
		plume_bleu = new ImageJeu(image13);
		plume_rouge = new ImageJeu(image14);
		plume_vert = new ImageJeu(image15);
		plume_violet = new ImageJeu(image16);

		compteur = 1;
	}

	void fixePosition(int x, int y) {
		position = new Point(x, y);
	}

	public void paintComponent(Graphics g) {
		System.out.println("Entree dans paintComponent : " + compteur++);
		

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

		// On affiche une petite image au milieu
		
		tracerContinuum();
		//drawable.drawImage(img1.image(), position.x+600, position.y+200, 400, 600, null);
		//drawable.drawImage(img2.image(), position.x+500, position.y+200, 400, 600, null);
		//drawable.drawImage(img3.image(), position.x+400, position.y+200, 400, 600, null);
	}

	void tracerContinuum(){
		hauteurCarte = getHeight()/4;
		largeurCarte = getWidth()/8;
		System.out.println("largeur : "+largeurCarte +" hauteur : "+ hauteurCarte);
		debutContinuumY = hauteurCarte;
		hauteurContinuum = hauteurCarte;
		largeurContinuum = getWidth();
		ArrayList<Carte> continuum = jeu.getPlateau().getContinuum();
		for(int i =0; i< continuum.size(); i++){
			tracerCarteContinuum(continuum.get(i), i);
		}
	}

	void tracerMainJoueur(boolean numJoueur){
		if(numJoueur){
			ArrayList<Carte> main = jeu.getPlateau().getContinuum();
			for(int i =0; i< main.size(); i++){
				tracerCarteMain(main.get(i), i);
			}
		}
		else{
			ArrayList<Carte> main = jeu.getPlateau().getContinuum();
			for(int i =0; i< main.size(); i++){
				tracerCarteMain(main.get(i), i);
			}
		}
	}

	void tracerCarteMain(Carte carte, int position){

	}

	void tracerCarteContinuum(Carte carte, int position){
		switch (carte.getCarte()){
			case "CRANE_BLEU":
				drawable.drawImage(crane_bleu.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CRANE_ROUGE":
				drawable.drawImage(crane_rouge.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CRANE_VERT":
				drawable.drawImage(crane_vert.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CRANE_VIOLET":
				drawable.drawImage(crane_violet.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "ANNEAU_BLEU":
				drawable.drawImage(anneau_bleu.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "ANNEAU_ROUGE":
				drawable.drawImage(anneau_rouge.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "ANNEAU_VERT":
				//todo remplacer par anneau vert
				drawable.drawImage(anneau_rouge.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "ANNEAU_VIOLET":
				drawable.drawImage(anneau_violet.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CLE_BLEU":
				drawable.drawImage(cle_bleu.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CLE_ROUGE":
				drawable.drawImage(cle_rouge.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CLE_VERT":
				drawable.drawImage(cle_vert.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "CLE_VIOLET":
				drawable.drawImage(cle_violet.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "PLUME_BLEU":
				drawable.drawImage(plume_bleu.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "PLUME_ROUGE":
				drawable.drawImage(plume_rouge.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "PLUME_VERT":
				drawable.drawImage(plume_vert.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			case "PLUME_VIOLET":
				drawable.drawImage(plume_violet.image(), largeurCarte*position, debutContinuumY, largeurCarte, hauteurCarte, null);
				break;
			default:
				break;
		}
	}

	void tracerImage(Carte carte, int position){
	}
}