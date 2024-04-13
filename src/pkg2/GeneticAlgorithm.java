package pkg2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

import pkg1.*;
public class GeneticAlgorithm {
	Mkp mkp;
	Comparator<State> fitnessComparator = (s1, s2) -> Integer.compare(this.fitness(s2), this.fitness(s1));

	public GeneticAlgorithm(Mkp mkp) {
		this.mkp=mkp;
	}
	
	public State randomSolution() {
		int nbKnapsacks=mkp.getNbKnapsacs();
		int nbObjects=mkp.getNbObjects();
		State sol;
		Random random=new Random();

		do {
			sol=new State(nbKnapsacks,nbObjects);
			for(int i=0;i<nbObjects;i++) {
				int randomSac=random.nextInt(-1,nbKnapsacks);
				if(randomSac!=-1)sol.getMatrix()[randomSac][i]=1;
			}
		}while(!this.mkp.Evaluate(sol));
		
		return sol;
	}
	public LinkedList<State>generatePopulation(int n){
		LinkedList<State>population=new LinkedList<State>();
		int i=0;
		while( i<n) {
			boolean isDuplicate=false;
			State newSol=randomSolution();
			for(State sol:population) {
				if(sol.isEqualTo(newSol)) {
					isDuplicate=true;
					break;
				}
			}
			if(!isDuplicate) {
				population.add(newSol);
				i++;
			}
		}
		Collections.sort(population,fitnessComparator);
		return population;
	}
	public LinkedList<State>ElitistSelection(LinkedList<State>population){
		Collections.sort(population, fitnessComparator);
		LinkedList<State>parents=new LinkedList<State>();
		parents.add(population.poll());
		parents.add(population.poll());
		return parents;
	}
	public LinkedList<State>RandomSelection(LinkedList<State>population){
		Random random=new Random();
		int index1=random.nextInt(0,population.size());
		int index2;
		do {
			index2=random.nextInt(0,population.size());
		}while(index1==index2);
		LinkedList<State>parents=new LinkedList<State>();
		parents.add(population.poll());
		parents.add(population.poll());
		return parents;
	}
	public int fitness(State solution) {
		return this.mkp.totalValue(solution);
	}

