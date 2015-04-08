#include <stdio.h>

typedef int bool;
#define true 1
#define false 0

int main(void)
{
	bool validInput = false;		// Flag for input correctness
	int n = 0;						// Number of payments/withdrawls to make
	int numPayment = 0;				// Total number of payments made
	float balance = 0;				// Current bank balance
	float interest = 0;				// The interest rate on the bank balance
	float payment = 0;				// The withdrawl amount
	float finalPayment = 0;			// The value of the final payment
	
	// This loop loops until a valid input is inputed.
	while (!validInput)
	{
		printf("Please enter (seperated by spaces) the principle amount, interest rate, monthly payment, and the number of monthly payments: ");
		
		scanf("%f %f %f %d", &balance, &interest, &payment, &n);
		
		// Checks to make sure inputs are all positive. Interest rate was ommitted to allow this program to use real interest rates, which can be negative.
		if (balance < 0 || payment < 0 || n < 0)
		{
			printf("Values must be positive. \n");
			continue;
		}
		
		validInput = true;
	}
	
	interest = interest / 1200;		// Interest is converted from an integer to a decimal and applied monthly.
	
	while (n > 0)
	{
		numPayment++;		// Increment the number of payments.
		
		// Applies the interest rate and adjust for the withdrawl of the payment.
		balance = balance * (1 + interest) - payment;
		
		// Outputs a message with "payment" used singularly for the first payment.
		if (numPayment == 1)
			printf("The balance after %d payment of %.2f would be: %.2f. \n", numPayment, payment, balance);
		
		// As long as the bank account has enough money, keep withdrawing.
		else if (balance > 0)
			printf("The balance after %d payments of %.2f would be: %.2f. \n", numPayment, payment, balance);
		
		// Once the bank account has finally run out of money, withdraw the last little bit.
		else 
		{
			finalPayment = payment + balance;
			
			printf("The final payment after %d payments of %.2f would be: %.2f. \n", numPayment, payment, finalPayment);
			break;
		}
		
		n--;			// Decrement n as it represents the number of payments to make.
	}
	
	return 0;
}
