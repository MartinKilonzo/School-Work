package Main;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 *  This class includes methods for displaying the image in a window on
 *  the screen
 */

public class PictureViewer extends JFrame {
    JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();

    /**
     * Constructor.
     */
    public PictureViewer() {}

   /**
     * Display the picture in a window on the screen.
     */
    public void show (String fileName) throws MultimediaException {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            contentPane = (JPanel) getContentPane();
            contentPane.setLayout(new BorderLayout());
            setTitle(fileName);

            ImageIcon imicon = new ImageIcon(fileName);
            imageLabel.setIcon(imicon);
            contentPane.add(imageLabel, java.awt.BorderLayout.CENTER);

	    this.pack();
            this.setVisible(true);

	    System.out.println("Press RET to continue");
	    BufferedReader keyboard =  new BufferedReader
		                          (new InputStreamReader(System.in));
	    String c = keyboard.readLine();
	}
        catch (Exception exception) {
            throw new MultimediaException ("Error opening image file "+fileName);
        }

	this.dispose();
    }
}
