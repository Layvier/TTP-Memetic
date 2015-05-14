/*
* Nom de classe : Journee
*
* Version : 2.0
*
* Date : 10/2011
*
* Auteurs : Chams Lahlou, Damien Prot
*/

public class Journee implements Cloneable {
	private int nbVilles;	// nombre de villes
	private int[] rencontre;	// si rencontre (A,B) lors de la journée j on pose rencontre[A] = B et rencontre[B] = -A

    // constructeur par défaut
	public Journee() {
		setNbVilles(0);
		setRencontre(null);
	}
	
	// constructeur 2 : création d'un ensemble de rencontres vides
	public Journee(int nbVilles) {
		setNbVilles(nbVilles);
		rencontre = new int[getNbVilles()+1]; // la ville 0 n'est pas utilisée, donc on va de 1 à nbRencontres+1
		for (int i = 0; i < getNbVilles() + 1; i++)
			rencontre[i] = 0; 
		}

	public int getNbVilles() {
		return nbVilles;
	}

	public void setNbVilles(int nbVilles) {
		this.nbVilles = nbVilles;
	}

	public int[] getRencontre() {
		return rencontre;
	}

	public void setRencontre(int[] rencontre) {
		this.rencontre = rencontre;
	}

	// EXO 1
	// crée la rencontre "v1 reçoit v2" et "v2 se déplace chez v1"
	public void setRencontre(int v1, int v2) {
		if((v1 < this.getNbVilles()+1 && v2 < this.getNbVilles()+1) && v1 != v2)
		{
			this.rencontre[v1]=v2;
			this.rencontre[v2]=-v1;
		}
	}

	// renvoie la ville rencontrée par v
	public int getRencontre(int v) {
		return this.rencontre[v];
	}
    public Journee clone() {
    	Journee j = null;
    	try {
    		j = (Journee) super.clone();
    	}
    	catch(CloneNotSupportedException cnse) {
    		cnse.printStackTrace(System.err);
    	}
    	j.setRencontre(getRencontre().clone());
    	return j;
	}
    public void afficher() {
    	System.out.print("rencontres : ");
    	for (int v = 1; v < getNbVilles() + 1; v++)
    		if (getRencontre(v) > 0) // si v joue à domicile
    			System.out.print("(" + v + " reçoit " + getRencontre(v) + ") ");
    }
}
