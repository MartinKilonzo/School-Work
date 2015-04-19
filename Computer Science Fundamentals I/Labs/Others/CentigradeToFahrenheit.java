public class CentigradeToFahrenheit
{
  public static void main (String[] args)
  {
    double temperature, fdegrees;
    temperature = 29.7;
    //Question 6: temperature = -3.5;
    fdegrees = ((temperature * 9) / 5) + 32;
    //Question 3: double temperature = 29.7;
    //Question 3: double fdegrees = ((temperature * 9.0) / 5.0) + 32;
    //Question 4: double fdegrees = ((temperature * 9) / 5) + 32;
    System.out.println(temperature + " degrees celcius is: ");
    //Question 5: System.out.print(temperature + " degrees celcius is: "); 
    System.out.println(fdegrees + " degrees fahrenheit.");
  }
}
//Question 7: "Error: Type mismatch: cannot convert from double to int."
