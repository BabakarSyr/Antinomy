package Vue;

import javax.swing.* ;
//import java.awt.* ;

import Modele.*;


// L'interface runnable déclare une méthode run
public class InterfaceGraphique implements Runnable {
	Jeu jeu;
	CollecteurEvenements controleur;

	public InterfaceGraphique(Jeu j, CollecteurEvenements c){
		jeu = j;
		controleur = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements c) {
		InterfaceGraphique vue = new InterfaceGraphique(j, c);
		SwingUtilities.invokeLater(vue);
	}


	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("Ma fenetre a moi");
		frame.setSize(500, 300);

		// Ajout de notre composant de dessin dans la fenetre
		PlateauGraphique plateauGraphique = new PlateauGraphique(jeu, controleur);
		frame.add(plateauGraphique, controleur);

		// Ecoute des évènements liés à la souris dans l'AireDeDessin
		plateauGraphique.addMouseListener(new AdaptateurSouris(plateauGraphique, controleur));

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		
		frame.setVisible(true);
	}
}
