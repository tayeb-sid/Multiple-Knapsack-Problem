package pkg1;

public class Item {
	private int weight,value,num;
	boolean taken=false;
	public Item(int num,int weight,int value){
		this.num=num;
		this.weight=weight;
		this.value=value;
	}
	public int getWeight() {
		return this.weight;
	}
	public int getValue() {
		return this.value;
	}
	public int getNum() {
		return this.num;
	}
	public void setNum(int num) {
		this.num=num;
	}	
	public void setWeight(int weight) {
		this.weight=weight;
	}	
	public void setValue(int value) {
		this.value=value;
	}
	public String toString() {
		return "obj"+this.num+"{w:"+this.weight+" v:"+this.value+"}";
	}
	public String[]stringify(){
		String[] arr = new String[3];
		arr[0]="object "+this.num;
		arr[1]=String.valueOf(this.value);
		arr[2]=String.valueOf(this.weight);
		return arr;
	}
	public double getRatio() {
		double v =Double.valueOf(this.value);
		double w=Double.valueOf(this.weight);
		return v/w;
	}

}
