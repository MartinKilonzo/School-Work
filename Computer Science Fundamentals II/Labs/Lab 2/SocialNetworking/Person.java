/**
 * Class that represents a person with attributes name, email address 
 * @author CS1027
 *
 */
public class Person {
	
	/* Attribute declarations */
	private String lastName;	// last name
	private String firstName;	// first name
	private String email;		// email address
			
	/**
	 * Constructor initializes the person's name and email address
	 */
	public Person(String firstName, String lastName, String email) {
		this.firstName = firstName;
		this.lastName = lastName;		
		this.email = email;
	}

	/**
	 * getName method returns the person's full name
	 * @return first name followed by last name, blank separated
	 */
	public String getName(){
		return firstName + " " + lastName;
	}
	
	/**
	 * getEmail method returns the person's email address
	 * @return email address
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * setEmail method sets the person's email address
	 * @param email
	 */
	public void setEmail (String email) {
		this.email = email;
	}
	
	/**
	 * equals method determines whether two persons have the same name
	 * @param other	other Person object that this is compared to
	 * @return true of they have the same first name and last name, false otherwise
	 */
	public boolean equals(Person other){
		if (this.firstName.equals(other.firstName)&& this.lastName.equals(other.lastName))
			return true;
		else
			return false;
	}

	/**
	 * toString method returns a string representation of the person
	 * @return string with first name and last name, email address 
	 */
	public String toString() {
		String s = firstName + " " + lastName + "\t" + email;
		return s;
	}
	
	/**
	 * test harness
	 */

	public static void main (String[] args) 
	{
		// create a friend
		Person friend1 = new Person("Mickey", "Mouse", "");
		friend1.setEmail("mickey@uwo.ca");
		System.out.println(friend1);

		// test accessor methods
		System.out.println(friend1.getName());
		System.out.println(friend1.getEmail());

		// create a friend without email
		Person friend2 = new Person("Minnie", "Mouse", "");
		System.out.println(friend2);
		
		// test equals method with friends 1 and 2
		String value = null;
		if (friend1.equals(friend2) == true)
		{
			value = "is";
		}
		else
		{
			value = "is not";
		}
		System.out.println("Friend1 " + value + " the same as friend2.");
		
		// test equals method with friends 1 and 3			
		Person friend3 = new Person("Mickey", "Mouse", "dduck@uwo.ca");
		if (friend1.equals(friend3) == true)
		{
			value = "is";
		}
		else
		{
			value = "is not";
		}
		System.out.println("Friend1 " + value + " the same as friend2.");
	}
}
