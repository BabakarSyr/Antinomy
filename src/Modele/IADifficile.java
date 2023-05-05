package Modele;

import java.util.ArrayList;
import java.util.List;

public class IADifficile extends Joueur{
     Joueur joueurIA;
     MainDeCartes mainIA= joueurIA.getMain();
     Plateau clone;

     Plateau etatPlateauAvant = sauvegarderEtatPlateau();



     Joueur adversaire;
     Partie partie;
     int num_JoueurIA;
   /* 
    public IADifficile() {
        super("Merlin",0);
        this.adversaire = adversaire;
        this.partie = partie;
    }*/

    public Plateau sauvegarderEtatPlateau() {
        Plateau plateauClone = new Plateau(clone.joueur1, clone.joueur2, clone.codex);
        plateauClone.continuum = new ArrayList<>(clone.continuum);
        return plateauClone;
    }

    public List<Carte>  Main_Potentiel_Adversaire(){
        List<Carte> Reliques=clone.initialiser();
        List<Carte> continuum=clone.continuum;
        //Supprimer toutes les cartes que l'IA a en main dans Reliques
        for(int i=0;i<3;i++){
            Carte carte=mainIA.getCarte(i);
            Reliques.remove(carte);
        }
         //Supprimer toutes les cartes du continuum présent dans Reliques
         for(int j=0;j<continuum.size();j++){
            Carte carte_continuum=continuum.get(j);
            Reliques.remove(carte_continuum);
        }
        return Reliques;
    }

    public Carte Coup_jouer_Par_Adversaire(Plateau plateauActuel){
       // List<Carte> Main=Main_Potentiel_Adversaire();
        //On sauvegarde le plateau avant que l'adversaire joue 
        Plateau plateauClone=sauvegarderEtatPlateau();

         //On compare plateauClone avec l'état actuel (plateauActuel)
         for (int i = 0; i < plateauActuel.continuum.size(); i++) {
            Carte carteActuelle = plateauActuel.continuum.get(i);
            Carte carteAvant = plateauClone.continuum.get(i);
            
            if (!carteActuelle.equals(carteAvant)) {
                return carteAvant; // La carte qui a été remplacée
                //Maintenant oon sait que l'adversaire a entre ses main cette carte


            }
        }
        // Aucune carte n'a été remplacée mais ce cas se se produira jamais
        return null; 
    }

    public int getScore(List<Integer> mouvement_possible){
        int score=0;
        
        int positionSorcier = sorcier.getPositionSorcier();
        for(int i=0;i<3;i++){   
            Carte carte=mainIA.getCarte(i);
            //Si le mouvement(a savoir jouer) permet à l'IA d'obtenir 3 cartes de la même couleur, valeur ou icône (sans inclure la couleur interdite), attribuez un score élevé (par exemple, 100 points).
            List<Integer> Mouvement_Possible_carte=sorcier.calculerEmplacementsAccessibles(carte, clone.continuum);
            //Parcourir les mouvement possible et pour chaque mouvement possible ca veut dire que la carte i peut aller a la possition i du MouvementPossible(jouerCarte).Verifier si ce coup(le fait de jouer)permet d'obtenir  3 cartes de la même couleur, valeur ou icône (sans inclure la couleur interdite), attribuez un score élevé
            for(int j=0;j<Mouvement_Possible_carte.size();j++){
                int position=Mouvement_Possible_carte.get(j);
                clone.setPositionSorcier(position, num_JoueurIA);
                //JouerCarte
                jouerCarte(i,clone.continuum);  

                //Si le fait de jouer cette carte va permettre a l'IA de créer un paradoxe,on lui attribue le maximum de points
                if(partie.isParadoxe(mainIA)){
                    score+=100;
                }
               //Attribuer des points en fonction de la possibilité d'engager un duel :
             //Si le mouvement place l'IA en face de l'adversaire, vérifiez la somme des valeurs des cartes en main (en excluant la couleur interdite). Si la somme est supérieure à celle de l'adversaire, attribuez un score (par exemple, 50 point)



             
            }

            

        }
        return score;
    }


    



}