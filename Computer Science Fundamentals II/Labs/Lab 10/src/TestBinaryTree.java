import java.util.Iterator;
/** Class containing a main program to test the
  * LinkedBinaryTree class.  The test tree constructed
  * in this program looks like this:
  *              6
  *            /   \
  *           4     8
  *          / \   / \
  *         2   5 7  10
  *        / \       /
  *       1   3     9
  * 
  * @author CS1027
  */
public class TestBinaryTree {  
  public static void main (String[] args) {

    LinkedBinaryTree<Integer> leftTree;
    LinkedBinaryTree<Integer> rightTree;
    LinkedBinaryTree<Integer> tree;    
    // construct the left subtree of the binary tree
    LinkedBinaryTree<Integer> t1 = new LinkedBinaryTree<Integer>(1);
    LinkedBinaryTree<Integer> t2 = new LinkedBinaryTree<Integer>(3);
    LinkedBinaryTree<Integer> t3 = new LinkedBinaryTree<Integer>(2,t1,t2);
    t1 = new LinkedBinaryTree<Integer>(5);
    leftTree = new LinkedBinaryTree<Integer>(4,t3,t1);    
    // construct the right subtree of the binary tree
    t1 = new LinkedBinaryTree<Integer>(9);
    t2 = new LinkedBinaryTree<Integer>(10,t1,null);
    t3 = new LinkedBinaryTree<Integer>(7);
    rightTree = new LinkedBinaryTree<Integer>(8,t3,t2);    
    // add the root node
    tree = new LinkedBinaryTree<Integer>(6, leftTree, rightTree);    
    // test isEmpty
    if (tree.isEmpty()) 
      System.out.println("The tree is empty.");
    else
      System.out.println("The tree is not empty.");	 // test size
    System.out.println("The size of the tree is " + tree.size());    
    // check whether the tree contains the element 9
    if (tree.contains(9))
      System.out.println("The tree contains 9.");
    else
      System.out.println("The tree does not contain 9.");
    // test toString to print tree
    System.out.println("Output from toString method:");
    System.out.println(tree.toString());    
    // test the traversals
    System.out.println("Inorder traversal:");
    Iterator<Integer> it = tree.iteratorInOrder();    while(it.hasNext())
      System.out.print(it.next() + " ");    System.out.println();
    System.out.println("Preorder traversal:");    it = tree.iteratorPreOrder();    while(it.hasNext())       System.out.print(it.next() + " ");    System.out.println();
    System.out.println("Postorder traversal:");    it = tree.iteratorPostOrder();    while(it.hasNext())       System.out.print(it.next() + " ");    System.out.println();
     //System.out.println("Level order traversal:");     //it = tree.iteratorLevelOrder();     //while(it.hasNext())     //  System.out.print(it.next() + " ");     //System.out.println();
    System.out.println("\nTest Finished!");  }}