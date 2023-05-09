package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Modele.Carte.Couleur;


public class Partie {
     public Joueur joueurActif;

     public Joueur joueur1;
     public Joueur joueur2;
    List<Carte> All_Cartes=new ArrayList<>();

    public int nbTours;

    List<Carte> continuum=new ArrayList<>();
    public Codex codex;
 
    public Partie() {
        
        
        joueur1=new Joueur("",0);
        joueur2=new Joueur("",0);
      
        joueurActif = joueur1;
        codex=new Codex(new Carte(null, null,0));
        this.nbTours=0;
        initialiser();

       

    }
    public Partie copie() {
        Partie copie = new Partie();
        copie.joueurActif = this.joueurActif.copy();
        copie.joueur1 =this.joueur1.copy();
        copie.joueur2 = this.joueur2.copy();
        copie.All_Cartes = new ArrayList<>(this.All_Cartes);
        copie.nbTours = this.nbTours;
        copie.continuum = new ArrayList<>(this.continuum);
        copie.codex = this.codex;
        return copie;
    }
    

    
    public void setNbTours(int nbTours) {
        this.nbTours = nbTours;
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

    public List<Carte> getContinuum() {
        return continuum;
    }
    public void afficher_continuum() {
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
    public  List <Integer> pos_carte_couleur_interdite() {
        List <Integer> pos = new ArrayList<>();
        for (int j = 0; j < continuum.size(); j++) {
            if (continuum.get(j).getCouleur() == codex.getCouleurInterdite()) {
                pos.add(j);
            }
        }
        return pos;
    }


    public String colorer_pos_sorcier(int pos_sorcier,int joueur) {
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
     

















    public boolean est_Possible_Placer_3cartes(String Direction) {
        int posSorcier=joueurActif.sorcier.getPositionSorcier();
        boolean possible=false;
        int nbCarte=0;
        switch(Direction) {
            case "gauche":
                for(int i=posSorcier-1;i>=0;i--){
                    nbCarte++;
                }
                break;
                
            case "droite":
                for(int i=posSorcier+1;i<continuum.size();i++){
                    nbCarte++;
                }
                break;
        }
        if(nbCarte>=3)
            possible= true;
        return possible;
    }
    
    public boolean isParadoxe(MainDeCartes mainJoueur) {
        //Si la main est vide, il n'y a pas de paradoxe
        if (mainJoueur.getCartes().isEmpty()) {
            return false;
        }


        Couleur couleurInterdite = codex.getCouleurInterdite();
        ArrayList<Carte> cartes = mainJoueur.getCartes();
    
        boolean memeCouleur = true;
        boolean memeForme = true;
        boolean memeValeur = true;
        boolean differentCouleurInterdite = true;
    
        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getForme() != cartes.get(0).getForme()) {
                memeForme = false;
                break;
            }
        }
    
        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getValeur() != cartes.get(0).getValeur()) {
                memeValeur = false;
                break;
            }
        }
    
