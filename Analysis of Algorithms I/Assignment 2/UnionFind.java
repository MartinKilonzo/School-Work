/**
 * Class defining a UnionFind object.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 3340 Assignment 2
 */

import java.util.Arrays;

public class UnionFind 
{
	//**  Attributes  **//
	
	private int size;					// The final size of the UnionFind object after finalSets
	private int[] parent;				// The array storing the parents of each value (index)
	private int[] rank;					// The array storing the rank of each parent
	private boolean finalized = false;	// Flag indicating whether the set has been finalized, disabling makeSet and unionSets
	
	
	//**  Constructors  **//
	
	/**
	 * Constructor which creates a UnionFind object of size max. The zeroth element is ignored as required.
	 * @param max		- The size of the UnionFind object.
	 */
	public UnionFind(int max)
	{
		this.parent = new int[max + 1];
		this.rank = new int[max + 1];
	}
	
	
	//**  Methods  **//
	
	/**
	 * Method which invokes the constructor, creating a UnionFind object of size n.
	 * @param n			- The maximum value in the UnionFind
	 * @return			- A UnionFind object with values [1...n]
	 */
	public static UnionFind uandf(int n)
	{
		return new UnionFind(n);
	}
	
	/**
	 * Method which forces the creation of a new set with one member i, overwriting any existing set in its place.
	 * It will expand the UnionFind object if the new set is beyond its domain.
	 * @param i			- The new set
	 */
	public void makeSet(int i)
	{
		if (!finalized)
		{
			// If the index already exists in the array, change it
			if (i < this.parent.length)
			{
				parent[i] = i;
				rank[i] = 0;
			}
			
			// Otherwise, expand the array to fit the new object, expanding the rank array appropriately
			else
			{
				int[] newParent = new int[i + 1];
				int[] newRank = new int[i + 1];
				
				// Copy over existing values
				for (int j = 1; j < parent.length; j++)
				{
					newParent[j] = this.parent[j];
					newRank[j] = this.rank[j];
				}
				
				this.parent = newParent;
				this.rank = newRank;
			}
		}
	}
	
	/**
	 * Method to unite the dynamic sets of i and j into a new set that is the union of the two sets.
	 * @param i			- The first set to unite
	 * @param j			- The second set to unite
	 */
	public void unionSets(int i, int j)
	{
		if (!finalized)
		{
			int set1 = findSet(i);
			int set2 = findSet(j);
			
			// If the values are identical, do nothing
			if (set1 == set2)
				return;
			
			// If the rank of the first set is greater than the second, add the second set to the first set
			if (this.rank[set1] > this.rank[set2])
				this.parent[set2] = set1;
			
			// Otherwise, if the rank of the second set is greater than the first, add the first set to the second set
			else if (this.rank[set1] < this.rank[set2])
				this.parent[set1] = set2;
			
			// Otherwise, they have the same rank and thus the first set is chosen arbitrarily as the root, who's rank is incremented appropriately
			else
			{
				this.parent[set2] = set1;
				this.rank[set1]++;
			}
		}
	}
	
	/**
	 * Method which finds and returns the representative of the set containing i.
	 * @param i			- The set to look for
	 * @return			- The representative of i
	 */
	public int findSet(int i)
	{
		if (!finalized)
		{
			if (parent[i] != i)
				return this.parent[i] = findSet(parent[i]);
			
			else
				return i;
		}
		
		else
			return parent[i];
	}
	
	/**
	 * Method which returns the number of current sets and finalizes the set so that no modifications can be made.
	 * @return			- The number of current sets
	 */
	public int finalSets()
	{
		for (int i = 1; i < parent.length; i++)
		{
			if (parent[i] != 0)
				findSet(i);
		}
		
		int counter = 0;
		
		for (int i = 1; i < parent.length; i++)
		{
			if (parent[i] == i)
			{
				counter++;
				parent[i] = counter;
				
				rank[i] = 1;				
			}
			
			else
				rank[i] = 0;
		}
		
		for (int i = 1; i < parent.length; i ++)
		{
			if (rank[i] == 0 && parent[i] > 0)
				parent[i] = parent[parent[i]];
		}
		
		this.finalized = true;
		this.size = counter;
		
		return counter;
	}
	
	/**
	 * Method which returns the finalzed state of the UnionFind object.
	 * @return			- True if the set has been finalized, false otherwise
	 */
	public boolean isFinal()
	{
		return this.finalized;
	}
	
	/**
	 * Method to return the final size of the UnionFind as the number of current sets.
	 * @return			- The number of current sets in the finalized UnionFind. If the set has not been finalized, return -1
	 */
	public int size()
	{
		if (finalized)
			return this.size;
		
		else return -1;
	}
	
	/**
	 * Method which returns a string representation of the UnionFind object.
	 * @return			- The string representation of the UnionFind object
	 */
	public String toString()
	{
		return "UnionFind\np: " + Arrays.toString(this.parent) + "\nr: " + Arrays.toString(this.rank) + "\n";
	}
	
	/**
	 * Main method used for testing purposes.
	 * @param args		 - To be left as null
	 */
	public static void main(String[] args)
	{
		UnionFind uf = new UnionFind(5);
		for (int i = 1; i <= 5; i++)
			uf.makeSet(i);
	    System.out.println(uf);

	    uf.unionSets(2,3);
	    System.out.println("Union 2, 3");
	    System.out.println(uf);

	    uf.unionSets(2,3);
	    System.out.println("Union 2, 3");
	    System.out.println(uf);

	    uf.unionSets(4,5);
	    System.out.println("Union 4, 5");
	    System.out.println(uf);

	    uf.unionSets(2,1);
	    System.out.println("Union 2,1");
	    System.out.println(uf);

	    uf.unionSets(2,4);
	    System.out.println("Union 2, 4");
	    System.out.println(uf);

	    uf.findSet(5);
	    System.out.println("find 5");
	    System.out.println(uf);
	    
	    uf.makeSet(4);
	    System.out.println("Make set 4");
	    System.out.println(uf);
	    
	    uf.makeSet(10);
	    System.out.println("Make set 10");
	    System.out.println(uf);
	    
	    System.out.println("Populate the list");
	    for (int i = 6; i <= 10; i++)
			uf.makeSet(i);
	    System.out.println(uf);
	    
	    uf.unionSets(8,9);
	    System.out.println("Union 8, 9");
	    System.out.println(uf);
	    
	    uf.unionSets(7,10);
	    System.out.println("Union 7, 10");
	    System.out.println(uf);
	    
	    uf.unionSets(7,8);
	    System.out.println("Union 7, 8");
	    System.out.println(uf);
	    
	    System.out.println("finalize");
	    System.out.println("Count: " + uf.finalSets());
	    System.out.println(uf);
	    
	    uf.makeSet(4);
	    System.out.println("Make set 4 on finalized set");
	    System.out.println(uf);
	    
	    uf.unionSets(4,9);
	    System.out.println("Union 4, 9 on finalized set");
	    System.out.println(uf);
	}
}
