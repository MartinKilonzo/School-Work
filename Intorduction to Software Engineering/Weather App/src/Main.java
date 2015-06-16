/**
 * This is the main class which instantiates the program.
 */
import java.awt.EventQueue;

public class Main {

	public static void main(String args[]) throws Exception {	

		final GUI window = new GUI();			// The window through which the user interacts

		// If it is the first run, run the Pop-up
		if(window.initial_use()) {
			final PopUpWindow popup = new PopUpWindow(window);
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						popup.getFrame().setVisible(true);
					} catch (Exception e) { e.printStackTrace();}
				}
			});
		}
		
		// Otherwise it's business as usual:
		else {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						window.getFrame().setVisible(true);
					} catch (Exception e) { e.printStackTrace();}
				}
			});
		}
	}
}





