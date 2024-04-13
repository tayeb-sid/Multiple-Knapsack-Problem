package pkg1;

import java.util.Arrays;

public class State {
	int [][] matrix;
	int h,g,nbDeveloppedNodes;
	
	public State() {}
	// create a copy 
    public State(State originalState) {
    	this.h=0;
    	this.g=0;
    	this.nbDeveloppedNodes=0;
        int nbKnapsacks = originalState.matrix.length;
        int nbObjects = originalState.matrix[0].length;
        this.matrix = new int[nbKnapsacks][nbObjects];

        for (int i = 0; i < nbKnapsacks; i++) {
            for (int j = 0; j < nbObjects; j++) {
                this.matrix[i][j] = originalState.matrix[i][j];
            }
        }
    }
	public State(int nbKnapsacks,int nbObjects) {
		this.h=0;
    	this.g=0;
    	this.nbDeveloppedNodes=0;
		this.matrix=new int [nbKnapsacks][nbObjects];
	}
	public State(int[][]matrix) {
		this.matrix=matrix;
	}
	public void insertItemInSac(int item,int sac) {
		this.matrix[sac-1][item-1]=1;
	}
	public int [][]getMatrix(){
		return this.matrix;
		}
	public void setMatrix(int [][] matrix) {
		this.matrix=matrix;
	}
	public void printMatrix() {
	
	for(int[] line:this.matrix) {
		System.out.println(Arrays.toString(line));
	}
	}

	public void printMatrixAStar() {
		System.out.println("H:"+this.getH()+" G:"+this.getG()+" F:"+(this.getH()+this.getG()));
		for(int[] line:this.matrix) {
			System.out.println(Arrays.toString(line));	
		}
	}
	public boolean isEqualTo(State state) {
		return Arrays.deepEquals(this.getMatrix(), state.getMatrix());
	}
	public String toString() {
		//Boolean empty=true;
		StringBuffer s=new  StringBuffer();
		for(int i=0;i<this.matrix.length;i++) {
			for(int j=0;j<this.matrix[0].length;j++) {
				
				if(this.matrix[i][j]!=0) {
					s.append("object "+(j+1)+" in sac "+(i+1)+"      \n");
					//not the initial state
					//empty=false;
				}
			}
		}
	 return s.toString();
	}
	public String matrixToString() {
		StringBuffer s=new  StringBuffer();
		for(int [] line :this.matrix) {
			s.append(Arrays.toString(line)+"\n");
		}
		return s.toString();
	}
	
	
	int getH() {
		return this.h;
	}

	int getG() {
		return this.g;
	}
	void setH(int h) {
		this.h=h;
	}
	void setG(int g) {
		this.g=g;
	}
	public int getF() {
		return (this.h+this.g);
	}
	public void setNbDeveloppedNodes(int n) {
		this.nbDeveloppedNodes=n;
	}
	public int getNbDeveloppedNodes() {
		return this.nbDeveloppedNodes;
	}
}
