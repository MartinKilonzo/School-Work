package ExceptionExamples;
import java.io.*;

public class ExceptionExample10 {

	public static void main(String[] args) throws Exception {

		/*
		 * - this will compile because the throws keyword in the header says
		 * that all exceptions are thrown, that is, not handled - run this and
		 * type in something that is not an integer (e.g. abc, 3.3)
		 *  
		 */

		BufferedReader keyboard = new BufferedReader(new InputStreamReader(
				System.in), 1);

		System.out.print("Enter an integer: ");
		String userTyped = keyboard.readLine();

		int value = Integer.parseInt(userTyped);

	}
}