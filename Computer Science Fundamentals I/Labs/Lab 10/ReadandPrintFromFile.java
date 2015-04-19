import java.io.*;

public class ReadandPrintFromFile
{
  public static void main(String[] args)
  {
    String fileName = "test.txt";
    SimpleReader reader = new SimpleReader(fileName);
    String[] lineArray = reader.readFile();
    
    for (int i = 0; i < reader.getFileLength(); i++)
    {
      System.out.println(lineArray[i]);
    }
  }
}