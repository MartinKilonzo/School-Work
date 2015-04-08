
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

public class Map 
{
	//*  Attributes  *//
	private final int MAX_EDGES = 4;		// Due to the nature of the map, there can be at most four edges attached to each node
	private Graph map;						// The map represented by a graph
	private int money;						// The toll booth budget for the routing process
	private int vertices;					// The number of intersections in the map
	private Node start, end;				// The start and end points of the pathfinding process
	private Stack<Node> path;				// The stack representing the path 

	//*  Constructors  *//
	
	/**
	 * This is the constructor of the class. It accepts a file which contains the string representation of a map 
	 * and translates each accepted character into a specific component of the map.
	 * 
	 * @param inputFile				- The location of the file storing the string representation of the map
	 * @throws MapException			- In the event that there is an error with the file, an unknown symbol is encountered
	 * 								  or a GraphException is thrown, a new MapException will be thrown detailing the problem
	 */
	Map(String inputFile) throws MapException
	{
		int width, length;
		try
		{
			BufferedReader inputBuffer = new BufferedReader(new FileReader(inputFile));

			// Discard the first line as it is meaningless to this program
			inputBuffer.readLine();
		
			// The second line is the width, so assign it to the width variable
			width = Integer.parseInt(inputBuffer.readLine());
			
			// The third line is the length, so assign it to the length variable
			length = Integer.parseInt(inputBuffer.readLine());
			
			// The fourth line contains the "money" that the driver has to spend on tolls
			this.money = Integer.parseInt(inputBuffer.readLine());
			
			// Create the graph using the provided length and width values
			this.vertices = length * width;
			this.map = new Graph(this.vertices);
			
			// Iterate through the file and assign character symbols to their proper representations
			for(int row = 0; row < length * 2 - 1; row++)
			{
				String inputLine = inputBuffer.readLine();

				for (int col = 0; col < width * 2 - 1; col++)
				{
					char symbol = inputLine.charAt(col);
					int nodeIndex;

					switch (symbol)
					{
									// Start nodes indicated with an 's'
						case 's':	nodeIndex = (row / 2) * width + col / 2;
									start = map.getNode(nodeIndex);
									break;
									
									// End node indicated with an 'e'
						case 'e':	nodeIndex = (row / 2) * width + col / 2;
									end = map.getNode(nodeIndex);
									break;

									// Intersections indicated with an 'o', however each node has already been initialized with the creation of the Graph map				
						case 'o':	break; // Nothing needs to be done

									// Horizontal toll roads indicated with an 'h' and are represented by edges with the label "toll"
						case 'h':	nodeIndex = (row / 2) * width + col / 2;
									map.insertEdge(map.getNode(nodeIndex), map.getNode(nodeIndex + 1), "toll");
									break;

									// Vertical toll roads indicated with a 'v' and are represented by edges with the label "toll"
						case 'v':	nodeIndex = (row / 2) * width + col / 2;
									map.insertEdge(map.getNode(nodeIndex), map.getNode(nodeIndex + width), "toll");
									break;
									
									// Horizontal free roads indicated with a '-' and are represented by edges with the label "free"
						case '-':	nodeIndex = (row / 2) * width + col / 2;
									map.insertEdge(map.getNode(nodeIndex), map.getNode(nodeIndex + 1), "free");
									break;

									// Vertical free roads indicated with a '|' and are represented by edges with the label "free"
						case '|':	nodeIndex = (row / 2) * width + col / 2;
									map.insertEdge(map.getNode(nodeIndex), map.getNode(nodeIndex + width), "free");
									break;
									
									// Empty spaces represent an absent edge, so nothing is done
						case ' ':	break;	// Nothing needs to be done

									// If the symbol does not match any of the defined symbols, throw an exception
						default:	throw new MapException("Invalid symbol \"" + symbol + "\" encountered.");
					}
				}
			}

			inputBuffer.close();	// Close the BufferedReader
		}
		
		catch(FileNotFoundException e)
		{
			throw new MapException("File \"" + inputFile + "\" cannot be found.");
		}
		
		catch(IOException in)
		{
			throw new MapException("Error with input file.");
		}
		
		catch(GraphException g)
		{
			throw new MapException(g.getMessage());
		}
	}
	
