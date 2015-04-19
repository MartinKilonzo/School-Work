public class EnterCorrectInput2
{
  public static void main(String[] args)
  {
    int num;
    boolean cont = true;
    System.out.println("Enter a number between 1 and 100.");
    
    while (cont)
    {
      num = SimpleInput.getIntNumber("Enter number: ");
      if (num >= 1 && num <= 100)
      {
        cont = false;
        System.out.println("The number you entered is: " + num);
      }
    }
  }
}