        for (Carte carte : cartes) {
            if (carte.getCouleur() == couleurInterdite) {
                differentCouleurInterdite = false;
                break;
            }
        }

        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getCouleur() != cartes.get(0).getCouleur()) {
                memeCouleur = false;
                break;
            }
        }
    

        // Il y a un paradoxe si toutes les cartes ont la même couleur ou la même forme ou la même valeur
        // et toutes les cartes sont de couleur différente de la couleur interdite
        return (memeCouleur || memeForme || memeValeur) && differentCouleurInterdite;
    }
    


    
    public boolean partieTerminee() {
        return joueur1.getNombreCristaux() == 5 || joueur2.getNombreCristaux() == 5;
    }

    
    //On a duel si positionSorcierJoueur1 == positionSorcierJoueur2
    public boolean isDuel(){
        int positionSorcierJoueur1 = joueur1.sorcier.getPositionSorcier();
        int positionSorcierJoueur2 = joueur2.sorcier.getPositionSorcier();
        return positionSorcierJoueur1 == positionSorcierJoueur2;
    }


    public boolean duel() {
        System.out.println("C'est l'heure du Duel!");
        System.out.println("Rappel,la couleur interdite est :"+ (String)codex.getCouleurInterdite().getCode());
        System.out.println("Recapitulatif des cristaux :");
        int nbCristauxJoueur1=joueur1.getNombreCristaux();
        int nbCristauxJoueur2 =joueur2.getNombreCristaux();
        System.out.println(joueur1.getNom() + " : " + nbCristauxJoueur1);
        System.out.println(joueur2.getNom() + " : " + nbCristauxJoueur2);



        int totalJoueur1 = 0;
        int totalJoueur2 = 0;
        //Les 2 jouerurs affichent leur main
        System.out.println("La main du joueur 1 est : ");
        for(int i=0;i<3;i++){
           
            System.out.println("Carte "+i+" : "+joueur1.getMain().getCartes().get(i).toString());
            
        }
       System.out.println();
        System.out.println("La main du joueur 2 est : ");
        for(int i=0;i<3;i++){
            
            System.out.println("Carte "+i+" : "+joueur2.getMain().getCartes().get(i).toString());
        }
        System.out.println();
    


        
        Couleur couleurInterdite = codex.getCouleurInterdite();
        for (Carte carte :joueur1.getMain().getCartes()) {
          
                totalJoueur1 += carte.getValeur(couleurInterdite);
            
        }
    
        for (Carte carte : joueur2.getMain().getCartes()) {
     
                totalJoueur2 += carte.getValeur(couleurInterdite);
            
        }
        System.out.println("Le total de points du joueur " +joueur1.getNom()+ " est :" +totalJoueur1);
        System.out.println("Le total de points du joueur " +joueur2.getNom()+ " est :" +totalJoueur2);


        if (totalJoueur1 > totalJoueur2) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            if(joueur1.volerCristal(joueur2)){
                System.out.println(joueur1.getNom() + " gagne le duel et vole un cristal à " + joueur2.getNom());
                System.out.println("Récapitulatif des cristaux :");
                System.out.println(joueur1.getNom() + " : " + nbCristauxJoueur1);
                System.out.println(joueur2.getNom() + " : " + nbCristauxJoueur2);
                
                return true;
            }
            else{
                System.out.println("Impossible de voler un cristal à " + joueur1.getNom() + " car il n'en a plus  ou n'en a pas.");
                return false;
            }
        } 
        else if (totalJoueur1 < totalJoueur2) {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            if(joueur2.volerCristal(joueur1)){
                System.out.println(joueur2.getNom() + " gagne le duel et vole un cristal à " + joueur1.getNom());
                System.out.println("Récapitulatif des cristaux :");
                System.out.println(joueur1.getNom() + " : " + nbCristauxJoueur1);
                System.out.println(joueur2.getNom() + " : " + nbCristauxJoueur2);
           
                return true;
                
            }
            else{
                System.out.println("Impossible de voler un cristal à " + joueur2.getNom() + " car il n'en a plus  ou n'en a pas.");
                return false;
            }
        } 
         // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
         Random random = new Random();
         //boolean egalite = true;
             
             
         joueur1.getMain().melangerCartes();
         joueur2.getMain().melangerCartes();
             
        // Sélectionner une carte au hasard pour chaque joueur
         int indexCarteJoueur1 = random.nextInt(3);
         int indexCarteJoueur2 = random.nextInt(3);
 
         Carte carteJoueur1 = joueur1.getMain().getCarte(indexCarteJoueur1);
         System.out.println(joueur1.getNom() + " tire la carte " + carteJoueur1.toString());
         Carte carteJoueur2 = joueur2.getMain().getCarte(indexCarteJoueur2);
 
         System.out.println(joueur2.getNom() + " tire la carte " + carteJoueur2.toString());
         // On compare  les valeurs des cartes en tenant compte de la couleur interdite
         int valeurCarteJoueur1 = carteJoueur1.getValeur(codex.getCouleurInterdite());
         int valeurCarteJoueur2 = carteJoueur2.getValeur(codex.getCouleurInterdite());
             
         if (valeurCarteJoueur1 > valeurCarteJoueur2) {
             joueur1.volerCristal(joueur2);
             System.out.println(joueur1.getNom() + " gagne le duel et vole un cristal à " + joueur2.getNom());
             return true;
         } else if (valeurCarteJoueur1 < valeurCarteJoueur2) {
             joueur2.volerCristal(joueur1);
             System.out.println(joueur2.getNom() + " gagne le duel et vole un cristal à " + joueur1.getNom());
             return true;
         } else {
             System.out.println("Égalité a nouveau, le duel est annulé.");
             return false;
         }
    }

}
