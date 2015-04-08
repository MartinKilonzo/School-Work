/**
 * This class is used to define nodes that are to be used by the graph to represent 
 * intersections of roads or vertices of edges.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 2210 Assignment 5
 */

public class Node 
{
	//*  Attributes  *//

	private boolean checked;	// When true, indicates that the node is not to be revisited
	private int name;			// The name identity of the node

	//*  Constructors *//

	/**
	 * The constructor for the class: creates a Node object with a specified name
	 * 
	 * @param name		- A value from 0 to n-1, which is to become the object's name
	 */
	public Node(int name)
	{
		this.checked = false;
		this.name = name;
	}
	
	
	//*  Methods  *//

	/**
	 * Setter method used to "mark" an object as being visited
	 * 
	 * @param mark		- True to mark to object as visited, false to mark it as unvisited
	 */
	public void setMark(boolean mark)
	{
		this.checked = mark;
	}
	
	/**
	 * Getter method which returns the "checked" or "marked" status of the node
	 * @return			- The status of the attribute "checked" (True/False)
	 */
	public boolean getMark()
	{
		return this.checked;
	}
	
	/**
	 * Getter method which returns the name of a node
	 * @return			- The integer name of the node
	 */
	public int getName()
	{
		return this.name;
	}
}
