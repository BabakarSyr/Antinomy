package Patterns;
import Global.Configuration;
import Structures.Iterateur;
import Structures.Sequence;

public class Observable {

    Sequence<Observateur> observateurs;

    public Observable() {
        Configuration.instance();
        observateurs = Configuration.nouvelleSequence();
    }

    public void ajouteObservateur(Observateur observateur) {
        observateurs.insereQueue(observateur);
    }

    public void miseAJour() {
        Iterateur<Observateur> iterateur;
        iterateur = observateurs.iterateur();
        while (iterateur.aProchain()) {
            ((Observateur) iterateur.prochain()).miseAJour();
        }
    }

}
