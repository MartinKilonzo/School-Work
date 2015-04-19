import java.util.StringTokenizer;
public class TokenizeFile
{
  public static void main(String[] args)
  {
    SimpleReader reader = new SimpleReader("test.txt");
    String[] lineArray = reader.readFile();
    String firstName, lastName, userName;
    StringTokenizer tokenizer;

    for (int i = 0; i < reader.getFileLength(); i = i + 2)
    {
      tokenizer = new StringTokenizer(lineArray[i]);
      firstName = tokenizer.nextToken();
      lastName = tokenizer.nextToken();
      userName = tokenizer.nextToken();
      System.out.println(lastName + ", " + firstName + " - " + userName);
    }
  }
}