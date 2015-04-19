public class SimpleReaderTest
{
  public static void main(String[] args)
  {
    //b)
    SimpleReader reader = new SimpleReader("test.txt");
    
    //c)
    String[] lineArray = reader.readFile();
    
    System.out.println("The number of lines in the file is " + reader.getFileLength());
    System.out.println(lineArray[0]);
    
    //d)
    System.out.println(lineArray[2]);
    
    //e) An error message appears as it cannot find the file "blahblah".
    reader = new SimpleReader("blahblah");
  }
}