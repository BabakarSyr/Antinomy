package Structures;


import java.util.Random;

public class TestFAP {
	public static void main(String[] args) {
		int min = 0;
		int[] count = new int[100];
		Random r = new Random();
		FAP<Integer> f = new FAPListe<>();
		FAP<Integer> g = new FAPTableau<>();

		assert (f.estVide());
		assert (g.estVide());
		for (int i = 0; i < 10000; i++) {
			if (r.nextBoolean()) {
				int val = r.nextInt(count.length);
				System.out.println("Insertion de " + val + " (Tableau et Liste)");
				f.insere(val);
				g.insere(val);
				assert (!f.estVide());
				assert (!g.estVide());
				if (val < min)
					min = val;
				count[val]++;
			} else {
				if (!f.estVide()) {
					assert (!g.estVide());
					int val = f.extrait();
					int val2 = g.extrait();
					assert (val == val2);
					count[val]--;
					assert (count[val] >= 0);
					assert (val >= min);
					if (val > min)
						min = val;
					System.out.println("Extraction de " + val + " (Tableau et Liste)");
				}
			}
		}
	}
}
