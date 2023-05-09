
//import java.awt.* ;

import Controleur.ControleurMediateur;
import Global.Configuration;
import Modele.Jeu;
import Modele.Plateau;
import Vue.CollecteurEvenements;
import Vue.InterfaceGraphique;


// L'interface runnable déclare une méthode run
public class Antinomy {
	//TODO Etablir fichier configuration
	final static String typeInterface = "Graphique";

	public static void main(String[] args) {
		Jeu jeu = new Jeu();
		CollecteurEvenements control = new ControleurMediateur(jeu);
		switch (typeInterface) {
			case "Graphique":
				InterfaceGraphique.demarrer(jeu, control);
				break;
			default:
				Configuration.erreur("Interface inconnue");
		}
	}

}
