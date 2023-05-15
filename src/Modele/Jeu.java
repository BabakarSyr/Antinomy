package Modele;

import java.util.ArrayList;
import java.util.Random;






public class Jeu
{
    Plateau plateau;
    
    
    public Jeu()
    {
		this.plateau = new Plateau();
    }

    public Jeu(Plateau p)
    {
        this.plateau = p;
    }

    ////////
    public Plateau plateau()
    {
        return this.plateau;
    }

    public Jeu copie()
    {
        return new Jeu(plateau.copie());
    }

    public void definirJoueur1(Joueur j)
    {
        if (j==plateau.joueur1)
        {
            plateau.joueur1.setSensDuTemps(true);
            plateau.joueur2.setSensDuTemps(false);
            plateau.joueurActif=1;
        }
        else
        {
            plateau.joueur1.setSensDuTemps(false);
            plateau.joueur2.setSensDuTemps(true);
            plateau.joueurActif=2;
        }
    }
    
    public void definirOrdresJoueurs(String dejaVuJoueur1, String dejaVuJoueur2)
    {
      if ((dejaVuJoueur1.equals("non") && dejaVuJoueur2.equals("non")) || (dejaVuJoueur1.equals("oui") && dejaVuJoueur2.equals("oui"))) 
      {
        Random rand = new Random();
        int num_joueur_actif = rand.nextInt(2)+1 ;  // [1-2]
        plateau.setJoueurActif(num_joueur_actif);  // [1-2]
        if (num_joueur_actif==1)
        {
          plateau.getJoueur(1).setSensDuTemps(true);
          plateau.getJoueur(2).setSensDuTemps(false);
        }
        else
        {
          plateau.getJoueur(1).setSensDuTemps(false);
          plateau.getJoueur(2).setSensDuTemps(true);
        }
      }
      else if (dejaVuJoueur1.equals("oui")) 
      {
        int num_joueur_actif = 1;
        plateau.setJoueurActif(num_joueur_actif);
        plateau.getJoueur(1).setSensDuTemps(true);
        plateau.getJoueur(2).setSensDuTemps(false);
      }
      else 
      {
        int num_joueur_actif = 2;
        plateau.setJoueurActif(num_joueur_actif);
        plateau.getJoueur(1).setSensDuTemps(false);
        plateau.getJoueur(2).setSensDuTemps(true);
      }
    }

    public ArrayList<Integer>positionsDepart(){
        return plateau.positionsDepart();
    }

    public void afficher_cartes_main()
    {
        for (int i =0 ;i < 3; i++) 
        {
            int j=i+1;
            System.out.print("Carte " + j  +"="+ plateau.joueurActif().getMain().get(i).toString() + " ");
            System.out.println();
        }
    }

    ////////

    public boolean estParadoxe() {
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        ArrayList<Carte> cartes = joueurActif().getMain();
    
        boolean memeCouleur = true;
        boolean memeForme = true;
        boolean memeValeur = true;

        Carte carte = cartes.get(0);
        if (carte.getCouleur() == couleurInterdite) {
            return false;
        }
        for (int i = 1; i < cartes.size(); i++) {
            if (cartes.get(i).getCouleur() == couleurInterdite) {
                return false;
            }
            memeCouleur = memeCouleur&(cartes.get(i).getCouleur() == carte.getCouleur());
            memeForme = memeForme&(cartes.get(i).getForme() == carte.getForme());
            memeValeur = memeValeur&(cartes.get(i).getValeur() == carte.getValeur());
            
        }
        return memeCouleur || memeForme || memeValeur;
    }
    
    




    public void paradoxe(int indiceCarteContinuum)
    {
        if(indiceCarteContinuum>joueurActif().getPositionSorcier()){
            echangerParadoxe(true);
        }
        else{
            echangerParadoxe(false);
        }
        joueurActif().ajouterCristaux();
        plateau.codex.changerCouleurInterdite();
    }

    public boolean partieTerminee() {
        return plateau.joueur1.getNombreCristaux() == 5 || plateau.joueur2.getNombreCristaux() == 5;
    }

    public String nomVainqueur()
    {
        if (partieTerminee() && plateau.joueur1.getNombreCristaux() == 3)
        {
            return plateau.joueur1.getNom();
        }
        else if (partieTerminee() && plateau.joueur2.getNombreCristaux() == 3)
        {
            return plateau.joueur2.getNom();
        }
        else
        {
            return null;
        }
    }

    //On a duel si positionSorcierJoueur1 == positionSorcierJoueur2
    public boolean estDuel(){
        return plateau.joueur1.getPositionSorcier() == plateau.joueur2.getPositionSorcier();
    }

    public void jouerCarte(int indiceCarteMain, int indiceContinuum){
        deplacerSorcier(indiceContinuum);
        echangerCarte(indiceCarteMain, indiceContinuum);
    }

