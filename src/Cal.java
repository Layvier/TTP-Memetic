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
		this.setMatchs = new Match[nbcities][nbcities];
		for(int i = 0 ; i < nbdays ; i++){
			days[i] = new ArrayList<Match>();
		}
		this.nbdays = nbdays;
		this.nbcities = nbcities;
		
		for(int i = 1; i < nbcities; i++){
			for(int j=1; j <= nbcities; j++ ){
				if(i!=j){
					Match match = new Match(i, j);
					this.setMatchs[i][j]=match;
					this.meetingTime.put(match, -1);
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
					}
					if(k==1){
						this.addMatch(parent2.getMatch(i, j), parent2.getTimeMatch(i, j));
					}
				}
			}
		}
		this.makeValid();
	}
	public void random(){
		for(int i = 1; i < nbcities; i++){
			for(int j=1; j <= nbcities; j++ ){
				if(i!=j){
					Match match = new Match(i, j);
					this.setMatchs[i][j]=match;
					this.meetingTime.put(match, -1);
					this.addMatch(i,j , (int)Math.random()*this.nbdays);
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
		this.allMatchSet();//all different matchs are played
		this.oneMatchPerDay();// only and exactly one match per city per day
		this.noLazyCity();//each city must not receive 4 matchs in a row, nor go to others cities 4 times in a row.
	}
	/**
	 * Make sure that the calendar have the required amount of matchs each day, and no more.
	 */
	public void standardize(){// a implémenter
		int k = -1;
		for(int i =0; i< this.nbdays; i++){// for each day
			if(this.days[i].size() > this.nbcities/2){//if more than 3 matchs
				Match m1 = this.days[i].get(0);
				if(k != -1){
					this.moveMatch(m1, k);
				}
			}
			if(this.days[i].size() < this.nbcities/2){
				k = i;
			}
			
		}
	}
	/**
	 * Make sure each city plays only once each day
	 */
	public boolean oneMatchPerDay(){// a implémenter
		return true;
	}
	public boolean noLazyCity(){//a implémenter
		return true;
	}
	/**
	 * return the first city that plays twice this day, -1 if none
	 */
	public int getDoublon(int day){// a implémenter
		return -1;
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
		return this.setMatchs[home][ext];
	}
	public int getTimeMatch(int home, int ext){
		return this.meetingTime.get(this.setMatchs[home][ext]);
	}
	public int getTimeMatch(Match m){
		return this.meetingTime.get(m);
	}
	public void addMatch(int home, int ext, int day){
		Match match = this.setMatchs[home][ext];
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
			for(int j=0; j < this.days[i].size(); j++){
				System.out.println(this.days[i].get(j).toString());
			}
			System.out.println("");
		}
	}
}
