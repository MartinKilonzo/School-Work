public class Lab5
{
  public static void main (String[] args)
  {
//Exercise 1
    //a) Ten asterisks are printed.
    int counter = 1;
    while (counter <= 10)
    {
      System.out.print("*");
      counter++;
    }
    System.out.println(); //Result: ********** 
    
    int counter2 = 0;
    while (counter2 < 10)
    {
      System.out.print("*");
      counter2++;
    }
    System.out.println(); //Result: ********** 
    
    //b)
    //1
    int[] nums = {1,2,3,4,5};
    System.out.println(nums[0]); //Result: 1
    
    //2    
    int arraySize = nums.length;
    for (int i = 0; i < arraySize; i++)
    {
    System.out.print(nums[i]);
    }
    System.out.println(); //Result: 12345
    
    //3, 4
    int index = 0;
    while (index < nums.length)
    {
      System.out.print(nums[index]);
      index++;
    }
    System.out.println(); //Result: 12345 
    
    //5
    System.out.println(nums[nums.length]); //Result: java.lang.ArrayIndexOutOfBoundsException; This occurs as the user is trying trying to print an index that does not exist since arrays begin indexing at 0.
    
    //6
    int[] moreNums = new int[100];
    int index2 = 0;
    while (index2 < moreNums.length)
    {
      moreNums[index2] = index2 + 1;
      index2++;
    }
    
    //7
    for (int i = 0; i < 10; i++)
    {
      System.out.print(moreNums[i] + " ");
    }
    System.out.println(); //Result: 1 2 3 4 5 6 7 8 9 10    
  }
}