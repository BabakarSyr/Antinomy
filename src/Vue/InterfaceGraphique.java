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

        int borderTop = getSize().height / 6;
        int borderBottom = getSize().height / 8;
        int borderSides = getSize().width / 3;

        panelMenuPrincipal = new MenuGraphique(controleur);
        panelMenuPrincipal.setLayout(new GridBagLayout());
        panelMenuPrincipal.setBorder(new EmptyBorder(borderTop, borderSides, borderBottom, borderSides));
        GridBagConstraints gbc = new GridBagConstraints();

		//panelMenuPrincipal.getGraphics().drawImage(getIconImage(), borderSides, borderTop, borderBottom, borderSides, getForeground(), rootPane);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10);

        gbc.weighty = 0.2;
        JButton boutonJouer = new JButton("Jouer");
        boutonJouer.addActionListener(new AdaptateurCommande(controleur, "Plateau"));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelMenuPrincipal.add(boutonJouer, gbc);

        JButton boutonQuitter = new JButton("Quitter");
        boutonQuitter.addActionListener(new AdaptateurCommande(controleur, "Quitter"));
        gbc.gridy++;
        panelMenuPrincipal.add(boutonQuitter, gbc);

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
