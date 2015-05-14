import java.util.List;


public class Principale {
	
	
	public static void main(String[] arg) {
		//Chargement des donnees 
		TTP ttp = new TTP("donnees/donnees6.txt");
		System.out.println("Nb villes"+ttp.getNbVilles());
		System.out.println("Nb journees :"+ttp.getNbJournees());
		
		//Génération de toutes les journees possibles
		List<Journee> jours = ttp.genererJournees();
		System.out.println("Nb de journees :"+jours.size());
		System.out.println(jours.toString());
		
		
		//Calcul du calendrier optimal
		ttp.calendrierOptimal();
		ttp.getOptimum().afficher();
		boolean valide = ttp.getOptimum().estValide(0, ttp.getNbJournees());
		System.out.println("Valide : "+valide);
		System.out.println(" Cout : "+ttp.getValeurOptimum());
		System.out.println(ttp.nbCalPosssibles);
	}
	

}
