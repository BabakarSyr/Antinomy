
package Vue;

public interface CollecteurEvenements {
    
    void clicSouris(int coupX, int coupY);
    void traqueSouris(int coupX, int coupY);
	boolean commande(String com);
    void clicCarteMain(int indiceCarte, PlateauGraphique plateauGraphique);
    void clicCarteContinuum(int indiceCarte, PlateauGraphique plateauGraphique);
    int carteSelectionnee();
    boolean voirMainAdversaire();
    boolean voirMainJoueurActif();
    String infoPlateau();
    void clicCarteMainAdverse(PlateauGraphique plateauGraphique);
    boolean joueurActif(int numJoueur);
}