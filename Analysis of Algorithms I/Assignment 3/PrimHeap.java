/**
 * This class takes as input a graph and outputs an MST based on Prim's MST algorithm.
 * It extends Heap.java -- the Heap ADT.
 * 
 * @author 	Remmy Martin Kilonzo, 250750759
 * @task	CS 3340 Assignment 3
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PrimHeap extends Heap<Vertex>
{
	int numVertices;				// The number of vertices within the graph
	Vertex[] vertices = null;		// The collection of the vertices
	int[][] edgeMatrix;				// The matrix containing the weight for each edge[u][v]
	
	ArrayList<Vertex> path;			// The path of the MST
	
	Heap<Vertex> heap;				// The min-priority-heap to be used to create the path
	
	/**
	 * Constructor to create the PrimHeap and parse the input file.
	 * @param file				- The file containing the input graph.
	 * @throws IOException		- Throw an exception if the file cannot be read.
	 */
	public PrimHeap (String file) throws IOException
	{
		BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		String line = null;
		
		line = input.readLine();
		
		numVertices = Integer.parseInt(line);
		
		edgeMatrix = new int[numVertices + 1][numVertices + 1];
		
		while(input.ready())
		{
			line = input.readLine();
			
			String[] columns = line.split("\\s+");
			
			int i = Integer.parseInt(columns[0]);
			int j = Integer.parseInt(columns[1]);
			int w = Integer.parseInt(columns[2]);
			
			edgeMatrix[i][j] = w;
			edgeMatrix[j][i] = w;
		}
		
		input.close();
		
		vertices = new Vertex[numVertices];
		vertices[0] = new Vertex(1, 0, 0);
		for (int i = 1; i < numVertices; i ++)
		{
			vertices[i] = (new Vertex(i + 1, 0, Integer.MAX_VALUE));
		}
	}
	
	//**  Methods  **//
	/**
	 * Method to print the edge list from the input file.
	 */
	public void printFile()
	{
		for (int i = 1; i < numVertices + 1; i++)
		{
			System.out.print("Vertex " + i + ":\t");
			for (int j = 1; j < numVertices + 1; j++)
			{
				if (edgeMatrix[i][j] > 0)
				{
					System.out.print("[" + i + ", " + j + ", " + edgeMatrix[i][j] + "]\t");
				}
			}
			System.out.println();
		}
	}
	
	/**
	 * Method which prints the path (should it exist).
	 */
	public void printPath()
	{
		if (path != null)
		{
			System.out.println("Start" + "\t" + "End" + "\t" + "Weight");
			
			for (int i = 0; i < path.size(); i++)
			{
				System.out.println(path.get(i).getParent() + "\t" + path.get(i).getID() + "\t" + edgeMatrix[path.get(i).getParent()][path.get(i).getID()]);
			}
			
			System.out.println(path.get(path.size()-2).getID() + "\t" + path.get(path.size()-1).getID() + "\t" + path.get(path.size()-1).getWeight());
		}
	}
	
	/**
	 * Method which constructs a Minimum Spanning Tree using Prim's algorithm.
	 */
	public void primPath()
	{	
		// Initialize the heap
		heap_ini(vertices, vertices.length);
		
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		
		while(path.size() < numVertices && !heap.isEmpty())
		{
			// Remove and assign the smallest element in the heap
			Vertex vertexA = (Vertex) min_key(heap);
			delete_min(heap);
			
			// Add it to the MST path
			path.add(vertexA);
			
			// Process each adjacent vertex on vertexA
			for (int j = 0; j < numVertices + 1; j++)
			{
				/*
				 *  If the adjacent edge is in the heap, and has a weight greater than its 
				 *  existing weight, update the weight and assign it as the child of vertexA.
				 */
				if (edgeMatrix[vertexA.getID()][j] > 0 && in_heap(heap, j))
				{
					Vertex vertexB = key(heap, j);
					if (vertexB.getWeight() > edgeMatrix[vertexA.getID()][j])
					{
						decrease_key(heap, j, new Vertex (j, vertexA.getID(), edgeMatrix[vertexA.getID()][j]));
					}
				}			
			}
		}
		
		this.path = path;
	}
	
	/**
	 * Method to instantiate the heap.
	 * @param keys			- The collection of Vertexes for the heap.
	 * @param size			- The size of the collection.
	 */
	public void heap_ini(Vertex[] keys, int size)
	{
		heap = new Heap<Vertex>(){};
		
		for (int i = 0; i < size; i++)
		{
			heap.insert(keys[i]);
		}
	}
	
	/**
	 * Method which returns a boolean value based on the 
	 * presence of a Vertex with a specific ID within the heap.
	 * @param hp			- The heap to search through.
	 * @param id			- The ID to search for.
	 * @return				- Returns true if the element exists in the heap, false otherwise.
	 */
	public boolean in_heap(Heap<Vertex> hp, int id)
	{		
		for (int i = 1; i <= hp.size(); i++)
		{
			if (hp.get(i).getID() == id)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Method which returns the minimum key within the heap.
	 * @param hp			- The heap from which the element with the smallest key is to be found.
	 * @return				- The Vertex with the smallest key in the heap.
	 */
	public Vertex min_key(Heap<Vertex> hp)
	{
		return hp.get(getMinIndex());
	}
	
	/**
	 * Helper method which returns the ID of the smallest element within the heap.
	 * @param hp			- The heap from which the element with the smallest key is to be found.
	 * @return				- The ID of the element with the minimum key in the heap.
	 */
	public int min_id(Heap<Vertex> hp)
	{
		return hp.getMinIndex();
	}
	
	/**
	 * Method which returns the Vertex with the specified ID within the heap.
	 * @param hp			- The heap to search through.
	 * @param id			- The ID of the Vertex to search for.
	 * @return				- The Vertex with the specified ID.
	 */
	public Vertex key(Heap<Vertex> hp, int id)
	{
		return hp.get(getKeyIndex(hp, id));
	}
	
	/**
	 * Helper method which returns the index of a specific Key within the heap. 
	 * @param hp			- The heap to search through.
	 * @param id			- The ID of the Vertex to search for.
	 * @return				- The index where the vertex is located.
	 */
	public int getKeyIndex(Heap<Vertex> hp, int id)
	{
		for (int i = 1; i <= hp.size(); i++)
		{
			if (hp.get(i).getID() == id)
			{
				return i;
			}
		}
		
		return 0;
	}
	
	/**
	 * Method which removes the minimum value from the heap.
	 * @param hp			- The heap from which the minimum value is to be removed.
	 */
	public void delete_min(Heap<Vertex> hp)
	{
		hp.removeMin();
	}
	
	/**
	 * Method which decreases the key of a Vertex in the heap.
	 * @param hp			- The heap containing the vertex.
	 * @param id			- The id of the Vertex to be decreased.
	 * @param new_key		- The decreased value.
	 */
	public void decrease_key(Heap<Vertex> hp, int id, Vertex new_key)
	{
		if (in_heap(hp, id))
		{
			hp.decrease(getKeyIndex(hp, id), new_key);
		}
	}
	
	/**
	 * Main method. Runs the program given an input text file where the first line of the input file 
	 * contains an integer indicating the number of vertices of the input graph, each of the remaining 
	 * lines contains a triple 'i, j, w' indicating an edge between vertex i and vertex j with cost w.
	 * @param args				- args[0] should contain the path to the input file.
	 * @throws IOException
	 */
	public static void main(String[] args)
	{
		// Initialize path
		PrimHeap path = null;
		try
		{
			path = new PrimHeap(args[0]);
		} catch (IOException e)
		{
			System.out.println("The input file could not be read.");
		}
		// Print out the edges
		System.out.println(	"\n-----------------------\n"
						+	"Edge List:"
						+ 	"\n-----------------------\n");
		path.printFile();
		
		// Print out the MST
		System.out.println(	"\n-----------------------\n"
						+ 	"Minimum Spanning Tree:"
						+ 	"\n-----------------------\n");
		path.primPath();
		path.printPath();
		
	}
}
