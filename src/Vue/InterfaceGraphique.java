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
	JPanel panelRegle;
	PlateauGraphique plateauGraphique;
	ImageJeu menuBackground;
	JFrame frame;

	MenuGraphique panelParametrePartie;
	JTextField nomJoueur1Defaut, nomJoueur2Defaut;
	JComboBox comboBoxJoueur1, comboBoxJoueur2;
	JRadioButton radioJoueur1, radioJoueur2, radioAleatoire;
	JButton boutonValiderParametre, boutonAnnulerParametre;

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
		frame = new JFrame("Antinomy");
		frame.setSize(1280, 720);

		layout = new CardLayout();
        panelCourant = new JPanel(layout);
		frame.add(panelCourant);
		try{
			creerMenuPrincipal();
			panelCourant.add(panelMenuPrincipal, "MenuPrincipal");
			creerPlateauGraphique();
			panelCourant.add(plateauGraphique, "Plateau");
			creerParametrePartie();
			panelCourant.add(panelParametrePartie, "ParametrePartie");
			creerRegle();
			panelCourant.add(panelRegle,"Regles");
		}catch (IOException e) {
            Configuration.instance().logger().severe("Erreur d'affichage des menus !!!");
            e.printStackTrace();
        }
		
		// Garde à jour l'interface graphique du controleur
        controleur.interfaceGraphique(this);

		//boucle de jeu
		Timer time = new Timer(16, new AdaptateurTemps(controleur));
		time.start();

		// Un clic sur le bouton de fermeture clos l'application
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// On fixe la taille et on demarre
		frame.setVisible(true);
	}

	public void creerRegle(){
		panelRegle = new MenuGraphique(controleur);
		
		Aspects aspects = new Aspects(2);

		JLabel reg1 = new JLabel(new ImageIcon(aspects.fondBoutonPR.image()));
		panelRegle.add(reg1);
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
		Aspects aspects;
		aspects = new Aspects(2);
		Icon icon = new ImageIcon(aspects.fondBoutonPR.image());
		JButton boutonRapide = new JButton("Partie rapide",icon);
		boutonRapide.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		//gbc.gridwidth = 3;
		boutonRapide.setPreferredSize(new Dimension(250, 60));
		boutonRapide.addActionListener(new AdaptateurCommande(controleur, "Plateau"));
		gbc.gridx = 1;
		gbc.gridy = 0;
		boutonRapide.setBackground(Color.WHITE);
		boutonRapide.setForeground(Color.BLACK);
		panelMenuPrincipal.add(boutonRapide, gbc);

		Icon icon2 = new ImageIcon(aspects.fondBoutonPers.image());
		JButton boutonPersonnalise= new JButton("Partie personnalise",icon2);
		boutonPersonnalise.setPreferredSize(new Dimension(250, 60));
		boutonPersonnalise.addActionListener(new AdaptateurCommande(controleur, "ParametrePartie"));
		gbc.gridx = 1;
		gbc.gridy = 1;
		boutonPersonnalise.setBackground(Color.yellow);
		boutonPersonnalise.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		boutonPersonnalise.setForeground(Color.WHITE);
		panelMenuPrincipal.add(boutonPersonnalise, gbc);
	
		Icon icon3 = new ImageIcon(aspects.fondBoutonRegle.image());
		JButton boutonParametre = new JButton("Regles",icon3);
		boutonParametre.setPreferredSize(new Dimension(250, 60));
		boutonParametre.addActionListener(new AdaptateurCommande(controleur, "Regles"));
		gbc.gridy = 2;
		boutonParametre.setBackground(Color.WHITE);
		boutonParametre.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		boutonParametre.setForeground(Color.BLACK);
		panelMenuPrincipal.add(boutonParametre, gbc);

		Icon icon4 = new ImageIcon(aspects.fondBoutonQuitter.image());
		JButton boutonQuitter = new JButton("Quitter",icon4);
		boutonQuitter.setPreferredSize(new Dimension(250, 60));
		boutonQuitter.addActionListener(new AdaptateurCommande(controleur, "Quitter"));
		gbc.gridx = 1;
		gbc.gridy = 3;
		boutonQuitter.setBackground(Color.WHITE);
		boutonQuitter.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));
		boutonQuitter.setForeground(Color.BLACK);
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
		JMenu menu = new JMenu("          Menu         ");
		//JMenuItem boutonReprendre = new JMenuItem("Reprendre");
		JMenuItem boutonRecommencer = new JMenuItem("Recommencer");
		boutonRecommencer.addActionListener(new AdaptateurCommande(controleur, "Recommencer"));
		//JMenuItem boutonSauvegarder = new JMenuItem("Sauvegarder");
		JMenuItem boutonMenuPrincipal = new JMenuItem("Menu principal");
		boutonMenuPrincipal.addActionListener(new AdaptateurCommande(controleur, "MenuPrincipal"));


        // Ajout bouton paramètre
		Icon icon = new ImageIcon("res/Images/rouage.png");
		JButton opt = new JButton(icon);
		opt.setPreferredSize(new Dimension(50, 50)); 
		opt.addActionListener(new AdaptateurCommande(controleur, "ParametrePartie"));


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

        Container parametreMenu = Box.createHorizontalBox();
		parametreMenu.add(opt);
        parametreMenu.add(menubar);
        
        Container paramBox = Box.createVerticalBox();
        paramBox.add(parametreMenu);
        plateauGraphique.add(paramBox, gbc);



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

	public void creerParametrePartie() throws IOException{

        int borderTop = getSize().height / 3;
        int borderBottom = getSize().height / 3;
        int borderSides = getSize().width / 3;

        panelParametrePartie = new MenuGraphique(controleur);
        panelParametrePartie.setLayout(new GridBagLayout());
        panelParametrePartie.setBorder(new EmptyBorder(borderTop, borderSides, borderBottom, borderSides));
        GridBagConstraints gbc = new GridBagConstraints();

        String[] choixComboBox = {
            "Humain",
            "IA Facile",
            "IA Difficile"
        };

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.33;
        gbc.insets = new Insets(10,10,10,10);  //top padding

        JLabel nomJoueur1 = new JLabel("Joueur 1");
        gbc.gridx = 0;
        gbc.gridy = 0;  
        panelParametrePartie.add(nomJoueur1, gbc);

        gbc.ipady = 10;
        nomJoueur1Defaut = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;  
        nomJoueur1Defaut.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 15));
        nomJoueur1Defaut.setText("Joueur 1");
        panelParametrePartie.add(nomJoueur1Defaut, gbc);

        comboBoxJoueur1 = new JComboBox<>();
        for(int i = 0; i < choixComboBox.length; i++){
            comboBoxJoueur1.addItem(choixComboBox[i]);
        }
        comboBoxJoueur1.setFocusable(false);
        comboBoxJoueur1.addActionListener(new AdaptateurCommande(controleur, comboBoxJoueur1.getSelectedItem().toString()+"1"));
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelParametrePartie.add(comboBoxJoueur1, gbc);

        gbc.gridwidth = 2;


        gbc.gridwidth = 1;
        gbc.insets = new Insets(10,30,0,0);  //padding elements Joueur2

        gbc.ipady = 0;
        JLabel nomJoueur2 = new JLabel("Joueur 2");
		gbc.insets = new Insets(10,10,10,10);
        gbc.gridx = 1;
        gbc.gridy = 0; 
        panelParametrePartie.add(nomJoueur2, gbc);

        gbc.ipady = 10;
        nomJoueur2Defaut = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;  
        nomJoueur2Defaut.setText("Joueur 2");
        nomJoueur2Defaut.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 15));
        panelParametrePartie.add(nomJoueur2Defaut, gbc);


        comboBoxJoueur2 = new JComboBox<>();
        for(int i = 0; i < choixComboBox.length; i++){
            comboBoxJoueur2.addItem(choixComboBox[i]);
        }
        comboBoxJoueur2.setFocusable(false);
        comboBoxJoueur2.setSelectedIndex(2);
        comboBoxJoueur2.addActionListener(new AdaptateurCommande(controleur, comboBoxJoueur2.getSelectedItem().toString()+"2"));
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        panelParametrePartie.add(comboBoxJoueur2, gbc);

        gbc.insets = new Insets(30,10,10,10); 
        gbc.gridx = 2;
        gbc.gridy = 6; 

        gbc.gridx = 0;
        boutonAnnulerParametre = new JButton("Annuler");
        boutonAnnulerParametre.addActionListener(new AdaptateurCommande(controleur, "MenuPrincipal"));
        //boutonAnnulerParametre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelParametrePartie.add(boutonAnnulerParametre, gbc);

		gbc.gridx ++;
		boutonValiderParametre = new JButton("Valider");
        boutonValiderParametre.addActionListener(new AdaptateurCommande(controleur, "Valider"));
        //boutonValiderParametre.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelParametrePartie.add(boutonValiderParametre, gbc);
    }

    public void setBoutonHistoriqueAnnuler(boolean peutAnnuler) {
        boutonHistoriqueArriere.setEnabled(peutAnnuler);
    }

    public void setBoutonHistoriqueRefaire(boolean peutRefaire) {
        boutonHistoriqueAvant.setEnabled(peutRefaire);
    }
	
    public void afficherPanel(String nom) {
		layout.show(panelCourant, nom);
    }

	public void miseAjour(){
		frame.repaint();
	}

    public void setDuelEgalite(int indiceJ1, int indiceJ2)
    {
        plateauGraphique.setDuelEgalite(indiceJ1, indiceJ2);
    }
    
    public void clearDuelEgalite()
    {
        plateauGraphique.setDuelEgalite(-1, -1);
    }

}