    public boolean estDeplacementPossible(int indiceCarteMain, int indiceContinuum){
        return plateau.cartesAccessibles(joueurActif().getMain().get(indiceCarteMain)).contains(indiceContinuum);
    }

    public boolean estPossibleEchangerParadoxe(int indiceContinuum){
        int sorcier=joueurActif().getPositionSorcier();
            if(indiceContinuum>sorcier){
                return sorcier<6;
            }
            if(indiceContinuum<sorcier){
                return sorcier>2;
            }
        return false;
    }

    //true=droite false=gauche
    public boolean estPossibleEchangerParadoxe(boolean futur){
        int sorcier=joueurActif().getPositionSorcier();
        if(futur){
            return sorcier<6;
        }
        else{
            return sorcier>2;
        }   
    }

    public void deplacerSorcier(int positionSorcier) {
        joueurActif().setPositionSorcier(positionSorcier);
    }

    //Equivalent echanger carte
    public void echangerCarte(int indiceCarteMain, int indiceContinuum) {//indiceCarteMain=[0-2]
            Joueur joueur = joueurActif();
            Carte carte = joueur.getMain().get(indiceCarteMain);
            joueur.getMain().remove(indiceCarteMain);
        
            // Échange de la carte avec la carte à la position du sorcier dans le continuum
            Carte carteContinuum = continuum().get(indiceContinuum);
            continuum().set(indiceContinuum, carte);
        
            // Ajouter la carte du continuum à la main du joueur
            joueur.getMain().add(indiceCarteMain, carteContinuum);
    }

    // echange les 3 cartes en main avec 3 cartes du plateau suite à un paradoxe
    public void echangerParadoxe(boolean futur) {
        Joueur joueur = joueurActif();
        joueur.melangerMain();
        int indexSorcier = joueur.getPositionSorcier();
        int j = 0;
        if(!futur){
            for (int i = indexSorcier - 3; i < indexSorcier; i++) {
                echangerCarte(j, i);
                j++;
            }
        }
        else{
            for (int i = indexSorcier + 1; i < indexSorcier + 4; i++) {
                echangerCarte(j, i);
                j++;
            }
        }
    }

    public Joueur joueurActif(){
        return plateau.joueurActif();
    }

    public ArrayList<Carte> continuum(){
        return plateau.getContinuum();
    }

    public Couleur couleurInterdite(){
        return plateau.couleurInterdite();
    }

    public int valeurMain(Joueur j)
    {
        int valeurMain = 0;
        Couleur couleurInterdite = plateau.codex.getCouleurInterdite();
        for (Carte carte : j.getMain()) 
        {
            valeurMain += carte.getValeur(couleurInterdite);
        }
        System.out.println("valeur de la main :" + valeurMain);
        return valeurMain;
    }

    public Joueur meilleurMain()
    {
        int valeurMainJoueur1 = valeurMain(plateau.joueurActif());
        int valeurMainJoueur2 = valeurMain(plateau.joueurInactif());
        
        if (valeurMainJoueur1 > valeurMainJoueur2) 
        {
            return plateau.joueurActif();
        }
        else if (valeurMainJoueur1 < valeurMainJoueur2)
        {
            return plateau.joueurInactif();
        }
        return null;
    }

