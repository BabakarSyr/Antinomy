package Vue;


import javax.swing.*;

import Modele.Carte;
import Modele.Jeu;
import Modele.Joueur;

import Modele.ZoneClic;

import java.awt.*;
import java.util.ArrayList;

import Modele.EtatJeu;

public class RegleGraphique extends JPanel {

    int width, height;
    Point position;
    ImageJeu menuBackground;

    Graphics2D drawable;
    CollecteurEvenements c;


    public RegleGraphique(CollecteurEvenements c) {
        menuBackground = new ImageJeu("regle1");
    }

    public void paintComponent(Graphics g) {
        // Graphics 2D est le vrai type de l'objet passé en paramètre
        // Le cast permet d'avoir acces a un peu plus de primitives de dessin
        drawable = (Graphics2D) g;

        // On reccupere quelques infos provenant de la partie JComponent
        width = getSize().width;
        height = getSize().height;
        // Si la position n'est pas fixée, on calcule le centre de la zone et un rayon
        if (position == null)
            position = new Point(width/2, height/2);

        // On efface tout
        drawable.clearRect(0, 0, width, height);

        Aspects aspects = new Aspects(2);
        tracerImageRegle(aspects);
        

    }

    public void tracerImageRegle(Aspects aspects){
        int i = c.getImageRegle();
        switch(i){
            case 1:
                drawable.drawImage(aspects.regle1.image(), 0, 0, width, height, null);
                break;
            case 2:
                drawable.drawImage(aspects.regle2.image(), 0, 0, width, height, null);
                break;
            case 3:
                drawable.drawImage(aspects.regle3.image(), 0, 0, width, height, null);
                break;
            case 4:
                drawable.drawImage(aspects.regle3_1.image(), 0, 0, width, height, null);
                break;
            case 5:
                drawable.drawImage(aspects.regle5.image(), 0, 0, width, height, null);
                break;
            case 6:
                drawable.drawImage(aspects.regle6.image(), 0, 0, width, height, null);
                break;
            case 7:
                drawable.drawImage(aspects.regle8.image(), 0, 0, width, height, null);
                break;
            default:
                break;
        }
    }
}