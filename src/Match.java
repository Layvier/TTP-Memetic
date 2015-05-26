
public class Match {
		public int home ;
		public int ext;
		
		public Match(int home, int ext){
			this.home = home;
			this.ext = ext;
		}
		public int getHome(){
			return this.home;
		}
		public int getExt(){
			return this.ext;
		}
		public String toString(){
			return "("+this.getHome()+","+this.getExt()+")";
		}
		public boolean contains(int city){
			if (this.home == city || this.ext == city){
				return true;
			}
			return false;
		}
}
