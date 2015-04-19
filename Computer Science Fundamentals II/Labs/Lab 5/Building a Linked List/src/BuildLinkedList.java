/**
 * Build a linked list of integers from 1 to 10
 * @author CS1027
 */
public class BuildLinkedList {

	public static void main(String[] args) {
		
		// create a linked list that holds 1, 2, ..., 10
		// by starting at 10 and adding each node at head of list
		
		LinearNode<Integer> head = null;	//create empty linked list
		LinearNode<Integer> intNode;
		
		for (int i = 10; i >= 1; i--)
		{
			// create a new node for i
			intNode = new LinearNode<Integer>(new Integer(i));
			// add it at the head of the linked list
			intNode.setNext(head);
			head = intNode;
		}
		
		// traverse list and display each data item
		// current will point to each successive node, starting at the first node
		
		LinearNode<Integer> current = head; 
		while (current != null)
		{
			System.out.println(current.getElement());
			current = current.getNext();
		}
	}

}
