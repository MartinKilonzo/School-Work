/**
 * This class defined the Vertex object, which will hold the data for each vertex within the graph.
 * 
 * @author 	Remmy Martin Kilonzo, 250750759
 * @task	CS 3340 Assignment 3
 */

public class Vertex implements Comparable<Vertex>
{
	private int 
	id, 		// The identifier of the vertex
	parent, 	// The parent of the vertex
	weight;		// The weight of the vertex
	
	public Vertex(int id,int parent, int weight)
	{
		this.setID(id);
		this.setParent(parent);
		this.setWeight(weight);
	}
	
	/**
	 * Getter method to get the ID of the vertex.
	 * @return			- The ID of the vertex.
	 */
	public int getID()
	{
		return id;
	}
	
	/**
	 * Setter method to set the ID of the vertex.
	 * @param id		- The ID to be set.
	 */
	public void setID(int id)
	{
		this.id = id;
	}
	
	/**
	 * Getter method to get the parent of the vertex.
	 * @return			- The parent of the vertex.
	 */
	public int getParent()
	{
		return parent;
	}
	
	/**
	 * Setter method to set the parent of the vertex.
	 * @param parent	- The parent to be set.
	 */
	public void setParent(int parent)
	{
		this.parent = parent;
	}
	
	/**
	 * Getter method to get the weight of the vertex.
	 * @return			- The weight of the vertex.
	 */
	public int getWeight()
	{
		return weight;
	}

	/**
	 * Setter method to set the weight of the vertex.
	 * @param weight	- The weight to be set.
	 */
	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	/**
	 * Override method for Comparable
	 */
	public int compareTo(Vertex vertexB)
	{
		int weightA = this.getWeight();
		int weightB = vertexB.getWeight();
		
		return (weightA > weightB) ? 1 : ((weightA < weightB) ? -1 : 0); 
		
	}
}
