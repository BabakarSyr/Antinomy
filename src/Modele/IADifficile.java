package Modele;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Noeud {
    public Jeu etat;
    public int score;
    public List<Noeud> fils;

    public Noeud(Jeu etat) {
        this.etat = etat;
        this.score = 0;
        this.fils = new ArrayList<>();
    }

    public Noeud(Jeu etat, int score) {
        this.etat = etat;
        this.score = score;
        this.fils = new ArrayList<>();
    }


      
}




public class IADifficile {
    //IA c'es le joeuur 2 du plateau
   
    Plateau plateau_Avant_Coup_Adversaire;
    Map<Carte, Boolean> Potentiel_Main_Adversaire ;
    
    ArrayList<ArrayList<Carte>> mainPossible;
    int nbTours;

    Plateau clone;
    int[] meilleurCoup ;


    public IADifficile(String nom, int nombreCristaux,Plateau partie) {
     
        this.clone=partie;
        
        this.mainPossible=new ArrayList<>();
       
        this.Potentiel_Main_Adversaire = Cartes_inconnu_par_IA();
        this.mainPossible=All_Main_Possible();
        this.meilleurCoup=new int[2];
  
    }
   
    public Map<Carte, Boolean> Cartes_inconnu_par_IA() {
        Map<Carte, Boolean> Cartes = new HashMap<>();
        List<Carte> Reliques = clone.Reliques;
        List<Carte> continuum = clone.continuum;
        // Initialiser chaque carte à false (inconnue)
        for (Carte carte : Reliques) {
            Cartes.put(carte, false);
            
        }

        // Supprimer toutes les cartes que l'IA a en main dans Reliques
        for (int i = 0; i < 3; i++) {
           //Recuperer clé d'indice5 dans hashmap=
            Carte carte = clone.joueur1.main.get(i);
            Cartes.remove(carte); 
        }
        // Supprimer toutes les cartes Reliques présent dans Cartes
        for (int j = 0; j < continuum.size(); j++) {
            Carte carte_continuum = continuum.get(j);
            Cartes.remove(carte_continuum);
        }
        return Cartes;
    }


    public ArrayList<ArrayList<Carte>> All_Main_Possible() {
   

        // Pour chaque carte, créer une main potentielle en retirant cette carte
        for (int i = 0; i < 4; i++) {
            ArrayList<Carte> main = new ArrayList<>(Potentiel_Main_Adversaire.keySet());
            main.remove(i);
            mainPossible.add(main);
        }

        return mainPossible;
    }



    public double Probabilite_Gagner_Duel(){
        double probabilite=0;
        int somme_valeur_IA=0;
        int somme_valeur_Adversaire=0;
        
        for(int i=0;i<3;i++){
            Carte carte=clone.joueur2.main.get(i);
            somme_valeur_IA+=carte.getValeur();
        }

        for(int i=0;i<mainPossible.size();i++){
            List<Carte> main=mainPossible.get(i);
            for(int j=0;j<main.size();j++){
                Carte carte=main.get(j);
                somme_valeur_Adversaire+=carte.getValeur();
            }
            if(somme_valeur_IA>somme_valeur_Adversaire){
                probabilite++;
            }
        }
        probabilite=100*probabilite/mainPossible.size();//mainPossible.size()=4
        //Arrondir à la proba(supérieur ou égale) la plus proche
        probabilite=Math.ceil(probabilite);
        return probabilite;
    }




    public Plateau get_Plateau_avant_Coup_Adversaire(){
        return plateau_Avant_Coup_Adversaire;
    }

    public void set_Plateau_avant_Coup_Adversaire(Plateau plateau){
        this.plateau_Avant_Coup_Adversaire=plateau;
    }


    public void Coup_jouer_Par_Adversaire(Plateau plateauActuel,Plateau plateau_Avant){
     
         //On compare plateauClone avec l'état actuel (plateauActuel)

         for (int i = 0; i < plateauActuel.continuum.size(); i++) {
            Carte carteActuelle = plateauActuel.continuum.get(i);
            Carte carteAvant = plateau_Avant.continuum.get(i);
            
            if (!carteActuelle.equals(carteAvant)) {
                //On remplace carteActuelle par carteAvant dans Potentiel_Main_Adversaire
                Potentiel_Main_Adversaire.remove(carteActuelle);
            
                Potentiel_Main_Adversaire.put(carteAvant, true);

                if(nbTours==3){
                    //La seul entréé avec comme boolean false est supprime de Potentiel_Main_Adversaire et c'est cette carte qui sera defini comme le codex
                    for (Map.Entry<Carte, Boolean> entry : Potentiel_Main_Adversaire.entrySet()) {
                        Carte carte = entry.getKey();
                        Boolean boolean1 = entry.getValue();
                        if(boolean1==false){
                            Potentiel_Main_Adversaire.remove(carte);
                            //Transformer Potentiel_Main_Adversaire en ArrayList
                          
                            clone.codex.setCarte(carte);
                        }

                    }
                }

                break;
            }
        }
        
    }



