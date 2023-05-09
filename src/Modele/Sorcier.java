package Modele;

import java.util.ArrayList;
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
    



