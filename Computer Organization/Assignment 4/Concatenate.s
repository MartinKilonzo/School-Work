		AREA Concatenate, CODE, READONLY
		
		ENTRY
		
		;Initializaion
		LDRB	r0, EoS							; Initialize r0 with the null character, marking the end of a string	
		ADR		r1, STRING1						; Initialize r1 with the address of the frist string to concatenate
		ADR		r2, STRING2						; Initialize r2 with the address of the second string to concatenate
		ADR		r3, STRING3						; Initialize r3 with the address of the concatenated string
		
		;Processing the first string
loop1	LDRB	r4, [r1], #1					; Load the character pointed to by r1 into r4 for processing
		CMP		r0, r4							; Compare the null character with the character in question
		STRBNE	r4, [r3], #1					; If it is not null, store it in the resultant string
		BNE		loop1							; And continue processing
												; If a null has been reached the program will just fall into this step
		;Processing the second string
loop2	LDRB	r4, [r2], #1					; Load the character pointed to by r2 into r4 for processing
		STRB	r4, [r3], #1					; Store the character in the resultant string
		CMP		r0, r4							; Compare the null character with the character in question
		BNE		loop2							; If the character is not a null character, continue processing
												; If the character is a null character the program will fall into the terminating loop
fin		B		fin								; Terminating loop
		
STRING1 DCB 	"This is a test string1" 		; String1
EoS		DCB 	0x00 							; End of string1
STRING2 DCB 	"This is a test string2" 		; String2
EoS2	DCB 	0x00 							; End of string2
STRING3 SPACE 	0xFF

		END
			