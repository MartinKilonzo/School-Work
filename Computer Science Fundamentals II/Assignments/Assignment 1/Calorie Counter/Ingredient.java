/**
 * Class that creates an "Ingredient" object
 * to be used in "ListIngredient.java"
 * @author Remmy Martin Kilonzo, 250750759
 * @task Assignment 1 2013
 *
 */
public class Ingredient 
{
	//* Attributes *//
	
	private final double PER_100_GRAMS = 0.01;	//Factor to count calories per 100 grams
	
	private String name;			//Name of the ingredient
	private double calorieCount;	//Number of calories
	private double weight;			//Mass of the ingredient
	
	
	//* Constructors *//
	
	/**
	 * Constructor that creates an ingredient
	 * object with the name and calories per 100 grams
	 * @param foodName
	 * @param calories
	 * @param weight
	 */
	public Ingredient(String foodName, double calories, double weight)
	{
		name = foodName;
		calorieCount = calories / weight * PER_100_GRAMS;
	}
	
	//* Methods *//
	
	/**
	 * Method to return the name of the food
	 * @return food name
	 */
	public String getFood()
	{
		return name;
	}
	
	/**
	 * Method to return the number of calories;
	 * @return number of calories per 100 grams
	 */
	public double getCalories()
	{
		return calorieCount;
	}
	
	public static void main(String[] args)
	{

	}

}
