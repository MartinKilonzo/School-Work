import java.util.*;

import Dependencies.LinkedStack;
import Dependencies.StackADT;

/**
 * A simple example of evaluation of postfix expressions
 */

public class Evaluator{

  /**  basic postfix evaluator
   *   takes input and writes output to the given stack
   *   in this example, the tokens are either +,-,*, or integers
   */
  public static void eval(StringTokenizer st, StackADT<Integer> stack){
    
    while(st.hasMoreTokens()){
      String s = st.nextToken();
      if (s.equals("+")){
	Integer i1 = stack.pop();
	Integer i2 = stack.pop();
	stack.push(i1+i2);
      }
      if (s.equals("-")){
	Integer i1 = stack.pop();
	Integer i2 = stack.pop();
	stack.push(i2-i1);
      }
      if (s.equals("*")){
	Integer i1 = stack.pop();
	Integer i2 = stack.pop();
	stack.push(i1*i2);
      }

      // this is a quick and dirty test, to decide if s represents an integer
      try{
	Integer i = Integer.parseInt(s);
	stack.push(i);
      }
      catch(NumberFormatException e){ 
      }
    }
  }

  public static void main(String[] args){
    // represents (1+2)*3 followed by (6-1)
    String s = "1 2 + 3 * 6 1 -";
    StringTokenizer st = new StringTokenizer(s);
    StackADT<Integer> stack = new LinkedStack<Integer>();
    eval(st, stack);
    // so that the end, we should see 5, then 9
    System.out.println(stack.pop());
    System.out.println(stack.pop());
  }
}
