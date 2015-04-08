/**
 * This class contains the solution for 7.c.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @Task CS 3340 Assignment 1 Question 7.c
 */

public class asn1_c 
{
	/**
	 * Iterative method which uses matrices to compute fibonacci values in O(log n) time.
	 * @param n					- The input number, ie the fibonacci number
	 * @return					- The fibonacci value for the fibonacci number
	 */
	private static BigInt fibonacci(int n)
	{
		if (n < 2) 
			return new BigInt(1);
		
		BigInt[][] result = {{new BigInt(1), new BigInt(0)}, {new BigInt(0), new BigInt(1)}}; // Base case
		BigInt[][] fibonacciMatrix = {{new BigInt(1), new BigInt(1)}, {new BigInt(1), new BigInt(0)}};
		
		/* Solving for the nth power of the matrix:
		 * There are two cases, where n is odd and where n is even.
		 * When n is odd, we must multiply the matrix by its previous matrix.
		 * When n is even, we square the matrix.
		 * This can be done in O(log n) time 
		 */
		while (n > 0) 
		{
			//If n is odd, multiply the matrices together
		    if (n % 2 == 1) 
		    {
		    	matrixMultiply(result, fibonacciMatrix);
		    }
		    
		    //While n is even, square the matrices
		    n = n / 2;
		    matrixSquare(fibonacciMatrix);
		}
		
		//Return the fibonacci number
		return result[1][0];
	}
	
	/**
	 * Method used to multiply matrices of BigInts
	 * @param n					- BigInt matrix1, or the matrix of n-1
	 * @param m					- BigInt matrix2, or the current matrix
	 */
	private static void matrixMultiply(BigInt[][] n, BigInt[][] m)
	{
		//Perform matrix multiplication
		BigInt a = (n[0][0].multiply(m[0][0].toString())).add(n[0][1].multiply(m[1][0].toString()));
		BigInt b = (n[0][0].multiply(m[0][1].toString())).add(n[0][1].multiply(m[1][1].toString()));
		BigInt c = (n[1][0].multiply(m[0][0].toString())).add(n[1][1].multiply(m[1][0].toString()));
		BigInt d = (n[1][0].multiply(m[0][1].toString())).add(n[1][1].multiply(m[1][1].toString()));
		
		//Assign the results
		n[0][0] = a;
        n[0][1] = b;
        n[1][0] = c;
        n[1][1] = d;
	}
	
	/**
	 * Method used to square matrices of BigInts
	 * @param n					- BigInt matrix to be squared 
	 */
	private static void matrixSquare(BigInt[][] n)
	{
		//Square the matrix by performing matrix multiplication
		BigInt a = (n[0][0].multiply(n[0][0].toString())).add(n[0][1].multiply(n[1][0].toString()));
		BigInt b = (n[0][0].multiply(n[0][1].toString())).add(n[0][1].multiply(n[1][1].toString()));
		BigInt c = (n[1][0].multiply(n[0][0].toString())).add(n[1][1].multiply(n[0][1].toString()));
		BigInt d = (n[1][0].multiply(n[0][1].toString())).add(n[1][1].multiply(n[1][1].toString()));
		
		//Assign the new values to the matrix
		n[0][0] = a;
        n[0][1] = b;
        n[1][0] = c;
        n[1][1] = d;
	}
	
	public static void main(String[] args)
	{
		for (int i = 0; i <= 30; i++)
		{
			System.out.println(fibonacci(i * 10).toString());
		}
	}
}

