public class Lab7
{
  public static void main (String[] args)
  {
//Exercise 1
    //a)
    double x = SimpleInput.getNumber("Enter the Double Number: ");
    System.out.println("Entered value is " + x);
    //It prints out the integer with a ".0" attached.
    //"Try again that wasnt a number!"
    
    //b)
    int n = SimpleInput.getIntNumber("Enter the Integer Number: ");
    System.out.println("Entered value is " + n);
    //"Try again that wasn't an integer!"
    
    String s = SimpleInput.getString("Enter String: ");
    System.out.println("Entered string is " + s);
    //It prints the number because a number can be classed as a string.
  }
}