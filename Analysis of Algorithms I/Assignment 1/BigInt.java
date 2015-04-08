/**
 * This is the class for the BigInt object, which is an object that can hold numbers of vast sizes. 
 * Additionally, support for addition and subtraction is included.
 * 
 * @author Remmy Martin Kilonzo, 250750759
 * @task CS 3340 Assignment 1 Question 7.b
 */

public class BigInt 
{
	//**  Attributes  **//
	
	private String value;			// The string which contains the value of the BigInt
	
	
	//**  Constructors  **//
	
	/**
	 * Constructor which creates a BigInt from a String object
	 * @param value			- The String containing the value of the BigInt
	 */
	public BigInt(String value)
	{
		this.value = value;
	}
	
	/**
	 * Constructor which creates a BigInt from an int
	 * @param value			- The int containing the value of the BigInt
	 */
	public BigInt(int value)
	{
		this.value = Integer.toString(value);
	}

	
	//**  Methods  **//
	
	/**
	 * Accessor method to return the number of digits in the BigInt
	 * @return				- The number of digits in the BigInt
	 */
	private int size()
	{
		return this.value.length();
	}
	
	/**
	 * Method which produces the sum of two BigInts
	 * @param addition		- The number to add to the original number
	 * @return				- The sum of both numbers as a BigInt
	 */
	public BigInt add(BigInt addition)
	{
		String result = "";
		
		BigInt number1 = new BigInt(this.value);
		BigInt number2 = new BigInt(addition.value);
		
		// Padding the shorter number
		if (number1.size() > number2.size())
		{	
			int size = number2.size();
			
			for (int i = 0; i < number1.size() - size; i++)
				number2.value = "0" + number2.value;
		}
		
		else
		{	
			int size = number1.size();
			
			for (int i = 0; i < number2.size() - size; i++)
				number1.value = "0" + number1.value;
		}
		
		int carry = 0;
		
		//Add each value in their respective number columns and adjust the carry as needed
		for (int i = 0; i < number1.size(); i++)
		{
			int sum = Character.getNumericValue(number1.value.charAt(number1.size() - i - 1)) 
					+ Character.getNumericValue(number2.value.charAt(number1.size() - i - 1)) + carry;  
			
			carry = sum / 10;
			
			result = Integer.toString(sum % 10) + result;
		}
		
		//If a carry exists, add it to the front of the result
		if (carry > 0)
			result = Integer.toString(carry) + result;
		
		//Remove extra padding
		int i = 0;
		while (i < result.length() && result.charAt(i) == '0')
			i++;
		
		result = result.substring(i);
		
		return new BigInt(result);
	}
	
	/**
	 * Method which produces the sum of two BigInts
	 * @param subtraction	- The number to subtract from the original number
	 * @return				- The difference of both numbers as a BigInt, or null if the value is negative
	 * @throws Exception 
	 */
	public BigInt subtract(String subtraction)
	{
		String result = "";
		BigInt number1 = new BigInt(this.toString());
		BigInt number2 = new BigInt(subtraction);
		
		// Padding the shorter number
		if (number1.size() >= number2.size())
		{	
			int size = number2.size();
			
			for (int i = 0; i < number1.size() - size; i++)
				number2.value = "0" + number2.value;
		}
		
		//If the first number is smaller than the second number, return null as there is no need for support for negative values
		else
		{	
			return null;
		}
		
		boolean carryOccured = false;
		int carry = 0;
		int difference = 0;
		
		//Calculate the difference between numbers of the same magnitude. Adjust the carry as needed.
		for (int i = 0; i < number1.size(); i++)
		{
			//If a carry has occurred, activate the carry
			if (carryOccured)
				carry = -1;
			
			//Otherwise, deactivate the carry
			else
				carry = 0;
				
			int value1 = Character.getNumericValue(number1.value.charAt(number1.size() - i - 1)) + carry;
			int value2 = Character.getNumericValue(number2.value.charAt(number1.size() - i - 1));
			
			//If the first number is greater than the second, there is no need for a carry, so simply calculate the difference between the values
			if (value1 >= value2)
			{
				difference = value1 - value2;
				carryOccured = false;
			}
			
			//Otherwise the first number is less than the second, and we need to indicate that a carry has occurred for the next iteration
			else
			{
				difference = value1 + 10 - value2;
				carryOccured = true;
			}
			
			result = Integer.toString(difference) + result;
		}
		
		//If a carry has persisted, then the value is negative. There is no need for support for negatives, so return null.
		if (carryOccured)
			return null;
		
		return new BigInt(result);
	}
	
