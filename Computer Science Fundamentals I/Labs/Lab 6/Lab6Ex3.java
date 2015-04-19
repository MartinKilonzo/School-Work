public class Lab6Ex3
{
  public static void main (String[] args)
  {
    Picture pictureObj = new Picture
      (FileChooser.getMediaPath("/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/butterfly2.jpg"));
    pictureObj.explore();
    pictureObj.decreaseRed(35); //Input is desired percentage change in red values
    pictureObj.explore();
  }
}