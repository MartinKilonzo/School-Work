/**
 * This class is used on the first run of the program to prompt the user for a location.
 */
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class PopUpWindow {

	private JFrame frame;				// The visuals of the window
	private JTextField textField;		// The text field where the user inputs their initial location
	final GUI window;					// The main GUI object created on initial initialization. This is where the data from this window is passed
	
	/**
	 * Launches the application.
	 */
	String location = null;
	int selected_units = 1;
	Weather weather = new Weather();
	int cityID=0;
	

	/**
	 * Creates the application.
	 */
	public PopUpWindow(GUI window) {
		this.window = window;
		initialize();
	}

	/**
	 * Initializes the contents of the frame.
	 */
	private void initialize() {
		// Defining the frame
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 220);
				
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(0, 191, 255));
		
		textField = new JTextField();
		textField.setBounds(125, 86, 200, 26);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		// Defining the message
		JLabel lblHiWeSee = new JLabel("<html>Hi! We see this your first time using the Team12 weather app.<br>Please set your Location</html>",SwingConstants.CENTER);
		lblHiWeSee.setForeground(Color.WHITE);
		lblHiWeSee.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblHiWeSee.setBounds(0,10 , 434, 50);
		frame.getContentPane().add(lblHiWeSee);
		
		// Defining the submit button
		JButton btnNewButton = new JButton("Set Location");
		btnNewButton.setBounds(170, 120, 110, 23);
		btnNewButton.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				setLocation();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							window.getFrame().setVisible(true);
						} catch (Exception e) { e.printStackTrace();}
					}
				});
				frame.dispose();
			}
			
		});
		
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setLocation();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							window.getFrame().setVisible(true);
						} catch (Exception e) { e.printStackTrace();}
					}
				});
				frame.dispose();
			}
		});
		frame.getContentPane().add(btnNewButton);
	}
	
	/**
	 * Getter method which returns the frame of the window
	 * @return	the frame
	 */
	public JFrame getFrame(){
		return frame;
	}
	
	/**
	 * User input is passed through this method
	 */
	private void setLocation(){	
		window.change_location(textField.getText());
	}
	
	
}
