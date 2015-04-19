import java.util.StringTokenizer;
public class Tokenize
{
  public static void main(String[] args)
  {
    SimpleReader reader = new SimpleReader("test.txt");
    String[] lineArray = reader.readFile();
    StringTokenizer tokenizer = new StringTokenizer(lineArray[0]);
    
    String firstName = tokenizer.nextToken();
    System.out.println(firstName);
    
    String lastName = tokenizer.nextToken();
    System.out.println(lastName);
    
    String userName = tokenizer.nextToken();
    System.out.println(userName);
    
    System.out.println(lastName + ", " + firstName + " - " + userName);
  }
}
    
    