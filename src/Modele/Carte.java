package Modele;

import Global.Configuration;

enum Forme {
   PLUME,
   ANNEAU,
   CLE,
   CRANE
}


public class Carte implements Cloneable{

     Forme forme;
     Couleur couleur;
     int valeur;

    

     public Carte(Forme forme, Couleur couleur, int valeur) {
         this.forme = forme;
         this.couleur = couleur;
         this.valeur = valeur;
     }

    
     public Couleur getCouleur() {
        return couleur;
    }

    public int getValeur() {
        return valeur;
    }

    public int getValeur(Couleur couleurInterdite) {
        return (couleur == couleurInterdite) ? 0 : valeur;//retunr 0 si la couleur est interdite sinon retourne la valeur de la carte
    }
    public Forme getForme() {
        return forme;
    }


    public String getCarte() {
        return forme + "_" + couleur.getCouleur();
    }
    
    public String toString() {
        return "{" +
        "forme=" + forme +
        ", valeur=" + valeur +
        ", couleur=" + couleur.getCode( )  +
        '}';
    }

    public Carte clone()
    {
        try
        {
            Carte cloneCarte = (Carte) super.clone();
            cloneCarte.forme = this.forme;
            cloneCarte.couleur = this.couleur;
            cloneCarte.valeur = this.valeur;
            return cloneCarte;
        }
        catch (CloneNotSupportedException e)
        {
            Configuration.erreur("Bug interne, carte non clonable");
            return null;
        }
    }

}
     
