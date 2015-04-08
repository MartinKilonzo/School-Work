#include <stdio.h>

void payMethod(int dollars, int *twenties, int *tens, int *fives, int *toonies, int *loonie);

int main(void)
{
	int dollars;
	int twenties;
	int tens;
	int fives;
	int toonies;
	int loonies;
	
	printf("Enter the payment value: $");
	scanf("%d", &dollars);
	
	payMethod(dollars, &twenties, &tens, &fives, &toonies, &loonies);
	printf("The fewest bills and coins necessary to pay $%d.00 is: %d twenties, %d tens, %d fives, %d toonies, %d loonies.\n", dollars, twenties, tens, fives, toonies, loonies);
	
	return 0;
}

void payMethod(int dollars, int *twenties, int *tens, int *fives, int *toonies, int *loonies)
{
	// How many twenties
	*twenties = dollars / 20;
	
	// Updating the dollar value
	dollars %= 20;
	
	// How many tens
	*tens = dollars / 10;
	
	// Updating the dollar value
	dollars %= 10;
	
	// How many fives
	*fives = dollars / 5;
	
	// Updating the dollar value
	dollars %= 5;
	
	// How many toonies
	*toonies = dollars / 2;
	
	// Updating the dollar value
	dollars %= 2;
	
	// How many loonies
	*loonies = dollars;
}
