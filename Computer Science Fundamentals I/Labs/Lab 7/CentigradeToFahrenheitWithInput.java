public class CentigradeToFahrenheitWithInput
{
  public static void main (String[] args)
  {
    double temperature, fdegrees;
    temperature = SimpleInput.getNumber("Enter the Temperature in Degrees Celcius: ");
    fdegrees = ((temperature * 9) / 5) + 32;
  //System.out.println(temperature + " degrees celcius is: ");
  //System.out.println(fdegrees + " degrees fahrenheit.");
    //b)
    SimpleOutput.showInformation(temperature + " degrees celcius is: " + fdegrees + " degrees fahrenheit.");
  }
}
