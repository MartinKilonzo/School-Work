public class Lab8
{
  public static void main(String[] args)
  {
//Exercise 1
    int a = 12;
    int b = 7;
    boolean t = true;
    char c = 'e';
    
    System.out.println("(a > 0) && (b > 0) = "  + ((a > 0) && (b > 0)));
    System.out.println("('a' > c) || ('E' != c) = " + (('a' > c) || ('E' != c)));
    System.out.println("(a > b) ^ (t == (c > 's')) = " + ((a > b) ^ (t == (c > 's'))));
    System.out.println("(a % b != 0) && (b * 2 > a) = " + ((a % b != 0) && (b * 2 > a)));
    System.out.println("(t != !(a > b)) && t = " + ((t != !(a > b)) && t));
  }
}
    