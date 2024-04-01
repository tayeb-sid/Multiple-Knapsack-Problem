package pkg2;
import java.util.LinkedList;
import java.util.Random;

import pkg1.*;
public class GeneticAlgorithm {
	Mkp mkp;
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
		for(int i=0;i<n;i++) population.add(randomSolution());
		
		return population;
	}
	
	public int fitness(State solution) {
		return this.mkp.totalValue(solution);
	}
}
