package Vue;

public class Aspects 
{
    ImageJeu anneau_bleu, anneau_rouge, anneau_vert, anneau_violet,
    cle_bleu, cle_rouge, cle_vert, cle_violet,
    crane_bleu, crane_rouge, crane_vert, crane_violet,
    plume_bleu, plume_rouge, plume_vert, plume_violet,
    codex_bleu, codex_rouge, codex_vert, codex_violet, 
    carte_dos, carte_dos2,
    sorcier1, sorcier2,
	fleche1, fleche2;


	public Aspects()
	{
		chargerAspect2();
	}

	public Aspects(int i)
	{
		switch(i)
		{
			case 1:
				chargerAspect1();
				break;
			case 2:
				chargerAspect2();
				break;

            case 3:
                chargerCarte1();
                chargercodex2();
                chargerSorciers2();
				break;
			default:
				break;
		}
    }


    ///////////////////////
    ///// ASPECT 1 ////////
    ///////////////////////


    public void chargerAspect1()
    {
        anneau_bleu = new ImageJeu("anneau_bleu");
		anneau_rouge = new ImageJeu("anneau_rouge");
		anneau_vert = new ImageJeu("anneau_vert");
		anneau_violet = new ImageJeu("anneau_violet");
		cle_bleu = new ImageJeu("cle_bleu");
		cle_rouge = new ImageJeu("cle_rouge");
		cle_vert = new ImageJeu("cle_vert");
		cle_violet = new ImageJeu("cle_violet");
		crane_bleu = new ImageJeu("crane_bleu");
		crane_rouge = new ImageJeu("crane_rouge");
		crane_vert = new ImageJeu("crane_vert");
		crane_violet = new ImageJeu("crane_violet");
		plume_bleu = new ImageJeu("plume_bleu");
		plume_rouge = new ImageJeu("plume_rouge");
		plume_vert = new ImageJeu("plume_vert");
		plume_violet = new ImageJeu("plume_violet");
		codex_bleu = new ImageJeu("codex");
		codex_rouge = new ImageJeu("codex");
		codex_violet = new ImageJeu("codex");
		codex_vert= new ImageJeu("codex");
		carte_dos= new ImageJeu("codex");
		sorcier1= new ImageJeu("sorcier_1");
		sorcier2= new ImageJeu("sorcier_2");
		
    }

    public void chargerCarte1()
    {
        anneau_bleu = new ImageJeu("anneau_bleu");
		anneau_rouge = new ImageJeu("anneau_rouge");
		anneau_vert = new ImageJeu("anneau_vert");
		anneau_violet = new ImageJeu("anneau_violet");
		cle_bleu = new ImageJeu("cle_bleu");
		cle_rouge = new ImageJeu("cle_rouge");
		cle_vert = new ImageJeu("cle_vert");
		cle_violet = new ImageJeu("cle_violet");
		crane_bleu = new ImageJeu("crane_bleu");
		crane_rouge = new ImageJeu("crane_rouge");
		crane_vert = new ImageJeu("crane_vert");
		crane_violet = new ImageJeu("crane_violet");
		plume_bleu = new ImageJeu("plume_bleu");
		plume_rouge = new ImageJeu("plume_rouge");
		plume_vert = new ImageJeu("plume_vert");
		plume_violet = new ImageJeu("plume_violet");
		codex_bleu = new ImageJeu("codex");
		codex_rouge = new ImageJeu("codex");
		codex_violet = new ImageJeu("codex");
		codex_vert= new ImageJeu("codex");
		carte_dos= new ImageJeu("codex");
		
    }

    public void chargerSorciers1()
    {
        sorcier1= new ImageJeu("sorcier_1");
		sorcier2= new ImageJeu("sorcier_2");
    }




    ///////////////////////
    ///// ASPECT 2 ////////
    ///////////////////////




    public void chargerAspect2()
    {
        anneau_bleu = new ImageJeu("anneau_bleu2");
		anneau_rouge = new ImageJeu("anneau_rouge2");
		anneau_vert = new ImageJeu("anneau_vert2");
		anneau_violet = new ImageJeu("anneau_violet2");
		cle_bleu = new ImageJeu("cle_bleu2");
		cle_rouge = new ImageJeu("cle_rouge2");
		cle_vert = new ImageJeu("cle_vert2");
		cle_violet = new ImageJeu("cle_violet2");
		crane_bleu = new ImageJeu("crane_bleu2");
		crane_rouge = new ImageJeu("crane_rouge2");
		crane_vert = new ImageJeu("crane_vert2");
		crane_violet = new ImageJeu("crane_violet2");
		plume_bleu = new ImageJeu("plume_bleu2");
		plume_rouge = new ImageJeu("plume_rouge2");
		plume_vert = new ImageJeu("plume_vert2");
		plume_violet = new ImageJeu("plume_violet2");
		codex_bleu = new ImageJeu("codex_bleu");
		codex_rouge = new ImageJeu("codex_rouge");
		codex_violet = new ImageJeu("codex_violet");
		codex_vert= new ImageJeu("codex_vert");
		carte_dos= new ImageJeu("carte_dos2");
		sorcier1= new ImageJeu("sorcier1");
		sorcier2= new ImageJeu("sorcier2");
		fleche1 = new ImageJeu("fleche_temps_J1");
		fleche2 = new ImageJeu("fleche_temps_J2");
    }

    public void chargerCarte2()
    {
        anneau_bleu = new ImageJeu("anneau_bleu2");
		anneau_rouge = new ImageJeu("anneau_rouge2");
		anneau_vert = new ImageJeu("anneau_vert2");
		anneau_violet = new ImageJeu("anneau_violet2");
		cle_bleu = new ImageJeu("cle_bleu2");
		cle_rouge = new ImageJeu("cle_rouge2");
		cle_vert = new ImageJeu("cle_vert2");
		cle_violet = new ImageJeu("cle_violet2");
		crane_bleu = new ImageJeu("crane_bleu2");
		crane_rouge = new ImageJeu("crane_rouge2");
		crane_vert = new ImageJeu("crane_vert2");
		crane_violet = new ImageJeu("crane_violet2");
		plume_bleu = new ImageJeu("plume_bleu2");
		plume_rouge = new ImageJeu("plume_rouge2");
		plume_vert = new ImageJeu("plume_vert2");
		plume_violet = new ImageJeu("plume_violet2");

		carte_dos= new ImageJeu("carte_dos2");
    }

    
    public void chargerSorciers2()
    {
        sorcier1= new ImageJeu("sorcier1");
		sorcier2= new ImageJeu("sorcier2");
    }

    public void chargercodex2()
    {
		codex_bleu = new ImageJeu("codex_bleu");
		codex_rouge = new ImageJeu("codex_rouge");
		codex_violet = new ImageJeu("codex_violet");
		codex_vert= new ImageJeu("codex_vert");
    }
}
