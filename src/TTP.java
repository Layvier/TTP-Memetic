/*
* Nom de classe : TTP
*
* Version : 2.0
*
* Date : 10/2011
*
* Auteurs : Chams Lahlou, Damien Prot
*/

import java.util.*;

public class TTP {
	private int nbVilles; // Nombre de villes
	private int nbJournees; // Nombre de journées
	private DonneesVilles donnees; // Données liées aux villes
	private Calendar Optimum; // Meilleur calendrier
	private int valeurOptimum; // Valeur du meilleur calendrier
	private double nbCalTestes;  // Nombre de calendriers déjà testés
	public double nbCalPosssibles; // Nombre de calendriers possibles (sans tenir compte des contraintes 1 et 2)
	private int pourcentEffectue; // Pourcentage déjà effectué (arrondi au pourcent inférieur)
	public int nbJourneesTotales; // Nombre de journées possible
	
	
	// constructeur par défaut
	public TTP() {
		setNbVilles(0);
		setNbJournees(0);
		setDonnees(null);
		setOptimum(null);
		setValeurOptimum(Integer.MAX_VALUE);
		setNbCalTestes(0);
		setNbCalPosssibles(0);
		setPourcentEffectue(0);
	}
	
	// constructeur 2 : à partir d'un fichier de données
	public TTP(String s) {
		setDonnees(new DonneesVilles(s));
		setNbVilles(getDonnees().getNbVilles());
		setNbJournees(2*getNbVilles()-2);
		setOptimum(new Calendar(getNbVilles(), getNbJournees()));
		setValeurOptimum(Integer.MAX_VALUE);
		
		setPourcentEffectue(0);
		setNbCalTestes(0);
		long nbJourneesPossibles = fact(getNbVilles()) / fact(getNbVilles()/2);
		setNbCalPosssibles(arrangement(nbJourneesPossibles, getNbJournees()));
		
	}
	
	public int getNbVilles() {
		return nbVilles;
	}

	public void setNbVilles(int nbVilles) {
		this.nbVilles = nbVilles;
	}

	public int getNbJournees() {
		return nbJournees;
	}

	public void setNbJournees(int nbJournees) {
		this.nbJournees = nbJournees;
	}

	public DonneesVilles getDonnees() {
		return donnees;
	}

	public void setDonnees(DonneesVilles donnees) {
		this.donnees = donnees;
	}

	public double getNbCalTestes() {
		return nbCalTestes;
	}

	public void setNbCalTestes(double nbCalTestes) {
		this.nbCalTestes = nbCalTestes;
	}

	public double getNbCalPosssibles() {
		return nbCalPosssibles;
	}

	public void setNbCalPosssibles(double nbPossibilites) {
		this.nbCalPosssibles = nbPossibilites;
	}

	public int getPourcentEffectue() {
		return pourcentEffectue;
	}

	public void setPourcentEffectue(int pourcentEffectue) {
		this.pourcentEffectue = pourcentEffectue;
	}

	public Calendar getOptimum() {
		return Optimum;
	}

	public void setOptimum(Calendar optimum) {
		Optimum = optimum;
	}

	public int getValeurOptimum() {
		return valeurOptimum;
	}

	public void setValeurOptimum(int valeurOptimum) {
		this.valeurOptimum = valeurOptimum;
	}


	// EXO 2 - partie 1
	// renvoie la liste de toutes les journées (liste générée de façon récursive)
	public ArrayList<Journee> genererJournees() {
		//Création de la liste de journées possibles. Elle sera remplie grâce à la fonctin récursive
		ArrayList<Journee> s = new ArrayList<Journee>();
		
		//Création de la liste de villes. La première ville est la ville 1 en i =0;
		List<Integer> villes = new ArrayList<Integer>();
		for(int i =0; i<this.getNbVilles(); i++){
			villes.add(i, i+1);;
			//System.out.println("ville "+villes.toString());
		}
		
		//Journee vide, nécessaire au lancement de la fonction récursive
		Journee j = new Journee(this.getNbVilles());
		
		
		//Appel de la méthode récursive. 
		this.genererJournees(s, j, villes);
		
		// Initialisation de nbJourneesTotales. Vaut n!/(n/2)!, n nombre de villes
		this.nbJourneesTotales = s.size();
		
		return s;
	}
 
