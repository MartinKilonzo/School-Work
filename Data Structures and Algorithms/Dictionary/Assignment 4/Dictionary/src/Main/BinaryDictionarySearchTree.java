package Main;

public class BinaryDictionarySearchTree
{
	//*  Attributes  *//
	
	private BinaryDictionaryTreeNode<DictEntry> root;

	//*  Constructors  *//

	public BinaryDictionarySearchTree() 
	{
		this.root = null;
	}


	//*  Methods  *//

	public static BinaryDictionaryTreeNode<DictEntry> find(BinaryDictionaryTreeNode<DictEntry> root, String word) throws DictionaryException
	{
		if (root == null)
			throw new DictionaryException("The word " + word + " already exists.");
			
		else
		{
			BinaryDictionaryTreeNode<DictEntry> temp = root;
			
			if (word.compareTo(temp.getWord()) > 0)
			{
				temp = root.getRight();
				find(temp, word);
			}
			
			else if (word.compareTo(temp.getWord()) < 0)
			{
				temp = root.getLeft();
				find(temp, word);
			}
			
			else if (word.compareTo(temp.getWord()) == 0)
				return temp;
		}
		
		return null;
	}
	
	public BinaryDictionaryTreeNode<DictEntry> getRoot()
	{
		return this.root;
	}
	
	public static BinaryDictionaryTreeNode<DictEntry> findParent(BinaryDictionaryTreeNode<DictEntry> parent, String word) throws DictionaryException
	{
		BinaryDictionaryTreeNode<DictEntry> leftChild = parent.getLeft();
		BinaryDictionaryTreeNode<DictEntry> rightChild = parent.getRight();
		
		// If the word does not exist in the tree, return the entry that would be the parent if the word was present.
		if ((word.compareTo(parent.getWord()) > 0 && rightChild == null) || (word.compareTo(parent.getWord()) < 0 && leftChild == null))
			return parent;
		
		else
		{
			if (word.compareTo(parent.getWord()) > 0)
			{
				if (word.compareTo(rightChild.getWord()) == 0)
					return parent;
				
				else
					findParent(rightChild, word);
			}
			
			else if (word.compareTo(parent.getWord()) < 0)
			{
				if (word.compareTo(leftChild.getWord()) == 0)
					return parent;
					
				else
					findParent(leftChild, word);
			}
			
			// This case is only applicable if the parent is the root of the tree and thus has no parents itself.
			else if (word.compareTo(parent.getWord()) == 0)
				return null;
		}
		
		return null;
	}

	public static void insert(BinaryDictionaryTreeNode<DictEntry> root, DictEntry entry) throws DictionaryException
	{
		if (root == null)
			root = new BinaryDictionaryTreeNode<DictEntry>(entry);

		else
		{
			String word = entry.word();
			BinaryDictionaryTreeNode<DictEntry> temp = root;

			if (word.compareTo(temp.getWord()) > 0) 
			{
				temp = root.getRight();
				insert(temp, entry);
			}

			else if (word.compareTo(temp.getWord()) < 0)
			{
				temp = root.getLeft();
				insert(temp, entry);
			}
			
			else if (word.compareTo(temp.getWord()) == 0)
				throw new DictionaryException("The word " + word + " already exists in the dictionary.");
		}
	}

	public void remove(BinaryDictionaryTreeNode<DictEntry> root, String removal) throws DictionaryException
	{
		BinaryDictionaryTreeNode<DictEntry> oldNode = find(root, removal);
		
		if (oldNode.getEntry() == null)
			throw new DictionaryException("The word " + removal + " is not found in the dictionary.");
			
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
	
	public BinaryDictionaryTreeNode<DictEntry> largest(BinaryDictionaryTreeNode<DictEntry> root)
	{
		BinaryDictionaryTreeNode<DictEntry> largest = root;
		
		while (largest.getRight() != null)
			largest = largest.getRight();
		
		return largest;
	}
	
	public BinaryDictionaryTreeNode<DictEntry> smallest(BinaryDictionaryTreeNode<DictEntry> root)
	{
		BinaryDictionaryTreeNode<DictEntry> smallest = root;
		
		while (smallest.getLeft() != null)
			smallest = smallest.getLeft();
		
		return smallest;
	}
	
	public BinaryDictionaryTreeNode<DictEntry> predecessor(String word)
	{
		BinaryDictionaryTreeNode<DictEntry> predecessor = this.root;
		
		if (word.compareTo(predecessor.getWord()) <= 0)
		{
			while (predecessor.isInternal() && word.compareTo(predecessor.getWord()) < 0)
				predecessor = predecessor.getLeft();
			
			if (predecessor.getRight() == null)
				return predecessor;
			
			else
				return largest(predecessor.getRight());
		}
		
		if (word.compareTo(predecessor.getWord()) > 0)
		{
			while (predecessor.isInternal() && word.compareTo(predecessor.getWord()) > 0)
				predecessor = predecessor.getRight();
			
			if (predecessor.getLeft() == null)
				return predecessor;
			
			else
				return largest(predecessor.getLeft());
		}
		
		return null;
	}
	
	public BinaryDictionaryTreeNode<DictEntry> successor(String word)
	{
		BinaryDictionaryTreeNode<DictEntry> successor = this.root;
		
		if (word.compareTo(successor.getWord()) > 0)
		{
			while (successor.isInternal() && word.compareTo(successor.getWord()) < 0)
				successor = successor.getRight();
			
			if (successor.getLeft() == null)
				return successor;
			
			else
				return smallest(successor.getLeft());
		}
		
		if (word.compareTo(successor.getWord()) <= 0)
		{
			while (successor.isInternal() && word.compareTo(successor.getWord()) > 0)
				successor = successor.getLeft();
			
			if (successor.getRight() == null)
				return successor;
			
			else
				return smallest(successor.getRight());
		}
		
		return null;
	}
	
	public static String findAll(BinaryDictionaryTreeNode<DictEntry> root, String prefix) throws DictionaryException
	{
		String list = "";
		String word = root.getWord();
		
		// traverse the tree like find until you find a node that contains the prefix. Add it to the list. Use String.startsWith(prefix) on each of its subtrees and add matches to the list.
		if (root.getEntry() == null)
			return list;
			
		else
		{
			if (prefix.compareTo(word) > 0)
			{
				if (root.getWord().startsWith(prefix))
					list += " " + word;
					
				list += findAll(root.getRight(), prefix);
			}
			
			else if (prefix.compareTo(word) <= 0)
			{
				if(root.getWord().startsWith(prefix))
					list += " " + word;
					
				list += findAll(root.getRight(), prefix);
			}
			
			return null;
		}
	}
}
