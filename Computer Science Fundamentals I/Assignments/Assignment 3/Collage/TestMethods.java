import java.awt.Color;
public class TestMethods
{
  public static void main(String[] args)
  {
    Picture test1 = new Picture(FileChooser.pickAFile());
    Picture test2 = new Picture(FileChooser.pickAFile());
    Picture canvas = new Picture();
    Color testColor = ColorChooser.pickAColor();
    test1.explore();
    test1.colourAll(testColor.getRed(),testColor.getGreen(),testColor.getBlue());
    test1.explore();
    test2.explore();
    test2.makeMosaic(1,4);
    test2.explore();    
  }
}