	// EXO 2 - partie 2
	// génère toutes les journées de façon récursive
	public void genererJournees(ArrayList<Journee> s, Journee j, List<Integer> villes) {
		
		if(villes.size() == 0){// Si la liste de villes est nulle, alors chaque ville a une rencontre pour cette journée et on peut l'ajouter
			s.add(j);
			j.afficher();
		}
		
		else{ // Si il reste des villes à placer (minimum 2 et nombre pair)
			int ville1 = villes.get(0);
			int ville2;
			
			// On prend la première ville dispo (get(0)) et on cherche 
			//toutes les différentes rencontres encore possibles avec cette ville
			for(int i=1; i < villes.size(); i++){ 
				
				//Soit ville2 la deuxième ville disponible
				ville2=villes.get(i);
				
				//On créé deux journées, une pour ville1 recoit ville2 et l'autre l'inverse
				Journee j2=j.clone();
				Journee j3=j.clone();
				j2.setRencontre(ville1, ville2);
				j3.setRencontre(ville2, ville1);
				
				//On clone la liste des villes disponible, et on retire les villes ville1 et ville2 que l'on vient de placer
				List<Integer> villes1 = new ArrayList<Integer>(villes);
				List<Integer> villes2 = new ArrayList<Integer>(villes);
				villes1.remove(i);
				villes1.remove(0);
				
				villes2.remove(i);
				villes2.remove(0);
				
				//On rappel la méthode pour les deux journées ainsi crées.
				//Si il ne reste plus de villes disponibles dans villes1 et villes2, les journées sont complètes et seront ajoutées.
				this.genererJournees(s, j2, villes1);
				this.genererJournees(s, j3, villes2);
				
				
			}
			
		}
	}
	
	// EXO 3
	// renvoie le coût total des déplacements d'un calendrier
	public int cout(Calendar cal, int fin) {
		if(fin == 0){
			return 0;
		}
		else {
			if(fin > cal.getNbJournees()){// Si fin est trop grand, on le ramène au nombre de journées max
				fin = cal.getNbJournees();
				System.out.println("Nouvelle fin :"+fin);
			}
			int cout=0;
			
			for(int i = 0; i < fin ;i++){// passage de à domicile à en ext.
				for(int j = 1; j < cal.getNbVilles()+1; j++){
					if( (i == 0 || cal.getJournee(i-1).getRencontre(j) > 0) && cal.getJournee(i).getRencontre(j) < 0 ){
						cout= cout+this.donnees.getDistanceVilles(j, -cal.getJournee(i).getRencontre(j));
					}
				}
			}
			for(int i = 1; i < fin; i++){	
				for(int j = 1; j < cal.getNbVilles()+1; j++){
					if(cal.getJournee(i-1).getRencontre(j) < 0 && cal.getJournee(i).getRencontre(j) < 0 ){//Si deux rencontres en extérieur de suite en i
						cout= cout+this.donnees.getDistanceVilles(-cal.getJournee(i-1).getRencontre(j), -cal.getJournee(i).getRencontre(j));
					}
					if(cal.getJournee(i-1).getRencontre(j) < 0 && cal.getJournee(i).getRencontre(j) > 0 ){// Si retour à domicile en i
						cout= cout+this.donnees.getDistanceVilles(-cal.getJournee(i-1).getRencontre(j), j);
					}
				}
			}
	
			for(int j = 1; j<cal.getNbVilles()+1;j++){
				if(cal.getJournee(fin-1).getRencontre(j) < 0){// Si le dernier match est en extérieur, on ajoute le cout du retour à domicile
					cout=cout+this.donnees.getDistanceVilles(-cal.getJournee(fin-1).getRencontre(j), j);;
				}
			}
			
			return cout;
		}
	}

	public int coutTotal(Calendar cal) {
		return this.cout(cal, getNbJournees());
	}
	
	/** Factorielle n
	 * @param n
	 * @return
	 */
	private int fact(int n) { 
		if (n==1)
			return n;
		else
			return n*fact(n-1);
		
	}

	/**
	 * Méthode calculant l'arrangement de k parmi n
	 * @param n
	 * @param k
	 * @return la valeur de l'arrangement de k éléments parmi n
	 */
	private double arrangement(long n, int k) {
		if (k==0)
			return 1;
		
		double valeur = n;
		for (int i=1;i<k;i++)
		{
			valeur *= (n-i);
		}
		return valeur;
	}

	// EXO 5 - partie 1
	/**
	* recherche le meilleur calendrier trouvé par séparation et évaluation
	* l'énumération se fait en appelant la méthode genererCalendrier
	*/
	public void calendrierOptimal() {
		
		System.out.println("Recherche du calendrier optimal");
		
		//création de la liste de toutes les journées possibles.
		ArrayList<Journee> totalJ = this.genererJournees();
		
		
		int[] planif = new int[this.nbJournees];
		
		Calendar cal = new Calendar(this.nbVilles, this.nbJournees);
		// On initialise le cout avec une valeur très forte. 
		//Destiné à être amélioré dès le premier calendrier valide trouvé.
		this.valeurOptimum=1000000000;
		
		this.nbCalPosssibles=this.arrangement(totalJ.size(),this.nbJournees);
		this.nbCalTestes=0;
		this.pourcentEffectue=0;
		
		//*******Choisir la méthode récursive********
		
	//	this.genererCalendrier( totalJ, 0, planif, cal);System.err.println((this.nbCalTestes/this.nbCalPosssibles)*100+"%");//Appel à la méthode récursive d'exporation en profondeur demandée
		this.genererCalendrierMieux(totalJ, 0, cal);//Méthode améliorée, plus rapide
		
		//Affichage du pourcentage total-A effacer lorsqu'on utilise genererCalendrierMieux
		
	}
 

