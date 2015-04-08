		AREA NoThe, CODE, READONLY
		
		ENTRY
		
		;Initializaion
		LDRB	r0, EoS							; Storing the value of the null character in r0
		ADR		r1, STRING1						; Storing the address of the string in r1
		ADR		r2, STRING2						; Storing the value of the resultant string in r2
		LDR		r3, THE_						; Storing the value of "the_" in r3 (using its hex encoding)
		
		
		;For the inital check, we skip the first space to check for "the" at the beginning of a sentence
		LDRB	r5, [r1]						; Loading the first character for processing
		CMP		r5, #'t'						; If it is a 't', then there may be an instance of "the"
		BEQ		beg								; In that case, branch to the 'beg'inning label for processing

loop	LDRB	r5, [r1], #1					; Load the character at r1 into r5 for processing
		;Check for end of string
		CMP		r5, r0							; If the string in question is a null character, we have reached the end of the string and can terminate the program
		STRB	r5, [r2], #1					; Regardless of value we store the character
		BEQ		fin								; If a null character has been reached, end the program
		;Regular character handling
		CMP		r5, #' '						; Otherwise, check to see if the current character is a space,
												; indicating a potential instance of "the"
		BNE		loop							; If not (we've already copied the character), repeat the process
												; If it is a space, the program will fall through	
beg		MOV		r6, r1							; Save the memory address in case we need to go back (in the event that "the" is not found
		MOV		r4, #0							; Initialize r4 as our substring which will contain the next four characters of the main string
build	LDRB	r5, [r1], #1					; Store the character at r1 into r5 for processing
		CMP		r5, r0							; Compare the character to the null character to see if we have reached the end of the string
		LDREQ	r3, THENULL						; If we have, the substring needs to be removed if it equals "the\0" (this value is stored using its hex encoding)
		ADD		r4, r5							; Store the character into the substring
		CMP		r4, #0x01000000					; If there are less than 4 bytes:
		LSLLT	r4, #8							; Shift the substring over 1 byte, and
		BLT		build							; Continue building the substring
		;Four characters have been added to the substring
		CMP		r4, r3							; Now that the substring is full, compare it with r3, which contains the 'invalid' substring that needs to be removed
		SUBEQ	r1, #1							; If the string is invalid, move the main string pointer by 1 so that we may consider the space or the null at the end 
		MOVNE	r1, r6							; If the string is valid, restore the initial address of r1 to copy it over

		B		loop							; Continue processing
		
fin		B		fin								; Terminating loop

STRING1 DCB 	"the the man said don't bother to bathe, The go the" ;String1
EoS		DCB 	0x00 							; End of string1

		ALIGN 
											
THE_	DCD		0x74686520						; Hexadecimal encoding of "the "
THENULL	DCD		0x74686500						; Hexadecimal encoding of "the\0"
STRING2 SPACE 	0xFF							; The resultant string
		
		END
			