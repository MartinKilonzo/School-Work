
import java.util.*;

import Dependencies.*;

public class Test4{

  public static void main(String[] args){
    StringTokenizer st = FileTokenizer.read("src/prog2.txt");
    Program prog = new Program(st);
    
    StackADT<Integer> stack = new LinkedStack<Integer>();
    stack.push(12);
    stack.push(34);
    SimpleFunction func = prog.find("func");
    func.eval(stack, prog);
    System.out.println(stack.pop());
  }

}