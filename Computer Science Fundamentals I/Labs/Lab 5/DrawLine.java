import java.awt.Color;
public class DrawLine
{
  public static void main (String[] args)
  {
    String  caterpillar = FileChooser.pickAFile();
    Picture pictureObj = new Picture(caterpillar);
        
    for (int i = 0; i < 6; i++)
    {
      pictureObj.getPixel(100+i,100+i).setColor(Color.black);
    }
    
    int i = 0;
    while (i<25)
    {
      pictureObj.getPixel(100+i,100+i).setColor(Color.magenta);
      pictureObj.getPixel(100+i,100).setColor(Color.white);
        i++;
    }
    pictureObj.explore();
  }
}

//It adds a black diagonal line of length 25 pixels.