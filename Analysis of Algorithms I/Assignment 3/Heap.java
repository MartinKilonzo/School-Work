/**
 * This class implements a minimum-priority-heap as an ADT.
 * 
 * @author 	Remmy Martin Kilonzo, 250750759
 * @task	CS 3340 Assignment 3
 */

import java.util.ArrayList;
import java.util.List;

public abstract class Heap<T extends Comparable<T>>
{
	//**  Constructors  **//
	private int size;
	private List<T> heap;
	
	public Heap()
	{
		size = 0;
		heap = (List<T>)new ArrayList<T>();
		heap.add(null);
	}
	
	/**
	 * Method to insert an element in the heap. Maintains the min-order property.
	 * @param element				- The element to be added to the heap.
	 */
	public void insert(T element)
	{		
		heap.add(element);
		size++;
		trickleUp(size);
	}
	
	/**
	 * Method which decreases an element should the suggested element be smaller.
	 * @param index					- The index of the element to change.
	 * @param new_key				- The new element.
	 */
	public void decrease(int index, T newKey)
	{
		if (index > 0 && heap.get(index).compareTo(newKey) >= 0)
		{
			heap.set(index, newKey);
			
			// In case the change has made the new node the smallest in the heap, we must update the heap.
			trickleUp(index);
		}
	}
	
	/**
	 * Method to remove and return the minimum element in the heap. Maintains the min-order property.
	 * @return						- The minimum element in the heap.
	 */
	public T removeMin()
	{
		if (heap.isEmpty())
		{
			return null;
		}
		
		T min = heap.get(getMinIndex());
		heap.set(getMinIndex(), heap.get(size));
		heap.remove(size);
		size--;
		
		trickleDown();
		
		return min;
	}
	
	/**
	 * Method which returns a boolean value based on the presence of an index.
	 * @param index				- The index to check for.
	 * @return					- True if the index contains an element, false otherwise.
	 */
	public boolean exists(int index)
	{
		return index <= size;
	}
	
	/**
	 * Method that returns the element found at the index specified.
	 * @param index				- The index where the key is found.
	 * @return					- The element found at the index, or null if no such index exists.
	 */
	public T get(int index)
	{
		return exists(index) ? heap.get(index) : null;
	}
	
	/**
	 * Method which returns the index of the smallest value.
	 * @return					- The index of the smallest value (1).
	 */
	public int getMinIndex()
	{
		return 1;
	}
	
	
	/**
	 * Getter method to retrieve the size of the heap.
	 * @return					- The number of elements in the heap.
	 */
	public int size()
	{
		return size;
	}
	
	/**
	 * Method which returns a boolean based on the empty state of the heap.
	 * @return					- Returns true if the heap is empty, false otherwise.
	 */
	public boolean isEmpty()
	{
		return heap.isEmpty();
	}
	
	/**
	 * Helper method which maintains the min-order property in the heap upon insertion 
	 * by "trickling" the new element up to its proper position in the heap.
	 */
	private void trickleUp(int index)
	{		
		while (index > 1 && (getParent(index).compareTo(heap.get(index)) > 0))
		{
			swap(index, getParentIndex(index));
			index = getParentIndex(index);		
		}
	}
	
	/**
	 * Helper method which maintains the min-order property in the heap upon deletion
	 * by "trickling" the top element to its proper position in the heap.
	 */
	private void trickleDown()
	{
		int index = 1;
		
		while (hasLeftChild(index))
		{
			int smallestChildIndex = getLeftIndex(index);
			
			if (hasRightChild(index) && heap.get(getRightIndex(index)).compareTo(heap.get(getLeftIndex(index))) < 0)
			{
				smallestChildIndex = getRightIndex(index);
			}
			
			if (heap.get(smallestChildIndex).compareTo(heap.get(index)) < 0)
			{
				swap(index, smallestChildIndex);
			}
			
			else 
			{
				break;
			}
			
			index = smallestChildIndex;
		}
	}
	
	/**
	 * Helper method which swaps the ordering of two elements in the heap.
	 * @param indexA			- The index of the first element involved in the swap.
	 * @param indexB			- The index of the second element involved in the swap.
	 */
	private void swap(int indexA, int indexB)
	{
		T holder = heap.get(indexA);
		heap.set(indexA, heap.get(indexB));
		heap.set(indexB, holder);
	}
	
	/**
	 * Method which returns the left child of the element at the index specified.
	 * @param index				- The index to check for left children.
	 * @return					- The index of the left child.
	 */
	private int getLeftIndex(int index)
	{
		return index * 2;
	}
	
	/**
	 * Method which returns the right child of the element at the index specified.
	 * @param index				- The index to check for right children.
	 * @return					- The index of the right child.
	 */
	private int getRightIndex(int index)
	{
		return index * 2 + 1;
	}
	
	/**
	 * Method which returns a boolean value based on the presence of a left child at the index.
	 * @param index				- The index to check for left children.
	 * @return					- True if a left child exists, false otherwise.
	 */
	private boolean hasLeftChild(int index)
	{
		return getLeftIndex(index) <= size;
	}
	
	/**
	 * Method which returns a boolean value based on the presence of a right child at the index.
	 * @param index				- The index to check for right children.
	 * @return					- True if a right child exists, false otherwise.
	 */
	private boolean hasRightChild(int index)
	{
		return getRightIndex(index) <= size;
	}
	
	/**
	 * Getter method which fetches the parent element.
	 * @param index				- The index of the child.	
	 * @return					- The parent of the indexed child.
	 */
	private T getParent(int index)
	{
		return heap.get(index/2);
	}
	
	/**
	 * Getter method which fetches the index of the parent element.
	 * @param index				- The index of the child.
	 * @return					- The index of the child's parent.
	 */
	private int getParentIndex(int index)
	{
		return index/2;
	}

}
