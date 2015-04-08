
import java.util.Iterator;
import java.util.Stack;

public class Graph 
{
	//*  Attributes  *//
	
	private int n;				// The number of nodes in the graph
	private Node[] nodes;		// The Node array used to store all the nodes in the graph
	private Edge[][] edges;		// The Edge matrix used to store the connections between nodes

	//*  Constructors  *//
	
	/**
	 * The constructor of the class.
	 * Creates a Graph object by initializing a one-dimensional Node array and 
	 * a two-dimensional Edge array of size n and n * n respectively.
	 * The one-dimensional array is used to store the Nodes in the Graph.
	 * The two-dimensional array represents the Edge matrix.
	 * 
	 * @param n						- The number of nodes in the graph
	 */
	public Graph(int n)
	{
		this.n = n;
		this.nodes = new Node[n];
		this.edges = new Edge[n][n];

		for (int i = 0; i < n; i++)
			this.nodes[i] = new Node(i);
	}
	
	//*  Methods  *//
	
	/**
	 * Method which creates an edge between two existing nodes.
	 * 
	 * @param u						- One endpoint of the new edge
	 * @param v						- The other endpoint of the new edge
	 * @param edgeType				- The type of the edge ("free" or "toll");
	 * @throws GraphException		- If neither node exists, throw a new GraphException
	 */
	public void insertEdge(Node u, Node v, String edgeType) throws GraphException
	{
		// If node u does not exist, throw an exception
		if (!doesExist(u))
			throw new GraphException("Node u does not exist!");

		// If node v does not exist, throw an exception
		if (!doesExist(v))
			throw new GraphException("Node v does not exist!");

		// If the edge already exists, throw an exception
		if (edges[u.getName()][v.getName()] != null)
			throw new GraphException("An edge connecting Nodes u and v already exists!");

		// After passing all the above checks, add the edge to one side of the graph
		edges[u.getName()][v.getName()] = new Edge(u,v,edgeType);

		// Then, add the edge to the other side of the graph
		edges[v.getName()][u.getName()] = new Edge(v,u,edgeType);
	}

	/**
	 * Method which returns the node specified.
	 * 
	 * @param name					- The name of the node to return
	 * @return						- The node specified
	 * @throws GraphException		- If the node does not exist, throw a new GraphException
	 */
	public Node getNode(int name) throws GraphException
	{
		Node temp = new Node(name);
		
		// If Node temp does not exist, throw an exception
		if (!doesExist(temp))
			throw new GraphException("Node u does not exist!");
		
		return this.nodes[name];
	}

	/**
	 * Method which returns an iterator list of all the edges stemming from a specified node.
	 * 
	 * @param u						- The node from which the edges originate 
	 * @return						- A list of all the edges connecting to the specified node, or null if there are no edges incident on the specified node
	 * @throws GraphException		- If the node does not exist, throw a new GraphException
	 */
	public Iterator<Edge> incidentEdges(Node u) throws GraphException
	{
		// If node u does not exist, throw an exception
		if (!doesExist(u))
			throw new GraphException("Node u does not exist!");
		
		// Create a stack to store the incident edges
		Stack<Edge> incidentEdges = new Stack<Edge>();

		// Push all the incident edges from the edge matrix onto the stack
		for (int i = 0; i < n; i++)
		{
			Edge incidentEdge = edges[u.getName()][i];

			// Check to make sure that an edge exists before pushing it onto the stack
			if (incidentEdge != null)
				incidentEdges.push(incidentEdge);
		}

		if (!incidentEdges.isEmpty())
			return incidentEdges.iterator();

		else
			return null;

	}

	/**
	 * Method which returns the edge that connects two specified nodes.
	 *  
	 * @param u						- One endpoint of the new edge
	 * @param v						- The other endpoint of the edge
	 * @return						- The edge that connects the two nodes
	 * @throws GraphException		- If either node does not exist, or if there does not exist an edge between the nodes, throw a new GraphException
	 */
	public Edge getEdge(Node u, Node v) throws GraphException
	{
		// If node u does not exist, throw an exception
		if (!doesExist(u))
			throw new GraphException("Node u does not exist!");

		// If node v does not exist, throw an exception
		if (!doesExist(v))
			throw new GraphException("Node v does not exist!");

		//If an edge exists between the two nodes, return it
		if (areAdjacent(u,v))
			return edges[u.getName()][v.getName()];

		// Otherwise throw an exception
		else
			throw new GraphException("An edge between u and v does not exist!");
	}
	
	/**
	 * Method which checks to see if there exists an edge between two nodes.
	 * Should one be present, it returns true, otherwise it returns false.
	 * 
	 * @param u						- One endpoint of the new edge
	 * @param v						- The other endpoint of the edge
	 * @return						- True, should an edge exist that connects the two nodes, false otherwise
	 * @throws GraphException		- If neither node exists, throw a new GraphException
	 */
	public boolean areAdjacent(Node u, Node v) throws GraphException
	{
		// If node u does not exist, throw an exception
		if (!doesExist(u))
			throw new GraphException("Node u does not exist!");

		// If node v does not exist, throw an exception
		if (!doesExist(v))
			throw new GraphException("Node v does not exist!");

		// If an edge exists, then the nodes are said to be adjacent, thus return true
		if (this.edges[u.getName()][v.getName()] != null)
			return true;

		// Otherwise, an edge does not exist between the two nodes, thus return false
		else
			return false;

	}

	/**
	 * Helper method which finds whether or not a node exists in the graph
	 * 
	 * @param u					- The Node to search for
	 * @return					- True if the node exists in the graph, false otherwise
	 */
	private boolean doesExist(Node u)
	{
		// If the node is within the domain of nodes of the graph, return true
		if (u.getName() >= 0 && u.getName() <= this.n - 1)
			return true;
		
		// Otherwise, the node does not exist, so return false
		else
			return false;
	}
}
