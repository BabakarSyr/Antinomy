package Modele;

public class Codex {
     Couleur couleurInterdite;
     Carte carte;

    public Codex(Carte carte) {
        // initialisation de la couleur interdite à Rouge par défaut
        this.couleurInterdite = Couleur.ROUGE;
        this.carte = carte;
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }
    // retourne la couleur interdite actuelle
    public Couleur getCouleurInterdite() {
        return this.couleurInterdite;
    }
    public void setCouleur(Couleur couleurInterdite) {
        this.couleurInterdite = couleurInterdite;
    }

    // change la couleur interdite dans le sens horaire (Rouge -> Bleu -> Violet -> Vert -> Rouge)
    public void changerCouleurInterdite() {
        switch (this.couleurInterdite) {
            case ROUGE:
                this.couleurInterdite = Couleur.BLEU;
                break;
            case BLEU:
                this.couleurInterdite = Couleur.VERT;
                break;
            case VERT:
                this.couleurInterdite = Couleur.VIOLET;
                break;
            case VIOLET:
                this.couleurInterdite = Couleur.ROUGE;
                break;
        }
    }


}
