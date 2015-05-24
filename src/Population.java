import java.util.ArrayList;
import java.util.List;


public class Population {
	public List<Cal> pop;
	
	
	public Population(){
		this.pop= new ArrayList<Cal>();
	}
	public void addCalendrier(Cal cal){
		this.pop.add(cal);
	}
	public int getSize(){
		return pop.size();
	}
	public Cal getCal(int i){
		return pop.get(i);
	}
	
	
}
