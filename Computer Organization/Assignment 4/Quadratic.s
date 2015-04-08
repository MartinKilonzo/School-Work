		AREA Quadratic, CODE, READWRITE
		ENTRY
		
		LDR		r0, =3				; Initialize register 0 with 3
		BL		QUAD				; Call to the quadratic function
		MOV 	r1, r0, LSL #1		; Double the result and store it in r1
		B		fin					; The program has finished

QUAD	STMFD	sp!, {r1-r6, lr}	; Save working registers and link register
		
		; Register initialization 
		ADR		r1, VARA			; Initializing r1 with a
		ADR		r2, VARb			; Initializing r2 with b
		ADR		r3, VARC			; Initializing r3 with c
		ADR		r4, VARD			; Initializing r4 with d
		
		;r0 = b * x + c
		MOV		r5, r0				; Copying x into r5 as a holder and for multiplication
		LDR		r6, [r2]			; Copying b into r6 to prepare it for multiplication
		LDR		r0, [r3]			; Copying c into r0 for addition
		MLA		r0, r5, r6, r0		; Multiplying r5 and r6, and adding r0 to yield an r0 = b * x + c
		
		;r0 = a * x^2 + b * x + c
		MUL		r6, r5, r5			; Squaring x and storing the value in r6
		LDR		r5, [r1]			; Copying a into r5
		MLA		r0, r5, r6, r0		; Multipliying r5 and r6, and  adding it to the b * x + c (r0) 
									; to yield an r0 = a * x^2 + b * x + c
		
		;clipping
		LDR		r5, [r4]			; Store the value boundary in r5
		CMP 	r0, r5				; If r0 (a * x^2 + b * x + c) is greater than the boundary
		MOVGT	r0, r5				; Clip it to return the boudary
		
		LDMFD	sp!, {r1-r6}		; Restore working registers
		BX		lr
		
fin		B		fin					; Terminating loop
		
		ALIGN
			
VARA	DCD		-5					; The constant representing a
VARb	DCD		6					; The constant representing b
VARC	DCD		7					; The constant representing c
VARD	DCD		10					; The constant representing d--the upper limit
	
		END
			