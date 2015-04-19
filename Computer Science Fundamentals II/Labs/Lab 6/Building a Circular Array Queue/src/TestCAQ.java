/*
**  A java class that tests the implementation of CircularArrayQueue
**
**  @author Ben Stephenson
**  @version 0.2
**  edited for CS1027 2007, 2009
** 
*/ 

public class TestCAQ
{
  // test isEmpty method
  
  public static void t_isEmpty(CircularArrayQueue<Object> it, boolean expected)
  {
    System.out.println("Testing isEmpty: ");
    if (it.isEmpty() == expected)
    {
      System.out.println("Passed\n");
    }
    else
    {
      System.out.println("Failed\n");
    }
  }

  // test size method
  
  public static void t_size(CircularArrayQueue<Object> it, int expected)
  {
    System.out.println("Testing size: ");
    if (it.size() == expected)
    {
      System.out.println("Passed\n");
    }
    else
    {
      System.out.println("Failed");
      System.out.println("  Expected: '" + expected + "'");
      System.out.println("  Got:      '" + it.size() + "'");
      System.out.println("");
    }
  }

  // test toString method

  public static void t_toString(CircularArrayQueue<Object> it, String expected)
  {
    System.out.println("Testing toString: ");
    if (it.toString().equals(expected))
    {
      System.out.println("Passed\n");
    }
    else
    {
      System.out.println("Failed");
      System.out.println("  Expected: '" + expected + "'");
      System.out.println("  Got:      '" + it.toString() + "'");
      System.out.println("");
    }
  }

  // test dequeue method
  
  public static void t_dequeue(CircularArrayQueue<Object> it, String expected) throws Exception
  {
    Object obj;

    System.out.println("Testing dequeue: ");
    obj = it.dequeue();
   
    if (obj.toString().equals(expected))
    {
      System.out.println("Passed\n");
    }
    else
    {
      System.out.println("Failed");
      System.out.println("  Expected: '" + expected + "'");
      System.out.println("  Got:      '" + obj.toString() + "'");
      System.out.println("");
    }
  }

  public static void main(String args[]) throws Exception
  {
    CircularArrayQueue<Object> first;                // first queue used in testing
    int i;                                           // for loop counter

    first = new CircularArrayQueue<Object>();        // create empty queue

    //
    //  Empty queue tests
    //
    t_isEmpty(first,true);
    t_size(first,0);
    t_toString(first,"");

    //
    //  Add some items of differing types -- every third item is an Integer 
    //  instead of being a Float
    //
    for (i = 0; i < 15; i++)
    {
      if (i % 3 == 0)
      {
        first.enqueue(new Integer(i));
      }
      else
      {
        first.enqueue(new Float(i * 10));
      }
    }

    //
    //  Queue tests now that it has some elements
    //
    t_isEmpty(first,false);
    t_size(first,15);
    t_toString(first,"0\n10.0\n20.0\n3\n40.0\n50.0\n6\n70.0\n80.0\n9\n100.0\n110.0\n12\n130.0\n140.0\n");

    //
    //  Remove 2 elements and make sure they are correct
    //
    t_dequeue(first,"0");
    t_dequeue(first,"10.0");

    //
    //  Verify that isEmpty, size and toString still give correct results
    //
    t_isEmpty(first,false);
    t_size(first,13);
    t_toString(first,"20.0\n3\n40.0\n50.0\n6\n70.0\n80.0\n9\n100.0\n110.0\n12\n130.0\n140.0\n");

    first.enqueue("A");
    first.enqueue("S");
    first.enqueue("D");
    first.enqueue("F");

    //
    //  Verify that isEmpty, size and toString still give correct results
    //
    t_isEmpty(first,false);
    t_size(first,17);
    t_toString(first,"20.0\n3\n40.0\n50.0\n6\n70.0\n80.0\n9\n100.0\n110.0\n12\n130.0\n140.0\nA\nS\nD\nF\n");

    //
    //  Remove 10 items from the queue
    //
    for (i = 0; i < 10; i++)
    {
      first.dequeue();
    }

    //
    //  Verify that isEmpty, size and toString still give correct results
    //
    t_isEmpty(first,false);
    t_size(first,7);
    t_toString(first,"12\n130.0\n140.0\nA\nS\nD\nF\n");

    //
    //  Add 3 more integers
    //
    for (i = 0; i < 3; i++)
    {
      first.enqueue(new Integer(-i));
    }
    
    //
    //  Verify that isEmpty, size and toString still give correct results
    //
    t_isEmpty(first,false);
    t_size(first,10);
    t_toString(first,"12\n130.0\n140.0\nA\nS\nD\nF\n0\n-1\n-2\n");

    //
    //  Remove 10 items from the queue
    //
    for (i = 0; i < 10; i++)
    {
      first.dequeue();
    }

    //
    //  Verify that isEmpty, size and toString still give correct results
    //
    t_isEmpty(first,true);
    t_size(first,0);
    t_toString(first,"");

    //
    //  Try to dequeue another item when the queue is empty
    //
    System.out.println("Testing dequeue from an empty queue:");
    try
    {
      first.dequeue();
    }
    catch (NullPointerException e)
    {
      System.out.println("Failed with a Null Pointer Exception\n");
    }
    catch (ArrayIndexOutOfBoundsException e)
    {
      System.out.println("Failed with an Array Index Out Of Bounds Exception\n");
    }
    catch (Exception e)
    {
      System.out.println("Caught an exception: '" + e + "'");
      System.out.println("Assuming that it passed but if this is a system exception instead");
      System.out.println("of an exception that you intended to throw then it failed.");
      System.out.println("");
    }
    
    //
    //  Adding 1 item to the queue
    //
    first.enqueue("Hello World");
    //
    //  Verify that isEmpty, size and toString still give correct results
    //
    t_isEmpty(first,false);
    t_size(first,1);
    t_toString(first,"Hello World\n");

    //
    //  Test removal with only 1 element
    //
    t_dequeue(first,"Hello World");

    //
    //  Add lots of elements
    //
    for (i = 0; i < 1000; i++)
    {
      first.enqueue(new Float(i));
    }
    //
    //  Verify that isEmpty and size give correct results
    //
    t_isEmpty(first,false);
    t_size(first,1000);

  }
}
