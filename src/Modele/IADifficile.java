package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IADifficile {
    //IA c'es le joeuur 2 du plateau
   
    Plateau plateau_Avant_Coup_Adversaire;
    Map<Carte, Boolean> Potentiel_Main_Adversaire ;
    
    ArrayList<ArrayList<Carte>> mainPossible;
    int nbTours;

    Plateau clone;


    public IADifficile(String nom, int nombreCristaux,Plateau partie) {
     
        this.clone=partie;
        
        this.mainPossible=new ArrayList<>();
       
        this.Potentiel_Main_Adversaire = Cartes_inconnu_par_IA();
        this.mainPossible=All_Main_Possible();
  
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


    public int[] minimax(Jeu JeuClone, int profondeur) {
        int meilleurScore;
        int[] meilleurCoup = new int[3];
        
        if (profondeur == 0 || JeuClone.partieTerminee()) {
            meilleurCoup[0]=evaluer(JeuClone);
            return  meilleurCoup;
            
        } 
        else if ( JeuClone.plateau.joueurActif().getNom()==JeuClone.plateau.joueur1.getNom()) {
            // Tour de l'IA=Max(minimax(etatcourantdujeu,profondeur-1,tourIA).Donc l'IA cherche a maximiser son score
            meilleurScore = Integer.MIN_VALUE;
            for(int i=0;i<3;i++){
                Carte carte = JeuClone.plateau.joueur1.main.get(i);

                List<Integer> Mouvement_Possible=JeuClone.plateau.cartesAccessibles(carte);
                for (int j : Mouvement_Possible) {
                  
                   
                    JeuClone.jouerCarte(i, j);
                    //JeuClone.paradoxeIA_aleatoirechoix();
                    if(JeuClone.estDuel())
                       JeuClone.duel();

                    nbTours--;
                    JeuClone.plateau.changerJoueurActif();
                    //le joueur actif sera l'adversaire
                    int score = minimax(JeuClone, profondeur - 1)[0];
                    if (score > meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup[1] = i;
                        meilleurCoup[2] = j;
                    }
                }
            }
            
          
        } 
        else {
            // Tour de l'adversaire=Min(minimax(etatcourantdujeu,profondeur-1,tourIA).Donc l'adversaire cherche a minimiser le score d'évaluation
            meilleurScore = Integer.MAX_VALUE;
                for (int i = 0; i < 3; i++) {
                    
                    Carte carte = JeuClone.plateau.joueur2.main.get(i);

                    for (int j : JeuClone.plateau.cartesAccessibles(carte) ) {

                        JeuClone.jouerCarte(i, j);
                        JeuClone.paradoxeIA_aleatoirechoix();
                        if(JeuClone.estDuel())
                          JeuClone.duel();


                        nbTours=nbTours+2;
                       //Coup_jouer_Par_Adversaire(JeuClone.plateau,etatAvant.plateau);
                       JeuClone.plateau.changerJoueurActif();
                        
                        int score = minimax(JeuClone, profondeur - 1)[0];
                        if (score < meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup[1] = i;
                            meilleurCoup[2] = j;
                        }
                      
                    }
                   
                   
                }
            
        }
    
        return  meilleurCoup;
    }
    
    public int evaluer(Jeu etat) {
        int score = 0;
    
        int cristauxIA = etat.plateau.joueur2.getNombreCristaux();
        int cristauxAdversaire = etat.plateau.joueur1.getNombreCristaux();
        
        //Par exemple si IA a 4 cristaux de moins que son adversaire alors le score de l'IA sera de -80
        score += 20 * (cristauxIA - cristauxAdversaire);
   
            if (etat.estParadoxe())
                score += 100;
            if (etat.estDuel()) {
                
                Joueur gagnant = etat.meilleurMain();
                if (gagnant == etat.plateau.joueur1)
                      score+=100;
                else 
                    score-=100;
            
            }
            //Si l'IA a 2 cartes de la meme valeur,couleur ou forme alors on ajoute 50 au score
            if(etat.plateau.joueur1.DeuxCarteMemeProp())
                score += 50;

        return score;
    }

    public void jouer(Jeu Jeu) {
        Jeu JeuClone = Jeu.copie();
        int[] res=minimax(JeuClone, 3);
        int meilleurCarte=res[1];
        int meilleurePosition=res[2];
       
        Jeu.jouerCarte(meilleurCarte, meilleurePosition);
    }
    

}