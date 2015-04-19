package ExceptionExamples;
import java.io.*;

public class ExceptionExample11 
{

   public static void main (String[] args) throws Exception 
   {

      /* 
         - this handles the NumberFormatException

      */

      BufferedReader keyboard=
         new BufferedReader (new InputStreamReader(System.in),1);

      boolean flag = true;
      String userTyped = "";
  	  
      while (flag)
  	  {
  	      System.out.print("Enter an integer: ");
  	      userTyped = keyboard.readLine();
	      try 
	      {
	         int value = Integer.parseInt(userTyped);
	         flag = false;
	      }
	      catch (NumberFormatException e) 
	      {
	         System.out.println("Hey, " + e.getMessage() + " is not an integer!");
	      }
   	  }
   }
}