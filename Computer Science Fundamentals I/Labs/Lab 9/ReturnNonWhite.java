public class ReturnNonWhite
{
  public static void main(String[] args)
  {
    Picture caterpillar = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/caterpillar.jpg");
    System.out.println(caterpillar.countNonWhitePixels());
    System.out.println(caterpillar.countNonWhitePixels2());
  }
}