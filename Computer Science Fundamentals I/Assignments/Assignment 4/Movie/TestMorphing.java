/* 
 * Remmy Martin Kilonzo
 * 250750759
 * The methods included in this file are to be used for 
 * the video assignment and include
 * tests for the morphStage() method
 */
public class TestMorphing
{
  public static void main(String[] args)
  {
    //Part 1: Testing with 3 intermediate pictures
    Picture startPicture1 = new Picture
      ("C:/Users/Martin/Documents/Java/My Labs/Assignments/Movie/Pictures/Saints Row Day.jpg");
    Picture endPicture1   = new Picture
      ("C:/Users/Martin/Documents/Java/My Labs/Assignments/Movie/Pictures/Saints Row Night.jpg");
    
    for (int k = 0; k < 5; k++)
    {
      startPicture1.morphStage(startPicture1, endPicture1, 3, k);
      startPicture1.explore();
    }
    
    //Part 2: Testing with 9 intermediate pictures
    Picture startPicture2 = new Picture
      ("C:/Users/Martin/Documents/Java/My Labs/Assignments/Movie/Pictures/California Flag.jpg");
    Picture endPicture2   = new Picture
      ("C:/Users/Martin/Documents/Java/My Labs/Assignments/Movie/Pictures/NCR.png");
    
    for (int k = 0; k < 11; k ++)
    {
      startPicture2.morphStage(startPicture2, endPicture2, 9, k);
      startPicture2.explore();
    }
  }
}