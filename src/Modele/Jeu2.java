package Modele;

import java.util.ArrayList;
import java.util.Random;

public class Jeu2 extends Jeu{

    public Jeu2()
    {
		this.plateau = new Plateau2();
    }
    




    public Jeu2 (String joueur1, String joueur2, String typeJ1, String typeJ2)
    {
        this.plateau = new Plateau(joueur1, joueur2, typeJ1, typeJ2);
    }

    public Jeu2(Plateau p)
    {
        this.plateau = p;
    }

    ////////
    public Plateau plateau()
    {
        return this.plateau;
    }

   
}