	public ArrayList<State>SinglePointCrossover(State parent1,State parent2,int CrossoverPoint){
		ArrayList<State>children = new ArrayList<State>();
		int nbObj=this.mkp.getNbObjects();
		int nbSacs=this.mkp.getNbKnapsacs();

		State child1=new State(nbSacs,nbObj);
		State child2=new State(nbSacs,nbObj);
		
		
		for(int sac_i=0;sac_i<nbSacs;sac_i++) {
			int sacCapacity=mkp.getKnapsacks().get(sac_i).getPMAX();
			
			//child1 = first part of parent1 + second part of parent2
			//child1 part1
			System.arraycopy(parent1.getMatrix()[sac_i], 0, child1.getMatrix()[sac_i], 0, CrossoverPoint);
			//make sure we don't violate the weight constraint 
			//weight of items in sac i
			int child1CurrentWeight=mkp.totalWeight(child1, sac_i);
			//child1 part2
			for(int j=CrossoverPoint;j<nbObj;j++) {
				if(parent2.getMatrix()[sac_i][j]!=0) {
					int currentObjWeight=mkp.getObjects().get(j).getWeight();
					
					if(child1CurrentWeight+currentObjWeight <=sacCapacity) {
					
						child1.getMatrix()[sac_i][j]=1;
						child1CurrentWeight+=currentObjWeight;
					}
					
				
					
				}
			}
			
			//child2 = first part of parent2+ second part of parent1 
			//child2 part1
			System.arraycopy(parent2.getMatrix()[sac_i], 0, child2.getMatrix()[sac_i], 0, CrossoverPoint);
			int child2CurrentWeight=mkp.totalWeight(child2, sac_i);
			//child2 part2
			for(int j=CrossoverPoint;j<nbObj;j++) {
				if(parent1.getMatrix()[sac_i][j]!=0) {
					int currentObjWeight=mkp.getObjects().get(j).getWeight();
					
					if(child2CurrentWeight+currentObjWeight <=sacCapacity) {
						child2.getMatrix()[sac_i][j]=1;
						child2CurrentWeight+=currentObjWeight;
					}
				
					
				}
			}
		}
		children.add(child1);
		children.add(child2);
		return children;
	}
	public ArrayList<State>BiPointCrossover(State parent1,State parent2,int CrossoverPoint1,int CrossoverPoint2){
		ArrayList<State>children = new ArrayList<State>();
		int nbObj=this.mkp.getNbObjects();
		int nbSacs=this.mkp.getNbKnapsacs();

		State child1=new State(nbSacs,nbObj);
		State child2=new State(nbSacs,nbObj);
		
		
		for(int sac_i=0;sac_i<nbSacs;sac_i++) {
			//int sacCapacity=mkp.getKnapsacks().get(sac_i).getPMAX();
			
			//child1 = first part of parent1 + second part of parent2
			//child1 part1
			
			System.arraycopy(parent1.getMatrix()[sac_i], 0, child1.getMatrix()[sac_i], 0, CrossoverPoint1);
			System.arraycopy(parent1.getMatrix()[sac_i],CrossoverPoint2,child1.getMatrix()[sac_i],CrossoverPoint2,nbObj-CrossoverPoint2);
			System.out.println("qsd"+(nbObj-CrossoverPoint2));
			
			//child2 = first part of parent2+ second part of parent1 
			//child2 part1
			System.arraycopy(parent2.getMatrix()[sac_i], 0, child2.getMatrix()[sac_i], 0, CrossoverPoint1);
			System.arraycopy(parent2.getMatrix()[sac_i],CrossoverPoint2,child2.getMatrix()[sac_i],CrossoverPoint2,nbObj-CrossoverPoint2);
		
		}
		children.add(child1);
		children.add(child2);
		System.out.println(children);
		return children;
	}
	public  State Mutation(State child, double mutationRate){
		State mutatedChild =new State(child);
		int nbMutations=(int) Math.round(mutationRate*mkp.getNbObjects());
		Random random=new Random();
			
			for (int sac_i = 0; sac_i < this.mkp.getNbKnapsacs(); sac_i++) {
				for(int i=0;i<nbMutations;i++) {
					int rand_object  = random.nextInt(0,this.mkp.getNbObjects());
					int currentSacWeight=mkp.totalWeight(mutatedChild, sac_i);
					int currentSacCapacity=mkp.getKnapsacks().get(sac_i).getPMAX();
					int currentObjectWeight=mkp.getObjects().get(rand_object).getWeight();
					boolean weightConstraintViolated=(currentSacWeight+currentObjectWeight>currentSacCapacity);
					//1 -->0 or  0-->1 if the object isn't in another sac
					if(mutatedChild.getMatrix()[sac_i][rand_object]==1 ||(mutatedChild.getMatrix()[sac_i][rand_object]==0&&!Tools.objectAlreadyTaken(mutatedChild.getMatrix(), rand_object+1)&&!weightConstraintViolated)){						
						//flip the value
						mutatedChild.getMatrix()[sac_i][rand_object]=1-mutatedChild.getMatrix()[sac_i][rand_object];
					}
				}
		    }
		
		return mutatedChild;
	}
	public LinkedList<State> Execute(LinkedList<State>population,int maxIter,int crossoverPoint1,int crossoverPoint2,double mutationRate){
		LinkedList<State>finalPopulation=new LinkedList<State>();
		finalPopulation.addAll(population);
		for(int i=0;i<maxIter;i++) {
			LinkedList<State>parents=ElitistSelection(finalPopulation);
			State p1= parents.poll();
			State p2= parents.poll();
			ArrayList<State>children;
			if(crossoverPoint2==-1)children=SinglePointCrossover(p1, p2, crossoverPoint1);
			else children=BiPointCrossover(p1, p2, crossoverPoint1,crossoverPoint2);
			ArrayList<State>mutatedChildren=new ArrayList<State>();
			mutatedChildren.add(Mutation(children.get(0), mutationRate));
			mutatedChildren.add(Mutation(children.get(1), mutationRate));
			finalPopulation.clear();
			finalPopulation.addAll(children);
			finalPopulation.addAll(mutatedChildren);
		
		
		}
		return finalPopulation;
		//Collections.sort(population,fitnessComparator);
	}
	State getBestSol(LinkedList<State>population) {
		Collections.sort(population,fitnessComparator);
		return population.poll();
	}
	
	 public String[][] stringifyPopulation(LinkedList<State> population){
		 String [][]arr=new String[population.size()][2];
		 
		 for(int i=0;i<population.size();i++) {
			 arr[i][0]=population.get(i).toString();
			 arr[i][1]=String.valueOf(fitness(population.get(i)));
			 
		 }
		 
		 return arr;
	 }
	 public String[][] stringifyPopulation2(LinkedList<State> population){
		 String [][]arr=new String[population.size()][2];
		 
		 for(int i=0;i<population.size();i++) {
			 arr[i][0]=population.get(i).matrixToString();
			 arr[i][1]=String.valueOf(fitness(population.get(i)));
			 
		 }
		 
		 return arr;
	 }

}
