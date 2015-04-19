import java.util.*;
import Dependencies.*;

public class Test1{

  public static void main(String[] args){
    String s = "add m n { m n + }";
    StringTokenizer st = new StringTokenizer(s);
    SimpleFunction func = new SimpleFunction(st);
    
    StackADT<Integer> stack = new LinkedStack<Integer>();
    stack.push(56);
    stack.push(34);
    stack.push(12);
    func.eval(stack);
    System.out.println(stack.pop());
  }

}