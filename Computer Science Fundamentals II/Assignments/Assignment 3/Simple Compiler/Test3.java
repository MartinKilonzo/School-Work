
import java.util.*;

import Dependencies.*;

public class Test3{

  public static void main(String[] args){
    StringTokenizer st = FileTokenizer.read("src/prog1.txt");
    Program prog = new Program(st);
    SimpleFunction func;

    StackADT<Integer> stack = new LinkedStack<Integer>();
    stack.push(12);
    stack.push(34);
    // finds the function called sub
    func = prog.find("sub");
    // calls it on our stack, so the result should be 34-12=22
    func.eval(stack);
    System.out.println(stack.pop());

    stack.push(12);
    stack.push(34);
    // finds the function called add
    func = prog.find("add");
    // calls it on our stack, so the result should be 34+12=46
    func.eval(stack);
    System.out.println(stack.pop());
  }

}