package ExceptionExamples;
public class ExceptionExample2 {

   public static void main (String[] args) {

      /* 
         - unchecked exceptions: standard runtime exceptions
                                 methods need not state if they throw these
         - generate an array index out of bounds unchecked exception which is
           handled gracefully with a try/catch structure
      */

      final int NUM_STUDENTS = 5;

      int students[] = new int[NUM_STUDENTS];

      try {
         students[NUM_STUDENTS] = 1;
      }
      catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("Sorry, the array element " + NUM_STUDENTS + 
                            " does not exist.");
      }

   }
}