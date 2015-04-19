/**
 * BinaryTreeNode represents a node in a binary tree with a left and 
 * right child.
 * 
 * @author Dr. Lewis
 * @author Dr. Chase
 * @version 1.0, 8/19/08
 */

public class BinaryTreeNode<T>
{
   private T element;
   private BinaryTreeNode<T> left, right;

   /**
    * Creates a new tree node with the specified data.
    *
    * @param obj  the element that will become a part of the new tree node
   */
   BinaryTreeNode (T obj) 
   {
      element = obj;
      left = null;
      right = null;
   }

   /**
    * Returns the number of non-null children of this node.
    * This method may be able to be written more efficiently.
    *
    * @return  the integer number of non-null children of this node 
    */
   public int numChildren() 
   {
      int children = 0;

      if (left != null)
         children = 1 + left.numChildren();

      if (right != null)
         children = children + 1 + right.numChildren();

      return children;
   }
   
   /**
    * Accessor method for the element in the tree
    * 
    * @return	The object
    */
   public T getElement()
   {
	   return element;
   }
   
   /**
    * Specifically sets the left node
    * 
    * @param left	The node to be set as left
    */
   public void setLeft(BinaryTreeNode<T> left)
   {
	   this.left = left;
   }
   
   /**
    * Specifically sets the right node
    * 
    * @param right	The node to be set as right
    */
   public void setRight(BinaryTreeNode<T> right)
   {
	   this.right = right;
   }
   
   /**
    * Accessor method for the tree node on the left
    * 
    * @return	The node on the left
    */
   public BinaryTreeNode<T> getLeft()
   {
	   return left;
   }
   /**
    * Accessor method for the tree node on the right
    * 
    * @return	The node on the right
    */
   public BinaryTreeNode<T> getRight()
   {
	   return right;
   }
   
}

