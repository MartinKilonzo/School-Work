public class Lab6Ex4
{
  public static void main (String[] args)
  {
    FileChooser.setMediaPath("C:/Users/Martin/Documents/Java");
    Picture pictureObj = new Picture
      (FileChooser.getMediaPath("/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/butterfly2.jpg"));
    pictureObj.explore();
    pictureObj.clearBlue2();
    pictureObj.explore();
  }
}