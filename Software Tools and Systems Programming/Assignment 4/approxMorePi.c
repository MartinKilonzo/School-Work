#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef int bool;
#define true 1
#define false 0
#define MAX 10

int main(void)
{
	bool validInput = false; 		// Flag for input correctness
	int input;						// Number of iterations for the approximation of pi
	int inCircle;					// Number of values of x and y that lie within the circle
	double x;						// The x co-ordinate
	double y;						// The y co-ordinate
	double avgSum, stdDevSum;		// Variables holding the total summation values of the approximations
	double results[MAX];				// An array containing the ten approximations
	
	// Reads input and validates it
	while (!validInput)
	{
		printf("Enter the number of interations: ");

		scanf("%d", &input);
		
		// Checks to make sure that the number of iterations is positive
		if (input > 0)
			validInput = true;
	}
	
	srand(time(NULL));		// Seeding the random function
	
	int j;					// Counter for the outer loop
	int i;					// Counter for the for the inner loop
	
	for (j = 0; j < MAX; j++) 
	{
		inCircle = 0;
		for (i = 0; i < input; i++)
		{
			// Generate random x and y co-ordinates
			x = (double)rand()/RAND_MAX;
			y = (double)rand()/RAND_MAX;
			
			// If the point (x^2,y^2) lies in the circle, increment the counter
			if (x * x + y * y < 1)
				inCircle++;
		}
		results[j] = (double)inCircle * 4 / input;
		printf("%d: %lf\n", j, results[j]);
		avgSum += results[j];
	}
	
	avgSum = avgSum/MAX;
	
	for (i = 0; i < 10; i++)
		stdDevSum += (results[i] - avgSum) * (results[i] - avgSum);
		
	stdDevSum = sqrt(stdDevSum / MAX);
	
	printf("After %d iterations, the average approximation of pi is: %lf, with a standard deviation of %lf. \n", input * 10, avgSum, stdDevSum);
	
	return 0;
}
