package pkg1;

public abstract class Tools {

	//used to check if an object is already in a sac
	public static  int SumColMatrix(int[][]matrix,int col) {
		int sum=0;
		for(int i=0;i<matrix.length;i++) sum+=matrix[i][col-1];
		
		return sum;
	}
	public static boolean objectAlreadyTaken(int[][]matrix,int col) {
		return SumColMatrix(matrix,col)!=0;
	}

	public static int NbSelectedObjects(State state) {
		int n=0;
		for(int [] line : state.getMatrix()) {
			for(int col :line) {
				n+=col;
			}
		}
		
		return n;
	}


}

