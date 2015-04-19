import java.io.*;


public class DebuggingExercise {

	public static void main(String[] args)
	{
		int[][] testArray = new int[5][6];
		
		for(int i=0;i<5;i++)
		{
			for(int j=1; j<6; j++)
				testArray[i][j] = (i+1)*j;
		}
	}
	
	
	
}
