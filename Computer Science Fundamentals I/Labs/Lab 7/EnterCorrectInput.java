public class EnterCorrectInput
{
  public static void main(String[] args)
  { 
    int num = 200;
    while (1 > num || num > 100)
    {
      num = SimpleInput.getIntNumber("Enter a number between 1 and 100 inclusive: "); 
      
      if (1 <= num && num <= 100)
      {
        SimpleOutput.showInformation("The number you entered is " + num);
      }
      
      else
      {
        SimpleOutput.showInformation("The number you entered is not in the given range.");
      }
    }
  }
}