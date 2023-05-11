package Modele;

public class Sorcier {
    boolean sensDuTemps; // true si le futur est à droite et le passé à gauche, false sinon
    int positionSorcier;

   public Sorcier(boolean sensDuTemps) {
       this.sensDuTemps = sensDuTemps;
        
   }
   public Sorcier copie(){
         Sorcier sorcier = new Sorcier(this.sensDuTemps);
         sorcier.positionSorcier = this.positionSorcier;
         return sorcier;
   }
    

   public int getPositionSorcier() {
       return positionSorcier;
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

    
    

    
       
}
    



