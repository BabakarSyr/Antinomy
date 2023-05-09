package Vue;

import javax.imageio.ImageIO;


import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.io.File;

public class ImageJeu {
	Image img;

	public ImageJeu(String nom) {
		try {
			// Chargement de l'image de la même manière que le fichier de niveaux
			InputStream in = new FileInputStream("res"+ File.separator + "Images"+ File.separator + nom + ".png");
			img = ImageIO.read(in);
			
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : impossible de trouver l'image");
			System.exit(2);
		} catch (IOException e) {
			System.err.println("ERREUR : impossible de charger l'image");
			System.exit(3);
		}
	}
	public Image image(){
		return this.img;
	}
}