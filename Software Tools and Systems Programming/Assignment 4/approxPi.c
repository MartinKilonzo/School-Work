#include <stdio.h>
#include <stdlib.h>
#include <math.h>

typedef int bool;
#define true 1
#define false 0

int main(void)
{
	bool validInput = false;
	int input;
	int inCircle;
	double x;
	double y;

	while (!validInput)
	{
		printf("Enter the number of interations: ");

		scanf("%d", &input);
		
		if (input > 0)
			validInput = true;
	}

	int i;

	srand(time(NULL));

	for (i = 0; i < input; i++)
	{
		x = (double)rand()/RAND_MAX;
		y = (double)rand()/RAND_MAX;

		if (x * x + y * y < 1)
			inCircle++;
	}

	printf("After %d iterations, pi has been approximated to: %lf\n", input, (double)inCircle * 4 / input);
}
