package Main;

public class OrderedDictionary implements OrderedDictionaryADT 
{
	//*  Attributes  *//
	private BinaryDictionaryTreeNode<DictEntry> root;		// The data structure used to organize the dictionary entries

	//*  Constructors  *//
	
	/**
	 * Constructor to create an empty dictionary tree for use by the dictionary.
	 */
	public OrderedDictionary()
	{
		root = new BinaryDictionaryTreeNode<DictEntry>();
	}

	//*  Methods  *//
	
	/**
	 * Method to find a specific word's definition, permitted it exists in the dictionary.
	 * 
	 * @param word 		- The word to be defined.
	 */
	public String findWord(String word)
	{
		BinaryDictionaryTreeNode<DictEntry> temp = find(root,  word);
		
		if (temp.getEntry() == null)
			return "";
		
		else
			return temp.getEntry().define();

	}
	
	/**
	 * Method to find the type of the definition of a word, permitted it exists in the dictionary.
	 * 
	 * @param word		- The word to search for.
	 * @return			- 1: text definition; 2: image definition; 3: audio definition.
	 */
	public int findType(String word) 
	{
		BinaryDictionaryTreeNode<DictEntry> temp;
		
		temp = find(this.root,  word);
		
		if (temp == null)
			return -1;
		else
			return temp.getType();
	}

	/**
	 * Method which inserts a word, its definition, and the type of its definition into the dictionary.
	 * 
	 * @param word						- The word to be added into the dictionary.
	 * @param definition				- The definition for the word.
	 * @param type						- The type of the definition. 1: text definition; 2: image definition; 3: audio definition.
	 * @throws DictionaryException		- Should the word already exist, throw an exception.
	 */
	public void insert(String word, String definition, int type) throws DictionaryException 
	{
		DictEntry insertion = new DictEntry(word, definition, type);
		this.insertNode(insertion);
	}

	/**
	 * Method which removes an entry from the dictionary.
	 * 
	 * @param word						- The word to be removed.
	 * @throws DictionaryException		- Should the word not exist, throw an exception.
	 */
	public void remove(String word) throws DictionaryException 
	{
		BinaryDictionaryTreeNode<DictEntry> oldNode = find(this.root, word);
		
		if (oldNode.getEntry() == null)
			throw new DictionaryException("The word " + word + " is not found in the dictionary.");
			
		else
		{
			// If one of the child of the node-to-be-removed is a leaf, then the only concern is the non-leaf child:
			if (oldNode.getLeft() == null)
				oldNode = oldNode.getRight();
			
			else if (oldNode.getRight() == null)
				oldNode = oldNode.getLeft();
			
			// If the node to be removed has no leaf children:
			else
			{
				BinaryDictionaryTreeNode<DictEntry> smallest = smallest(oldNode.getRight());
				oldNode.setEntry(smallest.getEntry());
				smallest = smallest.getRight();
			}
		}
	}

	/**
	 * Method which returns the proceeding word in lexical order present in the dictionary.
	 * 
	 *  @param word			- The word to be succeeded.
	 */
	public String successor(String word) 
	{
		BinaryDictionaryTreeNode<DictEntry> temp = this.successorNode(word);
		
		if (temp == null)
			return "There is no word after " + word + " present in the dictionary.";
		
		else
			return temp.getWord();
	}

	/**
	 * Method which returns the preceding word in lexical order present in the dictionary.
	 * 
	 * @param word			- The word to be preceded.
	 */
	public String predecessor(String word) 
	{
		BinaryDictionaryTreeNode<DictEntry> temp = this.predecessorNode(word);
		
		if (temp == null)
			return "There is no word before " + word + " present in the dictionary.";
		
		else
			return temp.getWord();
	}
	
	
	
