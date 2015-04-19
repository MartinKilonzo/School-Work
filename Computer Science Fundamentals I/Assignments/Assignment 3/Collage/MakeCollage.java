import java.awt.Color;
public class MakeCollage
{
  public static void main(String[] args)
  {
    String fileName = FileChooser.pickAFile();
    Picture collage = new Picture(fileName);
    collage.makeMosaic(6,20);
  }
}