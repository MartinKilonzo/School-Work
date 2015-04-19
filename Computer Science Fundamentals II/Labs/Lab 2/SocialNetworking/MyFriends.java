public class MyFriends {
  public static void main(String args[]) {

    SocialNetwork contacts = new SocialNetwork();

    contacts.add("Snoopy","Dog","snoopy@uwo.ca");
    contacts.add("Felix","Cat","felix@uwo.ca");
    contacts.add("Mickey","Mouse","mickey@uwo.ca");

    System.out.println(contacts.toString());
    System.out.println("I have " + contacts.getNumFriends() + " friends in my list.");
    
    contacts.remove("Mickey", "Mouse");
    System.out.println("\nMickey Mouse was successfully removed from the list. The new list is: \n" + contacts.toString() + "\nI now have " + contacts.getNumFriends() + " friends in my list.");
    
    contacts.remove("Mickey", "Mouse");
    System.out.println("\nMickey Mouse was unsuccessfully removed from the list. The list is: \n" + contacts.toString() + "\nI have " + contacts.getNumFriends() + " friends in my list.");
    
  }
}
