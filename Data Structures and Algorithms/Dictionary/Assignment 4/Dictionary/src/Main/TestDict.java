package Main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestDict {

    public static void main(String[] args) {
	OrderedDictionary dictionary = new OrderedDictionary();
	String res;

	String words[] = {"homework", "course", "class", "computer", "four"};
	String definitions[] = {"Very enjoyable work that students need to complete outside the classroom.",
				"A series of talks or lessons. For example, CS210.",
				"A set of students taught together,",
				"An electronic machine frequently used by Computer Science students.",
				"One more than three"};

	// Insert one word and then find it
	try {
	    dictionary.insert(words[0],definitions[0],1);
	    res = dictionary.findWord(words[0]);
	    if (res.compareTo(definitions[0]) == 0)
		System.out.println("Test 1 passed");
	    else System.out.println("Test 1 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 1 failed");
	}

	// Try to find an inexistent word
	try {
	    res = dictionary.findWord(words[1]);
	    if (res == "") System.out.println("Test 2 passed");
	    else System.out.println("Test 2 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 2 failed");
	}

	// Try to insert the same word again
	try {
	    dictionary.insert(words[0],definitions[0],1);
	    System.out.println("Test 3 failed");
	}
	catch(DictionaryException e) {
	    System.out.println("Test 3 passed");
	}
	catch (Exception e) {
	    System.out.println("Test 3 failed");
	}

	// Insert and remove a word
	try {
	    dictionary.insert(words[1],definitions[1],1);
	    dictionary.remove(words[1]);
	    res = dictionary.findWord(words[1]);
	    if (res == "") System.out.println("Test 4 passed");
	    else System.out.println("Test 4 failed");
	}
	catch(DictionaryException e) {
	    System.out.println("Test 4 failed");
	}

	// Remove a word not in the dictionary
	try {
	    dictionary.remove(words[3]);
	    System.out.println("Test 5 failed");
	}
	catch(DictionaryException e) {
	    System.out.println("Test 5 passed");
	}
	catch (Exception e) {
	    System.out.println("Test 5 failed");
	}

	// Insert 5 words in the dictionary and test the successor method
	try {
	    dictionary.remove(words[0]);
	    dictionary.insert(words[1],definitions[1],1);
	    dictionary.insert(words[0],definitions[0],1);
	    for (int i = 2; i < 5; ++i)
		dictionary.insert(words[i],definitions[i],1);

	    res = dictionary.successor(words[3]);
	    if (res.compareTo(words[1]) == 0)
		System.out.println("Test 6 passed");
	    else System.out.println("Test 6 failed");
	}
	catch (Exception e) {
	    System.out.println("Test 6 failed");
	}

	// Test the successor method
	try {
	    res = dictionary.successor(words[2]);
	    if (res.compareTo(words[3]) == 0)
		System.out.println("Test 7 passed");
	    else System.out.println("Test 7 failed");
	}
	catch (Exception e) {
	    System.out.println("Test 7 failed");
	}

	//Test the predecessor method
	try {
	    res = dictionary.predecessor(words[0]);
	    if (res.compareTo(words[4]) == 0)
		System.out.println("Test 8 passed");
	    else System.out.println("Test 8 failed");
	}
	catch (Exception e) {
	    System.out.println("Test 8 failed");
	}

	// Test the prdecessor method
	try {
	    res = dictionary.predecessor(words[4]);
	    if (res.compareTo(words[1]) == 0)
		System.out.println("Test 9 passed");
	    else System.out.println("Test 9 failed");
	}
	catch (Exception e) {
	    System.out.println("Test 9 failed");
	}

        // Create a new empty dictionary

	dictionary = new OrderedDictionary();

	try {

	    // Insert a large number of words in the dictionary
	    BufferedReader in = new BufferedReader(new FileReader("Inputs/large.txt"));
	    String word = in.readLine();
	    String definition;
	    boolean test10 = true;

	    try {
		while (word != null) {
		    try {
			definition = in.readLine();
			dictionary.insert(word,definition,1);
			word = in.readLine();
		    }
		    catch (Exception e) {
			test10 = false;
		    }
		}

		if (test10) {
		    // Now, try to find the word "practic"
		    res = dictionary.findWord("practic");
		    if (res.indexOf("Artful; deceitful; skillful.") == -1)
			System.out.println("Test 10 failed");
		    else System.out.println("Test 10 passed");
		}
		else System.out.println("Test 10 failed");
	    }
	    catch (Exception e) {
		System.out.println("Test 10 failed");
	    }

	    // Try to find a word that is not in the dictionary
	    try {
		res = dictionary.findWord("schnell");
		if (res != "") System.out.println("Test 11 failed");
		else System.out.println("Test 11 passed");
	    }
	    catch (Exception e) {
		System.out.println("Test 11 failed");
	    }

	    // Test the remove method
	    try {
		dictionary.remove("practic");
		res = dictionary.findWord("practic");
		if (res == "") System.out.println("Test 12 passed");
		else System.out.println("Test 12 failed");
	    }
	    catch (Exception e) {
		System.out.println("Test 12 failed");
	    }

	    // Try to insert a word that is already in the dictionary
	    try {
		dictionary.insert("pool","Body of water",1);
		System.out.println("Test 13 failed");
	    }
	    catch(DictionaryException e) {
		System.out.println("Test 13 passed");
	    }
	    catch (Exception e) {
		System.out.println("Test 13 failed");
	    }

	    // Test the predecessor method
	    try {
		res = dictionary.predecessor("pony");
		if (res.compareTo("ponvolant") == 0) 
		    System.out.println("Test 14 passed");
		else System.out.println("Test 14 failed");
	    }
	    catch (Exception e) {
		System.out.println("Test 14 failed");
	    }

	    // Test the successor method
	    try {
		res = dictionary.successor("reel");
		if (res.compareTo("reem") == 0)
		    System.out.println("Test 15 passed");
		else System.out.println("Test 15 failed");
	    }
	    catch (Exception e) {
		System.out.println("Test 15 failed");
	    }

	    // Test removing a word and the using successor
	    try {
		dictionary.remove("langate");
		res = dictionary.successor("land");
		if (res.compareTo("langate") == 0)
		    System.out.println("Test 16 passed");
		else System.out.println("Test 16 failed");
	    }
	    catch (Exception e) {
		System.out.println("Test 16 failed");
	    }
	}
	catch (IOException e) {
	    System.out.println("Cannot open file: large.txt");
	}
    }
}
