/**
 * Class to read feeds
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 1
 *
 */
public class Reader
{
	//* Attributes *//
	
	private Feed feed;				//Feed to be interacted with through this class
	private int nextItem;			//Counter that holds the position in the list of the last read entry
	
	//* Constructors *//
	
	public Reader (Feed feed)
	{
		this.feed = feed;
		nextItem = 0;
	}
	
	//* Methods *//
	
	public void find(String pattern)
	{
		feed.find(pattern);
	}

	public void read()
	{
		nextItem = feed.readFrom(nextItem);
	}
}
