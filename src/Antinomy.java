

import java.io.InputStream;

import javax.swing.* ;
//import java.awt.* ;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Jeu;
import Modele.Plateau;
import Vue.CollecteurEvenements;
import Vue.DemoFenetre;


// L'interface runnable déclare une méthode run
public class Antinomy {
	//TODO Etablir fichier configuration
	final static String typeInterface = "Graphique";

	public static void main(String[] args) {
		Plateau plateau = new Plateau();
		Jeu jeu = new Jeu(plateau);
		CollecteurEvenements control = new ControleurMediateur();
		switch (typeInterface) {
			case "Graphique":
				DemoFenetre.demarrer(jeu, control);
				break;
			default:
				Configuration.erreur("Interface inconnue");
		}
	}

}
