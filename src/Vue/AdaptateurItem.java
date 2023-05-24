package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.JComboBox;

public class AdaptateurItem implements ActionListener {


    // =====================
    // ===== ATTRIBUTS =====
    // =====================
    CollecteurEvenements collecteurEvenements;
    JComboBox<String> box;

    /////////////////////////////////////////////////////////////////////////

    // ========================
    // ===== CONSTRUCTEUR =====
    // ========================
    public AdaptateurItem(CollecteurEvenements cEvenements, JComboBox<String> box) {
        collecteurEvenements = cEvenements;
        this.box = box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(box.getName() == "cb1"){
            collecteurEvenements.setTypeJ1(box.getSelectedItem().toString());
        }else{
            collecteurEvenements.setTypeJ2(box.getSelectedItem().toString());
        }
    }

}