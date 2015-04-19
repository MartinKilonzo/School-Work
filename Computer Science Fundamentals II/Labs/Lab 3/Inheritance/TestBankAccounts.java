/**
 * TestBankAccounts.java:
 *  This class will test aspects of inheritance for the BankAccount class
 *  and its subclasses.
 * @author CS027b 2007
 */

public class TestBankAccounts {
    
    public static void main(String[] args) {
      
        BankAccount bacc0 = new BankAccount(0);
        System.out.println(bacc0.toString());
        
        BankAccount bacc1 = new BankAccount(5000);
        System.out.println(bacc1.toString());
        
        CheckingAccount chacc1 = new CheckingAccount(500.0);
        System.out.println(chacc1.toString());
                          
        SavingsAccount sacc1 = new SavingsAccount(1000.0, 1.0);
        System.out.println(sacc1.toString()); 
        
        //-------------------------------------------------------
        System.out.println("\nThe deposit, withdraw, and toString methods are overriding methods.");
        
        bacc0 = chacc1;
        System.out.println("\nAssigning \"bacc0\" to \"chacc1\" is legal as the former belongs to the superclass and superclass variables may refer to subclass variables but subclass ones may not refer to superclass ones.");
        
        System.out.println("\n" + bacc0.toString() + ". This uses the toString method in the BankAccount class as \"bacc0\" belongs to that class.");
        
        //chacc1 = bacc0;
        System.out.println("\nAssigning \"chacc1\" to \"bacc0\" is illegal as the latter belongs to the superclass and superclass variables may refer to subclass variables but subclass ones may not refer to superclass ones.");
        
        BankAccount bacc2 = new CheckingAccount(200.0);
        chacc1 = (CheckingAccount)bacc2;
        System.out.println("\nAssigning \"chacc1\" to \"bacc2\" is illegal because the latter belongs to the superclass BankAccount and the former belongs to its subclass.");
        System.out.println("\nAdditionally, after casting, I noticed you can cast downwards through the inheritance tree but not upwards (BankAccount can be converted to CheckingAccount, but not vice versa.");
        
        //bacc0.deductFees();
        chacc1.deductFees();
        //sacc1.deductFees();
        System.out.println("\nThe deductFees method is only defined for objects that belong to CheckingAccount, so it only works on those.");
        
        System.out.println("\nPolymorphism occured where we assigned \"bacc0\" to \" chaac1\" and \"chaac1\" to \" baac2\".");
        
        chacc1.deposit(100.0);
        System.out.println("\nStackOverflowError; This caused an infinitie loop within the method where it was forced to keep depositing the ammount (calling the method) indefinitely.");
    }
    
}
