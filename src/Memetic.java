
public class Memetic {
	public Population pop;
	
	public Memetic(){
		this.pop = this.GenerateInitialPopulation();
	}
	/**
	 * Initialize a population of calendar. Can be random, but better results can be reached using a general heuristic
	 * @return
	 */
	public Population GenerateInitialPopulation(){
		Population pop = new Population();
		return pop;
	}
	/**
	 * Crossover method : generate descendants population from the parent population.
	 * @param pop
	 * @return
	 */
	public Population GenerateNewPopulation(Population pop){
		Population newpop = new Population();
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
	
	

}
