package Modele;
import java.util.ArrayList;

import Patterns.Commande;
import Structures.Iterateur;
import Structures.Sequence;
import Structures.SequenceListe;

public class Coup extends Commande{

    int indiceCarteJouee, indiceCarteContinuum, indiceParadoxe;
    ArrayList<Carte> mainJoueur;
    ArrayList<Carte> continuum;
    Plateau plateau;
    Sequence<Plateau> sequencePlateau;

    public Coup() 
    {
        sequencePlateau = new SequenceListe<Plateau>();
    }

    public int indiceCarteContinuum()
    {
        return this.indiceCarteContinuum;
    }

    public int indiceCarteJouee()
    {
        return this.indiceCarteJouee;
    }

    public int indiceParadoxe()
    {
        return this.indiceParadoxe;
    }

    public void fixerPlateau(Plateau p){
        this.plateau = p;
    }

    public void creerCoup(Plateau p){
        try{
            Plateau plateau = p.clone();
            sequencePlateau.insereQueue(plateau);
        }catch (Exception e) {
            // TODO: handle exception
        }
    }

    @Override
    public void execute(){
        jouerCoup();
    }

    @Override
	public void desexecute(){
        jouerCoup();
    }

    private void jouerCoup() {
        Iterateur<Plateau> iterateur = sequencePlateau.iterateur();
        while (iterateur.aProchain()) {
            Plateau p = iterateur.prochain();
            this.plateau.majPlateau(p);
        }
    }

}