	// EXO 5 - partie 2
	/**
	 * recherche le meilleur calendrier trouvé par séparation et évaluation
	 * l'énumération se fait de façon récursive
	 * Réponds à l'exercice, mais la méthode est très lourde
	 */
	
	public void genererCalendrier(ArrayList<Journee> totalJ, int pos, int[] planif, Calendar cal)  {
		for(int i=0; i<totalJ.size();i++){//On teste chaque journée non utilisée dans le calendrier actuel
			cal.setJournee(pos, totalJ.get(i));
			//planif[pos]=i;
			if(cal.estValide(0, pos+1)){
				int cout = this.coutTotal(cal);//Fonction objectif à minimiser
				
				if(cout < this.valeurOptimum){
					if(pos == this.nbJournees-1){//Si le calendrier est valide, complet et meilleur que la meilleure solution (l'optimum) trouvée jusqu'à présent
						
						//On remplace l'optimum par le calendrier actuel
						this.valeurOptimum = cout;
						this.Optimum = cal.clone();
						//cal.afficher();
						
						//Ne pas oublié de compter cette solution comme un calendrier testé
						this.nbCalTestes= this.nbCalTestes+1;
						System.out.println(this.valeurOptimum);//Affichage du cout optimal actuel
						
					}
					else{//Si le calendrier n'est pas complet, mais valide est son cout < cout trouvé minimum
						
						int l = pos+1;
						int[] p = new int[this.nbJournees];
						p = planif;
						
						//totalJ est cloné et la journée i placée en pos est retirée. 
						//Ralenti le programme, mais permet l'affichage en pourcentage demandé
						ArrayList<Journee> totalJ2 = (ArrayList<Journee>) totalJ.clone();
						totalJ2.remove(i);
						
						//Comme on se trouve à un embranchement de l'arbre valide, on rappel la méthode 
						this.genererCalendrier(totalJ2, l, p, cal.clone());
					}
				}
				else{// Si le coût du calendrier actuel, même incomplet est supérieur à celui de l'optimum
					
					//On arrête de rechercher des solutions depuis cet embranchement
					//et on compte le nombre de calendriers que l'on ne considère pas
					
					this.nbCalTestes=this.nbCalTestes+this.arrangement(this.nbJourneesTotales-pos-1, this.nbJournees-pos-1);
					this.pourcentEffectue=(int) (this.nbCalTestes/this.nbCalPosssibles)*100;
					if(pos == 2){
						System.out.println((this.nbCalTestes/this.nbCalPosssibles)*100+"%");
					}
				}
			}
			else{//Si le calendrier n'est pas valide
				//On arrête de rechercher des solutions depuis cet embranchement
				//et on compte le nombre de calendriers que l'on ne considère pas 
				this.nbCalTestes=this.nbCalTestes+this.arrangement(this.nbJourneesTotales-pos-1, this.nbJournees-pos-1);
				this.pourcentEffectue=(int) (this.nbCalTestes/this.nbCalPosssibles)*100;
				if(pos == 2){//Sans cette condition, l'affichage en pourcentage ralenti fortement le programme
					System.err.println((this.nbCalTestes/this.nbCalPosssibles)*100+"%");
				}
			}
			
			
		}
	}
	/**
	 * recherche le meilleur calendrier trouvé par séparation et évaluation
	 * l'énumération se fait de façon récursive
	 * Méthode moins lourde que genererCalendrier car :
	 * -le tableau int[] planif est supprimé car inutil
	 * -le test de validité du calendrier vérifie que la journée ajoutée ne change pas la validité du calendrier
	 * (ne teste pas tout le calendrier)
	 * -On ne retire pas la journée placée de totalJ. Complexité plus élevée, mais une seule variable pour la liste des journées possibles.
	 * Se révèle plus intéressant lorsque le nombre de villes est elevé, car le nombre de journées possibles augmente plus rapidement 
	 * que le nombre journées totales du calendrier
	 * @param totalJ
	 * @param pos
	 * @param planif
	 * @param cal
	 */
	public void genererCalendrierMieux(ArrayList<Journee> totalJ, int pos, Calendar cal)  {
		for(int i=0; i<totalJ.size();i++){
		if(pos == 1){
			//L'affichage en pourcentage est plus simple.
			this.nbCalTestes = this.nbCalTestes + 1;
			System.err.println((this.nbCalTestes/(totalJ.size()*totalJ.size()))*100+"%");
		}
		cal.setJournee(pos, totalJ.get(i));
		if(cal.estValide(pos)){
			//La méthode estValide(int pos) est moins lourde que estValide(int debut, int fin), car ne vérifie pas ce qui a déjà été vérifié pour pos-1
			int cout = this.coutTotal(cal);
			if(cout < this.valeurOptimum){
				if(pos == this.nbJournees-1){
					this.valeurOptimum = cout;
					this.Optimum = cal.clone();
					System.out.println(this.valeurOptimum);//Affichage du cout optimal actuel
					}
				else{
					int l = pos+1;
					this.genererCalendrierMieux(totalJ, l, cal.clone());//totalJ n'est pas cloné : trop lourd pour le programme
					}
				}
			}
		
		}
	
	}


}
