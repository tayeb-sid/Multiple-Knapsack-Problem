package pkg2;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import pkg1.*;
import pkg1.MkpInstanceFileReader.InvalidFileTypeException;
import pkg1.MkpInstanceFileReader.InvalidInstanceException;
public class Main {
	static Mkp mkp = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int NbIndividus=4;
		int crossoverPoint=5;
		double mutationRate=0.3;
		int maxIter=10;
		File file = new File("./Instances/inst 5(4sacs 8objs 300).txt");
		try {
			
			
			
			//read an instance from a file
			mkp = MkpInstanceFileReader.readFromFile(file.getAbsolutePath());
			//mkp.setMinValue(100);
			System.out.println(mkp);
			
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
		
	
		
		
		
		
		
		
		
		
			
		} catch (IOException | InvalidInstanceException | InvalidFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
		
		
		
		
	}

}