    public Joueur duel() 
    {
        Joueur j1 = plateau.joueurActif();
        Joueur j2 = plateau.joueurInactif();

        Joueur gagnantDuel = meilleurMain();
        if (gagnantDuel==j1) {
            // Le joueur 1 gagne le duel et vole un cristal au joueur 2
            if(j1.volerCristal(j2)){
                System.out.println(j1.getNom() + " gagne le duel et vole un cristal à " + j2.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j1.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return gagnantDuel;

        }
        if (gagnantDuel==j2) 
        {
            // Le joueur 2 gagne le duel et vole un cristal au joueur 1
            if(j2.volerCristal(j1))
            {
                System.out.println(j2.getNom() + " gagne le duel et vole un cristal à " + j1.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j2.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return gagnantDuel;
        }
        return null;
    }

    public Joueur duelEgalite()
    {
        Joueur j1 = plateau.joueurActif();
        Joueur j2 = plateau.joueurInactif();
        // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
                        
        j1.melangerMain();
        j2.melangerMain();

        Carte carteJoueur1 = j1.getMain().get(0);
        Carte carteJoueur2 = j2.getMain().get(0);

        // On compare  les valeurs des cartes en tenant compte de la couleur interdite
        int valeurCarteJoueur1 = carteJoueur1.getValeur(plateau.codex.getCouleurInterdite());
        int valeurCarteJoueur2 = carteJoueur2.getValeur(plateau.codex.getCouleurInterdite());
                
        if (valeurCarteJoueur1 > valeurCarteJoueur2) 
        {
            if(j1.volerCristal(j2))
            {
                System.out.println(j1.getNom() + " gagne le duel et vole un cristal à " + j2.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j1.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j1;
        }
        if (valeurCarteJoueur1 < valeurCarteJoueur2) 
        {
            if(j2.volerCristal(j1))
            {
                System.out.println(j2.getNom() + " gagne le duel et vole un cristal à " + j1.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j2.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j2;
        }
        System.out.println("Égalité!");
        return null;
    }


    public Joueur duelEgalite(int indiceCarteJ1, int indicieCarteJ2)
    {
        Joueur j1 = plateau.joueurActif();
        Joueur j2 = plateau.joueurInactif();
        // Égalité, procédez au tirage de cartes pour départager les joueurs, sinon annulez le duel
                        
        j1.melangerMain();
        j2.melangerMain();

        Carte carteJoueur1 = j1.getMain().get(indiceCarteJ1);
        Carte carteJoueur2 = j2.getMain().get(indicieCarteJ2);

        // On compare  les valeurs des cartes en tenant compte de la couleur interdite
        int valeurCarteJoueur1 = carteJoueur1.getValeur(plateau.codex.getCouleurInterdite());
        int valeurCarteJoueur2 = carteJoueur2.getValeur(plateau.codex.getCouleurInterdite());
                
        if (valeurCarteJoueur1 > valeurCarteJoueur2) 
        {
            if(j1.volerCristal(j2))
            {
                System.out.println(j1.getNom() + " gagne le duel et vole un cristal à " + j2.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j1.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j1;
        }
        if (valeurCarteJoueur1 < valeurCarteJoueur2) 
        {
            if(j2.volerCristal(j1))
            {
                System.out.println(j2.getNom() + " gagne le duel et vole un cristal à " + j1.getNom());
                plateau.codex.changerCouleurInterdite();
            }
            else
            {
                System.out.println("Impossible de voler un cristal à " + j2.getNom() + " car il n'en a plus  ou n'en a pas.");
            }
            return j2;
        }
        System.out.println("Égalité!");
        return null;
    }
    

    public void paradoxeIA_aleatoirechoix(){
        if(estParadoxe()){

            plateau.joueurActif().ajouterCristaux(1);

            plateau.joueurActif().melangerMain();

            Boolean direction;
            Random rand = new Random();

                 direction = rand.nextBoolean() ?  true: false;
                while(!estPossibleEchangerParadoxe(direction)){
                    direction = rand.nextBoolean() ?  true: false;

                }

                if(direction)
                    echangerParadoxe(direction);
                else    
                echangerParadoxe(direction);

                System.out.println("Voici le plateau apres votre coup :");

                plateau.codex.changerCouleurInterdite();

        }
      }



















      /*Ecrire la fonction paradoxe pour l'IA difficile
       Il est en effet important de réfléchir à la direction à prendre lors d'un déplacement dans le jeu Antinomy. Pour maximiser vos chances de former un Paradoxe ou de gagner un Duel, vous devriez envisager plusieurs facteurs, tels que la proximité des cartes qui pourraient vous aider à former un Paradoxe, la distance par rapport à votre adversaire, et la couleur interdite du Codex.

Si vous avez plusieurs options de déplacement qui vous permettent d'échanger des cartes avec des cartes qui pourraient vous aider à former un Paradoxe, vous devriez considérer quelle direction vous donnera la meilleure chance d'obtenir ces cartes. Si vous avez déjà des cartes qui correspondent à une couleur, une valeur ou une icône spécifique, vous pourriez être en mesure de prédire dans quelle direction vous avez le plus de chances de trouver une carte complémentaire.

En outre, vous devriez également tenir compte de la position de votre adversaire et essayer d'anticiper ses mouvements pour bloquer ses possibilités de formation de Paradoxe. Si vous êtes en mesure de vous rapprocher de votre adversaire, vous pourriez être en mesure de lancer un Duel et de voler un cristal.

Enfin, il est important de garder à l'esprit la couleur interdite du Codex, car cela pourrait limiter vos possibilités de déplacement et de formation de Paradoxe. Si vous avez déjà joué une carte dans la couleur interdite, vous devez trouver un moyen de contourner cette limitation pour maximiser vos chances de gagner.

D'abord, il est important de déterminer ce que vous souhaitez que la fonction Paradoxe fasse exactement. Voici ce que j'ai compris de vos explications :

La fonction Paradoxe doit déterminer la meilleure direction à prendre pour maximiser les chances de former un Paradoxe ou de gagner un Duel.
La fonction Paradoxe doit considérer plusieurs facteurs, tels que la proximité des cartes qui pourraient aider à former un Paradoxe, la distance par rapport à l'adversaire et la couleur interdite du Codex.
La fonction Paradoxe doit prédire dans quelle direction il y a le plus de chances de trouver une carte complémentaire si des cartes correspondent déjà à une couleur, une valeur ou une icône spécifique.
La fonction Paradoxe doit tenir compte de la position de l'adversaire et anticiper ses mouvements pour bloquer ses possibilités de formation de Paradoxe.
La fonction Paradoxe doit aider à contourner la limitation imposée par la couleur interdite du Codex.

       */













}

