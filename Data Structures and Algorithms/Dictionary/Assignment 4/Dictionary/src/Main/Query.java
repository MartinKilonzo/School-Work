package Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Query
{
	public static void main(String[] args) throws IOException, DictionaryException
	{
		if (args[0] == null)
		{
			System.out.println("Dictioanry is emtpy.");
			System.exit(0);
		}
		
		// Initialization 
		String dictionaryFile = args[0];

		OrderedDictionary dictionary = new OrderedDictionary();
		
		BufferedReader fileReader = new BufferedReader(new FileReader(dictionaryFile));
		
		String line;
		
		while ((line = fileReader.readLine()) != null) 
		{
		   String word = line;
		   
		   line = fileReader.readLine();
		   
		   String def = line;
		   
		   if (def.endsWith(".jpg") || def.endsWith(".gif"))
			   dictionary.insert(word, def, 3);
		   
		   else if (def.endsWith(".wav") || def.endsWith(".mid"))
			   dictionary.insert(word, def, 2);
		   
		   else
			   dictionary.insert(word, def, 1);
		}
		
		PictureViewer pictureDef = new PictureViewer();
		SoundPlayer audioDef = new SoundPlayer();
		// User Input
		
		boolean end = false;	// Flag which ends the program when true.
		
		while(!end)
		{
			StringReader keyboard = new StringReader();
			line = keyboard.read("Enter command: ");
			line.toLowerCase();
			
			StringTokenizer input = new StringTokenizer(line);
			
			// Add the first term to 'command' and the second term to 'word'.
			String command, word ="";
			
			if (input.hasMoreElements())
			{
				command = input.nextToken().toLowerCase();
				
				if (input.hasMoreElements())
					word = input.nextToken().toLowerCase();
				
				else
				{
					// If either of the commands are exit or end, terminate
					if (command.equals("exit") || command.equals("end"))
					{
						end = true;
						System.out.println("Exiting...");
					}
					
					// Otherwise, notify the user that there must be two terms in the command
					else
						System.out.println("Command must contain two terms.");
						continue;
				}
			}
			
			// If the command has no terms, notify the user that there must be a command entered
			else
			{
				System.out.println("Please enter a valid command.");
				continue;
			}
			
			// This switch handles all the commands of the program exempting exit/end
			switch(command)
			{
				case "define":
					String definition = dictionary.findWord(word);
					
					// If it is a picture definition, show the picture
					if (definition.endsWith(".jpg") || definition.endsWith(".gif"))
					{
						try 
						{
							pictureDef.show("Inputs/" + definition);
						} 
						catch (MultimediaException e) 
						{
							System.out.println(e.getMessage());
						}
					}
					
					// Otherwise, if it is a audio definition, play the audio
					else if (definition.endsWith(".wav") || definition.endsWith(".mid"))
					{
						try 
						{
							audioDef.play("Inputs/" + definition);
						} 
						catch (MultimediaException e) 
						{
							System.out.println(e.getMessage());
							e.printStackTrace();
						}
					}
					
					// Otherwise, if the word does not exist, let the user know
					else if (definition == "")
						System.out.println("The word does not exist.");
					
					// Otherwise it must be a string definition, so show the string
					else
						   System.out.println(definition);
					
					break;
					
				case "delete":
					
					// If the word exists in the dictionary, remove it
					try
					{
						dictionary.remove(word);
						System.out.println(word + " removed.");
					}
					
					// Otherwise, display a message showing that the term does not exist
					catch (DictionaryException e)
					{
						System.out.println("The word \"" + word + "\" does not exist in the dictionary.");
					}
					
					break;
					
				case "list":
					// Create a list string and a holder string
					String list = "";
					String nextWord = dictionary.successor(word);
					
					// While the next word starts with the prefix, add it to the list
					while (nextWord.startsWith(word))
					{
						list += nextWord + " ";
						nextWord = dictionary.successor(nextWord);
					}
					
					// If no words start with the prefix, inform the user
					if (list.equals(""))
						System.out.println("There are no entries that start with " + word + ".");
					
					// Otherwise, print out the list
					else	
						System.out.println(list);
					
					break;
					
				case "next":
					// Print out the next term. If there is no next term a message will be displayed (handled by OrderedDictioanry.successor)
					System.out.println(dictionary.successor(word));
					break;
					
				case "previous":
					// Print out the previous term. If there is no previous term a message will be displayed (handled by OrderedDictioanry.predecessor)
					System.out.println(dictionary.predecessor(word));
					break;
					
				default:
					// Should any undefined command be entered, inform the user
					System.out.println("Command not recognized.");
					break;
			}
		}
	}
}
	