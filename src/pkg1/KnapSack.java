package pkg1;

public class KnapSack {
	int num,PMAX;
	public KnapSack(int num,int PMAX) {
		this.num=num;
		this.PMAX=PMAX;
	}
	public String toString() {
		return "Knapsack"+this.num+" {p:"+this.PMAX+"}";
	}
	public int getPMAX() {
		return this.PMAX;
	}
	
	public int getNum() {
		return this.num;
	}
	public String[]stringify(){
		String[] arr = new String[2];
		arr[0]="Knapsack "+this.num;
		arr[1]=String.valueOf(this.PMAX);
		return arr;
	}
}
	
