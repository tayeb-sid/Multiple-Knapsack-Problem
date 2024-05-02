package pkg2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import pkg1.*;

public class BeeSwarmOptimization {
	ArrayList<State>TabooList,danceTable;
	Mkp mkp;
	State sref;
	Comparator<State> fitnessComparator = (s1, s2) -> Integer.compare(this.fitness(s2), this.fitness(s1));
	public BeeSwarmOptimization(Mkp mkp) {
		this.mkp=mkp;
		TabooList=new ArrayList<State>();
		danceTable=new ArrayList<State>();
		this.sref=this.randomSolution();
		
	}
	public State getSref() {
		return this.sref;
	}
	public ArrayList<State>getTabooList(){
		return this.TabooList;
	}
	public ArrayList<State> getDanceTable() {
		// TODO Auto-generated method stub
		return this.danceTable;
	}
	public boolean inList(ArrayList<State>list,State s) {
		for(State state:list)if(s.isEqualTo(state))return true;
		return false;
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
	public int fitness(State solution) {
		return this.mkp.totalValue(solution);
	}

	public ArrayList<State>DetermineSearchArea(int k,int flip){
		ArrayList<State>searchArea=new ArrayList<State>();
		int n=mkp.getNbKnapsacs();
		int h=0,sizeOfSearchArea=0;
		while(sizeOfSearchArea<k && h<flip) {
			int p=0;
			State s=new State(sref);
			while(flip*p+h<n){
				
				for(int j=0;j<mkp.getNbKnapsacs();j++) {
					//make sure the solution is valid after flipping the values
					if(s.getMatrix()[j][flip*p+h]==0) {
						if(!Tools.objectAlreadyTaken(s.getMatrix(),flip*p+h+1)) {
							s.getMatrix()[j][flip*p+h]=1-s.getMatrix()[j][flip*p+h];
							}
					}
					else s.getMatrix()[j][flip*p+h]=1-s.getMatrix()[j][flip*p+h];
				}
	
				p++;
			}
			//insret the new solution in the search area
			searchArea.add(new State(s));
			sizeOfSearchArea++;
			h++;
		}
		return searchArea;
	}
	
	public  State localSearch(State state, int iterations){
		State solution =new State(state);
		State bestSolution=new State(state);
		
		int nbMutations=(int) Math.round(0.8*mkp.getNbObjects());
		Random random=new Random();
		for(int iteration=0;iteration<iterations;iteration++) {
			
			for (int sac_i = 0; sac_i < this.mkp.getNbKnapsacs(); sac_i++) {
				for(int i=0;i<nbMutations;i++) {
					int rand_object  = random.nextInt(0,this.mkp.getNbObjects());
					int currentSacWeight=mkp.totalWeight(solution, sac_i);
					int currentSacCapacity=mkp.getKnapsacks().get(sac_i).getPMAX();
					int currentObjectWeight=mkp.getObjects().get(rand_object).getWeight();
					boolean weightConstraintViolated=(currentSacWeight+currentObjectWeight>currentSacCapacity);
					//1 -->0 or  0-->1 if the object isn't in another sac
					if(solution.getMatrix()[sac_i][rand_object]==1 ||(solution.getMatrix()[sac_i][rand_object]==0&&!Tools.objectAlreadyTaken(solution.getMatrix(), rand_object+1)&&!weightConstraintViolated)){						
						//flip the value
						solution.getMatrix()[sac_i][rand_object]=1-solution.getMatrix()[sac_i][rand_object];
					}
				}
			}
			if(this.fitness(solution)>this.fitness(bestSolution))bestSolution=new State(solution);
		}
	
		return bestSolution;
	}
	public State SrefSelection(State sref,State bestSol,int maxChances,int nbChances) {
		if(fitness(bestSol)>fitness(sref)) {
			sref=new State(bestSol);
			if(nbChances<maxChances)nbChances=maxChances;
		}
		else {
			nbChances--;
			if(nbChances<0) {
				if(diversity(bestSol)>diversity(sref))sref=new State(bestSol);
				
			}
			
		}
		//else just keep the previous sref
		
		return sref;
	}
	public int diversity(State state) {
		int min=Integer.MAX_VALUE;
		for(State sol:TabooList) {
			int dist = distance(state,sol);
			if(dist<min)min=dist;
		}
		return 0;
	}
	int distance(State sol1,State sol2) {
		int dist=0;
		for(int i=0;i<mkp.getNbKnapsacs();i++) {
			for(int j=0;j<mkp.getNbObjects();j++) {
				if(sol1.getMatrix()[i][j]!=sol2.getMatrix()[i][j])dist++;
			}
		}
		return dist;
	}
	public State execute(int maxIter,int flip,int nbBees,int maxChances, int localSearchIterations) {
		//the intial solution found by the scout bee	
		int nbChances=maxChances;
		for(int i=0;i<maxIter;i++) {
			//insert sref in the tabooList
			if(!inList(TabooList,sref))TabooList.add(sref);
			//optimal solution
			if(fitness(sref)==mkp.maxValue())return sref;
			//determine the search area around sref and assign a search point to each bee
			ArrayList<State>searchPoints=DetermineSearchArea(nbBees,flip);
			for(State point:searchPoints) {
				//get the best solution found by each bee after localy searching its searchPoint
				State beeBestSolution=localSearch(point,localSearchIterations);
				//insert the solution in the dance table
				if(!inList(danceTable,beeBestSolution))danceTable.add(beeBestSolution);
			}
			Collections.sort(danceTable,fitnessComparator);
			State bestSolution=danceTable.get(0);
			//danceTable.clear();
			if(!inList(TabooList,bestSolution)) {
				sref=this.SrefSelection(sref, bestSolution, maxChances, nbChances);
			}
		}
		return sref;
	}
	 
	public String toString(int maxIterations,State srefInit,State bestSol) {
		String s="";
		s+="****INITIAL SREF****\n";
		s+=srefInit.toString()+" fitness: "+fitness(srefInit)+"\n";
		s+="\nAFTER "+maxIterations+" ITERATIONS OF BSO:\n";
		s+="*****TABOO LIST*****\n";
		for(State sol:TabooList)s+=sol.toString()+" fitness: "+fitness(sol)+"\n----------------------\n";
		s+="********************\n";
		s+="\n*****DANCE TABLE****\n";
		for(State sol:danceTable)s+=sol.toString()+" fitness: "+fitness(sol)+"\n----------------------\n";
		s+="********************\n";
		s+="****Best Solution****\n";
		s+=bestSol.toString()+" fitness: "+fitness(bestSol);
	
		return s;
	}

}
