		AREA UPCCode, CODE, READONLY
		
		ENTRY
		
		;Initializaion
		MOV 	r0, #0				; initialize register 0 as the 'return digit'; assume invalid UPC code
		ADR		r1, UPC				; register 1 now points to the UPC code string
		MOV 	r2, #10				; initialize register 2 as the loop counter
		MOV		r3, #3				; initialize register 3 as 3 for multiplication
		MOV		r9, #9				; initialize register 9 as 9 for subtraction
		; Summing UPC digits
Loop	LDRB	r4, [r1], #1		; load every other digit starting with the first into r4 and increment the pointer r1
		SUB		r4, r4, #'0'		; convert the character into a number
		ADD 	r5, r5, r4			; add the number to r5--the summation of the 'odd placed' digits
		LDRB	r4, [r1], #1		; load other digit into r4 and increment the pointer r1
		SUB		r4, r4, #'0'		; convert the character into a number
		ADD 	r6, r6, r4			; add the number to r6--the summation of the 'even placed' digits
		SUBS 	r2, r2, #2			; decrement the counter
		BNE		Loop
		; Summing the eleventh digit
		LDRB	r4, [r1], #1		; load the final, eleventh digit into r4
		SUB		r4, r4, #'0'		; convert the character into a number
		ADD		r5, r5, r4			; add the final digit into r5
		; Storing the check digit
		LDRB	r7, [r1], #1		; save the check digit in r7 for later use
		SUB		r7, r7, #'0'		; convert it into a number
		;
		MUL		r4, r5, r3			; multiply the odd placed summation by 3 and store thr product in r4
		ADD		r4, r4, r6			; add the even placed summation to r4
		SUB		r4, r4, #1
		; Modulo 10
Div		CMP		r4, #10				; at each subtraction , ensure that r4 will remain positive
		SUBGT	r4, r4, #10			; repeatedly subtract 10 to emulate modulo
		BGT		Div
				
Final	SUB		r4, r9, r4			; equivalent subtract the remainder from 9
		; Comparing the remainder with the check digit
		CMP		r4, r7				
		MOVEQ	r0, #1				; if the remainder is the same as the check digit, then the UPC code is valid so set r0 to 1
		; Constants
UPC		DCB		"064200115407"		; UPC string

		END
		