/**
 * Creates a SimpleFunction: A function that parses
 * Strings for directions to compute a stack of integers
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 3
 *
 */

package Dependencies;
import java.util.*;

public class SimpleFunction
{
	//** Attributes **//
	private String name;													//Name given to the SimpleFunction
	private SimpleListADT<String> args = new ArraySimpleList<String>();		//List to hold arguments
	private SimpleListADT<String> steps = new ArraySimpleList<String>();	//List to hold steps
	private final int NOT_FOUND = -1;										//Null/not found value for indexOf method 
	
	//** Constructors **//
	/**
	 * Creates a SimpleFunction object
	 * @param input
	 */
	public SimpleFunction(StringTokenizer input)
	{
		name = input.nextToken();
		
		//Create a holder String and Stack
		String tempString;
		
		//Fill both the argument and steps stacks
		while (input.hasMoreTokens())
		{
			tempString = input.nextToken();
			
			//Once this occurs, all the arguments have been listed and the steps are next
			while (!tempString.equals("{"))
			{
				args.addToRear(tempString);
				tempString = input.nextToken();
			}
			
			//Once this occurs, all the steps have been listed
			if (tempString.equals("{"))
				tempString = input.nextToken();
			
			while (!tempString.equals("}"))
			{
				steps.addToRear(tempString);
				tempString = input.nextToken();
			}
			
			if (tempString.equals("}"))
				break;
		}
	}
	
	//** Methods **//
	
	/**
	 * Applies the operations defined in steps 
	 * to integers in a specific stack
	 * @param stack
	 */
	public void eval(StackADT <Integer> stack)
	{
		//Creates and fills a list with the integers in the stack in specific slots to be recalled later
		SimpleListADT<Integer> vals = new ArraySimpleList<Integer>();
		
		while (!stack.isEmpty())
			vals.addToRear(stack.pop());
		
		Integer obj1, obj2;
		int size = steps.size();
		String s;
		
		//Scans step for an operator, at which point it applies that operation to the top two integers in the stack
		for (int i = 0; i < size; i++)
		{
			s = steps.get(i);
			if (s.equals("+"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 + obj1);
			}

			if (s.equals("-"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 - obj1);
			}

			if (s.equals("*"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 * obj1);
			}
			
			if (s.equals("/"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 / obj1);
			}
			
			/* Recalls the specific integers in the list found through 
			 * Searching for them in the arguments list and pushes them on the stack */
			int index = args.indexOf(s);
			
			if (index != NOT_FOUND)
				stack.push(vals.get(index));
		}
	}
	
	/**
	 * Returns the name of the Simple Function
	 * @return name - the SimpleFunction's name
	 */
	public String toString()
	{
		return name;
	}
	
	/**
	 * Applies the operations defined in steps to integers in a specific stack
	 * This method allows the SimpleFunction to use other SimpleFunctions in their evaluation
	 * @param stack of integers
	 * @param prog
	 */
	public void eval(StackADT<Integer> stack, Program prog)
	{
		//Creates and fills a list with the integers in the stack 
		SimpleListADT<Integer> vals = new ArraySimpleList<Integer>();
		
		while (!stack.isEmpty())
			vals.addToRear(stack.pop());
		
		Integer obj1, obj2;
		int size = steps.size();
		String s;
		
		//Scans step for an operator, at which point it applies that operation to the top two integers in the stack		
		for (int i = 0; i < size; i++)
		{
			s = steps.get(i);
			SimpleFunction temp = null;
			
			if (s.equals("+"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 + obj1);
			}

			if (s.equals("-"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 - obj1);
			}

			if (s.equals("*"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 * obj1);
			}
			
			if (s.equals("/"))
			{
				obj1 = stack.pop();
				obj2 = stack.pop();
				stack.push(obj2 / obj1);
			}
			
			/* Recalls the specific integers in the list found through 
			 * Searching for them in the arguments list and pushes them on the stack 
			 */
			int index = args.indexOf(s);
			
			if (index != NOT_FOUND)
				stack.push(vals.get(index));
			
			/* If none of the above apply: searches program for a SimpleFunction to use
			 * to evaluate the current one. Allows evaluation of a SimpleFunction within 
			 * an evaluation of a SimpleFunction.
			 */
			if (prog.find(s) != null)
				temp = prog.find(s);
			
			if (temp != null)
				temp.eval(stack, prog);
		}	
	}
}
