/**
 * Class to make fractal strings
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 2
 */
import java.util.Arrays;

import Dependencies.LinkedStack;
import Dependencies.StackADT;

public class MakeFractal 
{ 
	private final static int NOT_FOUND=-1;
	private String alphaNumeric;
	private String computedFractal; // the initial string is the axion
	private String initialAxion; // the initial string is the axion
	private Integer index,numSymbols,n,size; 
	private String[] symbols;
	private String[] rules;
	private Integer charsPerLine=60;
	
	// constructor
	public MakeFractal(String[] symbols,Integer numSymbols,String initialAxion,String[] rules,Integer n) 
	{
		this.numSymbols=numSymbols;
		this.symbols=new String[this.numSymbols];
		this.rules=new String[this.numSymbols];
		for(int i=0;i<this.numSymbols;i++) 
		{
		   this.symbols[i]=symbols[i];			//Transfers each unique symbol to the attribute symbols array.
		   this.rules[i]=rules[i];				//Transfers rules to the attribute rules array. Each symbol requires a rule, so there is an equal number of both symbols and rules.
	    }
		this.computedFractal=initialAxion;     	//The initial string is the axion
		this.n=n;								//The number of iterations
	}
	
	public String buildFractal() 
	{
		System.out.println("In buildFractal\n");
		
		// print out symbols and their production rules
		System.out.println("Symbol Table");
		for(int i=0;i<numSymbols;i++) 
		{
		    System.out.println("symbol(" + i + ")=" + symbols[i] + 
		                       "   rule(" + i +")=" + rules[i] + "\n");
		}
		
		StackADT<String> s = new LinkedStack<String>();

		for (int it_no =1; it_no < n; it_no++)
		{
			for (int i = 0; i < this.computedFractal.length(); i++)
			{
				alphaNumeric = this.computedFractal.toString().substring(i,i+1);
				
				//Adds any constant symbols to the stack
				if (alphaNumeric.equals("+") || alphaNumeric.equals("-") || alphaNumeric.equals("[") || alphaNumeric.equals("]"))
				{
					s.push(alphaNumeric);
				}
				
				//Replaces any letters with their appropriate rule
				else
				{
					this.index = this.in(alphaNumeric, symbols);
					if (index == -1)
						s.push(alphaNumeric);
					else
						s.push(rules[index]);
				}
				
			}
			//Add each rule to the new fractal list
			String str = "";
			while (!s.isEmpty())
			{
				str = s.pop() + str;
			}
			
			//Apply changes to computedFractal to prepare it for the next iteration or for drawing
			this.computedFractal = str;
			
		}  //end of iterations

		return computedFractal;
	}
	
	public Integer in(String alphaNumeric,String[] symbols) 
	{
		for(int i=0;i<symbols.length;i++)
		    {
			    System.out.println("i=" +i + " alphaNumeric=" + alphaNumeric + " symbols[" + i + "]=" + symbols[i]);
			    if(alphaNumeric.equals(symbols[i])) return(i);
		    }
		return(NOT_FOUND);
	}
	
	public void prettyPrint() 
	{
		String str=computedFractal;
		size=str.length();
		System.out.println("prettyPrint:");
		System.out.println("size=" + size);
		//System.out.println("str=" + str);
		//System.out.println("charsPerLine=" + charsPerLine); 
		
		while(charsPerLine < size) 
		{
		    // print out substrings of str of length charsPerLine
		    System.out.println(str.substring(0,charsPerLine-1));
		    str=str.substring(charsPerLine,size);
		    size=str.length();
		} 
		// print last bit of str
		System.out.println(str);
	}
	
	/**
	 * Test Harness
	 */
	public static void main(String[] args)
	{
		String[] testSymbolArray = {"F"};
		String[] testRulesArray = {"F+F-F-F+F"};
		Integer testNumSymbols = testSymbolArray.length;
		String testInitialAxion = "F";
		Integer testNumIterations = 3;
		
		MakeFractal test = new MakeFractal(testSymbolArray, testNumSymbols, testInitialAxion, testRulesArray, testNumIterations);
		test.buildFractal();
		test.prettyPrint();
	}
} // MakeFractal
