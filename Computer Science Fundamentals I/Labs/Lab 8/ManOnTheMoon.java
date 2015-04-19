public class ManOnTheMoon
{
  public static void main(String[] args)
  {
    Picture moon = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/moon-surface.jpg");
    Picture robot = new Picture
      ("C:/Users/Martin/Documents/Java/mediasources-no-movies-7-30-06/intro-prog-java/mediasources/caterpillar.jpg");
    moon.copyExceptWhite(robot,robot.getWidth(),robot.getHeight(),223,180);
    moon.explore();
  }
}