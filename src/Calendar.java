

/*
* Nom de classe : Calendar
*
* Version : 2.0
*
* Date : 10/2011
*
* Auteurs : Chams Lahlou, Damien Prot
*/
 

public class Calendar implements Cloneable {
	private int nbVilles; // nombre de villes participant au championnat
	private int nbJournees;	// nombre de journées pour un championnat
	private Journee[] journees; // tableau des journées du championnat
    // le constructeur par défaut 
	public Calendar() {
		this.nbJournees = 0;
		this.nbVilles = 0;
		this.journees = null;
	}
	
	// constructeur 2 : création d'un calendrier vide
	public Calendar (int nbVilles, int nbJournees) {
		this.nbVilles = nbVilles; 
		this.nbJournees = nbJournees;		
		this.journees = new Journee[this.nbJournees];
		for (int i = 0; i < this.nbJournees; i++)
			this.journees[i] = new Journee(getNbVilles());
	}

	public int getNbVilles() {
		return this.nbVilles;
	}

	public void setNbVilles(int nbVilles) {
		this.nbVilles = nbVilles;
	}

	public int getNbJournees() {
		return this.nbJournees;
	}

	public void setNbJournees(int nbJournees) {
		this.nbJournees = nbJournees;	
	}

	public Journee[] getJournees() {
		return journees;
	}

	public void setJournees(Journee[] journee) {
		this.journees = journee;
	}

	// renvoie la journée enregistrée à la date i
	public Journee getJournee(int i) {
		return this.journees[i];
	}

	// sauvegarde la journée j à la date i
	public void setJournee(int i, Journee j) {
		this.journees[i] = j;
	}
	
	// renvoie une copie du calendrier
    public Calendar clone() {
    	Calendar c = null;
    	try {
    		c = (Calendar) super.clone();
    	}
    	catch(CloneNotSupportedException cnse) {
    		cnse.printStackTrace(System.err);
    	}
    	c.journees = this.journees.clone();
    	return c;
	}

   
	// affiche le calendrier
	public void afficher() {
		System.out.println("\nCalendrier :");
		for (int j = 0; j < getNbJournees(); j++) {
			System.out.print("Journée " + j + " : ");
			this.journees[j].afficher();
			System.out.println();
		}
	}
	/**
	 * Vérifie que l'ajout d'une journée en position pos créé un calendrier valide (en considérant qu'en pos-1 il était déjà valide)
	 * @param pos
	 * @return
	 */
	public boolean estValide(int pos){//pos : dernière journée posée

		if(pos > this.getNbJournees()){
			pos = this.getNbJournees();
		}
		boolean reponse = true;
		if(pos > 2){
			int[] J4 = new int[4];
			for(int k =0; k< this.getNbVilles()+1; k++){//pour chaque ville
					for(int j=0;j < 4; j++){
						J4[j]=this.getJournee(pos-j).getRencontre(k);
					}
					if((J4[0] > 0 && J4[1] > 0) && (J4[2] > 0 && J4[3] >0)){
						reponse = false ;
					//	System.out.println("4 matchs à domicile pour "+k);
					}
					if((J4[0] < 0 && J4[1] < 0) && (J4[2] < 0 && J4[3] < 0)){
						reponse = false;
					//	System.out.println("4 matchs en extérieur pour "+k);
					}
				}
			
		}
		if(pos > 0 && reponse == true){
			for(int k =1; k< this.getNbVilles()+1; k++){//pour chaque ville
				if((this.getJournee(pos-1).getRencontre(k) == -this.getJournee(pos).getRencontre(k)) || (this.getJournee(pos-1).getRencontre(k) == this.getJournee(pos).getRencontre(k)) && (this.getJournee(pos).getRencontre(k) < 0)){
					reponse = false ;
				//	System.out.println("2 rencontres de suite entre : "+k+",  "+this.getJournee(i-1).getRencontre(k));
				}
			}			
		if(reponse == true){
			for(int k =1; k< this.getNbVilles()+1; k++){
				for(int i = 0; i < pos; i++){
						if(this.getJournee(i).getRencontre(k) == this.getJournee(pos).getRencontre(k)){
							reponse = false;
					//		System.out.println("Rencontre déjà effectuée : "+k+" , "+this.getJournee(fin-1).getRencontre(k));
						}
				
				}
			
			}
		}
		
	}	
		return reponse;
	
	}
	/**
	 * Retourne true si le calendrier est valide de la journée debut à la journée fin
	 * @param debut
	 * @param fin
	 * @return
	 */
	public boolean estValide(int debut, int fin) {
		
		if(fin-debut > this.getNbJournees()){
			debut = 0;
			fin = this.getNbJournees();
		}
		boolean reponse = true;
		if(fin-debut > 3){//On vérifie qu'il n'y a pas plus de trois matchs a domicile ou en extérieur pour une des équipes
			int[] J4 = new int[4];
			for(int k =0; k< this.getNbVilles()+1; k++){//pour chaque ville
				for(int i =debut+1; i < fin-2; i++){
					for(int j=0;j < 4; j++){
						J4[j]=this.getJournee(i+j-1).getRencontre(k);
					}
					if((J4[0] > 0 && J4[1] > 0) && (J4[2] > 0 && J4[3] >0)){
						reponse = false ;
					//	System.out.println("4 matchs à domicile pour "+k);
					}
					if((J4[0] < 0 && J4[1] < 0) && (J4[2] < 0 && J4[3] < 0)){
						reponse = false;
					//	System.out.println("4 matchs en extérieur pour "+k);
					}
				}
			}
			
		}
		if(fin-debut > 1 && reponse == true){//on vérifie qu'aucunes équipes ne se rencontrent deux fois de suite
			for(int k =1; k< this.getNbVilles()+1; k++){//pour chaque ville
				for(int i = debut+1; i < fin; i++){
					if((this.getJournee(i-1).getRencontre(k) == -this.getJournee(i).getRencontre(k)) || (this.getJournee(i-1).getRencontre(k) == this.getJournee(i).getRencontre(k)) && (this.getJournee(i).getRencontre(k) < 0)){
						reponse = false ;
					//	System.out.println("2 rencontres de suite entre : "+k+",  "+this.getJournee(i-1).getRencontre(k));
					}
				}
			}
		if(reponse == true){//On vérifie qu'aucun match ne soit déjà effectué avec la même équipe à domicile
			for(int k =1; k< this.getNbVilles()+1; k++){
				for(int i = debut; i < fin-1; i++){
						if(this.getJournee(i).getRencontre(k) == this.getJournee(fin-1).getRencontre(k)){
							reponse = false;
					//		System.out.println("Rencontre déjà effectuée : "+k+" , "+this.getJournee(fin-1).getRencontre(k));
						}
				
				}
			
			}
		}
		
	}	
		return reponse;
	
	}
}
