package Modele;

import java.util.ArrayList;
import java.util.List;



public class Sorcier {
    boolean sensDuTemps; // true si le futur est à droite et le passé à gauche, false sinon
    int positionSorcier;

    public Sorcier() {
        this.sensDuTemps = true;
        this.positionSorcier=-1;
    }

   public Sorcier(boolean sensDuTemps) {
       this.sensDuTemps = sensDuTemps;
       this.positionSorcier=-1;
   }
    
  
   public int getPositionSorcier() {
       return positionSorcier;
   }

    public boolean getSensDuTemps() {
        return this.sensDuTemps;
    }

    public void setSensDuTemps(boolean sensDuTemps) {
        this.sensDuTemps = sensDuTemps;
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
    
    

    
       
}
    



