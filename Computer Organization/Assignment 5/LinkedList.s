		AREA LinkedList, CODE, READONLY
Key		EQU		0x12347777			
		ENTRY
		
		;Initialization
		ADR		r0, List					; Initialize r0 as the linked list pointer
		LDR		r1, =Key					; Initialize r1 as the pointer to the key to search for
		LDR		r3, [r0]					; Initialize r3 with the contents of r0 to test if the list is empty:
		
		;Empty List Check
		CMP		r3, #0x00					; Check to see if the list is empty by testing the value at r0 with the terminator
		BEQ		Empty						; If it is empty, end the program, as the key cannot be found
											; Otherwise, the code falls through to continue with the program
		;Scanning	
Scan	LDR		r3, [r0], #4				; Load r3 with the key of the first node in the linked list, and set r0 to point to the address containing the address of the next node
		CMP 	r3, r1						; Compare this value with the key being searched for
		BEQ		Found						; If they are the same, then jump to the found clauses
		LDRNE	r0, [r0]					; Store the address of the next node in r0. If it is null, this will also be stored
		CMPNE	r0, #0x00					; Compare the address of the next node with the terminator
Empty	LDREQ	r2, =0xF0F0F0F0				; If they are equal, set r2 to the "not found" flag, and
		BEQ		fin							; Jump to the end of the program
		BNE		Scan						; If they are not equal, continue with the scan as the end of this list has yet to be reached
		
Found	MOV 	r3, r0						; If the key was found, store the address of the node in r3,
		LDR		r2, =0xFFFFFFFF				; Set r2 to the "found" flag, and
fin		B		fin							; Terminate the program
		
		;Memory
List 	DCD 	0x12341111, Item5
Item2 	DCD 	0x12342222, Item3
Item3 	DCD 	0x12343333, Item4
Item4 	DCD 	0x12344444, Item6
Item5 	DCD 	0x12345555, Item2
Item6 	DCD 	0x12346666, Item7
Item7 	DCD 	0x12347777, 0x00 			; Terminator

		ALIGN
			
		END
	