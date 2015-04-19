import java.awt.Color;
public class CopyTest
{
  public static void main(String[] args)
  {
    String fileName = FileChooser.pickAFile();
    Picture test = new Picture(fileName);
    Picture canvas = new Picture(2 * test.getWidth(), 2 * test.getHeight());
    canvas.copyTo(test, test.getWidth(), test.getHeight(), 30, 50);
    canvas.show();
  }
}