public class Lab10
{
  public static void main(String[] args)
  {
    //Exercise 1
    //a)
    String firstName = "harry";
    System.out.println(firstName.toUpperCase());
    System.out.println(firstName);
    
    //b)
    String test = "hello";
    System.out.println(test.charAt(0));
    System.out.println(test.charAt(3));
    System.out.println(test.charAt(4));
    
    System.out.println(test.charAt(1));
    
    //c, d: Play With Strings
    
    //Exercise 2
    //a)
    String str1 = "Hello";
    String str2 = "Goodbye";
    String str3 = "Goodbye";
    System.out.println(str1.equals(str2));
    
    //b)
    if (str1.equals(str2))
    {
      System.out.println("The strings are the same");
    }
    else
    {
      System.out.println("The strings are different");
    }
    
    //c)
    if (str2.equals(str3))
    {
      System.out.println("The strings are the same");
    }
    else
    {
      System.out.println("The strings are different");
    }
  }
}