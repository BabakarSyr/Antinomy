package Modele;


import Patterns.Commande;
import Structures.Sequence;
import Structures.SequenceListe;

public class Historique<E extends Commande> {
	Sequence<E> passe, futur;

	public Historique() {
		reinitialise();
	}

	void reinitialise() {
		passe = new SequenceListe<>();
		futur = new SequenceListe<>();
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
		//nouveau.execute();
		passe.insereTete(nouveau);
		while (!futur.estVide()) {
			futur.extraitTete();
		}
	}
}
