public class Lab2SyntaxErrors
{
  public static void main (String[] args)
  {
    double bill =  34.95;
    double tip = 5.00;
    double total = bill + tip;
    System.out.println("Total bill: " + total);
    final double TIP_RATE = 0.2;
    double bill2 = 100.0;
    double new_tip = TIP_RATE * bill2;
    
    System.out.println("Total bill: " + bill2 + " + " + new_tip);
  }
}