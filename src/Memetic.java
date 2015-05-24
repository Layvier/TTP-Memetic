
public class Memetic {
	private DonneesVilles donnees;
	public Population pop;
	public Population newpop;
	public Calendar optimum;
	public int optCost;
	public int nbIteration;
	public int sizePop;
	public int nbCities;
	public int nbDays;
	
	public Memetic(String fileName){
		this.pop = this.GenerateInitialPopulation();
	}
	public Population getPop(){
		return this.pop;
	}
	public void setPop(Population pop){
		this.pop =pop;
	}
	public Population getNewpop(){
		return this.newpop;
	}
	public void setNewpop(Population newpop){
		this.newpop = newpop;
	}
	/**
	 * Initialize a population of calendars. Can be random, but better results can be reached using a general heuristic
	 * @return
	 */
	public Population GenerateInitialPopulation(){
		Population pop = new Population();
		for(int i =0; i <this.sizePop; i++){
			Cal cal = new Cal(this.nbCities, this.nbDays);
			cal.random();
			cal.makeValid();
			pop.addCalendrier(cal);
		}
		return pop;
	}
	/**
	 * Crossover method : generate descendants population from the parent population.
	 * @param pop
	 * @return
	 */
	public Population GenerateNewPopulation(Population pop){
		Population newpop = new Population();
		for(int i=0; i< pop.getSize(); i =i+2){
			Cal cal = new Cal(this.nbCities, this.nbDays);
			cal.crossover(pop.getCal(i), pop.getCal(i+1));
			newpop.addCalendrier(cal);
		}
		return newpop;
	}
	/**
	 * Selection method : Keep only the best calendars among the parent population (pop) and the descendants population
	 * @param pop
	 * @param newpop
	 * @return
	 */
	public Population UpdatePopulation(Population pop, Population newpop){
		
		return pop;
	}
	public Calendar getBest(Population pop){
		return null;
	}
	public int getCost(Cal cal){
		
		return 0;
	}
	/**
	 * return the optimal solution found with this set of data and the parameters.
	 * @return
	 */
	public Calendar GlobalMemetic(int sizePop, int nbIteration){
		this.nbIteration= nbIteration;
		this.sizePop= sizePop;
		Population pop = this.GenerateInitialPopulation();
		Population newpop = null;
		int i=0;
		while(i < nbIteration){
			newpop = this.GenerateNewPopulation(pop);
			pop = this.UpdatePopulation(pop, newpop);
			i++;
		}
		this.optimum = this.getBest(pop);
		return this.optimum;
	}
	public static void main(String[] arg) {
		Memetic memetic = new Memetic("donnees/donnees4.txt");
		Calendar calopt = memetic.GlobalMemetic(50, 10);
		calopt.afficher();
	}
	

}
