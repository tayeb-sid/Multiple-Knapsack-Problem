package pkg1;

public class KnapSack {
	int num,PMAX;
	public KnapSack(int num,int PMAX) {
		this.num=num;
		this.PMAX=PMAX;
	}
	public String toString() {
		return "S"+this.num+" {p:"+this.PMAX+"}";
	}
	public int getPMAX() {
		return this.PMAX;
	}
	
	public int getNum() {
		return this.num;
	}
}
	