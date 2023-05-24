
package Vue;

import Modele.EtatJeu;

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
    public EtatJeu etatJeu();
    void interfaceGraphique(InterfaceGraphique interfaceGraphique);
    void setTypeJ1(String string);
    void setTypeJ2(String string);
}