package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class Plateau {
    public Joueur joueurActif;

    public Joueur joueur1;
    public Joueur joueur2;
    ArrayList<Integer> positionsDepart;
    List<Carte> All_Cartes=new ArrayList<>();

    ArrayList<Carte> continuum=new ArrayList<>();

     
    public Codex codex;
  
    
  
    public Plateau() {
        
        
        joueur1=new Joueur("",0);
        joueur2=new Joueur("",0);
      
        joueurActif = joueur1;
        codex=new Codex(new Carte(null, null,0));
    
        initialiser();
    }
   
    public Plateau copie() {
        Plateau copie = new Plateau();
        copie.joueurActif = joueurActif.copie();
        copie.joueur1 =joueur1.copie();
        copie.joueur2 = joueur2.copie();
        copie.All_Cartes = new ArrayList<>(this.All_Cartes);
        copie.continuum = new ArrayList<>(this.continuum);
        copie.codex = codex;
        return copie;
    }
    
    
    
    

    public void initialiser(){
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.VERT,1));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.VIOLET,2));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.BLEU,3));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.ROUGE,4));

        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.VIOLET,1));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.BLEU,2));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.ROUGE,3));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.VERT,4));

        All_Cartes.add(new Carte(Forme.CLE, Couleur.ROUGE,1));
        All_Cartes.add(new Carte(Forme.CLE, Couleur.VERT,2));
        All_Cartes.add(new Carte(Forme.CLE, Couleur.VIOLET,3));
        All_Cartes.add(new Carte(Forme.CLE, Couleur.BLEU,4));

        All_Cartes.add(new Carte(Forme.CRANE, Couleur.BLEU,1));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.ROUGE,2));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.VERT,3));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.VIOLET,4));

       //Copier le contenu de All_Cartes dans continuum
        continuum.addAll(All_Cartes);

        
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

    public int deplacementFuturPossible(Carte carteChoisie) {
        int valeurCarte = carteChoisie.getValeur();
        int res=-1;
        int positionSorcier = joueurActif.sorcier().positionSorcier;
        //Si le futur du sorcier est a droite
        if(joueurActif == joueur1)
            if(positionSorcier+ valeurCarte < continuum.size())
                res= positionSorcier+valeurCarte;
        
        // le futur est a gauche
        else
            if(positionSorcier - valeurCarte >= 0)
                res= positionSorcier-valeurCarte;
        return res;
        
    }

    public  ArrayList<Integer> deplacementPassePossible(Carte carteChoisie) {
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();
        ArrayList<Integer> positions=new ArrayList<>();
        //Si le passé du sorcier est à gauche
        if(joueurActif == joueur1){
            for (int i = joueurActif.sorcier().positionSorcier - 1; i >= 0; i--) {
                if( continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte){
                    positions.add (i);
                }        
            }
        } 
        //Le passé du sorcier est à droite
        else {
            for (int i = joueurActif.sorcier().positionSorcier + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte){
                    positions.add (i);
                }
            }
        }
        return positions;
    }

    public ArrayList<Integer> cartesAccessibles(Carte carteChoisie) {
        ArrayList <Integer> positions = new ArrayList<Integer>();
        List <Integer> positionsPasse = deplacementPassePossible(carteChoisie);
        int positionFutur = deplacementFuturPossible(carteChoisie);
        if(positionFutur != -1)
            positions.add(positionFutur);
        if(!positionsPasse.isEmpty())
            positions.addAll(positionsPasse);
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
        if(joueurActif.getNom().equals(joueur1.getNom())){
            joueurActif=joueur1;
            return joueur1;
        }
         
        else{
            joueurActif=joueur2;
            return joueur2;
        }

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


