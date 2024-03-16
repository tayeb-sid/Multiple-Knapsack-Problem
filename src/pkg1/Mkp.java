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
	public void setKnapSacks(ArrayList<KnapSack>knapsacks) {
		this.knapsacks=knapsacks;
	}
	public void setObjects(ArrayList<Item>objects) {
		this.objects=objects;
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
				System.out.println("number of developped nodes: "+closed.size());
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
		System.out.println("number of developped nodes: "+closed.size());
		return null;
	}
	
	
	public State BFS(State startNode) {
		
		ArrayList<State> closed=new ArrayList<State>();
		Queue<State> open = new LinkedList<State>();
		open.add(startNode);

		while(!open.isEmpty()) {
			State currentState=open.poll();	
			closed.add(currentState);
			if(this.Evaluate(currentState)) {
				System.out.println("number of developped nodes: "+closed.size());
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
		System.out.println("number of developped nodes: "+closed.size());
		return null;
		
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
				System.out.println("number of developped nodes: "+closed.size());
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
		System.out.println("number of developped nodes: "+closed.size());
		return null;
	
	}
	
	
}
