package Vue;

import javax.swing.* ;
import java.awt.* ;

import Modele.*;


// L'interface runnable déclare une méthode run
public class InterfaceGraphique extends JFrame implements Runnable {
	Jeu jeu;
	CollecteurEvenements controleur;
	PlateauGraphique plateau;
	int height;
	int width;

	JButton boutonOptionsJeu, boutonHistoriqueArriere, boutonHistoriqueAvant;

	public InterfaceGraphique(Jeu j, CollecteurEvenements c){
		jeu = j;
		controleur = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements c) {
		InterfaceGraphique vue = new InterfaceGraphique(j, c);
		SwingUtilities.invokeLater(vue);
	}	

	public void paintComponent() {
		width = getSize().width;
		height = getSize().height;
	}

	public void run() {
		// Creation d'une fenetre
		JFrame frame = new JFrame("Ma fenetre a moi");
		frame.setSize(1280, 720);

		// Creation d'un bouton
		JButton bouton = new JButton("Manu");

		// Recuperation taille de la frame
		int y = frame.getHeight();
		int x = frame.getWidth();
		//System.out.println("x = "+x+" y = "+y);

		bouton.setBounds(x-(x/5)*2,0,x/5,y/8);
	
		// Ajout de notre composant de dessin dans la fenetre
		PlateauGraphique plateauGraphique = new PlateauGraphique(jeu, controleur);

		plateauGraphique.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.33;
        // Top/right
        // gbc.gridx = 0;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        boutonOptionsJeu = new JButton("Menu");
        //boutonOptionsJeu.addActionListener(new AdaptateurCommande(controleur, "OptionsJeu"));
        plateauGraphique.add(boutonOptionsJeu, gbc);
		gbc.gridy++;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 0, 0, 0);

        boutonHistoriqueArriere = new JButton("←");
        //boutonHistoriqueArriere.addActionListener(new AdaptateurCommande(controleur, "Annuler"));
        boutonHistoriqueArriere.setEnabled(false);
        boutonHistoriqueAvant = new JButton("→");
        //boutonHistoriqueAvant.addActionListener(new AdaptateurCommande(controleur, "Refaire"));
        boutonHistoriqueAvant.setEnabled(false);
        Container historiqueAvantArriere = Box.createHorizontalBox();
        historiqueAvantArriere.add(boutonHistoriqueArriere);
        historiqueAvantArriere.add(boutonHistoriqueAvant);

        Container historiqueBox = Box.createVerticalBox();
        historiqueBox.add(historiqueAvantArriere);
        plateauGraphique.add(historiqueBox, gbc);
		
		frame.add(bouton);
		frame.add(plateauGraphique);

		// Ecoute des évènements liés à la souris dans l'AireDeDessin
		plateauGraphique.addMouseListener(new AdaptateurSouris(plateauGraphique, controleur));

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setVisible(true);

	}

}
