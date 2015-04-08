package Main;

public class DictEntry 
{
	//*  Attributes  *//
	
	private final int TEXT = 1;		// Variable denoting a text type definition
	private final int AUDIO = 2;	// Variable denoting an audio type definition
	private final int IMAGE = 3;	// Variable denoting an image type definition
	
	private String word;			// The term to be defined in the dictionary
	private String definition;		// The definition of the word
	private int type;				// The type of the definition
	
	
	
	//*  Constructors  *//
	/**
	 * Constructor to create DictEntry object
	 * 
	 * @param word			- The term to be defined in the dictionary
	 * @param definition	- The definition of the word
	 * @param type			- The type of the definition (1 for text, 2 for audio, and 3 for image)
	 */
	public DictEntry(String word, String definition, int type)
	{
		this.word = word;
		this.definition = definition;
		//this.type = type;
		
		if (type == 1)
			this.type = TEXT;
		
		else if (type == 2)
			this.type = AUDIO;
		
		else if (type == 3)
			this.type = IMAGE;
			
	}
	
	
	
	//*  Methods  *//

	/**
	 * Accessor method to get the word of the DictEntry object.
	 * 
	 * @return		- The word of the DictEntry object.
	 */
	public String word()
	{
		return this.word;
	}

	/**
	 * Accessor method to get the definition of the DictEntry object.
	 * 
	 * @return		- The definition of the DictEntry object.
	 */
	public String define()
	{
		return this.definition;
	}


	/**
	 * Accessor method to get the type of the DictEntry object.
	 * 
	 * @return		- The type of the DictEntry object.
	 */
	public int type()
	{
		return this.type;
	}
}
