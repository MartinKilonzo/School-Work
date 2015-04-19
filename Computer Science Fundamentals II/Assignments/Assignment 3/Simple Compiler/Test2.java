
import java.util.*;

import Dependencies.*;

public class Test2{
  
  public static void main(String[] args){
    StringTokenizer st = FileTokenizer.read("src/prog0.txt");
    SimpleFunction func = new SimpleFunction(st);
    
    StackADT<Integer> stack = new LinkedStack<Integer>();
    stack.push(56);
    stack.push(34);
    stack.push(12);
    func.eval(stack);
    System.out.println(stack.pop());
  }

}