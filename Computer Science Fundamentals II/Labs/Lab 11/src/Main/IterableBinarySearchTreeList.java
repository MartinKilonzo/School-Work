package Main;

import java.util.Iterator;

import Dependencies.ArrayUnorderedList;
import Dependencies.BinaryTreeNode;
import Dependencies.LinkedBinarySearchTree;

public class IterableBinarySearchTreeList<T> extends LinkedBinarySearchTree
{
	/**
	 * Performs an inorder traversal in reverse order on this binary tree
	 * by calling an overloaded, recursive inorderDescending method that 
	 * starts with the root.
	 *
	 * @return  an inorder-descending iterator over this binary tree
	 */
	public Iterator<T> iteratorInOrderDescending()
	{
		ArrayUnorderedList<T> tempList = new ArrayUnorderedList<T>();
		inOrderDescending (root, tempList);
		
		return tempList.iterator();	   
	}
	
	/**
	 * Performs a recursive inorder traversal.
	 *
	 * @param node      the node to be used as the root for this traversal
	 * @param tempList  the temporary list for use in this traversal
	 */
	private void inOrderDescending(BinaryTreeNode<T> node, ArrayUnorderedList<T> tempList)
	{
		if (node != null)
		{
			inOrderDescending (node.getRight(), tempList);
			tempList.addToRear(node.getElement());
			inOrderDescending (node.getLeft(), tempList);
		}
	}
	
} //class