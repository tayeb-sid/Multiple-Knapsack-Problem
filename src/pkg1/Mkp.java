package pkg1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;


public class Mkp {
	int NbKnapsacks,NbObjects,MinValue;
	ArrayList<Item> objects;
	ArrayList<KnapSack>knapsacks;
	
	public Mkp(int NbKnapsacks,int NbObjects,int MinValue,ArrayList<Item>objects,ArrayList<KnapSack>knapsacks) {
		this.NbKnapsacks=NbKnapsacks;
		this.NbObjects=NbObjects;
		this.MinValue=MinValue;
		this.objects=objects;
		this.knapsacks=knapsacks;
		
		
	}
	//generates a randome instance of the mkp
	public Mkp(int NbKnapsacks,int NbObjects,int MinValue) {
		this.MinValue=MinValue;
		this.NbKnapsacks=NbKnapsacks;
		this.NbObjects=NbObjects;
		this.objects=new ArrayList<Item>();
		this.knapsacks=new ArrayList<KnapSack>();
		Random random = new Random();
		for(int i=0;i<NbKnapsacks;i++) {
			//max weight between 1 and 100
			int randomMaxWeight =random.nextInt(100)+1;
			knapsacks.add(new KnapSack(i+1,randomMaxWeight));
		}
		for(int i=0;i<NbObjects;i++) {
			int randomValue =random.nextInt(100)+1;
			int randomWeight =random.nextInt(50)+1;
			objects.add(new Item(i+1,randomWeight,randomValue));
		}
	}
	public void setMinValue(int value) {
		this.MinValue=value;
	}
	public int getMinValue() {
		return this.MinValue;
	}
	public void setKnapSacks(ArrayList<KnapSack>knapsacks) {
		this.knapsacks=knapsacks;
	}
	public void setObjects(ArrayList<Item>objects) {
		this.objects=objects;
	}
	public ArrayList<Item>getObjects(){
		return this.objects;
	}
	public ArrayList<KnapSack>getKnapsacks(){
		return this.knapsacks;
	}
	public int getNbKnapsacs() {
		return this.NbKnapsacks;
	}
	public int getNbObjects() {
		return this.NbObjects;
	}
	public String toString() {
		StringBuffer s=new  StringBuffer();
		s.append("NbObjects: "+this.NbObjects+" \t"+"NbKnapsacks: "+this.NbKnapsacks+" \t"+"MinValue: "+this.MinValue+" \n");
		s.append("Objects = "+this.objects.toString()+"\n");
		s.append("Knapsacks= "+this.knapsacks.toString()+"\n");
		
		
		return s.toString();
	}
	public boolean Evaluate(State state) {
		int value=0;
		for(int i=0;i<this.NbKnapsacks;i++) {
			int sumWeightsLine=0;
			for(int j=0;j<this.NbObjects;j++) {
				if(state.getMatrix()[i][j]!=0) {
					//sum the weights of the objects in knapsack i
					sumWeightsLine+=this.objects.get(j).getWeight();
					//calculate the total value of the objects in all the sacs
					value+=this.objects.get(j).getValue();
				}
			}
		
			if(sumWeightsLine>this.knapsacks.get(i).getPMAX())return false;
		}
		
		if(value<this.MinValue) return false;
		return true;
	}
	public int  Evaluate2(State state) {
		int value=0;
		for(int i=0;i<this.NbKnapsacks;i++) {
			int sumWeightsLine=0;
			for(int j=0;j<this.NbObjects;j++) {
				if(state.getMatrix()[i][j]!=0) {
					//sum the weights of the objects in knapsack i
					sumWeightsLine+=this.objects.get(j).getWeight();
					//calculate the total value of the objects in all the sacs
					value+=this.objects.get(j).getValue();

					if(sumWeightsLine>this.knapsacks.get(i).getPMAX()) {
						//System.out.println("pos: "+i+j);
						//state.getMatrix()[i][j]=-1;
					}
				}
				if(sumWeightsLine>this.knapsacks.get(i).getPMAX()) {
					return -1;
				}
			}
		
		}
		
		if(value<this.MinValue) return -2;
		return 0;
	}
	public int totalValue(State state) {
		int value=0;
		for(int i=0 ;i<this.NbKnapsacks;i++) {
			for(int j=0 ;j<this.NbObjects;j++) {
				if(state.getMatrix()[i][j]!=0)value+=this.objects.get(j).getValue();
			}
		}
		return value;
	}
	
