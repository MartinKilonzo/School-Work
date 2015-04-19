/* 
 * This class is for representing employees
 * of a University given their first name,
 * last name, position, and income.
 */

public class Employee
{
  /*  Attriblutes  */
  private String name, firstName, lastName, jobTitle, faculty;
  private int empNumber;
  private double salary;
  
  /*  Constructors */
  public Employee(String empFirst, String empLast, int empNumber)
  {
    this.firstName = empFirst;
    this.lastName = empLast;
    this.empNumber = empNumber;
  }
  
  public Employee(String empFirst, String empLast, int empNumber, String jobTitle, String faculty, double salary)
  {
    this.firstName = empFirst;
    this.lastName = empLast;
    this.empNumber = empNumber;
    this.jobTitle = jobTitle;
    this.faculty = faculty;
    this.salary = salary;
  }
  
  /*  Methods  */
  public String toString()
  {
    return this.firstName + " " + this.lastName + ": " + this.empNumber + "; " + this.jobTitle + ", Faculty of " + this.faculty + ", Salary: $" + this.salary;
  }
  
  public String getFirstName()
  {
    return "First name: " + this.firstName;
  }
  
  public String getLastName()
  {
    return "Last name: " + this.lastName;
  }
  
  public String getSalary()
  {
    return "Salary: $" + this.salary;
  }
  
  public String setSalary(double value)
  {
    salary = Math.round(value*100)/100.00;
    return this.firstName + " " + this.lastName + "'s new salary is: $" + salary;
  }
  
  public String lastNameFirst()
  {
    return this.lastName + ", " + this.firstName;
  }
  
  public static void main(String[] args)
  {
    //Create two employee objects
    Employee emp1 = new Employee("Jon", "Snow", (int) (Math.random()*10000000));
    Employee emp2 = new Employee("Eddard", "Stark", (int) (Math.random()*10000000), "Project Manager", "Engineering", Math.round(Math.random()*10000000)/100.00);
    
    //Display the employee information
    //Ex 1:
    System.out.println(emp1.firstName);
    System.out.println(emp2.firstName);
    //Ex 2:
    System.out.println(emp1.toString());
    System.out.println(emp2.toString());
    //Ex 3:
    System.out.println(emp1.getLastName());
    System.out.println(emp2.getFirstName());
    System.out.println(emp2.setSalary(97352.9345));
    System.out.println(emp2.getSalary());
    //Ex 4:
    System.out.println(emp1.lastNameFirst());
    
/*
 *  Ex 1: 
 *  Jon 
 *  Eddard 
 *  
 *  Ex 2:
 *  Jon 
 *  Eddard 
 *  Jon Snow: 5793703; null, Faculty of null, Salary: $0.0 
 *  
 *  Ex 3:
 *  Jon 
 *  Eddard 
 *  Jon Snow: 5793703; null, Faculty of null, Salary: $0.0 
 *  Eddard Stark: 6931951; Project Manager, Faculty of Engineering, Salary: $82158.49 
 *  Last name: Snow 
 *  First name: Eddard 
 *  Eddard Stark's new salary is: $97352.93 
 *  Salary: $97352.93  
 * 
 *  Ex 4:
 *  Jon 
 *  Eddard 
 *  Jon Snow: 5793703; null, Faculty of null, Salary: $0.0 
 *  Eddard Stark: 6931951; Project Manager, Faculty of Engineering, Salary: $82158.49 
 *  Last name: Snow 
 *  First name: Eddard 
 *  Eddard Stark's new salary is: $97352.93 
 *  Salary: $97352.93 
 *  Snow, Jon 
 */
  }
}