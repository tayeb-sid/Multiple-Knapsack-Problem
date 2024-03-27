package pkg1;

//import java.io.IOException;

//import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		//launch in console
		int NbKnapsacks=3;
		int NbObjects=5;
		int MinValue=150;
		//add an the objects/knapsacks manually
		/*
		ArrayList<Item>objects =new ArrayList<Item>();
		objects.add(new Item(1,5,25));
		objects.add(new Item(2,15,30));
		ArrayList<KnapSack>knapsacks=new ArrayList<>();
		knapsacks.add(new KnapSack(1,10));
		knapsacks.add(new KnapSack(2,20));
		
		Mkp mkp=new Mkp(NbKnapsacks, NbObjects, MinValue, objects, knapsacks);
		*/
		
		//read instance from a file
		/*try {
		    Mkp mkp = MkpInstanceFileReader.readFromFile("file path here");
		    System.out.println(mkp);
		} catch (IOException e) {
		    e.printStackTrace();
		}*/
		//randome instance
		Mkp mkp=new Mkp(NbKnapsacks, NbObjects, MinValue);
		System.out.println(mkp);
	
		State initialState=new State(NbKnapsacks,NbObjects);
		State sol=null;
		
		System.out.println("*********DFS***********");
		long startTimeDFS= System.currentTimeMillis();	    
		sol=mkp.DFS(initialState);
		long endTimeDFS= System.currentTimeMillis();
	    System.out.println("DFS execution time: " + (endTimeDFS - startTimeDFS) + " milliseconds");

		if(sol==null||sol==initialState) System.out.println("no solution for this value");
		else{
			sol.printMatrix();
			System.out.println("solution: \n"+sol+" Total Value: "+mkp.totalValue(sol));
		}
		
			System.out.println("*********BFS***********");
		long startTimeBFS= System.currentTimeMillis();	    
		sol=mkp.BFS(initialState);
		long endTimeBFS= System.currentTimeMillis();
	    System.out.println("BFS execution time: " + (endTimeBFS - startTimeBFS) + " milliseconds");
		if(sol==null||sol==initialState) System.out.println("no solution for this value");
		else{
			sol.printMatrix();
			System.out.println("solution: \n"+sol+" Total Value: "+mkp.totalValue(sol));
		}
		System.out.println("*********AStar***********");
		long startTimeAStar= System.currentTimeMillis();	    
		sol=mkp.AStar(initialState);
		long endTimeAStar= System.currentTimeMillis();
	    System.out.println("AStar execution time: " + (endTimeAStar - startTimeAStar) + " milliseconds");

		if(sol==null||sol==initialState) System.out.println("no solution for this value");
		else{
			sol.printMatrix();
			System.out.println("solution: \n"+sol+" Total Value: "+mkp.totalValue(sol));
		}
	}

}
