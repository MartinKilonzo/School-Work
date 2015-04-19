package ExceptionExamples;
public class ExceptionExample8 {

   private static boolean debug = true;
   private static final int NUM_STUDENTS = 5;
   private static final int ILLEGAL_INDEX = 5;

   /*  - try/catch in the method and at the method invocation level
       - notice that the exception is caught once
   */

   public static void change(int[] course) {

      if (debug) System.out.println("in method change ");

      try {
         course[ILLEGAL_INDEX] = 10;
      }

      catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("in method change: index " + ILLEGAL_INDEX + " does not exist.");
      }
      
      if (debug) System.out.println("end of method change");
   }

   public static void main (String[] args) {

      int students[] = new int[NUM_STUDENTS];

      try {
         change(students);
      }

      catch (ArrayIndexOutOfBoundsException e) {
         System.out.println("Sorry, index " + ILLEGAL_INDEX + " does not exist.");
      }

      if (debug) System.out.println("end of main");
   }
}