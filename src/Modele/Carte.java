package Modele;
enum Forme {
   PLUME,
   ANNEAU,
   CLE,
   CRANE
}


public class Carte {
    public enum Couleur {
        VIOLET,
        VERT,
        BLEU,
        ROUGE;
    
            public String getCode() {
                String code = "";
                switch (this) {
                    case ROUGE:
                        code = "\033[31mROUGE\033[0m"; // Rouge
                        break;
                    case VERT:
                        code = "\033[32mVERT\033[0m"; // Vert
                        break;
                    case BLEU:
                        code = "\033[34mBLEU\033[0m"; // Bleu
                        break;
                    case VIOLET:
                        code = "\033[35mVIOLET\033[0m"; // Violet
                        break;
                    default:
                        code = "\033[0m"; // Couleur par défaut (blanc)
                        break;
                }
                return code;
            }
            public String getCouleur() {
                switch (this) {
                    case ROUGE:
                        return "ROUGE";
                    case VERT:
                        return "VERT";
                    case BLEU:
                        return "BLEU";
                    case VIOLET:
                        return "VIOLET";
                    default:
                        return "BRUH";
                }
            }
        }
    
    
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
     
