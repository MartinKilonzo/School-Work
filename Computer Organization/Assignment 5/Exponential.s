		AREA Exponential, CODE, READONLY
n    	EQU 	5
x    	EQU		6
		ENTRY
		
Main	ADR		sp, stack				; Define the stack

		LDR		r0, =n					; Initialize r0 as n for use with the function call
		LDR		r1, =x					; Initialize r1 with x for use with the function call
		ADR		r2, result				; Initialize r2 with the address of the result
		STR		r0, [sp, #-4]			; Store the initial n as the initial parameter
		SUB		sp, sp, #4				; Move the stack pointer up to free up space for the result
		
		BL		Exp						; Call to the function
		LDR		r3, [sp]				; Load the final result into r3 to move it into
		STR		r3, [r2]				; The memory address of r2, or the "result" address
		
fin		B		fin						; Terminate the program
;----------------------------------------------------------------------------------
		AREA Exponential, CODE, READONLY
Exp		STMFD	sp!, {r0, r1, r2, fp, lr}		; Push general registers and frame pointer and link register
		MOV		fp, sp							; Set the frame pointer for this call
		SUB		sp, sp, #4						; Create space for y
		LDR		r0, [fp, #24]					; Store the parameter in r0, taken from the stack
		
		;If n == 0
		CMP		r0, #0							; If n is equal to 0
		MOVEQ	r0, #1							; Set r0 as 1, and
		BEQ		return							; Return it
		
		;Is n even or odd?
		AND		r1, r0, #1						; ANDing r0 with 1 yields a 1 or a 0 if r0 (n) is even or odd respectively
		CMP		r1, #1							; If n is even
		BNE		even							; Go to the even processing
												; If it is odd, the code will fall through
		;n is odd
odd		SUB		r0, #1							; Subtract 1 from r0 (n): n - 1
		STR		r0,[sp,#-4]!					; Push the decremented parameter n onto the stack
		SUB		sp, sp, #4						; Move the stack pointer to point to the new parameter, or the top of the stack
		BL		Exp								; Recursive call to the function using the perameters x and n - 1
		LDR		r1, [sp], #8					; Load the result into r1 and advance the stack pointer past the result
		LDR		r2, =x							; Set r2 to equal x for:
		MUL		r0, r1, r2						; r0 = r2 * r1
		B		return
		
		
		;n is even
even	LSR		r0, #1							; Divide n by 2: n = n / 2
		STR		r0,[sp,#-4]!					; Push the newly halved parameter n into the stack
		SUB		sp, sp, #4						; Move the stack pointer to point to the new parameter, or the top of the stack
		BL		Exp								; Recursive call to the function using the parameters x and n / 2
		LDR		r1, [sp], #8					; Load the result into r1 and advance the stack pointer past the result
		MUL		r0, r1, r1						; r0 = y * y = power (x, n/2) * power (x, n/2)
		
return	STR		r0, [fp, #20]					; Store the result in the stack
		MOV		sp, fp							; Collapse all working spaces for this function call
		LDMFD	sp!, {r0, r1, r2, fp, pc}		; Restore working registers; load all registers and return to the caller
;----------------------------------------------------------------------------------
		AREA Exponential, DATA, READONLY
result	DCD		0x00							; The final result
		SPACE	0x100							; The space for the stack
stack	DCD		0x00							; The initial stack position (as a full descending stack)
		ALIGN
		
		END
		