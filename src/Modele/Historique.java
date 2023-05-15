package Modele;

import Global.Configuration;
import Patterns.Commande;
import Structures.Sequence;

public class Historique<E extends Commande> {
	Sequence<E> passe, futur;

	public Historique() {
		reinitialise();
	}

	void reinitialise() {
		passe = Configuration.nouvelleSequence();
		futur = Configuration.nouvelleSequence();
	}

	public boolean peutAnnuler() {
		return !passe.estVide();
	}

	public boolean peutRefaire() {
		return !futur.estVide();
	}

	private E transfert(Sequence<E> source, Sequence<E> destination) {
		E resultat = source.extraitTete();
		destination.insereTete(resultat);
		return resultat;
	}

	public E annuler() {
		E comm = transfert(passe, futur);
		comm.desexecute();
		return comm;
	}

	public E refaire() {
		E comm = transfert(futur, passe);
		comm.execute();
		return comm;
	}

	public void faire(E nouveau) {
		nouveau.execute();
		passe.insereTete(nouveau);
		while (!futur.estVide()) {
			futur.extraitTete();
		}
	}

	@Override
	protected Historique clone() throws CloneNotSupportedException {
		// Pour simplifier, on décide de ne pas cloner l'historique :
		// notre seul cas d'utilisation est pour le niveau de l'IA qui
		// n'a pas besoin de l'historique utilisateur. Par contre il faut
		// que le clone ait son propre historique (initialement vide)
		Historique resultat = (Historique) super.clone();
		resultat.reinitialise();
		return resultat;
	}
}
