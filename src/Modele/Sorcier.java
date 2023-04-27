package Modele;



public class Sorcier {
    boolean sensDuTemps; // true si le futur est à droite et le passé à gauche, false sinon

   public Sorcier(boolean sensDuTemps) {
       this.sensDuTemps = sensDuTemps;
   }

   public boolean getSensDuTemps() {
       return sensDuTemps;
   }
}