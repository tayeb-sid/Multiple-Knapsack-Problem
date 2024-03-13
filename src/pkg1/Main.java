package pkg1;

import java.util.ArrayList;



public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//just an example
		int NbKnapsacks=3;
		int NbObjects=4;
		int MinValue=100;
		ArrayList<Item>objects =new ArrayList<Item>();
		objects.add(new Item(1,5,25));
		objects.add(new Item(2,15,30));
		ArrayList<KnapSack>knapsacks=new ArrayList<>();
		knapsacks.add(new KnapSack(1,10));
		knapsacks.add(new KnapSack(2,20));
		
		//Mkp mkp=new Mkp(NbKnapsacks, NbObjects, MinValue, objects, knapsacks);
		//randome instance
		Mkp mkp=new Mkp(NbKnapsacks, NbObjects, MinValue);
		System.out.println(mkp);

	
		State initialState=new State(NbKnapsacks,NbObjects);
		System.out.println("*********DFS***********");
		
		State sol=null;
		long startTimeDFS= System.currentTimeMillis();	    
		sol=mkp.DFS(initialState);
		long endTimeDFS= System.currentTimeMillis();
	    System.out.println("DFS execution time: " + (endTimeDFS - startTimeDFS) + " milliseconds");

		if(sol!=null) {
			sol.printMatrix();
			System.out.println("solution: \n"+sol);
		}
		else System.out.println("no solution");
		
		
		System.out.println("*********BFS***********");
		long startTimeBFS= System.currentTimeMillis();	    
		sol=mkp.BFS(initialState);
		long endTimeBFS= System.currentTimeMillis();
	    System.out.println("BFS execution time: " + (endTimeBFS - startTimeBFS) + " milliseconds");
		if(sol!=null) {
			sol.printMatrix();
			System.out.println("solution: \n"+sol);
		}
		else System.out.println("no solution");
		
		
		

		
	}

}