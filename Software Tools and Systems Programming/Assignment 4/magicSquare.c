#include<stdio.h>

typedef int bool;
#define true 1
#define false 0

int main(void)
{
	bool validInput = false;
	int size;


	// Input validation
	while (!validInput)
	{
		printf("Enter the size of the magic square: ");

		scanf("%d", &size);

		if (size > 0 && size < 99 && size %2 != 0)
			validInput = true;
		else
			printf("Invalid size.\n");
	}
		// Declare and initialize the two-dimensional array, filling all slots with 0s
		int square[size][size];

		// Declaring column, row and the value to add
		int col;
		int row;
		int val;

		for (col = 0; col < size; col++)
		{
			for (row = 0; row < size; row++)
				square[col][row] = 0;
		}

		// Reset row and column
		col = 0;
		row = size / 2;
		val = 2;

		// Set the middle value of the first row to 1 to mark the starting position
		square[col][row] = 1;

		while(val < size * size + 1)
		{
			// To move up a row and one column to the right
			col--;
			row++;

			// Make sure the pointers do not exceed the limits of the square
			if (col == -1)
				col = size - 1;
			if (row == size)
				row = 0;

			// If there is no valid value in the slot:
			if (square[col][row] == 0)
			{
				// Inserts the value into the slot
				square[col][row] = val;

				// Increments the value
				val++;
			}

			else
			{
				col += 2; 
				row--;

				// Make sure the pointers do not exceed the limits of the square
				if (col >= size)
					col -= size;

				if (row == -1)
					row = size -1;

				// Inserts the value into the slot
				square[col][row] = val;

				// Increments the value
				val++;
			}
		}

		// Print out the array
		for (col = 0; col < size; col++)
		{
			for (row = 0; row < size; row++)
				printf("\t %d", square[col][row]);
			printf("\n");
		}
}
