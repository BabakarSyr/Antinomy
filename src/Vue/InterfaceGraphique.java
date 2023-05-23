package Vue;

import javax.swing.* ;
import javax.swing.border.EmptyBorder;

import Global.Configuration;

import java.awt.* ;
import java.io.IOException;

import Modele.*;


// L'interface runnable déclare une méthode run
public class InterfaceGraphique extends JFrame implements Runnable {
	Jeu jeu;
	CollecteurEvenements controleur;
	PlateauGraphique plateau;
	JButton boutonOptionsJeu, boutonHistoriqueArriere, boutonHistoriqueAvant;
	Aspects aspects;
	JPanel panelMenuPrincipal;
	CardLayout layout;
	JPanel panelCourant;
	PlateauGraphique plateauGraphique;
	ImageJeu menuBackground;
	JFrame frame;

	public InterfaceGraphique(Jeu j, CollecteurEvenements c){
		jeu = j;
		controleur = c;
	}

	public static void demarrer(Jeu j, CollecteurEvenements c) {
		InterfaceGraphique vue = new InterfaceGraphique(j, c);
		SwingUtilities.invokeLater(vue);
	}	

	public void run() {
		//menuPrincipal();
		// Creation d'une fenetre
		frame = new JFrame("Ma fenetre a moi");
		frame.setSize(1280, 720);

		layout = new CardLayout();
        panelCourant = new JPanel(layout);
		frame.add(panelCourant);
		try{
			creerMenuPrincipal();
			panelCourant.add(panelMenuPrincipal, "MenuPrincipal");
			creerPlateauGraphique();
			panelCourant.add(plateauGraphique, "Plateau");
		}catch (IOException e) {
            Configuration.instance().logger().severe("Erreur d'affichage des menus !!!");
            e.printStackTrace();
        }
		
		// Garde à jour l'interface graphique du controleur
        controleur.interfaceGraphique(this);

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setVisible(true);
	}

	public void creerMenuPrincipal() throws IOException {

		panelMenuPrincipal = new MenuGraphique(controleur);
		panelMenuPrincipal.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
	
		//panelMenuPrincipal.setLayout(new GridLayout(9,3));
		
		gbc.insets = new Insets(20, 10, 10, 10);
	
		gbc.weightx=2;
		gbc.weighty=8;
	
		//gbc.weighty = 0.2;
		JButton boutonRapide = new JButton("Partie rapide");
		//gbc.gridwidth = 3;
		boutonRapide.setPreferredSize(new Dimension(250, 60));
		boutonRapide.addActionListener(new AdaptateurCommande(controleur, "Plateau"));
		gbc.gridx = 1;
		gbc.gridy = 0;
		panelMenuPrincipal.add(boutonRapide, gbc);
	
		JButton boutonPersonnalise= new JButton("Partie personnalise");
		boutonPersonnalise.setPreferredSize(new Dimension(250, 60));
		boutonPersonnalise.addActionListener(new AdaptateurCommande(controleur, "Plateau"));
		gbc.gridx = 1;
		gbc.gridy = 1;
		panelMenuPrincipal.add(boutonPersonnalise, gbc);
	
		JButton boutonParametre = new JButton("Regles");
		boutonParametre.setPreferredSize(new Dimension(250, 60));
		boutonParametre.addActionListener(new AdaptateurCommande(controleur, "Regles"));
		gbc.gridx = 1;
		gbc.gridy = 2;
		panelMenuPrincipal.add(boutonParametre, gbc);
	
		JButton boutonQuitter = new JButton("Quitter");
		boutonQuitter.setPreferredSize(new Dimension(250, 60));
		boutonQuitter.addActionListener(new AdaptateurCommande(controleur, "Quitter"));
		gbc.gridx = 1;
		gbc.gridy = 3;
		panelMenuPrincipal.add(boutonQuitter, gbc);
	
		//panelMenuPrincipal.setLayout(new GridLayout(9,3));*/
    }

	public void creerPlateauGraphique() throws IOException {
		// Ajout de notre composant de dessin dans la fenetre
		plateauGraphique = new PlateauGraphique(jeu, controleur);

		// Parametre des boutons 
		//MODIFIE
        //boutonOptionsJeu = new JButton("Menu");
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("            Menu           ");
		//JMenuItem boutonReprendre = new JMenuItem("Reprendre");
		JMenuItem boutonRecommencer = new JMenuItem("Recommencer");
		boutonRecommencer.addActionListener(new AdaptateurCommande(controleur, "Recommencer"));
		//JMenuItem boutonSauvegarder = new JMenuItem("Sauvegarder");
		JMenuItem boutonMenuPrincipal = new JMenuItem("Menu principal");
		boutonMenuPrincipal.addActionListener(new AdaptateurCommande(controleur, "MenuPrincipal"));

		//menu.add(boutonReprendre);
		menu.add(boutonRecommencer);
		//menu.add(boutonSauvegarder);
		menu.add(boutonMenuPrincipal);
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
        boutonHistoriqueArriere.addActionListener(new AdaptateurCommande(controleur, "Annuler"));
        boutonHistoriqueArriere.setEnabled(true);
        boutonHistoriqueAvant = new JButton("→");
        boutonHistoriqueAvant.addActionListener(new AdaptateurCommande(controleur, "Refaire"));
        boutonHistoriqueAvant.setEnabled(true);
        Container historiqueAvantArriere = Box.createHorizontalBox();
        historiqueAvantArriere.add(boutonHistoriqueArriere);
        historiqueAvantArriere.add(boutonHistoriqueAvant);

        Container historiqueBox = Box.createVerticalBox();
        historiqueBox.add(historiqueAvantArriere);
        plateauGraphique.add(historiqueBox, gbc);

		// Ecoute des évènements liés à la souris dans l'AireDeDessin
		plateauGraphique.addMouseListener(new AdaptateurSouris(plateauGraphique, controleur));
		plateauGraphique.addMouseMotionListener(new AdaptateurSourisMouvement(plateauGraphique, controleur));
	}

    public void afficherPanel(String nom) {
		layout.show(panelCourant, nom);
    }

	public void miseAjour(){
		frame.repaint();
	}

}
