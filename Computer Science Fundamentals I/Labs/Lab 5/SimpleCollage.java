public class SimpleCollage
{
  public static void main (String[] args)
  {
    Picture canvas = new Picture(640,640);
    canvas.show();
    
    String fileName1 = FileChooser.pickAFile();
    Picture picFlower1 = new Picture(fileName1);
    canvas.copyPictureTo(picFlower1,100,0);
    canvas.repaint();
    
    String fileName2 = FileChooser.pickAFile();
    Picture picFlower2 = new Picture(fileName2);
    canvas.copyPictureTo(picFlower1,300,0);
    canvas.repaint();
    
    canvas.write("C:/Users/Martin/Documents/Java/My Labs/Lab 5/collageLab5.jpg");
    
    String filename = FileChooser.pickAFile();
    Picture pictureObj = new Picture(filename);
    pictureObj.explore();
  }
}