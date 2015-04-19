
import java.util.*;

import Dependencies.*;

public class Test5{

  public static void main(String[] args){
    StringTokenizer st = FileTokenizer.read("src/prog4.txt");
    Program prog = new Program(st);
    
    StackADT<Integer> stack = new LinkedStack<Integer>();
    stack.push(10);
    SimpleFunction func = prog.find("fact");
    func.eval(stack, prog);
    System.out.println(stack.pop());
  }
}