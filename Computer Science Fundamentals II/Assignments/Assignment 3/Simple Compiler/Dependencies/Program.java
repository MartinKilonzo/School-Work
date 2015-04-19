/**
 * Creates a list of SimpleFunction objects
 * and allows the use of more advanced computations
 * using SimpleFunctions, including recursive computation
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 3
 *
 */

package Dependencies;

import java.util.StringTokenizer;

public class Program
{
	//** Attributes **//
	private SimpleListADT<SimpleFunction> sfList = new ArraySimpleList<SimpleFunction>();	/* A list to hold the SimpleFunctions. 
																							 * Made an attribute to be accessed by other method
																							 */
	//** Constructors **//
	/**
	 * Creates a Program object using a StringTokenizer
	 * to create a list of SimpleFunction objects
	 * @param st
	 */
	public Program(StringTokenizer st)
	{
		SimpleListADT<SimpleFunction> sfList = new ArraySimpleList<SimpleFunction>();
		
		//Fills the list with new SimpleFunction objects
		while (st.hasMoreTokens())
		{
			sfList.addToRear(new SimpleFunction(st));
		}
		
		this.sfList = sfList;
	}
	
	//** Methods **//
	/**
	 * Searches the Program's SimpleFunction List
	 * for a SimpleFunction matching the specified name
	 * @param name -- the name of the SimpleFunction
	 * @return the SimpleFunction named
	 */
	public SimpleFunction find(String name)
	{
		return sfList.find(name);
	}
}
