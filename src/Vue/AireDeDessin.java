package Vue;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AireDeDessin extends JComponent {
	int counter;
	Point position;
	ArrayList<Image> image;
	ImageJeu img1, img2, img3, img4, img5, img6, img7, img8, img9;

	public AireDeDessin() {
		
		String image1 = "bleu_1";
		String image2 = "bleu_2";
		String image3 = "bleu_3";

			// Chargement de l'image de la même manière que le fichier de niveaux
			img1 = new ImageJeu(image1);
			img2 = new ImageJeu(image2);
			img3 = new ImageJeu(image3);

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
		drawable.drawImage(img1.image(), position.x+600, position.y+200, 400, 600, null);
		drawable.drawImage(img2.image(), position.x+500, position.y+200, 400, 600, null);
		drawable.drawImage(img3.image(), position.x+400, position.y+200, 400, 600, null);
	}
}