	//*  Methods  *//

	/**
	 * Getter method which returns the graph should one be initialized.
	 * @return						- The graph
	 * @throws GraphException		- Throws an exception in the event that any errors are caught
	 */
	public Graph getGraph() throws GraphException
	{
		if (this.map != null)
			return this.map;

		else
			throw new GraphException("The map does not exist!");
	}
	
	/**
	 * Method which finds a path represented by a sequence of nodes and 
	 * converts it into an iterator for the solve class to interpret and draw.
	 * 
	 * @return						- An iterator which contains the sequence of nodes to follow
	 * @throws GraphException		- Throws an exception in the event that any errors are caught
	 */
	public Iterator<Node> findPath() throws GraphException
	{		
		Stack<Node> path = findNodePath(start,end);
		
		System.out.println("Money : $" + money + ".00");
		
		if (path == null)
			return null;
		
		else
			return path.iterator();
	}

	/**
	 * A helper method which finds and creates a path of nodes in the form of a stack.
	 * 
	 * @param start					- The start point of the path
	 * @param end					- The end point of the path
	 * @return						- A stack containing a sequence of nodes which represents the path
	 * @throws GraphException		- Throws an exception in the event that any errors are caught
	 */
	private Stack<Node> findNodePath(Node start, Node end) throws GraphException
	{
		Stack<Node> toProcess = new Stack<Node>();		// Stack which holds the nodes that need to be processed
		Stack<Node> visited = new Stack<Node>();		// Stack that holds nodes that have been visited in a sequential order. This stack is the return stack.
		
		// Prepare the traversal by pushing the starting point onto the stack
		toProcess.push(start);

		while (!toProcess.isEmpty())
		{
			Node u = toProcess.pop();
			
			// Flag which is true if an addition has been made to the toProcess stack and false otherwise
			Boolean addition = false;
			
			// As the node is being processed, add it to the visited stack
			visited.push(u);
			
			// If the end is next to process, we have completed the path, so return it
			if (u == end)
				return visited;

			Iterator<Edge> incidentEdgeIterator = map.incidentEdges(u);
			
			// Convert the iterator into a linked list
			LinkedList<Edge> incidentEdges = new LinkedList<Edge>();
			
			while (incidentEdgeIterator.hasNext())
				incidentEdges.add(incidentEdgeIterator.next());
			
			// Process each adjacent node
			while (!incidentEdges.isEmpty())
			{
				Edge edge = incidentEdges.remove();

				Node v = edge.secondEndpoint();
				
				// If the node has not been processed yet, add it to the toProcess stack to process it
				if (!v.getMark())
				{
					// If the edge is free, add it to the stack
					if (edge.getType().equals("free"))
					{
						v.setMark(true);
						toProcess.push(v);
						addition = true;
					}
					
					// If the edge costs money, make sure to deduct that money iff we have enough
					else if (edge.getType().equals("toll") && money > 0)
					{
						money--;
						v.setMark(true);
						toProcess.push(v);
						addition = true;
					}
				}
			}
			
			// If no addition was made to the toProcess stack, then the car is at a dead end or will complete a circuit, so we need to back track
			if (!addition)
			{
				while (!visited.isEmpty())
				{
					Node n = visited.pop();
					Node m = toProcess.peek();

					// If the program back-steps over a toll, add the money back
					if (map.getEdge(visited.peek(), n).getType().equals("toll"))
						money++;
					
					// Once there is continuity amongst both stacks, stop back-stepping
					if (map.areAdjacent(n,m))
					{
						visited.push(n);
						break;
					}
				}
			}
		}
		
		// Should no path be found (the toProcess stack is empty before reaching the end node) return null
		return null;
	}
}
