package Dependencies;

/**
 * @author Lewis and Chase
 * Represents a linked implementation of a stack.
 */

public class LinkedStack<T> implements StackADT<T>{
  /** indicates number of elements stored */
  private int count;  
  /** pointer to top of stack */
  private LinearNode<T> top; 

  /**
   * Creates an empty stack.
   */
  public LinkedStack(){
    count = 0;
    top = null;
  }

  /**
   * Adds the specified element to the top of this stack.
   */
  public void push (T element){
    LinearNode<T> temp = new LinearNode<T> (element);

    temp.setNext(top);
    top = temp;
    count++;
  }

  /**
   * Removes the element at the top of this stack and returns a
   * reference to it. Throws an EmptyCollectionException if the stack
   * is empty.
   */
  public T pop(){
    if (isEmpty())
      throw new EmptyCollectionException("Stack");

    T result = top.getElement();
    top = top.getNext();
    count--;
 
    return result;
  }
   
  /**
   * Returns a reference to the element at the top of this stack.
   * The element is not removed from the stack.  Throws an
   * EmptyCollectionException if the stack is empty.
   */
  public T peek(){
    if (isEmpty())
      throw new EmptyCollectionException("Stack"); 

    return top.getElement();
  }

  /**
   * Returns true if this stack is empty and false otherwise. 
   */
  public boolean isEmpty(){
    return count == 0;
  }
 
  /**
   * Returns the number of elements in this stack.
   */
  public int size(){
    return count;
  }

  /**
   * Returns a string representation of this stack. 
   */
  public String toString(){
    String s = "";
    LinearNode<T> tmp = top;
    while (tmp != null){
      s = s + " " + tmp.getElement();
      tmp = tmp.getNext();
    }
    return s;
  }
}
