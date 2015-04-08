#include <stdio.h>
#include <stdlib.h>

// The complex_t structure which will be used to define complex numbers
typedef struct
{
	double real, imaginary;
} complex_t;

// Prototypes for multiply and divide functions
complex_t multiply(complex_t a, complex_t b);
complex_t* divide(complex_t *a, complex_t *b);

int main (void)
{
	complex_t a, b, product, *quotient;
	
	// User inputs the two complex numbers. For all print functions, "i" is printed at the end to avoid it preceeding the negative sign.
	printf("This program will use complex numbers composed of a real component 'r' and an imaginary component 'bi'.\n");
	printf("Please enter both complex numbers in the form 'r + bi', separated by a comma: ");
	scanf("%lg + %lfi, %lf + %lfi", &a.real, &a.imaginary, &b.real, &b.imaginary);
	
	// Two complex_t structures for the results of the product and quotient of the two complex numbers
	product = multiply(a, b);
	quotient = divide(&a, &b);

	printf("Given the complex numbers: %lg + %lgi & %lg + %lgi,\n", a.real, a.imaginary, b.real, b.imaginary);
	
	// Printing the product result.
	printf("The product of the complex numbers is: %lg + %lgi \n", product.real, product.imaginary);
	
	// Printing the quotient result.
	printf("The quotient of the complex numbers is: %lg + %lgi\n", quotient->real, quotient->imaginary);
	// Now that quotient is used, free up the memory
	free(quotient);
	
	// Since we deallocated the memory, quotient points to nothing, so we will assign it to null
	quotient = NULL;
	
	return 0;
}

// A function which computes and returns the complex product of two complex numbers
complex_t multiply(complex_t a, complex_t b)
{
	complex_t complexProduct;

	// Calculate the real component of the complex product:
	complexProduct.real = (a.real * b.real) - (a.imaginary * b.imaginary);
	
	// Calculate the imaginary component of the complex product:
	complexProduct.imaginary = (a.real * b.imaginary) + (a.imaginary * b.real);
	
	// Returns the complex product
	return complexProduct;
}

// A function which computes and returns a pointer to the complex quotient of two complex numbers
complex_t* divide(complex_t *a, complex_t *b)
{
	complex_t *complexQuotient = malloc(sizeof(complex_t));
	
	// Check to make sure that the malloc succeeded in allocating memory before continuing
	if (complexQuotient)
	{
		double divisor = b->real * b->real + b->imaginary * b->imaginary;
		
		// To avoid dividing by zero, preform a check
		if (divisor > 0)
		{
			// Calculate the real component of the complex quotient:
			complexQuotient->real = (a->real * b->real + a->imaginary * b->imaginary) / divisor;
			
			// Calculate the imaginary component of the complex quotient:
			complexQuotient->imaginary = (b->real * a->imaginary - a->real * b->imaginary) / divisor;
		}
		
		// If the second complex number was zero, division by zero will occur, thus print out a message and exit the program
		else
		{
			printf("Error: Division by zero.\nTerminating...\n");
			exit(EXIT_FAILURE);
		}
	}

	// If the malloc failed, print an error message and exit the program
	else
	{
		printf("Failed to allocate memory block.\nTerminating...\n");
		exit(EXIT_FAILURE);
	}
	
	// Returns the complex quotient pointer
	return complexQuotient;
}
