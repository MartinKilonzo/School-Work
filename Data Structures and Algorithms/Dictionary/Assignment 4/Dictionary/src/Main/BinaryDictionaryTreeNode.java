package Main;

public class BinaryDictionaryTreeNode<T>
{
	//*  Attributes  *//

	private DictEntry entry;
	private BinaryDictionaryTreeNode<DictEntry> parent;
	private BinaryDictionaryTreeNode<DictEntry> left;
	private BinaryDictionaryTreeNode<DictEntry> right;


	//*  Constructors  *//
	
	/**
	 * Constructor to create a binary tree node specifically for use by the ordered dictionary.
	 * 
	 * @param entry 		- The DictEntry object to be inserted into the dictionary.
	 */
	public BinaryDictionaryTreeNode (DictEntry entry, BinaryDictionaryTreeNode<DictEntry> parent)
	{
		this.entry = entry;
		this.parent = parent;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * Constructor to create an empty binary tree node specifically for use by the ordered dictionary's root.
	 * 
	 */
	public BinaryDictionaryTreeNode ()
	{
		this.entry = null;
		this.parent = null;
		this.left = null;
		this.right = null;
	}


	//*  Methods  *//
	
	/**
	 * Accessor method to get the DictEntry object stored in the node.
	 * @return				- The DictEntry object stored in the node.
	 */
	public DictEntry getEntry()
	{
		return this.entry;
	}
	
	/**
	 * Accessor method to get the word stored in the DictEntry.
	 * @return				- The word stored in the DictEntry.
	 */
	public String getWord()
	{
		return this.entry.word();
	}
	
	public int getType()
	{
		return this.entry.type();
	}
	
	/**
	 * Accessor method to get the parent node of the current node.
	 * @return				- The parent node of the current node.
	 */
	public BinaryDictionaryTreeNode<DictEntry> getParent()
	{
		return this.parent;
	}
	
	/**
	 * Accessor method to get the left child node of the current node.
	 * @return				- The left child node of the current node.
	 */
	public BinaryDictionaryTreeNode<DictEntry> getLeft()
	{
		return this.left;
	}

	/**
	 * Accessor method to get the right child node of the current node.
	 * @return				- The right child node of the current node.
	 */
	public BinaryDictionaryTreeNode<DictEntry> getRight()
	{
		return this.right;
	}
	
	
	/**
	 * Setter method to set the current node's contents.
	 * @param entry			- The content to set.
	 */
	public void setEntry(DictEntry entry)
	{
		this.entry = entry;
	}
	
	public void setParent(BinaryDictionaryTreeNode<DictEntry> parent)
	{
		this.parent = parent;
	}

	/**
	 * Setter method to set the left child node of the current binary tree node.
	 * @param left			- The node which will become the left child.
	 */
	public void setLeft(BinaryDictionaryTreeNode<DictEntry> left)
	{
		this.left = left;
	}
	
	/**
	 * Setter method to set the right child node of the current binary tree node.
	 * @param right			- The node which will become the right child.
	 */
	public void setRight(BinaryDictionaryTreeNode<DictEntry> right)
	{
		this.right = right;
	}
	
	/**
	 * Method that returns the number of children (grand children, great grand children, etc.) belonging to the current node.
	 * @return				- The number of successors of this node.
	 */
	public int numChildren()
	{
		int children = 0;

		if (this.left != null)
			children = 1 + this.left.numChildren();

		if (this.right != null)
			children += 1 + this.right.numChildren();

		return children;
	}
	
	/**
	 * Boolean method that returns true if the node is internal and false otherwise.
	 * @return				- True if the node is internal, false otherwise.
	 */
	public boolean isInternal()
	{
		if (this.left != null || this.right != null)
			return true;
		
		else
			return false;
	}
}
