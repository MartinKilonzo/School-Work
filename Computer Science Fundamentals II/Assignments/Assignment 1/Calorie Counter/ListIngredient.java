
public class ListIngredient
{
	//* Attributes *//
	
	private final int DEFAULT_MAX_INGREDIENTS = 10;
	
	private Ingredient[] ingredients;		//List of ingredients
	private int numIngredients;				//Number of ingredients in the list
	
	//* Constructors *//
	
	/**
	 * Constructor to create a list of
	 * ingredients of default length
	 */
	public ListIngredient()
	{
		ingredients = new Ingredient[DEFAULT_MAX_INGREDIENTS];
		numIngredients = 0;
	}
	
	/**
	 * Constructor to create a list of
	 * ingredients of specified length
	 * @param max
	 */
	public ListIngredient(int max)
	{
		ingredients = new Ingredient[max];
		numIngredients = 0;
	}

	public static void main(String[] args)
	{
		

	}

}
