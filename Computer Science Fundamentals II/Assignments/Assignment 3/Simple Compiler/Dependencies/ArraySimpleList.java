package Dependencies;

/**
 * Represents an array implementation of an indexed list of objects 
 */

public class ArraySimpleList<T> implements SimpleListADT<T>{
  /** indicates number of elements stored */
  private int count;
  /** array that stores all elements */
  private T[] elements;

  /**
   * Creates an empty list
   */
  public ArraySimpleList(){
    elements = (T[]) new Object[0];
  }

  /**  Adds one element at the end of the list
   */
  public void addToRear(T t){
    if (count == elements.length)
      expandCapacity();
    elements[count] = t;
    count++;
  }

  /**  returns the element with a given name
   *   the test that is applied is whether T[i].toString() is equal to the given name
   *   when the element is not present, returns null
   */
  public T find(String name){
    int i = indexOf(name);
    if (i == -1)
      return null;
    else
      return elements[i];
  }

  /**  returns the index of the element with a given name
   *   the test that is applied is whether T[i].toString() is equal to the given name
   *   when the element is not present, returns -1
   */
  public int indexOf(String name){
    for (int i = 0; i < count; i++)
      if (name.equals(elements[i].toString()))
	return i;
    return -1;
  }

  /**  returns a reference to the element at index i
   *   the element is not removed from the list
   */   
  public T get(int i){
    return elements[i];
  }

  /**  Returns the number of elements in this list
   */
  public int size(){
    return count;
  }

  /**  Returns true if this list contains no element
   */
  public boolean isEmpty(){
    return count == 0;
  }

  /**  helper method
   */
  private void expandCapacity(){
    T[] newElements = (T[]) new Object[2*elements.length+1];
    for (int i = 0; i < elements.length; i++)
      newElements[i] = elements[i];
    elements = newElements;
  }

}