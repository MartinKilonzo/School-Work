/**
 * This class contains the solution for question 7.a.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 3340 Assignment 1 Question 7.a
 */

public class asn1_a 
{
	/**
	 * Recursive method which produces the fibonacci sum of a given value.
	 * @param n				- The inputted value
	 * @return				- The fibonacci sum of the given value
	 */
	public static int fibonacci(int n)
	{
		if (n < 2)
			return n;
		else 
			return fibonacci(n-1)+fibonacci(n-2);
	}
	
	public static void main(String [] args)
	{
		for (int i = 0; i <= 7; i++)
			System.out.println(fibonacci(i * 5));
	}
}