package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plateau2 extends Plateau 
{
    @Override
    public List<Carte> getAll_Cartes() {
        List<Carte> All_Cartes=new ArrayList<>();
        All_Cartes.add(new Carte(Forme.CLE, Couleur.VERT,2));

        All_Cartes.add(new Carte(Forme.CLE, Couleur.BLEU,4));
        

        All_Cartes.add(new Carte(Forme.PLUME, Couleur.BLEU,3));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.ROUGE,4));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.VERT,3));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.BLEU,2));
        
        
        
        
       
        
        
        
        
        All_Cartes.add(new Carte(Forme.CLE, Couleur.VIOLET,3));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.BLEU,1));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.VIOLET,2));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.VERT,1));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.ROUGE,2));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.VIOLET,4));
       
        All_Cartes.add(new Carte(Forme.CLE, Couleur.ROUGE,1));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.ROUGE,3));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.VERT,4));


        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.VIOLET,1));

        return All_Cartes;
    }

    @Override
    public void initialiser(){
        List <Carte> All_Cartes=getAll_Cartes();
       //Copier le contenu de All_Cartes dans continuum
        continuum.addAll(All_Cartes);

        
        
        // Mélanger les cartes Reliques
        //Collections.shuffle(continuum);

        ArrayList<Carte> mainJoueur1=new ArrayList<>();
        ArrayList<Carte> mainJoueur2=new ArrayList<>();
        // Distribuer les cartes RElique aux joueurs
        for(int i = 0; i < 3; i++) {
            mainJoueur1.add(continuum.remove(0));
        
            mainJoueur2.add(continuum.remove(0));
            
        }
        joueur1.setMain(mainJoueur1);
        joueur2.setMain(mainJoueur2);
        
        codex.setCarte(continuum.remove(continuum.size()-1));
        /*Couleur interdite au debut du jeu
        On place un cristal sur la couleur du Codex correspondant à la couleur de la carte la plus à gauche du continuum. 
        Ce sera la couleur interdite.*/
        Couleur couleurInterdite = continuum.get(0).getCouleur();
          
        codex.setCouleur(couleurInterdite);

        positionsDepart = positionsDepartPossible();
    }

}