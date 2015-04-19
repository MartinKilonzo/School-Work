package ExceptionExamples;
import java.io.*;

public class ExceptionExample9 {

   public static void main (String[] args) {

      /* 
         - checked exceptions: methods must either handle these exceptions
                               (with a try/catch) or state that they are 
                               throwing the exceptions (i.e. not dealing
                               with the exception) with the throws keyword

         - this will generate a syntax error because the checked exception,
           IOException, is not handled nor thrown

      */

      BufferedReader keyboard=
         new BufferedReader (new InputStreamReader(System.in),1);

      System.out.println("Enter an integer: ");
      String userTyped = keyboard.readLine();

      int value = Integer.parseInt(userTyped);

   }
}