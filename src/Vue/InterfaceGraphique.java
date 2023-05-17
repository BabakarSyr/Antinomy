package Vue;

import javax.swing.* ;
import java.awt.* ;

import Modele.*;


// L'interface runnable déclare une méthode run
public class InterfaceGraphique extends JFrame implements Runnable {
	Jeu jeu;
	CollecteurEvenements controleur;
	PlateauGraphique plateau;
	JButton boutonOptionsJeu, boutonHistoriqueArriere, boutonHistoriqueAvant;
	Aspects aspects;

	public InterfaceGraphique(Jeu j, CollecteurEvenements c){
		jeu = j;
		controleur = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements c) {
		InterfaceGraphique vue = new InterfaceGraphique(j, c);
		SwingUtilities.invokeLater(vue);
	}	

	public void run() {
		menuPrincipal();
		// Creation d'une fenetre
		JFrame frame = new JFrame("Ma fenetre a moi");
		frame.setSize(1280, 720);
	
		// Ajout de notre composant de dessin dans la fenetre
		PlateauGraphique plateauGraphique = new PlateauGraphique(jeu, controleur);

		// Parametre des boutons 
		//MODIFIE
        //boutonOptionsJeu = new JButton("Menu");
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("            Menu           ");
		JMenuItem boutonReprendre = new JMenuItem("Reprendre");
		JMenuItem boutonRecommencer = new JMenuItem("Recommencer");
		JMenuItem boutonSauvegarder = new JMenuItem("Sauvegarder");
		JMenuItem boutonQuitter = new JMenuItem("Quitter la partie");

		menu.add(boutonReprendre);
		menu.add(boutonRecommencer);
		menu.add(boutonSauvegarder);
		menu.add(boutonQuitter);
		menubar.add(menu);

		plateauGraphique.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.33;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
		

        //boutonOptionsJeu.addActionListener(new AdaptateurCommande(controleur, "OptionsJeu"));
        plateauGraphique.add(menubar, gbc);
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

		
		frame.add(plateauGraphique);

		// Ecoute des évènements liés à la souris dans l'AireDeDessin
		plateauGraphique.addMouseListener(new AdaptateurSouris(plateauGraphique, controleur));

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setVisible(true);

	}

	public void menuPrincipal(){
		Aspects aspects;
        JFrame frame_2 = new JFrame("Mon Manu à moi");
		aspects = new Aspects(2);
		Icon fond = new ImageIcon(aspects.fond.image());
		JLabel fond_l = new JLabel();

		// On fixe la taille et on demarre
        frame_2.setVisible(true);

        frame_2.setSize(1280, 720);
		frame_2.setLocationRelativeTo(null);
		
		
		fond_l.setSize(1280,720);
		fond_l.setIcon(fond); 
		frame_2.add(fond_l);

		frame_2.setLayout(new GridBagLayout());
		//fond_l.setPreferredSize(new Dimension(w,h));
		frame_2.add(fond_l);
        /*GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 0.5;
        gbc.weighty = 0.33;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;

		JButton boutonJeu =  new JButton("Jouer");
		frame_2.add(boutonJeu);*/

        // Un clic sur le bouton de fermeture clos l'application
        frame_2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
    }

}
