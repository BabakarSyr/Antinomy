package Modele;
enum Forme {
   PLUME,
   ANNEAU,
   CLE,
   CRANE
}


public class Carte {

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

}
     
