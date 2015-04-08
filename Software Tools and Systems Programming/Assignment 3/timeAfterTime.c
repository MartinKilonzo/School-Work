#include <stdio.h>

typedef int bool;
#define true 1
#define false 0

int main(void)
{
	bool validInput = false;		// Flag for input correctness
	bool incorrectEntry = false;	// Flag indicating that the user has made a mistake in their input
	int start = 0;					// Input start time
	int startHours = 0;				// Start time's hours
	int startMins = 0;				// Start time's minutes
	int duration = 0; 				// Input duration length
	int durationHours = 0;			// Duration's hours
	int durationMins = 0; 			// Duration's minutes
	int endTime = 0;				// Output end time
	int endHours = 0; 				// End time's hours
	int endMins = 0; 				// End time's minutes
	
	// This loop loops until a valid start time has been entered.
	while (!validInput)
	{
		// This test changes the prompt message to reaffirm that the user has made a mistake in their input.
		if (incorrectEntry) 
			printf("Please enter a correct start time: ");
		else 
			printf("Please enter the start time: ");
		
		scanf("%d", &start);
		
		// Checks to make sure that the start time is not negative.
		if (start < 0)
		{
			printf("Start time cannot be negative. \n");
			incorrectEntry = true;
			continue;
		}
		
		startHours = start / 100;			// Hours begin in the hundreads column.
		startMins = start % 100;			// Minute values end in the tens column.
		
		// Checks to make sure that the start time is less than a day.
		if (startHours > 23) 
		{
			printf("Start time must be less than a day. \n");
			incorrectEntry = true;
			continue;
		}
		
		// Checks to make sure that the start time is in the proper format.
		if (startMins > 59)
		{
			printf("Start time must be in 24-hour format. \n");
			incorrectEntry = true;
			continue;
		}
		
		validInput = true;
	}
	
	// Resetting the flags for the next input:
	validInput = false;
	incorrectEntry = false;
	
	// This loop loops until a valid duration has been entered.
	while (!validInput)
	{
		// This test changes the prompt message to reaffirm that the user has made a mistake in their input.
		if (incorrectEntry) 
			printf("Please enter a correct duration: ");
		else 
			printf("Please enter the duration: ");
		
		scanf("%d", &duration);
		
		durationHours = duration / 100;		// Hours begin in the hundreads column.
		durationMins = duration % 100;		// Minutes end in the tens column.
		
		// Checks to make sure that the duration is in the proper format.
		if (durationMins > 59 || durationMins < -59)
		{
			printf("Duration must be in 24-hour format. \n");
			incorrectEntry = true;
			continue;
		}
		
		validInput = true;
	}
	
	// Calculating the end time:
	
	endMins = startMins + durationMins;			// endMinutes cannot exceed 118 or deceed -118.
	endHours = startHours + durationHours;
	
	
	// As minutes are sexagesimal, the ending hours must be adjusted whenever the ending minutes exceed 60 or deceeds -60.
	
	// For minute values between 59 and 118, hours must be incremented:
	if (endMins > 59)
	{
		endMins %= 60;
		endHours++;
	}
	
	// for minute values less than 0, hours must be decremented.:
	else if (endMins < 0) 
	{
		endMins %= 60;
		
		// C 89/90 does not adjust the result of a modulo to have the same sign as the dividend, thus, it must be done manually:
		if (endMins < 0)
			endMins += 60;
		
		endHours--;
	}
	
	// To make sure we return an hour value that does not exceed a day, the modulo of endHours must be taken:
	endHours %= 24;
	
	// C 89/90 does not adjust the result of a modulo to have the same sign as the dividend, thus, it must be done manually:
	if (endHours < 0)
		endHours += 24;
	
	//Finally, the hours and minutes are combined together to obtain the end time.
	endTime = endHours * 100 + endMins;
	
	printf("The end time would be: %04d \n", endTime);
	
	return 0;
}
