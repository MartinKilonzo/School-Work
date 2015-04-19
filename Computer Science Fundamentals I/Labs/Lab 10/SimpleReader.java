import java.io.*;

public class SimpleReader
{
  /**
   * Method to read a file and print out the contents
   * @param fileName the name of the file to read from
   */
  private int size = 0;
  private String fileName;
  private String lines [];
  
  public SimpleReader(String name)
  {
    fileName = name;
    File file = new File(fileName);
    
    String line = null;
    lines = new String[100];
 
    // try to do the following
    try {
      
      // create the buffered reader
      BufferedReader reader = 
        new BufferedReader(new FileReader(fileName));

      // Loop while there is more data
      int i = 0;
      while((line = reader.readLine()) != null)
      {
        // store the current line
         lines[i] = line;
         i++;
      }
      
      // close the reader
      reader.close();
      size = i;
      } catch(FileNotFoundException ex) {
      SimpleOutput.showError("Couldn't find " + fileName +
                             " please pick it.");
      fileName = FileChooser.pickAFile();
    } catch(Exception ex) {
      SimpleOutput.showError("Error reading file " + fileName);
      ex.printStackTrace();
    }
   
    
  }
  
  public int getFileLength()
  {
    return  size;
  }
  
  public String[] readFile()
  {
    return lines;
  }
}
