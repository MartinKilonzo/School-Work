public class TestMirroringDiagonal
{
  public static void main (String[] args)
  {
    Picture pictureObj = new Picture("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/flower1.jpg");
    //Picture pictureObj = new Picture("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/blueMotorcycle.jpg");
    pictureObj.explore();
    pictureObj.mirrorDiagonalBottom();
    pictureObj.explore();
  }
}
//I do not see the image reflected right to left because both halves of the image are the same.