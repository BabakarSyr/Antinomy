package Structures;
import Structures.Iterateur;

public interface Sequence<E> {
	void insereQueue(E element);

	void insereTete(E element);

	E extraitTete();

	boolean estVide();

	Iterateur<E> iterateur();
}
