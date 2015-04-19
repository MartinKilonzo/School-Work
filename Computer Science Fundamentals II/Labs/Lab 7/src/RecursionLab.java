import java.io.*;

public class RecursionLab
{
    /**
     * Method to print a string in reverse
     * @param inString
     */
    public static void reversePrint (String inString)
    {
    	if (inString.length() > 0)		// if string is not empty
		{
    		System.out.print(inString.charAt(inString.length()-1));
			reversePrint(inString.substring(0, inString.length()-1));
		}
    	if (inString.length() == 0)
    		System.out.println();
    }
    /**
     * Method to return a reversed string
     * @param inString
     * @return
     */
	public static String reverseString (String inString)
    {
    	String result = "";
		if (inString.length() > 0)		// if string is not empty
		{
			result = result + inString.charAt(inString.length()-1) + reverseString(inString.substring(0, inString.length()-1));
		}
		
		return result;
    }
	/**
	 * Method to test for whether or not a string is a palindrome
	 * @param s
	 * @return
	 */
	public static boolean isPalindrome(String s)
	{
		boolean flag = false;
		
		if (s.equals(reverseString(s)))
			flag = true;
		return flag;
	}
    public static void main(String[] args)
    {
        String inString = "abcde";
        String palindrome = "racecar";

		// test reversePrint
        reversePrint(inString);
        System.out.println(reverseString(inString));
        System.out.println(isPalindrome(inString));
        
        System.out.println(isPalindrome(palindrome));
    }
}
