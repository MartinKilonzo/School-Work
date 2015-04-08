/**
 * Class for finding the connected components in a binary image and printing them in various ways.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 3340 Assignment 2
 */

package asn_2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class BinaryFind 
{
	//**  Attributes  **//
	
	private int length;				//The horizontal size of the image in characters
	private int height;				//The vertical size of the image in characters
	private int[][] imageArray;		//A 2D binary array that stores the bit representation of the image
	private UnionFind imageUFSet;	//The UnionFind set for the image
	
		
	//**  Constructors  **//
	
	public BinaryFind(String file, int imageLength, int imageHeight) throws IOException
	{
		this.length = imageLength;
		this.height = imageHeight;
		
		this.imageArray = new int[length][height];
		this. imageUFSet = UnionFind.uandf(length * height);
		
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		String line = null;
		
		for (int i = 0; input.ready(); i++)
		{
			line = input.readLine();
			
			// Process the input file and add its values to the imageArray and UnionFind set
			for (int j = 0; j < line.length(); j++)
			{
				if (line.charAt(j) == '+')
				{
					// Record the '+' in both the imageArray and the UnionFind Set
					imageArray[i][j] = 1;
					imageUFSet.makeSet(height * i + j + 1);
					
					// If an opportunity for a connected component arises, union the sets
					if (j > 0 && imageArray[i][j - 1] == 1)
						imageUFSet.unionSets(height * i + j, height * i + j + 1);
						
					if (i > 0 && imageArray[i - 1][j] == 1)
						imageUFSet.unionSets(height * i + j + 1, height * (i - 1) + j + 1);
				}
				// If the character is not '+', nothing needs to be changed as it won't be recorded in the UnionFind Set, and it is already a 0 in the imageArray
			}
		}
		
		this.imageUFSet.finalSets();
		
		input.close();
	}
	
	/**
	 * Method which reproduces the image by iterating through the binary array.
	 */
	public void printBinaryImage()
	{
		for (int i = 0; i < this.height; i++)
		{
			for (int j = 0; j < this.length; j++)
				System.out.print(imageArray[i][j]);
			
			System.out.println();
		}
	}

	public int[] printUFImage()
	{
		int component;
		int[] labelCount = new int[this.imageUFSet.size()];
		
		
		for (int i = 0; i < this.height; i++)
		{
			for (int j = 0; j < this.length; j++)
			{
				component = this.imageUFSet.findSet(this.height * i + j + 1);
				
				if (component > 0)
				{
					labelCount[component - 1]++;
					System.out.print((char) ('a' + component - 1));
				}
				
				else
					System.out.print(' ');
				
			}
			
			System.out.println();
		}
		
		return labelCount;
	}
	
	/**
	 * Method which prints the frequency of each label in ascending order.
	 * @param labelCount
	 * @return 
	 * @return
	 */
	public void printCompList(int[] labelCount)
	{
		
		int[] sortedList = new int[labelCount.length];			// The final sorted list of label counts
		int[] temp = new int[length * height];					// A temporary array used for the sorting process
		int[] labelCountProxy = new int[labelCount.length];		// An duplicate of labelCount, which will be manipulated
		int[] chars = new int[labelCount.length];				// An array that acts as the legend for the list (left column)
		
		// A modified counting-sort used to sort the labels
		for (int i = 0; i < labelCount.length; i++)
		{
			temp[labelCount[i]]++;
		}

		// Clone label count to create a list which we will use to assign character labels to the sorted array
		for (int i = 0; i < labelCount.length; i++)
			labelCountProxy[i] = labelCount[i];
		
		
		for (int i = 0, k = 0; i < temp.length; i++)
		{
			int n = 0;
			for (int j = 0; j < temp[i]; j++, k++)
			{
				sortedList[k] = i;
				
				// Find the first index in labelCount's proxy that contains the number in i (the count of the character at index n + 'a')
				while (labelCountProxy[n] != i)
					n++;
				
				// Assign that character "value" to the character array
				chars[k] = n;
				
				// Remove the value from the proxy so that it may not be returned again
				labelCountProxy[n] = 0;
			}
		}
		
		// Print the sorted list
		for (int i = 0; i < sortedList.length; i++)
		{
			System.out.println((char)('a' + chars[i]) +":\t" + sortedList[i]);
		}
	}
	
	/**
	 * Method which prints the image, excluding any labels that are below the prevalency threshold.
	 * @param labelCount			- The array containing the prevalence of each label
	 */
	public void printUFImage(int[] labelCount, int threshold)
	{
		int component;
		
		for (int i = 0; i < this.height; i++)
		{
			for (int j = 0; j < this.length; j++)
			{
				component = this.imageUFSet.findSet(this.height * i + j + 1);
				
				if (component == 0 || labelCount[component - 1] < threshold)
					System.out.print(' ');
				
				else
					System.out.print((char) ('a' + component - 1));
				
			}
			
			System.out.println();
		}
	}
	
	
	//**  Methods  **//
	
	
	public static void main(String[] args)
	{
		BinaryFind image = null;
		try
		{
			 image = new BinaryFind(args[0], 71, 71);
		}
		catch (IOException e)
		{
			System.out.println("Error reading file.");
			System.exit(1);
		}
		
		System.out.println("Part 1: ");
		image.printBinaryImage();
		
		System.out.println("\nPart 2: ");
		int[] n = image.printUFImage();
		
		System.out.println("\nPart 3: ");
		image.printCompList(n);
		
		System.out.println("\nPart 4: ");
		image.printUFImage(n, 2);
	}
}