    //Ecrire une fonction qui renvoie la liste des coups possibles d'un joueur
    public List<Coup> coupsPossibles(Jeu Jeu) {
        List<Coup> coups = new ArrayList<>();
        //On parcourt les cartes de la main du joueur
        for (int i = 0; i < 3; i++) {
            Carte carte = Jeu.plateau.joueurActif().main.get(i);
            //On parcourt les positions possibles pour chaque carte
            List<Integer> positions = Jeu.plateau.cartesAccessibles(carte);
            for (int position : positions) {
                coups.add(new Coup(i, position));
            }
        }
        return coups;
    }

    /* 
    public  int[] minimax(Jeu JeuClone, int profondeur) {
        //Noeud noeud = new Noeud(JeuClone, 0);
       
    
        if (profondeur == 0 || JeuClone.partieTerminee()) {
            //noeud.valeur = evaluer(JeuClone);
            //return noeud;



            
            return  evaluer(JeuClone);

            
        } 
        else if ( JeuClone.plateau.joueurActif().getNom()==JeuClone.plateau.joueur1.getNom()) {
            // Tour de l'IA=Max(minimax(etatcourantdujeu,profondeur-1,tourIA).Donc l'IA cherche a maximiser son score
            List <Coup> coups = coupsPossibles(JeuClone);
            int meilleurScore = Integer.MIN_VALUE;
            for (Coup coup : coups) {
                int i = coup.carte;
                int j = coup.position;
                   
                JeuClone.jouerCarte(i, j);
                if(JeuClone.estParadoxe()){
                    JeuClone.plateau.joueurActif().ajouterCristaux(1);
                    boolean condition = true;
                    
                    if(!JeuClone.estPossibleEchangerParadoxe(condition))
                        condition = false;
                    
                    JeuClone.echangerParadoxe(condition);
                    if(condition)
                        condition = false;
                    else
                        condition = true;

                    int score = minimax(JeuClone, profondeur)[0];
                    //noeud.fils.add(score);
                    
                    if (score > meilleurScore) {
                        meilleurScore = score;
                       
                        meilleurCoup[0] = i;
                        meilleurCoup[1] = j;
                    }
                }
                    
                if(JeuClone.estDuel())
                    JeuClone.duel();

                nbTours--;
                JeuClone.plateau.changerJoueurActif();
                //le joueur actif sera l'adversaire
                int score = minimax(JeuClone, profondeur - 1)[0];
                //noeud.fils.add(score);
                
                if (score > meilleurScore) {
                    meilleurScore = score;
                    
                    meilleurCoup[0] = i;
                    meilleurCoup[1] = j;
                }
            }
            return meilleurCoup;
        } 
        else {
            // Tour de l'adversaire=Min(minimax(etatcourantdujeu,profondeur-1,tourIA).Donc l'adversaire cherche a minimiser le score d'évaluation
           int meilleurScore = Integer.MAX_VALUE;
           List <Coup> coups = coupsPossibles(JeuClone);
      
            for (Coup coup : coups) {
                int i = coup.carte;
                int j = coup.position;

                JeuClone.jouerCarte(i, j);
                if(JeuClone.estParadoxe()){
                    JeuClone.plateau.joueur2.ajouterCristaux(1);
                    boolean condition = true;
                    
                    if(!JeuClone.estPossibleEchangerParadoxe(condition))
                        condition = false;
                    
                    JeuClone.echangerParadoxe(condition);
                    if(condition)
                        condition = false;
                    else
                        condition = true;

                        int score = minimax(JeuClone, profondeur)[0];
                        //noeud.fils.add(score);
                        
                        if (score < meilleurScore) {

                            meilleurScore = score;
                           
                            
                        }

                }
                if(JeuClone.estDuel())
                    JeuClone.duel();


                nbTours=nbTours+2;
                //Coup_jouer_Par_Adversaire(JeuClone.plateau,etatAvant.plateau);
                JeuClone.plateau.changerJoueurActif();
                
                int score = minimax(JeuClone, profondeur - 1)[0];
                //noeud.fils.add(score);
                
                if (score < meilleurScore) {
                    meilleurScore = score;
                  
                
                }
                      
            }
            return meilleurCoup;
            
        }
    
       
    }*/