	/**
	 * Method to multiply BigInt with another value
	 * @param multiplication		- The multiplicand
	 * @return						- The product of both values
	 */
	public BigInt multiply(String multiplication)
	{
		String result = "";
		
		BigInt number1 = new BigInt(this.toString());
		BigInt number2 = new BigInt(multiplication);
	
		// Padding the shorter number
		if (number1.size() > number2.size())
		{	
			int size = number2.size();
			
			for (int i = 0; i < number1.size() - size; i++)
				number2.value = "0" + number2.value;
		}
		
		else
		{	
			int size = number1.size();
			
			for (int i = 0; i < number2.size() - size; i++)
				number1.value = "0" + number1.value;
		}
		
		int carry = 0;
		BigInt bigIntResult = new BigInt(0);
		
		//Iterate through the second number so that each of each digits multiplies each of the first's
		for (int j = 0; j < number2.size(); j++)
		{
			result = "";
			
			//Multiply each value in the first number by the second number and adjust the carry as needed
			for (int i = 0; i < number1.size(); i++)
			{
				int product = Character.getNumericValue(number1.value.charAt(number1.size() - i - 1)) 
						* Character.getNumericValue(number2.value.charAt(number2.size() - j - 1)) + carry;  
				
				if (i == number1.size() - 1)
				{
					carry = 0;
					result = Integer.toString(product) + result;
				}
				
				else
				{
					carry = product / 10;
					result = Integer.toString(product % 10) + result;
				}
			}
			
			//Add each product the the resultant BigInt
			for (int k = 0; k < j; k++)
			{
				result = result + "0";
			}
			
			bigIntResult = bigIntResult.add(new BigInt(result));
		}
		
		//If a carry exists, add it to the front of the result
		if (carry > 0)
			bigIntResult = new BigInt(Integer.toString(carry) + bigIntResult.toString());
		
		return bigIntResult;
	}
	
	/**
	 * Method which sqaures the BigInt
	 * @return				- The Square of the BigInt
	 */
	public BigInt square()
	{
		return this.multiply(this.toString());
	}
	
	/**
	 * Method which produces the value stored in the BigInt
	 * @return				- The value stored in the BigInt
	 */
	public String toString()
	{
		return this.value;
	}
	
	/**
	 * Compares the integer values of a BigInt and an integer
	 * @param n				- The integer to compare with
	 * @return				- -1 if the number is smaller than n
	 * 						   0 if the number is the same as n
	 * 	  					   1 if the number is the larger as n
	 * @throws Exception 
	 */
	public int compareTo(int n)
	{	
		//If the difference is negative this is less than n, so return -1
		if (this.subtract(Integer.toString(n)) == null)
			return -1;
		
		int result = Integer.parseInt(this.subtract(Integer.toString(n)).value);
		
		// If there is no difference, they are the same number, so return 0
		if (result == 0)
			return 0;
		
		// Otherwise, the number must be larger than n so return 1
		else
			return 1;
	}
	
	/**
	 * Method which determines if the value of the BigInt is even
	 * @return				- True if the number is even, false otherwise
	 */
	public boolean isEven()
	{
		//If the last digit is even, then the number is even, thus return true
		if (Character.getNumericValue(this.toString().charAt(this.size() - 1)) % 2 == 0)
			return true;
		
		//Otherwise the number is not even, so return false
		return false;
	}
	
	public static void main(String[] args)
	{
		BigInt test1 = new BigInt("121");
		BigInt test2 = new BigInt("110");
		
		System.out.println(test1.multiply(test2.toString()));
		if (test1.isEven())
			System.out.println(test1.toString() + " is even!");
		System.out.println(test1.square());
		
		System.out.println(test1.compareTo(110));
	}
}