	/**
	 * Helper method to find a term in the dictionary
	 * 
	 * @param root						- The starting point of the search.
	 * @param word						- The word to be searched for.
	 * @return							- The node containing the entry.
	 * @throws DictionaryException		- If the word does not exist, throw a DictionaryException.
	 */
	private static BinaryDictionaryTreeNode<DictEntry> find(BinaryDictionaryTreeNode<DictEntry> root, String word)
	{
		if (root.getEntry() == null)
			return root;
			
		else
		{			
			while (root.getEntry() != null && word.compareTo(root.getWord()) != 0)
			{
				if (word.compareTo(root.getWord()) > 0)
					root = root.getRight();
				
				else if (word.compareTo(root.getWord()) < 0)
					root = root.getLeft();
			}
		}
		
		return root;
	}
	
	/**
	 * Helper method to insert an entry into the dictionary.
	 * 
	 * @param entry						- The entry to be inserted.
	 * @throws DictionaryException		- Should the entry already exist, throw an exception.
	 */
	private void insertNode(DictEntry entry) throws DictionaryException
	{
		String word = entry.word();
		BinaryDictionaryTreeNode<DictEntry> insertion = find(this.root, word);
		
		if (insertion.getEntry() == null)
		{
			insertion.setEntry(entry);
			insertion.setLeft(new BinaryDictionaryTreeNode<DictEntry>(null, insertion));
			insertion.setRight(new BinaryDictionaryTreeNode<DictEntry>(null, insertion));
		}
		
		else
			throw new DictionaryException("The word " + word + " already exists in the dictionary.");
	}
	
	/**
	 * Helper method that returns the smallest value in a tree.
	 * 
	 * @param root						- The root of the tree.
	 * @return							- The smallest value of the tree.
	 */
	private BinaryDictionaryTreeNode<DictEntry> smallest(BinaryDictionaryTreeNode<DictEntry> root)
	{
		BinaryDictionaryTreeNode<DictEntry> smallest = root;
		
		while (smallest.isInternal() && smallest.getLeft().getEntry() != null)
			smallest = smallest.getLeft();
		
		return smallest;
	}
	
	/**
	 * Helper method that returns the largest value in a tree.
	 * 
	 * @param root						- The root of the tree.
	 * @return							- The largest value of the tree.
	 */
	private BinaryDictionaryTreeNode<DictEntry> largest(BinaryDictionaryTreeNode<DictEntry> root)
	{
		BinaryDictionaryTreeNode<DictEntry> largest = root;
		
		while (largest.isInternal() && largest.getRight().getEntry() != null)
			largest = largest.getRight();
		
		return largest;
	}
	
	/**
	 * Method that returns the entry containing the next word in lexicographic order.
	 * @param word						- The word to succeed.
	 * @return							- The successor node.
	 */
	private BinaryDictionaryTreeNode<DictEntry> successorNode(String word)
	{
		BinaryDictionaryTreeNode<DictEntry> successor = find(this.root, word);
		BinaryDictionaryTreeNode<DictEntry> parent;
		
		if (successor.isInternal() && successor.getRight().isInternal())
			return smallest(successor.getRight());
		
		else
		{	
			parent = successor.getParent();
			
			while (successor != this.root && successor == parent.getRight())
			{
				successor = parent;
				parent = successor.getParent();
			}
			
			if (successor == this.root)
				return null;
			
			else
				return parent;
		}
	}
	
	/**
	 * Method that returns the entry containing the previous word in lexicographic order.
	 * @param word						- The word to precede.
	 * @return							- The predecessor node.
	 */
	private BinaryDictionaryTreeNode<DictEntry> predecessorNode(String word)
	{
		BinaryDictionaryTreeNode<DictEntry> predecessor = find(this.root, word);
		BinaryDictionaryTreeNode<DictEntry> parent;
		
		if (predecessor.isInternal() && predecessor.getLeft().isInternal())
			return largest(predecessor.getLeft());
		
		else
		{	
			parent = predecessor.getParent();
			
			while (predecessor != this.root && predecessor == parent.getLeft())
			{
				predecessor = parent;
				parent = predecessor.getParent();
			}
			
			if (predecessor == this.root)
				return null;
			
			else
				return parent;
		}
	}
}