	public int totalWeight(State state) {
		int weight=0;
		for(int i=0 ;i<this.NbKnapsacks;i++) {
			for(int j=0 ;j<this.NbObjects;j++) {
				if(state.getMatrix()[i][j]!=0)weight+=this.objects.get(j).getWeight();
			}
		}
		return weight;
	}
	public int totalWeight(State state,int numSac) {
		int weight=0;
		for(int j=0;j<this.NbObjects;j++) {
			if(state.getMatrix()[numSac][j]!=0)weight+=this.objects.get(j).getWeight();
		}
		return weight;
	}
	public State DFS(State startNode) {
		ArrayList<State> closed=new ArrayList<State>();
		Stack<State> open = new Stack<>();
		//not really necessary
		ArrayList<Item> objs= new ArrayList<Item>(this.objects);
		ArrayList<KnapSack>sacs=new ArrayList<KnapSack>(this.knapsacks);
		Collections.reverse(objs);
		Collections.reverse(sacs);
		open.push(startNode);
		while(!open.isEmpty()) {
			State currentState=open.pop();
			closed.add(currentState);
			if(this.Evaluate(currentState)) {
				currentState.setNbDeveloppedNodes(closed.size());
				return currentState;
			}
			for(Item object:objs) {
			for(KnapSack sac:sacs) {
					State childState=new State(currentState);
					if(!Tools.objectAlreadyTaken(childState.getMatrix(), (object.getNum()))) {
						childState.insertItemInSac(object.getNum(), sac.getNum());
						open.push(childState);
						}
				}
			
			}

		}
		
		//retourner l'etat initial si ya pas de solution
		startNode.setNbDeveloppedNodes(closed.size());
		return startNode;
	}
	

	
	public State BFS(State startNode) {
		
		ArrayList<State> closed=new ArrayList<State>();
		Queue<State> open = new LinkedList<State>();
		open.add(startNode);

		while(!open.isEmpty()) {
			State currentState=open.poll();	
			closed.add(currentState);
			if(this.Evaluate(currentState)) {
				currentState.setNbDeveloppedNodes(closed.size());
				return currentState;
			}
			
			
			for(Item object:this.objects) {
				for(KnapSack sac:this.knapsacks) {
					State childState=new State(currentState);
					if(!Tools.objectAlreadyTaken(childState.getMatrix(), object.getNum())) {
						childState.insertItemInSac(object.getNum(), sac.getNum());
						open.add(childState);
						}
				}
			}
		}
		
		//retourner l'etat initial si ya pas de solution
		startNode.setNbDeveloppedNodes(closed.size());
		return startNode;
	}
	
	
	
	public State AStar(State startNode) {
		//h poids sac - poids object choisi
		//g la valeur de l object choisi
		
		ArrayList<State> closed=new ArrayList<State>();
		//prioritize children with highest F value
		Queue<State> open = new PriorityQueue<>(Comparator.comparingInt(State::getF).reversed());
		open.offer(startNode);
		while(!open.isEmpty()) {
			State currentState=open.poll();
			closed.add(currentState);
			if(this.Evaluate(currentState)) {
				currentState.setNbDeveloppedNodes(closed.size());
				return currentState;
			}
			for(Item object:this.objects) {
			for(KnapSack sac:this.knapsacks) {
					State childState=new State(currentState);
					if(!Tools.objectAlreadyTaken(childState.getMatrix(), (object.getNum()))) {
						childState.insertItemInSac(object.getNum(), sac.getNum());
						//g= valeur de l'objet choisi;
						childState.setG(object.getValue());
						//h=poids sac - poids object choisi
						childState.setH(sac.getPMAX()-object.getWeight());
						
						open.offer(childState);
						}
				}
			
			}
			
		}
		
		//retourner l'etat initial si ya pas de solution
		startNode.setNbDeveloppedNodes(closed.size());
		return startNode;
	
	}
	

	public ArrayList<Item>getAvailableObjects(State state){
		ArrayList<Item>availableObjects=new ArrayList<Item>(this.objects);
		ArrayList<Item>unavailable=new ArrayList<Item>();
		for(int i=0;i<this.NbKnapsacks;i++) {
			for(int j=0;j<this.NbObjects;j++) {
				if(state.getMatrix()[i][j]==1) unavailable.add(this.objects.get(j));
			}
		}
		availableObjects.removeAll(unavailable);
		Collections.sort(availableObjects,Comparator.comparingDouble(Item::getRatio).reversed());
		return availableObjects;
	}
	public int maxValue() {
		int max=0;
		for(Item i:this.objects)max+=i.getValue();
		return max;
	}
	//FOR GUI 
	 public String[] getObjectNames() {
		 String []arr=new String[this.NbObjects+1];
		 arr[0]=" ";
		 for(int i=1;i<=this.NbObjects;i++) {
			 arr[i]="Object "+this.objects.get(i-1).getNum();
		 }
		 return arr;
	 }
	 public String[][] stringifyObjects(){
		 String [][]arr=new String[this.NbObjects][3];
		 for(int i=0;i<this.NbObjects;i++) {
			 arr[i]=this.objects.get(i).stringify();
		 }
		 return arr;
	 }
	 public String[][] stringifyKnapsacks(){
		 String [][]arr=new String[this.NbKnapsacks][2];
		 for(int i=0;i<this.NbKnapsacks;i++) {
			 arr[i]=this.knapsacks.get(i).stringify();
		 }
		 return arr;
	 }
	 public String[][] stringifySolution(State solution){
		 String [][]arr=new String[this.NbKnapsacks][this.NbObjects+1];
		 
		 for(int i=0;i<this.NbKnapsacks;i++) {
			 arr[i][0]="Knapsack "+this.knapsacks.get(i).getNum();
			for(int j=0;j<this.NbObjects;j++) {
				 if(solution.getMatrix()[i][j]!=0) arr[i][j+1]="X";
				 else arr[i][j+1]=" ";
			 }
			 
		 }
		 
		 return arr;
	 }

	 
	
}
