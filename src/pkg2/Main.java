package pkg2;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;

import pkg1.*;
import pkg1.MkpInstanceFileReader.InvalidFileTypeException;
import pkg1.MkpInstanceFileReader.InvalidInstanceException;
public class Main {
	static Mkp mkp = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File file = new File("./Instances/inst 7.txt");
		try {
			
			
			
			//read an instance from a file
			mkp = MkpInstanceFileReader.readFromFile(file.getAbsolutePath());
			//mkp.setMinValue(100);
			System.out.println(mkp);
			
			/*
			//GENETIC ALGORITHM
			int NbIndividus=4;
			int crossoverPoint=5;
			double mutationRate=0.3;
			int maxIter=10;
			GeneticAlgorithm GA=new GeneticAlgorithm(mkp);
			System.out.println("*********initial population*********");
			LinkedList<State>population=GA.generatePopulation(NbIndividus);	
			population.forEach(individual->{
						individual.printMatrix();
							System.out.println(" fitness: "+GA.fitness(individual));
					
						});
			
			
			for(int i=0;i<maxIter;i++) {
				LinkedList<State>parents=GA.ElitistSelection(population);
				State p1= parents.poll();
				State p2= parents.poll();
				ArrayList<State>children=GA.SinglePointCrossover(p1, p2, crossoverPoint);
				ArrayList<State>mutatedChildren=new ArrayList<State>();
				mutatedChildren.add(GA.Mutation(children.get(0), mutationRate));
				mutatedChildren.add(GA.Mutation(children.get(1), mutationRate));
				population.clear();
				population.addAll(children);
				population.addAll(mutatedChildren);
			
			
			}
			Collections.sort(population,GA.fitnessComparator);
			System.out.println("*********final population*********");
			population.forEach(c->{
				
				c.printMatrix();
				System.out.println("fitness: "+GA.fitness(c));
				System.out.println("weight: "+mkp.totalWeight(c));
				System.out.println(mkp.getAvailableObjects(c));
			});
		*/
	
			
			//BSO
			
			int flip=2;
			int localSearchIterations=5;
			int maxIter=5;
			int nbBees=3;
			int maxChances=5;
			BeeSwarmOptimization bso=new BeeSwarmOptimization(mkp);
			State srefInit=bso.getSref();
			State bestSol = bso.execute(maxIter, flip, nbBees, maxChances, localSearchIterations);
			System.out.println(bso.toString(maxIter,srefInit,bestSol));
		
			
		} catch (IOException | InvalidInstanceException | InvalidFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
		
		
		
		
	}

}
