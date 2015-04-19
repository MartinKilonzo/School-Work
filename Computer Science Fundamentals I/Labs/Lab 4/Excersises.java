public class Exercises
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
  }
}