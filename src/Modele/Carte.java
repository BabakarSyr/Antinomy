package Modele;

enum TypeCarte {
    RELIQUE,
    SORCIER,
}
enum Forme {
   PLUME,
   ANNEAU,
   CLE,
   CRANE
}

enum Couleur {
   VIOLET,
   VERT,
   BLEU,
   ROUGE
}

/* 
class Codex extends Carte {
    public Codex(Couleur couleurInterdite) {
        super(null, null, 0, TypeCarte.CODEX, couleurInterdite);
    }

    public boolean isCodex() {
        return typeCarte == TypeCarte.CODEX;
    }
   
    public Couleur getCouleurInterdite() {
        if (!isCodex() ) {
            throw new IllegalStateException("La carte n'est pas un Codex face visible");
        }
        return couleurInterdite;
    }
}*/


public class Carte {
     Forme forme;
     Couleur couleur;
     int valeur;

     Couleur couleurInterdite;


     public Carte(Forme forme, Couleur couleur, int valeur) {
         this.forme = forme;
         this.couleur = couleur;
         this.valeur = valeur;
     }

    
     public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
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
    public void setForme(Forme forme) {
        this.forme = forme;
    }



}
