package Modele;
import Patterns.Commande;

public class Coup extends Commande{

    int indiceCarteJouee, indiceCarteContinuum, indiceParadoxe;
    Plateau plateau;

    public Coup() {
    }

    public void fixerPlateau(Plateau p){
        this.plateau = p;
    }

    public Coup creerCoup(int indiceCarteJouee, int indiceContinuum, int indiceParadoxe){
        this.indiceCarteJouee = indiceCarteJouee;
        this.indiceCarteContinuum = indiceContinuum;
        this.indiceParadoxe = indiceParadoxe;
        return this;
    }

    @Override
    public void execute(){

    }

    @Override
	public void desexecute(){

    }

}
