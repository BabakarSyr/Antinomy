package Modele;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Plateau extends Historique<Coup> implements Cloneable
{
    //TODO remplacer joueur actif par un indice et creer une methode qui retourne le joueur actif: j1 ou j2
    public int joueurActif;

    public Joueur joueur1;
    //public Humain joueur2;
    public Joueur joueur2;
    ArrayList<Integer> positionsDepart;

    ArrayList<Carte> continuum=new ArrayList<>();

   
    public Codex codex;
  
    
  
    public Plateau() {
        
        
        joueur1=new Humain();
        joueur2=new Humain();
      
        joueurActif = 1;
        codex=new Codex(new Carte(null, null,0));
    
        initialiser();
       
    }

    public Plateau(String nomJoueur1) {
        
        
        joueur1=new Humain(nomJoueur1);
        joueur2=new IAFacile();
      
        joueurActif = 1;
        codex=new Codex(new Carte(null, null,0));
    
        initialiser();
        joueur2.initIA(this);
    }

    public Plateau(String nomJoueur1, String nomJoueur2)
    {
        switch (nomJoueur1)
        {
            case "IA Facile":
                this.joueur1 = new IAFacile();
                break;
            case "IA Difficile":
                this.joueur1 = new IADifficileV1();
                break;
            default:
                this.joueur1 = new Humain(nomJoueur1);
                break;
        }
        switch (nomJoueur2)
        {
            case "IA Facile":
                this.joueur2 = new IAFacile();
                break;
            case "IA Difficile":
                this.joueur2 = new IADifficileV1();
                break;
            default:
                this.joueur2 = new Humain(nomJoueur2);
                break;
        }
      
        joueurActif = 1;
        codex=new Codex(new Carte(null, null,0));
    
        initialiser();
    }

    public Plateau(Plateau p)
    {
        this.joueurActif = p.joueurActif;
        if (p.joueur1.estIA())
        {
            switch (p.joueur1.getNom())
            {
                case "IA Facile":
                    this.joueur1 = new IAFacile(p.joueur1);
                    break;
                case "IA Difficile":
                    this.joueur1 = new IADifficileV1(p.joueur1);
                    break;
                default:
                    break;
            }
        }
        else
        {
            this.joueur1 = new Humain(p.joueur1);
        }
        if (p.joueur2.estIA())
        {
            switch (p.joueur2.getNom())
            {
                case "IA Facile":
                    this.joueur2 = new IAFacile(p.joueur2);
                    break;
                case "IA Difficile":
                    this.joueur2 = new IADifficileV1(p.joueur2);
                    break;
                default:
                    break;
            }
        }
        else
        {
            this.joueur2 = new Humain(p.joueur2);
        }
        this.positionsDepart = new ArrayList<>(p.positionsDepart);
        this.continuum = new ArrayList<>(p.continuum);
        this.codex = new Codex(p.codex);
    }
   

    public List<Carte> getAll_Cartes() {
        List<Carte> All_Cartes=new ArrayList<>();
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.VERT,1));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.VIOLET,2));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.BLEU,3));
        All_Cartes.add(new Carte(Forme.PLUME, Couleur.ROUGE,4));

        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.VIOLET,1));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.BLEU,2));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.ROUGE,3));
        All_Cartes.add(new Carte(Forme.ANNEAU, Couleur.VERT,4));

        All_Cartes.add(new Carte(Forme.CLE, Couleur.ROUGE,1));
        All_Cartes.add(new Carte(Forme.CLE, Couleur.VERT,2));
        All_Cartes.add(new Carte(Forme.CLE, Couleur.VIOLET,3));
        All_Cartes.add(new Carte(Forme.CLE, Couleur.BLEU,4));

        All_Cartes.add(new Carte(Forme.CRANE, Couleur.BLEU,1));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.ROUGE,2));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.VERT,3));
        All_Cartes.add(new Carte(Forme.CRANE, Couleur.VIOLET,4));
        return All_Cartes;
    }
    public void initialiser(){
        List <Carte> All_Cartes=getAll_Cartes();
       //Copier le contenu de All_Cartes dans continuum
        continuum.addAll(All_Cartes);

        
        
        // Mélanger les cartes Reliques
        Collections.shuffle(continuum);

        ArrayList<Carte> mainJoueur1=new ArrayList<>();
        ArrayList<Carte> mainJoueur2=new ArrayList<>();
        // Distribuer les cartes RElique aux joueurs
        for(int i = 0; i < 3; i++) {
            mainJoueur1.add(continuum.remove(0));
        
            mainJoueur2.add(continuum.remove(0));
            
        }
        joueur1.setMain(mainJoueur1);
        joueur2.setMain(mainJoueur2);
        
        codex.setCarte(continuum.remove(continuum.size()-1));
        /*Couleur interdite au debut du jeu
        On place un cristal sur la couleur du Codex correspondant à la couleur de la carte la plus à gauche du continuum. 
        Ce sera la couleur interdite.*/
        Couleur couleurInterdite = continuum.get(0).getCouleur();
          
        codex.setCouleur(couleurInterdite);

        positionsDepart = positionsDepartPossible();
    }

    public void setJoueurActif(int i) {
        this.joueurActif = i;
    }


    public Joueur getJoueur(int i) {
        if (i == 1) {
            return joueur1;
        } else {
            return joueur2;
        }
    }


    public ArrayList<Integer> positionsDepart(){
        return positionsDepart;
    }

    public Couleur couleurInterdite(){
        return codex.couleurInterdite;
    }

    public Joueur getJoueurParNom(String nom)
    {
        if (joueur1.getNom() == nom)
        {
            return joueur1;
        }
        else if (joueur2.getNom() == nom)
        {
            return joueur2;
        }
        return null;
    }

    public int deplacementFuturPossible(Carte carteChoisie) {
        int valeurCarte = carteChoisie.getValeur();
        int res=-1;
        int positionSorcier = joueurActif().getPositionSorcier();
        //Si le futur du sorcier est a droite
        if(joueurActif().getSensDuTemps())
        {
            if(positionSorcier+ valeurCarte < continuum.size())
            {
                res= positionSorcier+valeurCarte;
            }
        }
        
        // le futur est a gauche
        else
        {
            if(positionSorcier - valeurCarte >= 0)
            {
                res= positionSorcier-valeurCarte;
            }
        }
        return res;
        
    }

    public  ArrayList<Integer> deplacementPassePossible(Carte carteChoisie) {
        Forme formeCarte = carteChoisie.getForme();
        Couleur couleurCarte = carteChoisie.getCouleur();
        ArrayList<Integer> positions=new ArrayList<>();
        //Si le passé du sorcier est à gauche
        if(joueurActif().getSensDuTemps()){
            for (int i = joueurActif().getPositionSorcier() - 1; i >= 0; i--) {
                if( continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte){
                    positions.add (i);
                }        
            }
        } 
        //Le passé du sorcier est à droite
        else {
            for (int i = joueurActif().getPositionSorcier() + 1; i < continuum.size(); i++) {
                if (continuum.get(i).getForme() == formeCarte || continuum.get(i).getCouleur() == couleurCarte){
                    positions.add (i);
                }
            }
        }
        return positions;
    }

    //Equivalent echanger carte
    public void echangerCarte(int indiceCarteMain, int indiceContinuum) {//indiceCarteMain=[0-2]
        Joueur joueur = joueurActif();
        Carte carte = joueur.getMain().get(indiceCarteMain);
        joueur.getMain().remove(indiceCarteMain);
    
        // Échange de la carte avec la carte à la position du sorcier dans le continuum
        Carte carteContinuum = continuum.get(indiceContinuum);
        continuum.set(indiceContinuum, carte);
    
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

    public void paradoxe(boolean estFutur){
        echangerParadoxe(estFutur);
        joueurActif().ajouterCristal(1);
        codex.changerCouleurInterdite();
    }

    public boolean estParadoxe() {
        Couleur couleurInterdite = codex.getCouleurInterdite();
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

    public boolean estParadoxe(int indiceMain, int indiceContinuum) {

        ArrayList<Carte> cartes =  new ArrayList<>();
        for(int i =0; i<3; i++){
            if(i == indiceMain){
                cartes.add(continuum.get(indiceContinuum));
            }else{
                cartes.add(joueurActif().getMain().get(i));
            }
        }
        Couleur couleurInterdite = codex.getCouleurInterdite();
    
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

        //le futur est a droite du sorcier et le passé a gauche
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

    public ArrayList<Integer> cartesAccessibles(Carte carteChoisie) {
        ArrayList <Integer> positions = new ArrayList<Integer>();
        List <Integer> positionsPasse = deplacementPassePossible(carteChoisie);
        int positionFutur = deplacementFuturPossible(carteChoisie);
        if(positionFutur != -1)
            positions.add(positionFutur);
        if(!positionsPasse.isEmpty())
            positions.addAll(positionsPasse);
        return positions;
     
    }

    public void afficherCartesAcceccibles(ArrayList<Integer> listeIndiceCartes)
    {
        System.out.println("emplacements possibles :");
        for (int i=0; i<listeIndiceCartes.size(); i++)
        {
            System.out.println(listeIndiceCartes.get(i));
        }
    }

    public void changerJoueurActif() {
        joueurActif = (joueurActif % 2);
        joueurActif++;
    }


    public Joueur joueurInactif()
    {
        if(this.joueurActif==1)
        {
            return this.joueur2;
        }
        return this.joueur1;
    }

    public Joueur joueurActif()
    {
        if(this.joueurActif==1)
        {
            return this.joueur1;
        }
        return this.joueur2;
    }

    public ArrayList<Carte> getContinuum() {
        return continuum;
    }
    public void afficherContinuum() {
        for (int i = 0; i < continuum.size(); i++) {
            System.out.println(i+":"+continuum.get(i));
        }
    }

    public void afficher_colorSorcier_continuum(int posSorcier1,int posSorcier2) {
       //Il y a duel,on surligne en rouge la position
        if(posSorcier1==posSorcier2){
            System.out.println("\033[31m\033[7m"+posSorcier1+ "\033[0m:  " + continuum.get(posSorcier1) + "\n");
        }
        
        for (int i = 0; i < continuum.size(); i++) {
            if (i == posSorcier1) {
                //Colorer en rose
                System.out.print(" \033[95m\033[7m"+i+ "\033[0m:  " + continuum.get(i) + "\n");

            } 
            else if (i == posSorcier2) {
                System.out.print(" \033[38;5;208m\033[7m"+i+ "\033[0m:  " + continuum.get(i) + "\n");
            }
            
            else {
                System.out.print(  i+":"+continuum.get(i)+"\n");
            }
        }
        System.out.println();
    }
    

    public int getPositionSorcier(int joueur) {
        if (joueur == 1) 
            return joueur1.getPositionSorcier();
        else 
            return joueur2.getPositionSorcier();
    }

    public void setTempsSorcier(int joueur) {
        if (joueur == 1) {
            joueur1.setSensDuTemps(true);
        } else {
            joueur2.setSensDuTemps(false);
        }
    }

    //Fonction qui me donne les cartes portant la couleur interdite sur le continuum
    public  ArrayList <Integer> positionsDepartPossible() {
        ArrayList <Integer> pos = new ArrayList<>();
        for (int j = 0; j < continuum.size(); j++) {
            if (continuum.get(j).getCouleur() == codex.getCouleurInterdite()) {
                pos.add(j);
            }
        }
        return pos;
    }

    public String colorerPositionSorcier(int pos_sorcier,int joueur) {
        String s = "";
        if (joueur == 1) {
            for (int i = 0; i <=continuum.size(); i++) {
                if (i == pos_sorcier ) {
                    //La baguette du joueur1 colore en rose
                    s = " \033[95m\033[7m" + pos_sorcier + "\033[0m!";
                } else {
                    s = "" + pos_sorcier;
                }
            }
        } else {
            for (int i = 0; i <=continuum.size(); i++) {
                if (i == pos_sorcier ) {
                    //La baguette du joueur2 colore en orange
                    s = " \033[38;5;208m\033[7m" + pos_sorcier + "\033[0m!";
                } else {
                    s = "" + pos_sorcier;
                }
            }
        }
        return s;
    }

    public Coup creerCoup(Plateau plateau) {

		Coup coup = new Coup();
        System.out.println("ALLONS CREER NOTRE COUP !");
        coup.creerCoup(plateau); 
        return coup;
	}

    public Coup jouerCoup(Coup coup) {
        if(coup != null){
            faire(coup);
        }
        return coup;
	}

    public void majPlateau(Plateau plateau) {
        setJoueurActif(plateau.joueurActif);
        joueurActif().setCristal(plateau.joueurActif().getNombreCristaux());
        joueurInactif().setCristal(plateau.joueurInactif().getNombreCristaux());
        joueurActif().setPositionSorcier(plateau.joueurActif().getPositionSorcier());
        this.joueurActif().setMain(plateau.joueurActif().getMain());
        this.continuum = plateau.getContinuum();
	}

    @Override
	public void faire(Coup nouveau) {
        nouveau.fixerPlateau(this);
		super.faire(nouveau);
	}

    public int valeurMain(Joueur j)
    {
        int valeurMain = 0;
        Couleur couleurInterdite = this.codex.getCouleurInterdite();
        for (Carte carte : j.getMain()) 
        {
            valeurMain += carte.getValeur(couleurInterdite);
        }
        System.out.println("valeur de la main :" + valeurMain);
        return valeurMain;
    }
    public boolean estVidePasse(){
        return passe.estVide();
    }

    public boolean estVideFutur(){
        return futur.estVide();
    }

    @Override
    public Plateau clone()
    {
        return new Plateau(this);
    }
}