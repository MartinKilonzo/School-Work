public class Restaurant
{
  public static void main (String[] args)
  {
    double bill, tip, total, pp;
    int numPeople;

    final double tipRate = 0.20;
    numPeople = 12;
    bill = 437.76;
    tip = (bill*tipRate);
    total = (bill+tip);
    pp = (total/numPeople);
    
    System.out.println("Total Per Person: " + pp);
  }
}