/**
 * PhoneTest.java:
 * This class creates an Ordered List of Phone objects.
 * @author CS1027 for Lab
 */
import java.io.*;
import java.util.*;

public class PhoneTest {

   public static void main(String[] args) throws Exception {

	  /**
	   * Lab 8
	   */
	  System.out.println("Lab 8:");
	  System.out.println();
   // get the filename from the user
     
      BufferedReader keyboard = new BufferedReader
                                 (new InputStreamReader(System.in),1);       
      System.out.println("Enter name of the input file: ");
      //String filename= keyboard.readLine();
      String filename = "C:/Users/Martin/Documents/Java/CS 1027/My Labs/Lab 8/src/Phone Numbers.txt";
    
   // create object that controls file reading and opens the file
         
      InStringFile reader = new InStringFile(filename);
      System.out.println("\nReading from file " + filename + "\n");

    // your code to create (empty) ordered list here
      
      ArrayOrderedList<Phone> phoneList = new ArrayOrderedList<Phone>();
      

           
      
   // read data from file two lines at a time (name and phone number)
     
      String name, phone;
      do {
        name = (reader.read());
        phone = (reader.read());

        // your code to add the entry to your ordered list here
        
        phoneList.add(new Phone(name, phone));
              
      } while (!reader.endOfFile()); 
   
      System.out.println("Here is my phone book:");

      System.out.println(phoneList.toString());




      
   // close file
      
      reader.close();
      System.out.println("\nFile " + filename + " is closed.");
      System.out.println();
      /**
       * Lab 9
       */
      System.out.println("Lab 9:");
      System.out.println();
      Iterator<Phone> iterator = phoneList.iterator();
      
      while (iterator.hasNext())
      {
    	  System.out.println(iterator.next().getName());
      }
      System.out.println();
      
      iterator = phoneList.iterator();
      
      while (iterator.hasNext())
      {
    	  System.out.println(iterator.next().getPhone());
      }
      System.out.println();
      
      //Answering the questions
      System.out.println("You would need to have ListADT.java, LinkedList.java, LinkedIterator.java, LinearNode.java, and EmptyCollectionException.java; nothing needs to be added. \n"
      		+ "The only change necessary would be: \"ArrayOrderedList<Phone> phoneList = new ArrayOrderedList<Phone>()\" --> \"LinkedOrderedList<Phone> phoneList = new LinkedOrderedList<Phone>()\"");
      System.out.println();
      
     //Testing toString2 method
      System.out.println(phoneList.toString2());
      System.out.println("Lab 9:");
      System.out.println("The exact same method would work in LinkedList.java too, since the itereators would behave in the same ways.");
   }
}
