package Dependencies;

import java.io.*;
import java.util.*;

/**
 * Defines a class that creates a StringTokenizer from a given filename
 */

public class FileTokenizer{

  /**  creates and returns a StringTokenizer from a given filename
   */
  public static StringTokenizer read(String filename){
    BufferedReader in = null;
    String allFile = "";

    try{
      in = new BufferedReader(new FileReader(filename));
    }
    catch (FileNotFoundException ee){
      System.out.println("File " + filename + " not found.");
      System.exit(0);
    }
    catch (IOException e){
      System.out.println("File " + filename + " cannot be read.");
      System.exit(0);
    }

    String current = "";
    while (current != null){
      try{
	current = in.readLine();
      }
      catch (IOException e){
	System.out.println("File cannot be read.");
	System.exit(0);
      }
      if (current != null)
	allFile = allFile + " " + current;
    }
    
    try {
      in.close();
      in = null;
    }
    catch (IOException e){
      System.out.println("Problem closing file.");
      System.exit(0);
    }

    return new StringTokenizer(allFile);
  }

  /**  shows the use of the two useful methods from a StringTokenizer
   *   boolean hasMoreTokens()   --- tests if there is something left
   *   String nextToken()        --- returns the next string, if there is one
   */
  public static void main(String[] args){
    StringTokenizer st = FileTokenizer.read("C:/Users/Martin/Documents/Java/CS 1027/Simple Compiler/src/Dependencies/prog0.txt");
    int i = 1;
    while (st.hasMoreTokens()){
      System.out.println("Token " + i + ": " + st.nextToken());
      i++;
    }
  }
}