package pkg2;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import pkg1.*;
import pkg1.MkpInstanceFileReader.InvalidFileTypeException;
import pkg1.MkpInstanceFileReader.InvalidInstanceException;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int NbIndividus=5;
		
		File file = new File("./Instances/inst1(3sac 6obj 250).txt");
		System.out.println(file);
		Mkp mkp = null;
		try {
			//read an instance from a file
			mkp = MkpInstanceFileReader.readFromFile(file.getAbsolutePath());
			System.out.println(mkp);
			
			GeneticAlgorithm GA=new GeneticAlgorithm(mkp);
			LinkedList<State>population=GA.generatePopulation(NbIndividus);
			if(population==null)System.out.println("no solution");
			else {
					population.forEach(individual->{
						individual.printMatrix();
						System.out.println(" fitness: "+GA.fitness(individual));
					});
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		} catch (IOException | InvalidInstanceException | InvalidFileTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		
		
		
		
		
		
	}

}
