#include <stdio.h>

typedef int bool;
#define true 1
#define false 0

int main(void)
{
	bool validInput = false;		// Flag for input correctness
	bool smallNumber = false;		// Flag for an input that is too small
	int i = 1;						// The factorial factor, n in n! = n * (n-1)!
	double n = 1.0;					// The demoninator of a single element in the Euler approximation formula, ie. 1 + 1/n!...
	double e = 1.0;					// The approximation of Euler's constant
	double fact = 0.0;				// The value of a single element in the Euler approximation formula
	double input = 0.0;				// The input value
	
	// This loop loops until a valid input has been inputed.
	while (!validInput) 
	{
		// If the user entered a number that was not sufficiently small, they are prompted to enter a smaller number. 
		// Otherwise, they are just prompted for a number.
		if (smallNumber)
			printf("Please enter a smaller, positive, decimal number: ");
		else
			printf("Please enter a small, positive, decimal number: ");
		
		scanf("%lf", &input);
		
		// Checks to make sure the input is not negative.
		if (input < 0)
		{
			printf("Number must be positive. \n");
			continue;
		}
		
		// Checks to make sure that the input is sufficiently small.
		if (input > 1) 
		{
			smallNumber = true;
			continue;
		}
		
		validInput = true;
	}
	
	while (true) 
	{
		n *= i;				// The denominator retains (n-1)! and is multiplied by n to give n!
		
		fact = 1/n;
		i++;				// Increment i to prepare it for the next factorial.
		// Checks to see if this element is smaller than the inputed value. If it is, stop the approximation.
		if (fact < input) 
			break;
		
		e += fact;			// Adding the new factorial value to approximate Euler's number.
	}
	
	printf("The decimal approximation of Euler's number to the %lgth (after %d terms) is: %0.15lf. \n", input, i, e);
	
	return 0;
}
