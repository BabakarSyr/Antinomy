package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Plateau {
     public Joueur joueurActif;

     public Joueur joueur1;
     public Joueur joueur2;

   
   

     List<Carte> continuum=new ArrayList<>();

     
    public Codex codex;
  
    
  
    public Plateau() {
        
        
        joueur1=new Joueur("",0);
        joueur2=new Joueur("",0);
      
        joueurActif = joueur1;
        codex=new Codex(new Carte(null, null,0));

    
        initialiser();

       

    }
   

    public void initialiser(){
    

        continuum.add(new Carte(Forme.PLUME, Couleur.VERT,1));
        continuum.add(new Carte(Forme.PLUME, Couleur.VIOLET,2));
        continuum.add(new Carte(Forme.PLUME, Couleur.BLEU,3));
        continuum.add(new Carte(Forme.PLUME, Couleur.ROUGE,4));

        continuum.add(new Carte(Forme.ANNEAU, Couleur.VIOLET,1));
        continuum.add(new Carte(Forme.ANNEAU, Couleur.BLEU,2));
        continuum.add(new Carte(Forme.ANNEAU, Couleur.ROUGE,3));
        continuum.add(new Carte(Forme.ANNEAU, Couleur.VERT,4));

        continuum.add(new Carte(Forme.CLE, Couleur.ROUGE,1));
        continuum.add(new Carte(Forme.CLE, Couleur.VERT,2));
        continuum.add(new Carte(Forme.CLE, Couleur.VIOLET,3));
        continuum.add(new Carte(Forme.CLE, Couleur.BLEU,4));

        continuum.add(new Carte(Forme.CRANE, Couleur.BLEU,1));
        continuum.add(new Carte(Forme.CRANE, Couleur.ROUGE,2));
        continuum.add(new Carte(Forme.CRANE, Couleur.VERT,3));
        continuum.add(new Carte(Forme.CRANE, Couleur.VIOLET,4));
        
        // Mélanger les cartes Reliques
        Collections.shuffle(continuum);


       
        MainDeCartes mainJoueur1=new MainDeCartes();
        MainDeCartes mainJoueur2=new MainDeCartes();
        // Distribuer les cartes RElique aux joueurs
        for(int i = 0; i < 3; i++) {
            mainJoueur1.ajouterCarte(continuum.remove(0));
         
            mainJoueur2.ajouterCarte(continuum.remove(0));
            
        }
        joueur1.main=mainJoueur1;
        joueur2.main=mainJoueur2;
        

        codex.setCarte(continuum.remove(continuum.size()-1));
         /*Couleur interdite au debut du jeu
         On place un cristal sur la couleur du Codex correspondant à la couleur de la carte la plus à gauche du continuum. 
          Ce sera la couleur interdite.*/
          Couleur couleurInterdite = continuum.get(0).getCouleur();
          
          codex.setCouleur(couleurInterdite);
  
   
     

     
       

    }

    public void setJoueurActif(int i) {
        if(i==1)
            joueurActif = joueur1;
        else
            joueurActif = joueur2;
    
    }


    public Joueur getJoueur(int i) {
        if (i == 1) {
            return joueur1;
        } else {
            return joueur2;
        }
    }
    



    public String toString() {
        String s = "";
        s += "Joueur 1 : " + joueur1.getNom() + "\n";
        s += "Joueur 2 : " + joueur2.getNom() + "\n";
        s += "Joueur actif : " + joueurActif + "\n";
       
        //s += "Position Sorcier Joueur 1 : " + positionSorcierJoueur1 + "\n";
        //s += "Position Sorcier Joueur 2 : " + positionSorcierJoueur2 + "\n";
        s += "Codex : " + codex + "\n";
        s += "Continuum : " + continuum + "\n";
        //s += "Main Joueur 1 : " + mainJoueur1 + "\n";
        //s += "Main Joueur 2 : " + mainJoueur2 + "\n";
        return s;
    }

    public void changerJoueurActif() {
        if (joueurActif == joueur1) {
            joueurActif = joueur2;
        } else {
            joueurActif = joueur1;
        }
    }


    public Joueur getJoueurActif() {
        return joueurActif;

    }
   
    public void afficher_continuum() {
        for (int i = 0; i < continuum.size(); i++) {
            System.out.println(i+":"+continuum.get(i));
        }
    }
    

    public int getPositionSorcier(int joueur) {
        if (joueur == 1) 
            return joueur1.getSorcier().getPositionSorcier();
        else 
            return joueur2.getSorcier().getPositionSorcier();
        
    }

    public void setTempsSorcier(int joueur) {
        if (joueur == 1) {
            joueur1.getSorcier().setSensDuTemps(true);
        } else {
            joueur2.getSorcier().setSensDuTemps(false);
        }
    }

    public void setPositionSorcier(int pos, int joueur) {
        if (joueur == 1) {
            joueur1.getSorcier().setPositionSorcier(pos);
        } else {
            joueur2.getSorcier().setPositionSorcier(pos);
        }
    }


    //Fonction qui me donne les cartes portant la couleur interdite sur le continuum
    public  void pos_carte_couleur_interdite() {
        for (int j = 0; j < continuum.size(); j++) {
            if (continuum.get(j).getCouleur() == codex.getCouleurInterdite()) {
               System.out.println( j);
            }
        }
    }
}
