package Modele;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class IADifficile {
    //IA c'es le joeuur 2 du plateau
   
    Partie plateau_Avant_Coup_Adversaire;
    Map<Carte, Boolean> Potentiel_Main_Adversaire ;
    MainDeCartes Vrai_Main_Adversaire;
    List<List<Carte>> mainPossible;

    Partie clone;


    public IADifficile(String nom, int nombreCristaux,Partie partie) {
     
        this.clone=partie;
        this.Vrai_Main_Adversaire=new MainDeCartes();
        this.mainPossible=new ArrayList<>();
       
        this.Potentiel_Main_Adversaire = Cartes_inconnu_par_IA();
        this.mainPossible=All_Main_Possible();
  
    }
   
    public Map<Carte, Boolean> Cartes_inconnu_par_IA() {
        Map<Carte, Boolean> Carte = new HashMap<>();
        List<Carte> Reliques = clone.All_Cartes;
        List<Carte> continuum = clone.continuum;
        // Initialiser chaque carte à false (inconnue)
        for (Carte carte : Reliques) {
            Carte.put(carte, false);
        }
        // Supprimer toutes les cartes que l'IA a en main dans Reliques
        for (int i = 0; i < 3; i++) {
            Carte carte = clone.joueur2.main.getCarte(i);
            Carte.remove(carte);
        }
        // Supprimer toutes les cartes du continuum présent dans Reliques
        for (int j = 0; j < continuum.size(); j++) {
            Carte carte_continuum = continuum.get(j);
            Carte.remove(carte_continuum);
        }
        return Carte;
    }


    public List<List<Carte>> All_Main_Possible() {
   

        // Pour chaque carte, créer une main potentielle en retirant cette carte
        for (int i = 0; i < 4; i++) {
            List<Carte> main = new ArrayList<>(Potentiel_Main_Adversaire.keySet());
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
            Carte carte=clone.joueur2.main.getCarte(i);
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




    public Partie get_Plateau_avant_Coup_Adversaire(){
        return plateau_Avant_Coup_Adversaire;
    }

    public void set_Plateau_avant_Coup_Adversaire(Partie plateau){
        this.plateau_Avant_Coup_Adversaire=plateau;
    }


    public Carte Coup_jouer_Par_Adversaire(Partie plateauActuel){
       // List<Carte> Vrai_Main_Adversaire=Main_Potentiel_Adversaire();
        //On sauvegarde le plateau avant que l'adversaire joue 
        Partie plateau_Avant=get_Plateau_avant_Coup_Adversaire();    

         //On compare plateauClone avec l'état actuel (plateauActuel)

         for (int i = 0; i < plateauActuel.continuum.size(); i++) {
            Carte carteActuelle = plateauActuel.continuum.get(i);
            Carte carteAvant = plateau_Avant.continuum.get(i);
            
            if (!carteActuelle.equals(carteAvant)) {
                //On remplace carteActuelle par carteAvant dans Potentiel_Main_Adversaire
                Potentiel_Main_Adversaire.remove(carteActuelle);
            
                Potentiel_Main_Adversaire.put(carteAvant, true);

                if(clone.nbTours>=3){
                    //La seul entréé avec comme boolean false est supprime de Potentiel_Main_Adversaire et c'est cette carte qui sera defini comme le codex
                    for (Map.Entry<Carte, Boolean> entry : Potentiel_Main_Adversaire.entrySet()) {
                        Carte carte = entry.getKey();
                        Boolean boolean1 = entry.getValue();
                        if(boolean1==false){
                            Potentiel_Main_Adversaire.remove(carte);
                            clone.codex.setCarte(carte);

                            //Transformer Potentiel_Main_Adversaire en MainDeCartes
                           
                            for (Map.Entry<Carte, Boolean> entry1 : Potentiel_Main_Adversaire.entrySet()) {
                              
                                Vrai_Main_Adversaire.ajouterCarte(entry1.getKey());
                            }
                                


                        }
                    }
                }

                return carteAvant; 
                //Maintenant l'IA sait que l'adversaire a entre ses main cette carte
            }
        }
        // Aucune carte n'a été remplacée mais ce cas se se produira jamais
        return null; 
    }

    public  int[]  Meilleur_Coup(int profondeur, boolean joueurMax ){
        int score=0;
        int meilleurScore=-1;
        int meilleurePosition = -1;
        int meilleurCarte=-1;
        Partie plateauClone = clone.copie();
       

       //int carte_rapportant_plus_points=-1;
       //int max_points=-1;
       int total_points=0;
       int meilleur_total_points=-1;
        for(int index_carte = 0; index_carte < 3; index_carte++){
            

             //Si le mouvement(a savoir jouer) permet à l'IA d'obtenir 3 cartes de la même couleur, valeur ou icône (sans inclure la couleur interdite), attribuez un score élevé (par exemple, 100 points).
            List<Integer> Mouvement_Possible_carte=plateauClone.joueur2.sorcier.calculerEmplacementsAccessibles(plateauClone.joueur2.main.getCarte(index_carte), plateauClone.continuum);
            //Parcourir les mouvement possible et pour chaque mouvement possible ca veut dire que la carte i peut aller a la possition i du MouvementPossible(jouerCarte).Verifier si ce coup(le fait de jouer)permet d'obtenir  3 cartes de la même couleur, valeur ou icône (sans inclure la couleur interdite), attribuez un score élevé
       
           
            for(int position : Mouvement_Possible_carte){
                Carte carte_continuum=plateauClone.continuum.get(position);
                plateauClone.joueur2.sorcier.setPositionSorcier(position);
                
                Carte carte_jouee=plateauClone.joueur2.jouerCarte(index_carte,plateauClone.continuum); 
                //Meilleur total de points entre les mains de l'IA
               
                 total_points=plateauClone.joueur2.totalValeurMain();
                if(total_points>meilleur_total_points){
                    meilleur_total_points=total_points;
                   // carte_rapportant_plus_points=index_carte;
                }
                
               
                if(carte_continuum.getCouleur()!=plateauClone.codex.getCouleurInterdite()){
        
                    if(carte_jouee.getCouleur()==plateauClone.codex.getCouleurInterdite()){
                        score+=20;
                    }

                        //Si le fait de jouer cette carte va permettre a l'IA de créer un paradoxe,on lui attribue le maximum de points
                    if(clone.isParadoxe(plateauClone.joueur2.main))
                       score+=100;
                    else{
                        if(carte_jouee.getValeur()<carte_continuum.getValeur()){
                          
                            if(clone.joueur2.getAllCouleurMain().contains(carte_continuum.getCouleur()) )
                                score+=60;//on peut se rapproche du paradoxe
                            
                            if(clone.joueur2.getAllValeurMain().contains(carte_continuum.getValeur()))
                                score+=60;
                            if(clone.joueur2.getAllFormeMain().contains(carte_continuum.getForme()))
                                score+=60;
                            
                            //On récupére une carte plus forte entre les mains mais ce dernier ne nous permet pas d'avoir au moins 2 cartes de la même couleur, valeur ou forme (sans inclure la couleur interdite)
                            if(!clone.joueur2.getAllValeurMain().contains(carte_continuum.getValeur())){
                                score+=40;
                            }
                        }
                       
                    
                    }   
              
                }
                else
                    score-=20;


                
                //Attribuer des points en fonction de la possibilité d'engager un duel :
                //plus la proba de gagner le duel est élevée, plus le score est élevé
                if(clone.isDuel()){
                    double probabilite=Probabilite_Gagner_Duel();
                    if(probabilite>50)
                        score+=probabilite;
                    
                    else
                    //Si le mouvement expose l'IA à un risque élevé de perdre un duel
                        score-=50;
                }

                
                //Coup qui va  permettre à l'adversaire de créer un Paradoxe
                if(clone.isParadoxe(Vrai_Main_Adversaire))
                    score-=50;




                    if (joueurMax) {
                        // Appel récursif pour trouver le meilleur coup pour l'adversaire
                        int[] resultat = Meilleur_Coup(profondeur - 1, false);
                        score += resultat[0];
                    } else {
                        // Appel récursif pour trouver le meilleur coup pour l'IA
                        int[] resultat = Meilleur_Coup(profondeur - 1, true);
                        score -= resultat[0];
                    }

                    
                if (score > meilleurScore) {
                    meilleurScore = score;
                    meilleurePosition = position;
                    meilleurCarte=index_carte;
                    score=0;//Remise a zero du score pour la prochaine carte
                }
            }

        }
        
        
        return new int[]{meilleurCarte, meilleurePosition};
    }
    
    



    public void jouer(Partie plateau) {
        int meilleurCarte=Meilleur_Coup()[0];
        int meilleurePosition=Meilleur_Coup()[1];
        plateau.setPositionSorcier(meilleurePosition, 2);
        clone.joueur2.jouerCarte(meilleurCarte, plateau.continuum);
    }



}