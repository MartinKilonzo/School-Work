public class CopyLeftHalf
{
  public static void main(String[] args)
  {
    Picture caterpillar = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/caterpillar.jpg");
    Picture target = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/640x480.jpg");
    target.copyLeftHalf(caterpillar);
    target.explore();
  }
}