package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdaptateurCommande implements ActionListener {


    // =====================
    // ===== ATTRIBUTS =====
    // =====================
    CollecteurEvenements collecteurEvenements;
    String commande;

    /////////////////////////////////////////////////////////////////////////

    // ========================
    // ===== CONSTRUCTEUR =====
    // ========================
    public AdaptateurCommande(CollecteurEvenements cEvenements, String com) {
        collecteurEvenements = cEvenements;
        commande = com;
    }

    // ===========================
    // ===== ACTION COMMANDE =====
    // ===========================
    @Override
    public void actionPerformed(ActionEvent e) {
        collecteurEvenements.commande(commande);
    }
}