import java.awt.Color;
public class Exercise2
{
  public static void main (String[] args)
  {
    String  caterpillar = FileChooser.pickAFile();
    Picture pictureObj = new Picture(caterpillar);
        
    for (int i = 0; i < 6; i++)
    {
      pictureObj.getPixel(100+i,100+i).setColor(Color.black);
    }
    pictureObj.repaint();
    pictureObj.show();
  }
}

//It adds a black diagonal line of length 6 pixels.