package Vue;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AireDeDessin extends JComponent {
	int counter;
	Point position;
	ArrayList<Image> image;
	Image img1, img2, img3, img4, img5, img6, img7, img8, img9;

	public AireDeDessin() {
		
		String image1 = "Images/bleu_1.png";
		String image2 = "Images/bleu_2.png";
		String image3 = "Images/bleu_3.png";
		
		try {
			// Chargement de l'image de la même manière que le fichier de niveaux
			InputStream in1 = new FileInputStream(image1);
			img1 = ImageIO.read(in1);
			InputStream in2 = new FileInputStream(image2);
			img2 = ImageIO.read(in2);
			InputStream in3 = new FileInputStream(image3);
			img3 = ImageIO.read(in3);

			// Chargement d'une image utilisable dans Swing
			
			
			
		} catch (FileNotFoundException e) {
			System.err.println("ERREUR : impossible de trouver le fichier du pousseur");
			System.exit(2);
		} catch (IOException e) {
			System.err.println("ERREUR : impossible de charger l'image");
			System.exit(3);
		}
		counter = 1;
	}

	void fixePosition(int x, int y) {
		position = new Point(x, y);
	}

	public void paintComponent(Graphics g) {
		System.out.println("Entree dans paintComponent : " + counter++);

		// Graphics 2D est le vrai type de l'objet passé en paramètre
		// Le cast permet d'avoir acces a un peu plus de primitives de dessin
		Graphics2D drawable = (Graphics2D) g;

		// On reccupere quelques infos provenant de la partie JComponent
		int width = getSize().width;
		int height = getSize().height;

		// Si la position n'est pas fixée, on calcule le centre de la zone et un rayon
		if (position == null)
			position = new Point(width/2, height/2);

		// On efface tout
		drawable.clearRect(0, 0, width, height);

		// On affiche une petite image au milieu
		drawable.drawImage(img1, position.x+600, position.y+200, 400, 600, null);
		drawable.drawImage(img2, position.x+500, position.y+200, 400, 600, null);
		drawable.drawImage(img3, position.x+400, position.y+200, 400, 600, null);
	}
}