/**
 * This class contains the solution for question 7.b.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 3340 Assignment 1 Question 7.b
 */

import java.util.ArrayList;

public class asn1_b 
{
	private ArrayList<BigInt> fibCache = new ArrayList<BigInt>();//(Arrays.asList(new BigInt(0), new BigInt(1)));
	
	
	/**
	 * Creates an object which holds the cache of fibonacci numbers.
	 * This object is initialized with both the base cases.
	 */
	public asn1_b()
	{
		fibCache = new ArrayList<BigInt>();
		fibCache.add(new BigInt(0));
		fibCache.add(new BigInt(1));
	}
	
	/**
	 * Recursive method which produces fibonacci numbers. New values are stored in the fibCache and are retrieved when needed to increase efficiency.
	 * @param n					- The input number
	 * @return					- the fibonacci value of the number
	 */
	public BigInt fibonacci(BigInt n) 
	{
		if (n.compareTo(2) == -1)
			return n;
		
		else
		{
			//If the value has previously been calculated, retrieve it
			if (n.compareTo(fibCache.size()) < 0)
				return fibCache.get(Integer.parseInt(n.toString()));
			
			//Otherwise, calculate the new fibonacci and store it
			BigInt sum = new BigInt(fibonacci(n.subtract("1")).add(fibonacci(n.subtract("2"))).toString());
			
			fibCache.add(sum);

			return sum;
		}
	}	
	
	public static void main(String [] args)
	{
		asn1_b test = new asn1_b();
		
		for (int i = 0; i <= 30; i++)
		{
			if (i == 0)
				System.out.println(1);
			
			else
				System.out.println(test.fibonacci(new BigInt(i * 10)).toString());
		}
	}
	
}