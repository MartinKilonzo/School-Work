/**
 * This class defines the edges which are essentially the links between nodes.
 * These links are to be used by the graph to represent road connections between nodes.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 2210 Assignment 5
 */

public class Edge 
{
	//*  Attributes  *//

	private Node u;				// One endpoint of the edge
	private Node v;				// The other endpoint of the edge
	private String type;		// Indicates the status of the road as either "free" or "toll"
	private String label = "";	// The status of the edge ("back" or "discovery")
	
	//*  Constructors  *//
	
	/**
	 * The constructor of the class.
	 * Creates an edge object which is to act as a link between nodes.
	 * The graph will interpret it as a road connection.
	 * 
	 * @param u					- One endpoint of the edge
	 * @param v					- The other endpoint of the edge
	 * @param type				- The status of the road (either "free" or "toll")
	 */
	public Edge(Node u, Node v, String type)
	{
		this.u = u;
		this.v = v;

		if (type.equals("toll"))
			this.type = "toll";

		else if (type.equals("free"))
			this.type = "free";
	}
	
	//*  Methods  *//

	/**
	 * Setter method which sets the label attribute of the edge
	 * 
	 * @param label				- The status of the edge (either "back" or "discovery")
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	/**
	 * Getter method which returns the first endpoint of the edge
	 * 
	 * @return					- The first endpoint of the edge
	 */
	public Node firstEndpoint()
	{
		return this.u;
	}

	/**
	 * Getter method which returns the second endpoint of the edge
	 * 
	 * @return					- The second endpoint of the edge
	 */
	public Node secondEndpoint()
	{
		return this.v;
	}

	/**
	 * Getter method which returns the string representation of the type of toll
	 * 
	 * @return					- Either "free" for a free road or "toll" for a paid road
	 */
	public String getType()
	{
		return this.type;
	}

	/**
	 * Getter method which returns the label of the edge
	 * 
	 * @return					- The status of the edge as either "back" or "discovery"
	 */
	public String getLabel()
	{
		return this.label;
	}
}
