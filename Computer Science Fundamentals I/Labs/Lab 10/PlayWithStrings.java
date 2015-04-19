public class PlayWithStrings
{
  public static void main(String[] args)
  {
    String test = "This is a test.";
    
     for (int i = 0; i < test.length(); i++)
     {
       System.out.print(test.charAt(i));
     }
     System.out.println();
     
     for (int i = test.length() - 1; i >= 0; i = i - 1)
     {
       System.out.print(test.charAt(i));
     }
     System.out.println();
  }
}