    /*
    public List<Coup> minimax(Jeu JeuClone, int profondeur) {
        List<Coup> meilleurChemin = new ArrayList<>();
        if (profondeur == 0 || JeuClone.partieTerminee()) {
            meilleurChemin.add(null);
            return meilleurChemin;
        } else if (JeuClone.plateau.joueurActif().getNom() == JeuClone.plateau.joueur1.getNom()) {
            List<Coup> coups = coupsPossibles(JeuClone);
            int meilleurScore = Integer.MIN_VALUE;
            for (Coup coup : coups) {
                int i = coup.carte;
                int j = coup.position;
                JeuClone.jouerCarte(i, j);
                if (JeuClone.estParadoxe()) {
                    JeuClone.plateau.joueurActif().ajouterCristaux(1);
                    boolean condition = true;
                    if (!JeuClone.estPossibleEchangerParadoxe(condition))
                        condition = false;
                    JeuClone.echangerParadoxe(condition);
                    if (condition)
                        condition = false;
                    else
                        condition = true;
                    int score = minimax(JeuClone, profondeur).get(0).score;
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurChemin.clear();
                        coup.score = meilleurScore; // Mettre à jour le score du coup
                        meilleurChemin.add(coup);
                        meilleurChemin.addAll(minimax(JeuClone, profondeur).subList(1, profondeur + 1));
                    }
                }
                if (JeuClone.estDuel())
                    JeuClone.duel();

                nbTours--;
                JeuClone.plateau.changerJoueurActif();
                int score = minimax(JeuClone, profondeur - 1).get(0).score;
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurChemin.clear();
                    coup.score = meilleurScore; // Mettre à jour le score du coup
                    meilleurChemin.add(coup);
                    meilleurChemin.addAll(minimax(JeuClone, profondeur - 1).subList(1, profondeur));
                }
            }
            meilleurChemin.get(0).score = meilleurScore;
            return meilleurChemin;
        } else {
            int meilleurScore = Integer.MAX_VALUE;
            List<Coup> coups = coupsPossibles(JeuClone);
            for (Coup coup : coups) {
                int i = coup.carte;
                int j = coup.position;
                JeuClone.jouerCarte(i, j);
                if (JeuClone.estParadoxe()) {
                    JeuClone.plateau.joueur2.ajouterCristaux(1);
                    boolean condition = true;
                    if (!JeuClone.estPossibleEchangerParadoxe(condition))
                        condition = false;
                    JeuClone.echangerParadoxe(condition);
                    if (condition)
                        condition = false;
                    else
                        condition = true;
                    int score = minimax(JeuClone, profondeur).get(0).score;
                    if (score < meilleurScore) {
                        meilleurScore = score;
                        meilleurChemin.clear();
                        coup.score = meilleurScore; // Mettre à jour le score du coup
                        meilleurChemin.add(coup);
                        meilleurChemin.addAll(minimax(JeuClone, profondeur).subList(1, profondeur + 1));
                    }
                }
                if (JeuClone.estDuel())
                    JeuClone.duel();
                nbTours--;
                JeuClone.plateau.changerJoueurActif();
                int score = minimax(JeuClone, profondeur - 1).get(0).score;
                if (score < meilleurScore) {
                    meilleurScore = score;
                    meilleurChemin.clear();
                    coup.score = meilleurScore; // Mettre à jour le score du coup
                    meilleurChemin.add(coup);
                    meilleurChemin.addAll(minimax(JeuClone, profondeur - 1).subList(1, profondeur));
                }
            }
            meilleurChemin.get(0).score = meilleurScore;
            return meilleurChemin;
        }
    }*/

