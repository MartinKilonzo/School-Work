/**
 * SavingsAccount.java models a simple savings account
 * and the effect of interest on an existing balance
 * @author Remmy Martin Kilonzo, 250750759
 * @task Lab 3
 *
 */
public class SavingsAccount extends BankAccount
{
	//* Attributes *//
	
	private final double INTEREST_RATE = 0.1;		//Fixed monthly interest rate factor of the savings account
	
	private double interestRate;					//Monthly interest rate of the account
	
	
	//* Constructors *//
	
	/**
	 * Constructor that creates a saving account with
	 * an initial amount and the fixed interest rate 
	 * @param initialAmount
	 */
	public SavingsAccount(double initialAmount)
	{
		super(initialAmount);
		interestRate = INTEREST_RATE;
	}
	
	/**
	 * Constructor that creates a saving account
	 * with an initial amount and specific interest rate 
	 * @param initialAmount
	 * @param rate
	 */
	public SavingsAccount(double initialAmount, double rate)
	{
		super(initialAmount);
		interestRate = rate;
	}
	
	//* Methods *//
	
	/**
	 * Method to get the interest rate
	 * of the savings account
	 * @return interest rate
	 */
	public double getRate()
	{
		return interestRate;
	}
	
	/**
	 * Method to add monthly interest
	 * earnings to the existing balance
	 */
	public void calculateInterest()
	{
		double x = super.getBalance() * interestRate;						//Applying the interest rate to the existing balance
		super.deposit(super.getBalance() + Math.round(x * 100.0) / 100);	//Rounding to nearest hundredth and adding it to the existing balance
	}
	
	/**
	 * Method to return a string representation
	 * of the accounts attributes
	 * @return account attributes as a string
	 */
	public String toString()
	{
		return  "SavingsAccount: balance $" + super.getBalance() + ", monthly interest: " + interestRate + "%"; 
	}
}
