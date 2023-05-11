package Vue;

public interface CollecteurEvenements {
    
    void clicSouris(int coupX, int coupY);
    void traqueSouris(int coupX, int coupY);
	boolean commande(String com);
    void clicCarteMain(int indiceCarte);
    void clicCarteContinuum(int indiceCarte);
    int carteSelectionnee();
    boolean voirMainAdversaire();
    boolean voirMainJoueurActif();
    String infoPlateau();
    void clicCarteMainAdverse();
    boolean joueurActif(int numJoueur);
}
