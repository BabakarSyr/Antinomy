package Modele;

public class Codex
{
    Couleur couleurInterdite;
    Carte carte;

    public Codex(Carte carte)
    {
        // initialisation de la couleur interdite à Rouge par défaut
        this.couleurInterdite = Couleur.ROUGE;
        this.carte = carte;
    }

    public Codex (Codex codex)
    {
        this.couleurInterdite = codex.getCouleurInterdite();
        this.carte = new Carte(codex.carte);
    }

    public void setCarte(Carte carte) {
        this.carte = carte;
    }
    // retourne la couleur interdite actuelle
    public Couleur getCouleurInterdite() {
        return this.couleurInterdite;
    }

    public String getCode(){
        String code = "";
        switch (this.couleurInterdite) {
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
