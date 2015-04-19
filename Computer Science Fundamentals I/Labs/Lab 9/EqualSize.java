public class EqualSize
{
  public static void main(String[] args)
  {
    Picture flower1 = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/flower1.jpg");
    Picture flower2 = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/flower2.jpg");
    System.out.println(flower1.equalPictureSize(flower2));
  }
}