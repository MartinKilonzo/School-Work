		AREA Palindrome, CODE, READONLY
		
		ENTRY
		
		;Initializaion
		MOV 	r0, #1							; initialize register 0 as the 'return digit'; assume valid palindrome string and attempt to prove wrong
		ADR		r1, STRING						; initialize register 1 as the pointer pointing to the first character of the string
		ADR		r2, STRING						; initialize register 2 as the pointer pointing to the first character of the string. This pointer will be updated to point to the last character of the string
		LDRB	r5, EoS							; initialize register 5 to represent the end of string character
		
		; Moving r2 to the end of the string	
		LDRB	r4, [r2], #1					; initialize register 4 with the first value of r2
		
loop	CMP		r4, r5							; while the pointer is not pointing to the end of the string
		LDRBNE	r4, [r2], #1					; move the pointer over
		BNE		loop
		
		SUB 	r2, r2, #2						; since the pointer is pointing beyond the end of the string (since the test is done after incrementing it
												; re-adjust it
		
		; Comparison tests begin:
same	CMP		r1, r2							; should the pointer r1 pass pointer r2, we have passed the halfway point of the string 
		BGE		done							; and can end the test
		LDRB	r3, [r1], #1					; load the first character to prepare it for testing
		LDRB	r4, [r2], #-1					; load the second character to prepare it for testing
		
		; Check to make sure that the first char is a lowercase letter. If it is uppercase, change it to lower case. If it is not a letter, skip it.
check1	CMP		r3, #'z'						; should the first character 
		CMPLE	r3, #'a'						; be a lowercase letter
		BGE		check2							; skip to the second character, as this character meets the comparison requirements
		; Uppercase check and conversion
		CMP		r3, #'Z'						; if the first character is an uppercase letter, convert it to lowercase
		CMPLE	r3, #'A' 						; 
		ADDGE	r3, r3, #32						; 32 being the conversion factor from uppercase to lowercase
		BGE		check2							; after the conversion, skip to the other char, as this one is now a lowercase character
		; Non-letter check
		LDRB	r3, [r1], #1					; should the char not be a letter, move the pointer over to the next character
		B		check1							; and restart the test
		; Check to make sure that the last char is a lowercase letter. If it is uppercase, change it to lower case. If it is not a letter, skip it.
check2	CMP		r4, #'z'						; should the second character 
		CMPLE	r4, #'a'						; be a lowercase letter
		BGE		compare							; skip to the comparison test, as both characters meet the comparison requirements
		; Uppercase check and conversion
		CMP		r4, #'Z'						; if the first character is an uppercase letter, convert it to lowercase
		CMPLE	r4, #'A'						; 
		ADDGE	r4, r4, #32						; 32 being the conversion factor from uppercase to lowercase
		BGE		compare							; after the conversion, both characters must be lowercase letters, and can be compared
		; Non-letter check
		LDRB	r4, [r2], #-1					; should the char not be a letter, move the pointer over to the next character
		B		check2							; and restart the test
		
		; Once both chars have been deemed lowercase, they are compared
compare	CMP		r3, r4							; compare the lowercase characters of both r3 and r4
		BEQ		same							; should they be the same, continue the tests
		MOVNE	r0, #0							; should they be different lowercase characters, the string is not a palindrome and the test can be concluded
		
done
		
STRING 	DCB 	"He lived as a devil, eh?" 		;string
EoS 	DCB 	0x00 							;end of string

		ALIGN

		END