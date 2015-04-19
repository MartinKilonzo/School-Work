public class Lab6
{
  public static void main (String[] args)
  {
//Exercise 1
    //1.
    for (int i = 1; i <= 10; i ++)
    {
      System.out.print("*");
    }
    System.out.println(); //Result: **********
    
    //2.
    for (int row = 1; row <= 5; row ++)
    {
      for (int column = 0; column < 10; column ++)
      {
        System.out.print("*");
      }
      System.out.println();
    }   /*Result: ********** 
                  ********** 
                  ********** 
                  ********** 
                  ********** */
    //3. "Static Error: Undefined name 'row'" as the variable is only defined for use by for loop statement.
    //4.
    for (int row = 1; row <= 5; row ++)
    {
      for (int i = 1; i <= row; i ++)
      {
        System.out.print("*");
      }
      System.out.println();
    } /*Result: * 
                ** 
                *** 
                **** 
                *****  */
    
//Exercise 2
    //1-3
    Picture pictureObj = new Picture
      (FileChooser.getMediaPath("/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/butterfly2.jpg"));
    pictureObj.explore();
    Pixel[] pixelArray = pictureObj.getPixels();
    System.out.println("The number of pixels in the image is: " + pixelArray.length + "."); //Result: The number of pixels in the image is: 284210. 
    
    //4.
    for (int i = 0; i < 100; i++)
    {
      Pixel pixelObj = pixelArray[i];
      System.out.println
        ("At position " + i + " pixel at (" + pixelObj.getX() +","+ pixelObj.getY() + ") colour is: " + pixelObj.getColor());
    }
    
    //5.
    for (int i = 1; i < 51; i ++)
    {
      Pixel pixelObj2 = pixelArray[i*2-1];
      System.out.println
        ("At position " + i + " pixel at (" + pixelObj2.getX() +","+ pixelObj2.getY() + ") colour is: " + pixelObj2.getColor());
    }
  }
}