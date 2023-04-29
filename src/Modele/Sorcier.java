package Modele;

import java.util.List;

public class Sorcier {
    boolean sensDuTemps; // true si le futur est à droite et le passé à gauche, false sinon
    int positionSorcier;

   public Sorcier(boolean sensDuTemps) {
       this.sensDuTemps = sensDuTemps;
        
   }
    
  
   public int getPositionSorcier() {
       return positionSorcier;
   }
   public void setPositionSorcier(int positionSorcier) {
       this.positionSorcier = positionSorcier;
   }

    public void getSensDuTemps() {
        if(sensDuTemps)
            System.out.println("Ton futur est à droite et ton passé à gauche");
        else
            System.out.println("Ton futur est à gauche et ton passé à droite");
    }

    public void setSensDuTemps(boolean sensDuTemps) {
        this.sensDuTemps = sensDuTemps;
    }

    
   public boolean est_possible_aller_futur(Carte carteChoisie,List<Carte> continuum) {
    int valeurCarte = carteChoisie.getValeur();
    
    //Si le futur du sorcier est a droite
    if (sensDuTemps) 
        return (positionSorcier + valeurCarte < continuum.size());
    
    // le futur est a gauche
    else 
        return (positionSorcier - valeurCarte >= 0);
    
    }

    public boolean est_possible_aller_passe(Carte carteChoisie, List<Carte> continuum) {
       
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();

        //Si le passé du sorcier est à gauche
        if (sensDuTemps) {
            for (int i = positionSorcier - 1; i >= 0; i--) {
                if( continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) 
                    return true;       
            }
        } 
        //Le passé du sorcier est à droite
        else {
            for (int i = positionSorcier + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) 
                    return true;
                
            }
        }

        return false;
    }


    public void deplacerFutur(Carte carteChoisie, List<Carte> continuum) {
        
            int valeurCarte = carteChoisie.getValeur(); 
            if (sensDuTemps) {
                // Si le futur du sorcier est à droite
                positionSorcier += valeurCarte;
            } else {
                // Pour ce sorcier, le futur est à gauche
                positionSorcier -= valeurCarte;
            }
      
    }
    
    public void deplacerPasse(Carte carteChoisie, List<Carte> continuum) {
       
            Forme formeCarte = carteChoisie.getForme();
            Couleur couleurCarte = carteChoisie.getCouleur();
            
            if (sensDuTemps) {
                // Si le passé du sorcier est à gauche
                for (int i = positionSorcier - 1; i >= 0; i--) {
                    if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) {
                        positionSorcier = i;
                        break;
                    }
                }
            } else {
                // Pour ce sorcier, le passé est à droite
                for (int i = positionSorcier + 1; i < continuum.size(); i++) {
                    if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte) {
                        positionSorcier = i;
                        break;
                    }
                }
            }
       
    }
    




}