    public int minimax(Jeu JeuClone, int profondeur,Joueur joeurActif, List<Coup> historique, Noeud parent) {
        if (profondeur == 0 || JeuClone.partieTerminee()) {
            int res= evaluer(JeuClone);
            parent.score=res;
            return res;
        } 
        else if (JeuClone.plateau.joueurActif().getNom() == JeuClone.plateau.joueur1.getNom()) {
            List<Coup> coups = coupsPossibles(JeuClone);
            int meilleurScore = Integer.MIN_VALUE;
           

           // Coup meilleurCoup = null;
            for (Coup coup : coups) {
                int i = coup.carte;
                int j = coup.position;
                Noeud noeud = new Noeud(JeuClone);
                parent.fils.add(noeud);

    
                JeuClone.jouerCarte(i, j);
                /* 
                if (JeuClone.estParadoxe()) {
                    JeuClone.plateau.joueurActif().ajouterCristaux(1);
                    boolean condition = true;
    
                    if (!JeuClone.estPossibleEchangerParadoxe(condition))
                        condition = false;
    
                    JeuClone.echangerParadoxe(condition);
                    if (condition)
                        condition = false;
                    else
                        condition = true;
    
                    int score = minimax(JeuClone, profondeur, historique, noeud);
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = coup;
                        noeud.score = meilleurScore;

                    }
    
                } else {*/
                    if (JeuClone.estDuel())
                        JeuClone.duel();
    
                    nbTours--;
                    
                    int score = minimax(JeuClone, profondeur - 1,JeuClone.plateau.changerJoueurActif(JeuClone.plateau.joueur1), historique, noeud);
                    meilleurScore = Math.max(meilleurScore, score);
                //}
    
               
            }
    
           /*  if (meilleurCoup != null) {
                historique.add(meilleurCoup);
            }*/
           
            //Renvoyer dans max le noeud ârmi les fils de parent qui a le score le plus grand=utilise Collections.max surles scores des fils de parent
            parent.score=meilleurScore;




            return meilleurScore;
        } 
        else {
            List<Coup> coups = coupsPossibles(JeuClone);
            int meilleurScore = Integer.MAX_VALUE;
           

            
           // Coup meilleurCoup = null;
            for (Coup coup : coups) {
                int i = coup.carte;
                int j = coup.position;

                Noeud noeud = new Noeud(JeuClone);
                parent.fils.add(noeud);

    
                JeuClone.jouerCarte(i, j);
                /*if (JeuClone.estParadoxe()) {
                    JeuClone.plateau.joueur2.ajouterCristaux(1);
                    boolean condition = true;
    
                    if (!JeuClone.estPossibleEchangerParadoxe(condition))
                        condition = false;
    
                    JeuClone.echangerParadoxe(condition);
                    if (condition)
                        condition = false;
                    else
                        condition = true;
    
                    int score = minimax(JeuClone, profondeur, historique, noeud);
                    if (score < meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = coup;
                        noeud.score = meilleurScore;

                    }
    
                } else {*/
                    if (JeuClone.estDuel()){
                        JeuClone.duel();
                    }
    
                    nbTours=nbTours+2;
                    JeuClone.plateau.changerJoueurActif();
                    int score = minimax(JeuClone, profondeur - 1,JeuClone.plateau.changerJoueurActif(JeuClone.plateau.joueur1), historique, noeud);
                    meilleurScore = Math.min(meilleurScore, score);

                   /*  if (score < meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = coup;
                      

                    }*/
              //  }
                
            }
    
            /*if (meilleurCoup != null) {
                historique.add(meilleurCoup);
            }*/
            parent.score=meilleurScore;
            return meilleurScore;
        }
    }

   





    public int evaluer(Jeu etat) {
        int score = 0;
        
    
        int cristauxIA = etat.plateau.joueur2.getNombreCristaux();
        int cristauxAdversaire = etat.plateau.joueur1.getNombreCristaux();
        
        //Par exemple si IA a 4 cristaux de moins que son adversaire alors le score de l'IA sera de -80
        score += 20 * (cristauxIA - cristauxAdversaire);
   
            if (etat.estParadoxe())
                score+=100;
            if (etat.estDuel()) {
                
                Joueur gagnant = etat.meilleurMain();
                if (gagnant == etat.plateau.joueur1)
                    if(etat.plateau.joueur1.nombreCristaux!=0)
                      score+=50;
                else if(gagnant == etat.plateau.joueur2)
                    score-=50;
                else{
                   Joueur gagne =etat.duelEgalite();
                   if(gagne==etat.plateau.joueur1)
                        score+=50;
                   else if(gagne==etat.plateau.joueur2)
                        score-=50;
               }
            }
            
           // }
            //Si le joueur a 2 cartes de la meme valeur,couleur ou forme alors on ajoute 50 au score
            //if(etat.plateau.joueurActif().DeuxCarteMemeProp())
              //  score += 50;

            //Plus un sorcier a un grand nombre de possibilité de mouvement mieux c'est pour lui
               
           int nbIA=etat.plateau.nbMouvementPossibleSorcier(etat.plateau.joueur1);
            int nbAdversaire=etat.plateau.nbMouvementPossibleSorcier(etat.plateau.joueur2);
            score+=nbIA-nbAdversaire;

        return score;
    }

    
    public void jouer(Jeu Jeu) {
        Jeu JeuClone = Jeu.copie();
        List<Coup> historique = new ArrayList<>();
        Noeud n=new Noeud(JeuClone,0);
        int meilleurscore=minimax(JeuClone, 5,JeuClone.plateau.joueurActif(), historique,n);
        System.out.println("meilleur score : " + meilleurscore);
        Coup meilleurCoup = historique.get(0);
        int i = meilleurCoup.carte;
        int j = meilleurCoup.position;
        Jeu.jouerCarte(i, j);
       



       // int meilleurCarte=res[0];
        //int meilleurePosition=res[1];
       
        //Jeu.jouerCarte(meilleurCarte, meilleurePosition);
    }
    

}