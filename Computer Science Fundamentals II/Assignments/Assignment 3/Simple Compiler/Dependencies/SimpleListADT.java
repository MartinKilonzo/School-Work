package Dependencies;

/**
 * Defines the interface to an indexed list of objects 
 */

public interface SimpleListADT<T> {

  /**  Adds one element at the end of the list
   */
  public void addToRear(T element);

  /**  returns the element with a given name
   *   the test that is applied is whether T[i].toString() is equal to the given name
   *   when the element is not present, returns null
   */
  public T find(String name);

  /**  returns the index of the element with a given name
   *   the test that is applied is whether T[i].toString() is equal to the given name
   *   when the element is not present, returns -1
   */
  public int indexOf(String name);

  /**  returns a reference to the element at index i
   *   the element is not removed from the list
   */   
  public T get(int i);

  /**  Returns the number of elements in this list
   */
  public int size();

  /**  Returns true if this list contains no element
   */
  public boolean isEmpty();

}