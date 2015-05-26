import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cal {
	private int nbcities;
	private int nbdays;
	public Match[][] setMatchs;
	public Map<Match, Integer> meetingTime;//associate a Match to the time it is played
	public List<Match> days[] ;// Array of Match, nbcities/2 per day
	
	public Cal(int nbcities, int nbdays){//creates a calendar null. 
		days = new ArrayList[nbdays];
		this.meetingTime = new HashMap<Match, Integer>();
		this.setMatchs = new Match[nbcities][nbcities];
		for(int i = 0 ; i < nbdays ; i++){
			days[i] = new ArrayList<Match>();
		}
		this.nbdays = nbdays;
		this.nbcities = nbcities;
		
		for(int i = 1; i <= nbcities; i++){
			for(int j=1; j <= nbcities; j++ ){
				if(i!=j){
					Match match = new Match(i, j);
					this.meetingTime.put(match, -1);
					this.setMatchs[i-1][j-1]=match;
					}
				else{
					this.setMatchs[i-1][j-1]=null;
				}
				
			}
		}
	}
	public void crossover(Cal parent1, Cal parent2){// a implémenter
		int k=-1;
		for(int i = 1; i < nbcities; i++){
			for(int j=1; j <= nbcities; j++ ){
				if(i!=j){
					if(k== -1){
						this.addMatch(parent1.getMatch(i, j), parent1.getTimeMatch(i, j));
						this.addMatch(parent1.getOpposite(parent1.getMatch(i, j)), parent1.getTimeMatch(parent1.getOpposite(parent1.getMatch(i, j))));
						k=k*(-1);
					}
					if(k==1){
						this.addMatch(parent2.getMatch(i, j), parent2.getTimeMatch(i, j));
						this.addMatch(parent2.getOpposite(parent2.getMatch(i, j)), parent2.getTimeMatch(parent2.getOpposite(parent2.getMatch(i, j))));
						k=k*(-1);
					}
				}
			}
		}
	}
	public void random(){
		for(int i = 1; i <= nbcities; i++){
			for(int j=1; j <= nbcities; j++ ){
				if(i!=j){
					Match match = this.getMatch(i, j);
					int l =(int)(Math.random()*this.nbdays);
					this.meetingTime.put(match, l);
					this.addMatch(i,j , l);
					}
				
			}
		}
	}
	public boolean allMatchSet(){
		if(this.meetingTime.containsValue(-1)){
			return false;
		}
		return true;
	}
	public void makeValid(){// a implémenter
		this.standardize();// nbcities/2 matchs per day
		this.print();
		//this.allMatchSet();//all different matchs are played
		this.oneMatchPerDay();// only and exactly one match per city per day
		//this.noLazyCity();//each city must not receive 4 matchs in a row, nor go to others cities 4 times in a row.
	}
	/**
	 * Make sure that the calendar have the required amount of matchs each day, and no more.
	 */
	public void standardize(){// a implémenter
		int k = -1;
		Match m1 = null;
		while(!this.isStandardized()){
		for(int i =0; i< this.nbdays; i++){// for each day
			if(this.moreThanExpected(i)){//if more than 3 matchs
				m1 = this.days[i].get(0);
			}
			else{
				if(this.lessThanExpected(i)){
					k = i;
				}
			}
			if(k !=-1 && m1 !=null){
				this.moveMatch(m1, k);
				k = -1;
				m1 = null;
			}
			
		}
		}
	}
	public boolean isStandardized(){
		for(int i =0; i< this.nbdays; i++){
			if(this.days[i].size() != this.nbcities/2){
				return false;
			}
		}
		return true;
	}
	public boolean moreThanExpected(int day){
		return this.days[day].size() > this.nbcities/2;
	}
	public boolean lessThanExpected(int day){
		return this.days[day].size() < this.nbcities/2;
	}
	/**
	 * Make sure each city plays only once each day
	 */
	public boolean oneMatchPerDay(){// a implémenter
		int doublon = -1;
		int missing = -1;
		int c =-1;
		Match m;
		for(int day=0; day< this.nbdays; day++){
			if(this.Doublon(day)){
				doublon = this.getDoublon(day);
				missing = this.getMissing(day);
				m = this.getMatchContaining(doublon, day);
				if(m.getExt() == doublon){
					c = m.getHome();
				}
				else if(m.getHome() == doublon){
					c= m.getExt();
				}
				if(this.getTimeMatch(missing, c) > day){
					this.switchMatch(m, this.getMatch(missing, c));
					System.out.println("switch "+m+" et "+this.getMatch(missing, c) );
				}
				else{
					this.switchMatch( this.getMatch(c, missing), m);
				}
				
			}
			
		}
		return true;
	}
	public boolean noLazyCity(){//a implémenter
		return true;
	}
	/**
	 * return the first city that plays twice this day, -1 if none
	 */
	public int getDoublon(int day){// a implémenter
		int[] nbMatchs = new int[this.nbcities];
		for(int j=0; j <this.nbcities; j++){
			if(nbMatchs[j] > 1){
				return j;
			}
		}
		return -1;
	}
	public int getMissing(int day){// a implémenter
		int[] nbMatchs = new int[this.nbcities];
		for(int j=0; j <this.nbcities; j++){
			if(nbMatchs[j] < 1){
				return j;
			}
		}
		return -1;
	}
	public Match getMatchContaining(int city, int day){
		for(int i =0; i < this.days[day].size(); i++){
			if(this.days[day].get(i).contains(city)){
				return this.days[day].get(i);
			}
		}
		System.err.println("Aucun match trouvé");
		return null;
	}
	public int[] getArrayDay(int day){
		int[] nbMatchs = new int[this.nbcities];
		for(int i = 0; i < this.nbcities; i++){
			nbMatchs[i] = 0;
		}
		for(int i =0; i < this.days[day].size(); i++){
			nbMatchs[this.days[day].get(i).getHome()-1]++;
			nbMatchs[this.days[day].get(i).getExt()-1]++;
		}
		return nbMatchs;
	}
	public boolean Doublon(int day){// a implementer
		int[] nbMatchs = this.getArrayDay(day);
		for(int j=0; j <this.nbcities; j++){
			if(nbMatchs[j] > 1){
				System.out.println("doublon ville"+(j+1)+" jour "+day);
				return false;
				
			}
			if(nbMatchs[j] == 0){
				System.out.println("pas ville"+(j+1)+" jour "+day);
				return false;
				
			}
		}
		return true;
	}
	public int[][] toArray(){//a implementer
		return null;
	}
	/**
	 * return true if the city plays a match the day X.
	 * @param day
	 * @param city
	 * @return
	 */
	public boolean dayContains(int day, int city){
		boolean ans = false;
		for(int i=0; i< this.days[day].size(); i++){
			if(this.days[day].get(i).contains(city)){
				ans = true;
			}
		}
		return ans;
	}
	public void moveMatch(Match m, int newtime){
		int t = this.meetingTime.get(m);
		this.days[t].remove(m);
		this.meetingTime.replace(m, newtime);
		this.days[newtime].add(m);
	}
	public void switchMatch(Match m1, Match m2){
		int t1 = this.meetingTime.get(m1);
		int t2 = this.meetingTime.get(m2);
		this.moveMatch(m1, t2);
		this.moveMatch(m2, t1);
		
	}
	public Match getOpposite(Match m){
		return this.setMatchs[m.getExt()][m.getHome()];
	}
	public Match getMatch(int home, int ext){
		return this.setMatchs[home-1][ext-1];
	}
	public int getTimeMatch(int home, int ext){
		return this.meetingTime.get(this.setMatchs[home][ext]);
	}
	public int getTimeMatch(Match m){
		return this.meetingTime.get(m);
	}
	public void addMatch(int home, int ext, int day){
		Match match = this.setMatchs[home-1][ext-1];
		this.meetingTime.put(match, day);
		this.days[day].add(match);
		System.out.println("ajout du match "+match+"en "+day);
	}
	public void addMatch(Match match, int day){
		this.meetingTime.put(match, day);
		this.days[day].add(match);
		System.out.println("ajout du match "+match+"en "+day);
	}
	/**
	 * Show the calendar in an array
	 */
	public void print(){//a implementer
		for(int i = 0; i < this.nbdays; i++){
			System.out.print("Day "+i);
			for(int j=0; j < this.days[i].size(); j++){
				System.out.print(this.days[i].get(j).toString());
			}
			System.out.println("");
		}
	}
}
