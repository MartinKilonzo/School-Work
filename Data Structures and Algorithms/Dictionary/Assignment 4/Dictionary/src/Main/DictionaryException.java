package Main;
/**
 * Class used to throw exceptions when either 
 * the element being inserted already exists or
 * the element being removed does not exist.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 2210 Assignment 4
 */


public class DictionaryException extends Exception 
{
	public DictionaryException(String message)
	{
		super(message);
	}
}
