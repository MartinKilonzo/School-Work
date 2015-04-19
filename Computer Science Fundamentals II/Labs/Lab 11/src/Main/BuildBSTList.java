package Main;
import java.util.Iterator;

import Dependencies.ArrayOrderedList;
import Dependencies.BinarySearchTreeList;

public class BuildBSTList {

	public static void main(String[] args)
	{
		// start with ArrayOrderedList of integers
		ArrayOrderedList<Integer> arrList = new ArrayOrderedList<Integer>();
		for (int i = 1; i <= 11; i++)
		{
			arrList.add(new Integer(i));
		}
		System.out.println(arrList);	// show what it looks like
		
		// create BinarySearchTreeList by adding items from ArrayOrderedList in order
		BinarySearchTreeList<Integer> bstList = new BinarySearchTreeList<Integer>();
		
		Iterator<Integer> iter = arrList.iterator();
		while (iter.hasNext()){
			//System.out.println("hi");
			bstList.add(iter.next());
		}
		System.out.println("============================================");	
		// print tree in level order to show what it looks like
		iter = bstList.iteratorLevelOrder();
		while (iter.hasNext())
			System.out.print(iter.next() + " ");
		System.out.println();
		
		System.out.println("----------------------------------------------");
		// now build a balanced tree
		bstList = new BinarySearchTreeList<Integer>();
		buildBalanced(arrList, bstList);
		
		// print list in level order to show what it looks like
		iter = bstList.iteratorLevelOrder();
		while (iter.hasNext())
			System.out.print(iter.next() + "\n");
		System.out.println();
		
	}
	
	// build binary search tree list from array ordered list so that it is
	// as balanced as possible
	
	public static void buildBalanced(ArrayOrderedList<Integer> arrList,
			BinarySearchTreeList<Integer> bstList)
	{
		Integer [] tempArr = new Integer[arrList.size()];
		int index = 0;
		Iterator<Integer> iter = arrList.iterator();
		while (iter.hasNext())
		{
			tempArr[index] = iter.next();
			index ++;
		}
		
		buildBalancedRec(tempArr, 0, tempArr.length - 1, bstList);
	}
		
	// recursive method that adds items in the specified array to the specified
	// binary search tree list such that the tree stay as balanced as possible
	
	private static void buildBalancedRec(Integer [] tempArr, int start, int end,
			BinarySearchTreeList<Integer> bstList)
	{		
		if (start <= end)
		{
			int midpoint = (start + end)/2;
			
			bstList.addElement(tempArr[midpoint]);
			buildBalancedRec(tempArr, start, midpoint - 1, bstList);
			buildBalancedRec(tempArr, midpoint + 1, end, bstList);
		}
		
		
		
		
	}

}
