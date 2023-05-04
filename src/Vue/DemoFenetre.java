package Vue;

import javax.swing.* ;
//import java.awt.* ;

import Modele.*;
import Controleur.ControleurMediateur;
import Vue.CollecteurEvenements;

// L'interface runnable déclare une méthode run
public class DemoFenetre implements Runnable {
	Jeu jeu;
	CollecteurEvenements controleur;

	public DemoFenetre(Jeu j, CollecteurEvenements c){
		jeu = j;
		controleur = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements c) {
		DemoFenetre vue = new DemoFenetre(j, c);
		SwingUtilities.invokeLater(vue);
	}


	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("Ma fenetre a moi");
		frame.setSize(500, 300);

		// Ajout de notre composant de dessin dans la fenetre
		AireDeDessin aire = new AireDeDessin(jeu);
		frame.add(aire);

		// Ecoute des évènements liés à la souris dans l'AireDeDessin
		aire.addMouseListener(new AdaptateurSouris(aire));

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		
		frame.setVisible(true);
	}
}
