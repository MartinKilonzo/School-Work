/**
 * Java class that creates a feed of news entries
 * to be used by Reader.java and TestFeed.java
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 1
 *
 */
public class Feed
{
	//* Attributes *//
	
	private final int DEFAULT_MAX_SIZE = 10;
	
	private String name;			//Name of the entry
	private String[] list;			//List of news entries
	private int size;				//Size of the feed
	
	//* Constructors *//
	
	/**
	 * Constructor to create an
	 * untitled feed of default size
	 */
	public Feed()
	{
		name = "Untitled";
		list = new String[DEFAULT_MAX_SIZE];
		size = 0;
	}
	
	/**
	 * Constructor to create a 
	 * feed with a specified size
	 * @param name
	 */
	public Feed(String name)
	{
		this.name = name;
		list = new String[DEFAULT_MAX_SIZE];
		this.size = 0;
	}
	
	/**
	 * Constructor to create a feed
	 * with a specified title and size
	 * @param name
	 * @param size
	 */
	public Feed(String name, int size)
	{
		this.name = name;
		list = new String[size];
		this.size = 0;
	}
	
	//* Methods *//
	
	/**
	 * Method to add an item
	 * to the Feed's list (array)
	 * @param item
	 */
	public void add(String item)
	{		
		//Check for space in the array
		if (size == list.length)
		{
			//Create a temporary holder array that is larger than the original
			String[] largerList = new String[list.length + DEFAULT_MAX_SIZE];
			
			//Duplicate values into the holder array
			for (int i = 0; i < list.length; i++)
			{
				largerList[i] = list[i];
			}
			
			//Assign the holder array to the existing one
			list = largerList;
		}
		
		list[size] = item;
		size++;
	}
	
	/**
	 * Method to find a keyword or phrase
	 * in the titles of the news pieces
	 * @param pattern
	 */
	public void find(String pattern)
	{
		int counter = 0;
		for (int i = 0; i < size; i++)
		{
			if (list[i].contains(pattern))
			{
				counter++;
				System.out.println(name + ": " + list[i]);
			}
		}
		
		System.out.println(counter + " results found.");
	}
	
	/**
	 * Method to list the number of entries
	 * and all entry titles in the feed from 
	 * a starting point in the list
	 * @param start
	 * @return number of articles read/position in the feed/list
	 */
	public int readFrom(int start)
	{
		int counter = 0;
		for (int i = start; i < list.length; i++)
		{
			if (list[i] != null)
			{
				System.out.println(list[i]);
				counter++;
			}
		}
		
		return counter;
	}
	
}
