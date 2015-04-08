
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class Map 
{
	//*  Attributes  *//
	private final int MAX_EDGES = 4;			// Due to the nature of the map, there can be at most four edges attached to each node
	private Graph map;						// The map represented by a graph
	private int money;						// The toll booth budget for the routing process
	private int vertices;					// The number of intersections in the map
	private Node start, end;				// The start and end points of the pathfinding process

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


	public Graph getGraph() throws GraphException
	{
		if (this.map != null)
			return this.map;

		else
			throw new GraphException("The map does not exist!");
	}
	
	public Iterator<Node> findPath() throws GraphException
	{
		LinkedList<Node> path = buildPath(start, end, findNodePath(start,end));
		
		return path.iterator();
	}

	private LinkedList<Node> findNodePath(Node start, Node end) throws GraphException
	{
		LinkedList<Node> queue = new LinkedList<Node>();
		LinkedList<Node> nodePath = new LinkedList<Node>();

		queue.add(start);
		start.setMark(true);
		nodePath.add(null);

		path_loops:
		while (!queue.isEmpty())
		{
			Node u = queue.remove();
			
			Iterator<Edge> incidentEdges = map.incidentEdges(u);				

			while (incidentEdges.hasNext())
			{
				Edge edge = incidentEdges.next();

				Node v = edge.secondEndpoint();

				if (!v.getMark())
				{
					edge.setLabel("shortest");
					queue.add(v);
					v.setMark(true);
					System.out.println("Added node: " + u.getName() + " --> " + v.getName());
					// Add the node to the path if it is not already there
					if (!nodePath.contains(v))
						nodePath.add(v);

					// If we arrived at the end, return the path by quitting the loops
					if (v == end)
						break path_loops;
				}

				else
				{
					if (edge.getLabel().equals(""))
						edge.setLabel("crossed");
				}
			}
		}

		Node pathNode = nodePath.removeLast();
		LinkedList<Node> path = new LinkedList<Node>();
		System.out.print("\n-------------------------\nEnqueing: ");
		while (pathNode != null)
		{
			path.add(pathNode);
			System.out.print(pathNode.getName() + " --> ");
			pathNode = nodePath.removeLast();
		}
		path.add(start);
		System.out.print(start.getName());
		System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		return path;
	}

	private LinkedList<Node> buildPath(Node start, Node end, LinkedList<Node> nodePath) throws GraphException
	{
		LinkedList<Node> path = new LinkedList<Node>();

		Node v = nodePath.remove();
		path.add(v);
		
		while (!nodePath.isEmpty())
		{
			v = nodePath.remove();
			
			Node adjacent = null;
			int numEdges = 0;
			
			for (int i = 0; i < nodePath.size() && numEdges < MAX_EDGES; i ++)
			{
				Node u = nodePath.get(i);
				if (map.areAdjacent(u, v))
				{
					if (numEdges == 0)
						adjacent = u;
					
					else
					{
						if (map.getEdge(u, v).getType().equals("toll") && map.getEdge(adjacent, v).getType().equals("free"))
							adjacent = u;
					}
					
					numEdges++;
				}
			}
			if (v != null)
				System.out.println("Processed node: " + adjacent.getName() + " --> " + v.getName());
			path.add(adjacent);
		}
		
		return path;
	}
}
