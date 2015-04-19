
public class Exercise1
{

	public static void main(String[] args)
	{
		System.out.println("Examples 1 & 2: The exception is thrown whenever an index is called and is undefined, yet Example2 allows the program to continue if this exception is thrown.");
		System.out.println("Examples 6 & 7: In example 6, the exception is caught in the method. In example 7 it is in the test harness. The execution should not be changed. If debug is changed to false, then the code will never print \"end of main\"");
		System.out.println("Example 8: The exception is caught at the method invocation level as it is first handled there.");
		System.out.println("Example 12: The maximum size of a mathod is the only limit for try/catch methods.");
		System.out.println("Example 9, 10, 11: The checked exception is an input/output exception (I/OException)"
				+ "Example 9 is missing try/catch blocks and the exception thrown is not checked. Example 10 has both a catched exception and a try catch block to handle invalid inputs. Example 11 has a checked exception, but is missing a try/catch block for invalid characters.");
	}
}
