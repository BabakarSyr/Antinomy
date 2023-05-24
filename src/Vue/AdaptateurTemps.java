package Vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdaptateurTemps implements ActionListener {
	CollecteurEvenements controle;

	AdaptateurTemps(CollecteurEvenements c) {
		controle = c;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		controle.tictac();
	}
}

    
