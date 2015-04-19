package ExceptionExamples;
import java.io.*;

public class ExceptionExample12 {

   public static void main (String[] args) throws Exception {

      /* 
         - multiple exceptions
         - notice what happens when there's a possibility for
           2 exceptions in the same try
      */

      BufferedReader keyboard=
         new BufferedReader (new InputStreamReader(System.in),1);

      System.out.print("Enter an integer: ");
      String userTyped = keyboard.readLine();

      try {
         int value = Integer.parseInt(userTyped);
         System.out.println("Divide by zero " + 5/0);
      }
      catch (NumberFormatException e) {
         System.out.println("Hey, that's not an integer!");
      }
      catch (ArithmeticException e) {
         System.out.println("I don't know how to divide by 0.");
      }

      System.out.println("end of main"); 
  }
}