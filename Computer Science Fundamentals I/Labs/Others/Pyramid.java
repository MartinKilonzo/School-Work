public class Pyramid
{
  public static void main (String[] args)
  {
    for (int row = 0; row <= 11; row ++)
    {
      for (int i = 0; i < 11 - row; i ++)
      {
        System.out.print("*");
      }
      for (int w = 1; w < w*2-1; w ++)
      {
        System.out.print(" ");
      }
      for (int i = 0; i < 10 - row; i ++)
      {
        System.out.print("*");
      }
      System.out.println();
    }
  }
}

