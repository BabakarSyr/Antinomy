package Modele;

import java.util.ArrayList;
import java.util.Collections;



public class Plateau {
    public Joueur joueurActif;

    public Joueur joueur1;
    public Joueur joueur2;
    ArrayList<Integer> positionsDepart;

    ArrayList<Carte> continuum=new ArrayList<>();

     
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

        ArrayList<Carte> mainJoueur1=new ArrayList<>();
        ArrayList<Carte> mainJoueur2=new ArrayList<>();
        // Distribuer les cartes RElique aux joueurs
        for(int i = 0; i < 3; i++) {
            mainJoueur1.add(continuum.remove(0));
        
            mainJoueur2.add(continuum.remove(0));
            
        }
        joueur1.main=mainJoueur1;
        joueur2.main=mainJoueur2;
        
        codex.setCarte(continuum.remove(continuum.size()-1));
        /*Couleur interdite au debut du jeu
        On place un cristal sur la couleur du Codex correspondant à la couleur de la carte la plus à gauche du continuum. 
        Ce sera la couleur interdite.*/
        Couleur couleurInterdite = continuum.get(0).getCouleur();
          
        codex.setCouleur(couleurInterdite);

        positionsDepart = positionsDepartPossible();
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

    public ArrayList<Integer> positionsDepart(){
        return positionsDepart;
    }

    public Couleur couleurInterdite(){
        return codex.couleurInterdite;
    }

    public Joueur getJoueurParNom(String nom)
    {
        if (joueur1.getNom() == nom)
        {
            return joueur1;
        }
        else if (joueur2.getNom() == nom)
        {
            return joueur2;
        }
        return null;
    }

    public boolean deplacementFuturPossible(Carte carteChoisie) {
        int valeurCarte = carteChoisie.getValeur();
        //Si le futur du sorcier est a droite
        if(joueurActif == joueur1){
            return (joueurActif.sorcier().positionSorcier + valeurCarte < continuum.size());
        }
        // le futur est a gauche
        else{
            return (joueurActif.sorcier().positionSorcier - valeurCarte >= 0);
        }
    }

    public boolean deplacementPassePossible(Carte carteChoisie) {
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();

        //Si le passé du sorcier est à gauche
        if(joueurActif == joueur1){
            for (int i = joueurActif.sorcier().positionSorcier - 1; i >= 0; i--) {
                if( continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte){
                    return true;
                }        
            }
        } 
        //Le passé du sorcier est à droite
        else {
            for (int i = joueurActif.sorcier().positionSorcier + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Integer> cartesAccessibles(Carte carteChoisie) {
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();
        ArrayList <Integer> positions = new ArrayList<Integer>();
        if(joueurActif == joueur1){
            if (this.deplacementFuturPossible(carteChoisie)){
                positions.add(joueurActif.sorcier().positionSorcier + carteChoisie.getValeur());
            }
            for (int i = 0; i < joueurActif.sorcier().positionSorcier; i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) {
                    positions.add(i);
                    System.out.println("Les positions possibles pour vous deplacer vers le passé sont : " + i);
                }
            }
        } else {
            if (this.deplacementFuturPossible(carteChoisie)){
                positions.add(joueurActif.sorcier().positionSorcier - carteChoisie.getValeur());
            }
            for (int i = joueurActif.sorcier().positionSorcier + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) {
                   positions.add(i);
                   System.out.println("Les positions possibles pour vous deplacer vers le passé sont : " + i);
                }
            }
        }
        return positions;
    }

    public void afficherCartesAcceccibles(ArrayList<Integer> listeIndiceCartes)
    {
        System.out.println("emplacements possibles :");
        for (int i=0; i<listeIndiceCartes.size(); i++)
        {
            System.out.println(listeIndiceCartes.get(i));
        }
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

    public ArrayList<Carte> getContinuum() {
        return continuum;
    }
    public void afficherContinuum() {
        for (int i = 0; i < continuum.size(); i++) {
            System.out.println(i+":"+continuum.get(i));
        }
    }

    public void afficher_colorSorcier_continuum(int posSorcier1,int posSorcier2) {
       //Il y a duel,on surligne en rouge la position
        if(posSorcier1==posSorcier2){
            System.out.println("\033[31m\033[7m"+posSorcier1+ "\033[0m:  " + continuum.get(posSorcier1) + "\n");
        }
        
        for (int i = 0; i < continuum.size(); i++) {
            if (i == posSorcier1) {
                //Colorer en rose
                System.out.print(" \033[95m\033[7m"+i+ "\033[0m:  " + continuum.get(i) + "\n");

            } 
            else if (i == posSorcier2) {
                System.out.print(" \033[38;5;208m\033[7m"+i+ "\033[0m:  " + continuum.get(i) + "\n");
            }
            
            else {
                System.out.print(  i+":"+continuum.get(i)+"\n");
            }
        }
        System.out.println();
    }
    

    public int getPositionSorcier(int joueur) {
        if (joueur == 1) 
            return joueur1.sorcier.getPositionSorcier();
        else 
            return joueur2.sorcier.getPositionSorcier();
    }

    public void setTempsSorcier(int joueur) {
        if (joueur == 1) {
            joueur1.sorcier.setSensDuTemps(true);
        } else {
            joueur2.sorcier.setSensDuTemps(false);
        }
    }

    //Fonction qui me donne les cartes portant la couleur interdite sur le continuum
    public  ArrayList <Integer> positionsDepartPossible() {
        ArrayList <Integer> pos = new ArrayList<>();
        for (int j = 0; j < continuum.size(); j++) {
            if (continuum.get(j).getCouleur() == codex.getCouleurInterdite()) {
                pos.add(j);
            }
        }
        return pos;
    }

    public String colorerPositionSorcier(int pos_sorcier,int joueur) {
        String s = "";
        if (joueur == 1) {
            for (int i = 0; i <=continuum.size(); i++) {
                if (i == pos_sorcier ) {
                    //La baguette du joueur1 colore en rose
                    s = " \033[95m\033[7m" + pos_sorcier + "\033[0m!";
                } else {
                    s = "" + pos_sorcier;
                }
            }
        } else {
            for (int i = 0; i <=continuum.size(); i++) {
                if (i == pos_sorcier ) {
                    //La baguette du joueur2 colore en orange
                    s = " \033[38;5;208m\033[7m" + pos_sorcier + "\033[0m!";
                } else {
                    s = "" + pos_sorcier;
                }
            }
        }
        return s;
    }
}


