/*
* Nom de classe : DonneesVilles.java
*
* Version : 2.0
*
* Date : 10/2011
*
* Auteurs : Chams Lahlou, Damien Prot
*/

import java.io.*; // pour les accès aux fichiers
import java.util.*; // pour les accès aux fichiers

public class DonneesVilles {
	private int nbVilles;
	private int[][] distance;
	
    // constructeur par défaut
	public DonneesVilles() {
		setNbVilles(0);
		setDistance(null);
	}
	
    // constructeur 2 : création à partir d'un fichier texte
	public DonneesVilles(String s) { // s = nom du fichier à lire 
		System.out.println("lecture du fichier " + s);
    	try {
    		Scanner scanner = new Scanner(new FileReader(s));
    		
    		// lecture du nombre de villes
    		if (scanner.hasNextInt()) {
    			setNbVilles(scanner.nextInt());
    		}
    		
    		// lecture des distances ligne par ligne
    		setDistance(new int[getNbVilles() + 1][getNbVilles() + 1]); 
    		int i = 1; // indice ligne
    		int j = 1; // indice colonne
    		while (scanner.hasNextInt()) {
    			setDistanceVilles(i,j,scanner.nextInt());
    			// Décommenter pour afficher la lecture en cours 
    			 System.out.println("lecture : distance(" + i + "," + j + ") = " + getDistanceVilles(i,j));
    			if (j < getNbVilles()) {
    				j++; // colonne suivante
    			}
    			else { // sinon on change de ligne
    				i++;
    				j = 1;
    			}
    		}
    		scanner.close();
    	}
    	catch (IOException e) {
    		System.err.println("Erreur : " + e.getMessage()) ;
    		System.exit(2) ;
    	}
    }

	public int getDistanceVilles(int v1, int v2) {
		return distance[v1][v2];
	}
	
	public void setDistanceVilles(int v1, int v2, int d) {
		distance[v1][v2] = d;
	}

	public int getNbVilles() {
		return nbVilles;
	}

	public int[][] getDistance() {
		return distance;
	}

	public void setDistance(int[][] distance) {
		this.distance = distance;
	}

	public void setNbVilles(int nbVilles) {
		this.nbVilles = nbVilles;
	}
}
