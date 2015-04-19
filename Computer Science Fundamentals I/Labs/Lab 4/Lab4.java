public class Lab4
{
  public static void main (String[] args)
  {
//Exercise 1
    //a)
    String butterfly1 = FileChooser.pickAFile();
    Picture butterfly = new Picture(butterfly1);
    butterfly.show();
    
    //b)
    String beach1 = FileChooser.pickAFile();
    Picture beach = new Picture(beach1);
    beach.show();
    
//Exercise 2
    beach.explore();
    //(0,0) is black; (530,258) (in the water) is teal-ish (101,147,147).
    
//Exercise 3
    //a)
    System.out.println(butterfly);
    //Location on the computer and height and with.
    
    //b)
    int width = butterfly.getWidth();
    int height = butterfly.getHeight();
    System.out.println("Picture size is: " + width + " by " + height + ".");
    //c)
    Pixel pixelObj1 = butterfly.getPixel(0,0);
    System.out.println(pixelObj1);
    //RGB values of the pixel at the given coordinates.
    
    //d)
    Pixel pixelObj2 = butterfly.getPixel(585,0);
    Pixel pixelObj3 = butterfly.getPixel(0,484);
    Pixel pixelObj4 = butterfly.getPixel(293,242);
    System.out.println("The pixels in the top right corner, top left corner and middle of the picture have values of: " + pixelObj2 + ", " + pixelObj3 + ", and " + pixelObj4 + " respectively.");
    
    //e)
    int red = pixelObj1.getRed();
    int green = pixelObj1.getGreen();
    int blue = pixelObj1.getBlue();
    System.out.println("Pixel at (0,0) has red, green, and blue values of: " + red + ", " + green + ", and " + blue + ".");
  }
}