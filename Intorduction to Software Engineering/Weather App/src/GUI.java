import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GUI {

	private final static int MAX_INPUTS = 2;
	private final static String current_URL = "http://api.openweathermap.org/data/2.5/weather?id=";		// The base chunk of the web address
	private final static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast";
	private final static String LOCATION_URL_1 = "http://api.openweathermap.org/data/2.5/find?q=";		// The first chunk of the location query address
	private final static String LOCATION_URL_2 = "&type=like";											// The second chunk of the location query address
	private final static String ST_FORECAST_URL = "?id=";												// The appendment to the forecast url to fetch the short term weather forecast
	private final static String LT_FORECAST_URL_1 = "/daily?id=";											// The first appendment to the forecast url to fetch the long term forecast data			
	private final static String LT_FORECAST_URL_2 = "&mode=json&cnt=7";									// The second appendment to the forecast url to fetch the long term forecast data
	private final static String OWM_APP_ID = "&APPID=1f70e8a244750a90380235c1cd68636d";					// The API ID
	private final static String[] UNITS = {"&units=metric&mode=json", "&units=imperial&mode=json", ""};						// An array containing the three appendments for unit conversion
	private final String DEGREE = "\u00b0";																//The degree symbol in UNIcode
	private final int SCALE_SIZE1 = 45;
	private final int SCALE_SIZE2 = 70;

	private int cityID = 0;													// The API ID referring to the active city
	private int selected_units = 0;
	private JFrame frame;													//frame is the frame that will have contain all the graphical content 
	private JTextField txtSearch = new JTextField();						//txtSearch is a textfield that the user will use to search for weather locations [WIP]

	private final JLabel curr_loc = new JLabel("current Location");  		//curr_loc is a JLabel that displays the name of the current location that the user is viewing  
	private final JComboBox<String> loc_list = new JComboBox();

	DecimalFormat df = new DecimalFormat("#.0");

	//JLabels that display what each value represents	
	private final JLabel lbl_sunrise = new JLabel("Sunrise Time:");				// Set the sunrise time JLabel
	private final JLabel lbl_sunset = new JLabel("Sunset Time:");				// Set the sunset time JLabel
	private final JLabel lbl_temp_high = new JLabel("H:");						// Set High Temp JLabel
	private final JLabel lbl_temp_low = new JLabel("L:");						// Set Low Temp JLabel
	private final JLabel lbl_humidity = new JLabel("Humidity:");				// Set Humidity JLabel
	private final JLabel lbl_pressure = new JLabel("Air Pressure:");			// Set hte Air Pressure JLabel
	private final JLabel lbl_wind = new JLabel("Wind:");						// Set Wind JLabel
	private final JLabel lbl_precip = new JLabel("Precipitation:");				// Set Precip Amount JLabel
	private final JLabel lbl_refresh = new JLabel("Last Updated at: ");
	private final JLabel lbl_loc_list = new JLabel("Did you mean...?");

	// initialize labels
	private JLabel temp = new JLabel();				// Temperature JLabel
	private JLabel temp_high = new JLabel();		// High Temp JLabel
	private JLabel temp_low = new JLabel();			// Low Temp JLabel
	private JLabel sunrise = new JLabel();
	private JLabel sunset = new JLabel();
	private JLabel humidity = new JLabel();			// Humidity JLabel
	private JLabel pressure = new JLabel();
	private JLabel wind = new JLabel();				// Wind JLabel
	private JLabel precip = new JLabel();			// Precipitation Amount JLabel
	private JLabel condition = new JLabel();		// Condition JLabel
	private JLabel icon = new JLabel();				// Icon JLabel
	private JPanel short_term = new JPanel();		// Short Term Tab Definition
	private JPanel long_term = new JPanel();		// Long Term Tab Definition
	//Error JLabel declarations
	private JLabel invalid_loc = new JLabel("Invalid Location");		// location doesn't exist
	private JLabel duplicate_loc = new JLabel("Already in List");		// location already populated in list
	
	// weather object to store weather data
	private String location = null;				 	// Location Parameter as a String
	private Weather weather;						// Weather Parameter as a Weather Object
	private Forecast st_forecast;					// Short Term Forecast as Forecast Object
	private Forecast lt_forecast;					// Long Term Forecast as Forecast Object

	// buttons
	private final JRadioButton celsius = new JRadioButton(DEGREE + "C");				// celsius radio button
	private final JRadioButton fahrenheit = new JRadioButton(DEGREE + "F");				// fahrenheit radio button
	private final JRadioButton kelvin = new JRadioButton(DEGREE + "K");					// Kelvin radio button
	private final JButton btnSearch = new JButton();
	private final JButton btnRefresh = new JButton();	
	private JLabel refresh = new JLabel();

	private final JLabel[][] st_lbls = new JLabel[8][5];
	private final JLabel[][] lt_lbls = new JLabel[6][7];

	private boolean adding = false;
	/**
	 * Calls initialize to create the application.
	 */
	public GUI() {
		frame = new JFrame();

		for (int i = 0; i < st_lbls.length; i++) {				// create short term JLabels
			for (int j = 0; j < st_lbls[0].length; j++) {
				st_lbls[i][j] = new JLabel("");
			}}

		for (int i = 0; i < lt_lbls.length; i++) {				// create long term JLabels
			for (int j = 0; j < lt_lbls[0].length; j++) {
				lt_lbls[i][j] = new JLabel("");
			}}

		retrieve_data();					// retrieve data
		create_labels();					// fill labels
		initialize();						// initialize

		if (this.get_city_ID() != 0) {
			refresh_data();						// refresh data following initialization
			weather_alert();					// calls weather alert method to alert user of extreme weather
		}
	}

	public JFrame getFrame(){
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}

		//frame properties
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("app_icon_trans2.png"));
		frame.setTitle("Team 12 - Weather App");							// Name Team 12 - Weather App
		frame.setContentPane(new JLabel(new ImageIcon("background.jpg")));
		frame.setResizable(false);											// Disallow Resizing of Window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Action listener that will call the store_data method on closing the window via the close button
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if(cityID != 0){
					store_data(cityID);						// Data Persistence
				}
				System.exit(0);
			}
		});

		//Action listener for txtSearch so that on enter the location is updated to that of the txtSearch
		txtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//invalid_loc.setVisible(false);
				change_location(txtSearch.getText());
			}
		});

	

		//Sets radio buttons in group so only one can be selected at a time
		ButtonGroup units = new ButtonGroup();
		units.add(celsius);						// celsius button
		units.add(fahrenheit);					// fahrenheit button
		units.add(kelvin);						// kelvin button
		
		// Enables the radio button appropriate to the initialized units value
		switch(selected_units) {
		case 0: celsius.doClick(); break;
		case 1: fahrenheit.doClick(); break;
		case 2: kelvin.doClick(); break;
		}

		//Unit conversion listeners for the radio buttons
		celsius.addActionListener(new ActionListener() {			// action listener for celsius button
			public void actionPerformed(ActionEvent arg0) {
				selected_units = 0;
				refresh_data();		// refresh data following conversion
			}
		});

		fahrenheit.addActionListener(new ActionListener() {			// action listener for fahrenheit button
			public void actionPerformed(ActionEvent arg0) {
				selected_units = 1;
				refresh_data();		// refresh data following conversion
			}
		});

		kelvin.addActionListener(new ActionListener() {				// action listener for kelvin button
			public void actionPerformed(ActionEvent arg0) {
				selected_units = 2;
				refresh_data();		// refresh data following conversion
			}
		});

		//ActionListener for btnSearch that updates curr_loc to the text in txtSearch 
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//invalid_loc.setVisible(false);
				change_location(txtSearch.getText());
			}
		});

		// ActionListener for btnRefresh that refreshes data and updates timestamp
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh_data();
			}
		});

		loc_list.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(!adding){
					invalid_loc.setVisible(false);
					JComboBox loc_list = (JComboBox)event.getSource();
					String selected = (String)loc_list.getSelectedItem();
					change_location(selected.toString());
					SwingUtilities.updateComponentTreeUI(frame);
				}
			}
		});
		
		frame.getLayeredPane().add(loc_list);

		invalid_loc.setFont(new Font("Calibri", Font.BOLD, 12));	// response to invalid location
		invalid_loc.setForeground(Color.RED);
		invalid_loc.setBounds(423, 155, 96, 22);
		invalid_loc.setVisible(false);
		frame.getLayeredPane().add(invalid_loc);

		//Error is for if the location that the user is trying to store is already in the list
		duplicate_loc.setForeground(Color.RED);
		duplicate_loc.setFont(new Font("Calibri", Font.BOLD, 12));
		duplicate_loc.setBounds(409, 234, 97, 22);
		duplicate_loc.setVisible(false);
		frame.getLayeredPane().add(duplicate_loc);

		// create tabbed pane for forecasts
		JTabbedPane weather_view = new JTabbedPane(JTabbedPane.TOP);
		weather_view.setFont(new Font("Calibri", Font.PLAIN, 14));
		weather_view.setBounds(45, 270, 1075, 300);
		frame.getLayeredPane().add(weather_view);

		weather_view.add("Short Term", short_term);		// short term tab creation
		weather_view.add("Long Term", long_term);		// long term tab creation
		//weather_view.add("Graphs", Graphs);

		short_term.setLayout(null);						// layout for short term initialized
		long_term.setLayout(null);						// layout for long term initialized
		//Graphs.setLayout(null);

		short_term.setBackground(new Color(0,0,0,0));	// background for short term tab
		long_term.setBackground(new Color(0,0,0,0));	// background for long term tab
		//Graphs.setBackground(new Color(0,0,0,0));

		JPanel panelBG = new JPanel();
		panelBG.setBackground(new Color(0,0,0,50));
		panelBG.setBounds(40, 30, 1085, 585);
		frame.getLayeredPane().add(panelBG);
		panelBG.setLayout(null);
	}

	/**
	 * Deserializes the data so that the program can load the last weather object that was being viewed by the user
	 * 
	 */
	public void retrieve_data() {
		File to_load = new File("./config.ser");			// file accessed to read

		if(to_load.exists() && !to_load.isDirectory()) {	// check if file exists
			int[] saved_data;								// saved data in integer array
			try {	
				FileInputStream fileIn = new FileInputStream("./config.ser");	// read from file
				ObjectInputStream in = new ObjectInputStream(fileIn);			// create object from file
				saved_data = (int[]) in.readObject();							// cast object to integer array
				in.close();					// close object
				fileIn.close();				// close file
			} catch(IOException i) {		
				i.printStackTrace();
				return;
			} catch(ClassNotFoundException c) {					// if weather class broken, throw exception
				System.out.println("Weather class not found");
				c.printStackTrace();
				return;
			}	

			cityID = saved_data[0];					// data persistence (city)
			selected_units = saved_data[1];			// data persistence (unit selection)
			if(selected_units == 0){				// sets unit based on persistent data
				celsius.setSelected(true);			
			}
			else if(selected_units == 1){
				fahrenheit.setSelected(true);
			}
			else if(selected_units == 2){
				kelvin.setSelected(true);
			}
			
			//System.out.println("Deserialized Weather...");

			refresh_data();							// refresh data
		}
	}

	/**
	 * Serializes a weather object to be saved for next time the program is executed. (Keeps the data persistent) 
	 * @param store - store is a Weather object to be serialized and stored.
	 */
	public void store_data(int store) {
		try {
			FileOutputStream fileOut = new FileOutputStream("./config.ser");		// output file creation
			ObjectOutputStream out = new ObjectOutputStream(fileOut);				// output object created
			int[] save_data = {store, selected_units};
			out.writeObject(save_data);				// close object
			out.close();							// close data
			fileOut.close();						// close file
			//System.out.println("Serialized data is saved in ./config.ser");
		} catch(IOException i) { i.printStackTrace();}			
	}

	/**
	 * Updates the weather object to the new (changed) values and changes the JLabels to their appropriate values
	 */
	public void update_data() {

		temp.setText(df.format(weather.get_temp())+ DEGREE);	// sets temperature data
		temp_high.setText(weather.get_temp_max() + DEGREE);		// sets high temp data
		temp_low.setText(weather.get_temp_min() + DEGREE);		// sets low temp data

		sunrise.setText(weather.get_sunrise());					// sets sunrise data
		sunset.setText(weather.get_sunset());					// sets sunset data

		humidity.setText(weather.get_humidity() + "%");			// sets humidity data
		pressure.setText(weather.get_pressure() + " hpa");		// sets air pressure data
		wind.setText(wind_data(weather));						// sets wind data
		precip.setText(weather.get_precip() + " mm");			// sets precipitation data
		condition.setText(weather.get_condition());				// sets conditional data
		icon.setIcon(new ImageIcon(weather.get_icon()));		// sets icon

		// st_weather row 1
		for (int i = 0; i < st_lbls.length/2; i++) {		
			// temperature
			st_lbls[i][0].setText((st_forecast.get(i).get_temp() + DEGREE));
			st_lbls[i][0].setForeground(Color.WHITE);
			st_lbls[i][0].setFont(new Font("Calibri", Font.PLAIN, 18));
			st_lbls[i][0].setBounds(100 + 250 * i, 75, 80, 25); // Optional 70 as horizontal displacement
			short_term.add(st_lbls[i][0]);

			// icon
			st_lbls[i][1].setIcon(scale_image(st_forecast.get(i).get_icon(), SCALE_SIZE1, SCALE_SIZE1));
			st_lbls[i][1].setFont(new Font("Calibri", Font.PLAIN, 14));
			st_lbls[i][1].setHorizontalAlignment(SwingConstants.RIGHT);
			st_lbls[i][1].setBounds(160 + 250 * i, 70, SCALE_SIZE1, SCALE_SIZE1);
			short_term.add(st_lbls[i][1]);

			// sky condition
			st_lbls[i][2].setText(st_forecast.get(i).get_condition());
			st_lbls[i][2].setForeground(Color.WHITE);
			st_lbls[i][2].setHorizontalAlignment(SwingConstants.CENTER);
			st_lbls[i][2].setFont(new Font("Calibri", Font.PLAIN, 14));
			st_lbls[i][2].setBounds(100 + 250 * i, 50, 100, 14);
			short_term.add(st_lbls[i][2]);	

			// precipitation
			st_lbls[i][3].setText(st_forecast.get(i).get_precip() + " mm");
			st_lbls[i][3].setForeground(Color.WHITE);
			st_lbls[i][3].setFont(new Font("Calibri", Font.PLAIN, 14));
			st_lbls[i][3].setBounds(185 + 250 * i, 115, 100, 14);
			short_term.add(st_lbls[i][3]);
			
			// date of weather data
			st_lbls[i][4].setText(st_forecast.get(i).get_st_time());
			st_lbls[i][4].setForeground(Color.WHITE);
			st_lbls[i][4].setHorizontalAlignment(SwingConstants.CENTER);
			st_lbls[i][4].setFont(new Font("Calibri", Font.BOLD, 16));
			st_lbls[i][4].setBounds(100 + 250 * i, 30, 100, 14);
			short_term.add(st_lbls[i][4]);	
			
		}

		// st_weather row 2
		int  c = 0;
		for (int j = 4; j < st_lbls.length; j++, c++){
			// temperature
			st_lbls[j][0].setText((st_forecast.get(j).get_temp() + DEGREE));
			st_lbls[j][0].setForeground(Color.WHITE);
			st_lbls[j][0].setFont(new Font("Calibri", Font.PLAIN, 18));
			st_lbls[j][0].setBounds(95 + 250 * c, 215, 80, 25); 
			short_term.add(st_lbls[j][0]);

			// icon
			st_lbls[j][1].setIcon(scale_image(st_forecast.get(j).get_icon(), SCALE_SIZE1, SCALE_SIZE1));
			st_lbls[j][1].setFont(new Font("Calibri", Font.PLAIN, 14));
			st_lbls[j][1].setHorizontalAlignment(SwingConstants.RIGHT);
			st_lbls[j][1].setBounds(165 + 250 * c, 210, SCALE_SIZE1, SCALE_SIZE1);
			short_term.add(st_lbls[j][1]);

			// sky condition
			st_lbls[j][2].setText(st_forecast.get(j).get_condition());
			st_lbls[j][2].setForeground(Color.WHITE);
			st_lbls[j][2].setHorizontalAlignment(SwingConstants.CENTER);
			st_lbls[j][2].setFont(new Font("Calibri", Font.PLAIN, 14));
			st_lbls[j][2].setBounds(100 + 250 * c, 190, 100, 14);
			short_term.add(st_lbls[j][2]);

			// precipitation
			st_lbls[j][3].setText(st_forecast.get(j).get_precip() + " mm");
			st_lbls[j][3].setForeground(Color.WHITE);
			st_lbls[j][3].setFont(new Font("Calibri", Font.PLAIN, 14));
			st_lbls[j][3].setBounds(185 + 250 * c, 255, 100, 14);
			short_term.add(st_lbls[j][3]);
			
			// date of weather data
			st_lbls[j][4].setText(st_forecast.get(j).get_st_time());
			st_lbls[j][4].setForeground(Color.WHITE);
			st_lbls[j][4].setHorizontalAlignment(SwingConstants.CENTER);
			st_lbls[j][4].setFont(new Font("Calibri", Font.BOLD, 16));
			st_lbls[j][4].setBounds(100 + 250 * c, 170, 100, 14);
			short_term.add(st_lbls[j][4]);	
		}

		// lt_weather
		for (int i = 0; i < lt_lbls.length; i++) {
			// temperature
			lt_lbls[i][0].setText(df.format(lt_forecast.get(i).get_temp())+ DEGREE);
			lt_lbls[i][0].setForeground(Color.WHITE);
			lt_lbls[i][0].setFont(new Font("Calibri", Font.PLAIN, 18));
			lt_lbls[i][0].setBounds(35 + 180 * i, 105, 100, 18);
			long_term.add(lt_lbls[i][0]);
			
			// icon
			lt_lbls[i][1].setIcon(scale_image(lt_forecast.get(i).get_icon(), SCALE_SIZE2, SCALE_SIZE2));
			lt_lbls[i][1].setFont(new Font("Calibri", Font.PLAIN, 14));
			lt_lbls[i][1].setHorizontalAlignment(SwingConstants.RIGHT);
			lt_lbls[i][1].setBounds(85 + 180 * i, 80, SCALE_SIZE2, SCALE_SIZE2);
			long_term.add(lt_lbls[i][1]);

			// sky condition
			lt_lbls[i][2].setText(lt_forecast.get(i).get_condition());
			lt_lbls[i][2].setForeground(Color.WHITE);
			lt_lbls[i][2].setHorizontalAlignment(SwingConstants.CENTER);
			lt_lbls[i][2].setFont(new Font("Calibri", Font.PLAIN, 14));
			lt_lbls[i][2].setBounds(30 + 180 * i, 45, 120, 14);
			long_term.add(lt_lbls[i][2]);
			
			// temp_high
			lt_lbls[i][3].setText(lt_forecast.get(i).get_temp_max() + DEGREE);
			lt_lbls[i][3].setForeground(Color.WHITE);
			lt_lbls[i][3].setHorizontalAlignment(SwingConstants.RIGHT);
			lt_lbls[i][3].setFont(new Font("Calibri", Font.PLAIN, 12));
			lt_lbls[i][3].setBounds(60 + 180 * i, 180, 100, 14);
			long_term.add(lt_lbls[i][3]);
			
			// temp_low
			lt_lbls[i][4].setText(lt_forecast.get(i).get_temp_min() + DEGREE);
			lt_lbls[i][4].setForeground(Color.WHITE);
			lt_lbls[i][4].setHorizontalAlignment(SwingConstants.RIGHT);
			lt_lbls[i][4].setFont(new Font("Calibri", Font.PLAIN, 12));
			lt_lbls[i][4].setBounds(60 + 180 * i, 210, 100, 14);
			long_term.add(lt_lbls[i][4]);
			
			// precipitation
			lt_lbls[i][5].setText(lt_forecast.get(i).get_precip() + " mm");
			lt_lbls[i][5].setForeground(Color.WHITE);
			lt_lbls[i][5].setHorizontalAlignment(SwingConstants.RIGHT);
			lt_lbls[i][5].setFont(new Font("Calibri", Font.PLAIN, 12));
			lt_lbls[i][5].setBounds(60 + 180 * i, 240, 100, 14);
			long_term.add(lt_lbls[i][5]);
			
			// date of weather data
			lt_lbls[i][6].setText(lt_forecast.get(i).get_lt_date());
			lt_lbls[i][6].setForeground(Color.WHITE);
			lt_lbls[i][6].setHorizontalAlignment(SwingConstants.CENTER);
			lt_lbls[i][6].setFont(new Font("Calibri", Font.BOLD, 16));
			lt_lbls[i][6].setBounds(40 + 180 * i, 20, 100, 14);
			long_term.add(lt_lbls[i][6]);
		}
	}

	/**
	 * Helper method to set the location.
	 * @param location			- The location to set
	 */
	public void setLocation(String location) {
		curr_loc.setText(location);
	}

	/**
	 * Method to query OWM API for a list of locations based on the content in location array.
	 * 
	 * @param location					- The array containing the location tokens to search for
	 * @return							- A url containing an XML file with all of the matching locations
	 * @throws MalformedURLException	- Thrown if the url is invalid
	 */
	private static String location(String[] location) throws MalformedURLException {
		String url = LOCATION_URL_1 + location[0];

		for (int i = 1; i < location.length; i++) {
			if (location[i] != null)
				url +="," + location[i];
		}

		url += LOCATION_URL_2 + OWM_APP_ID;

		try {
			new URL(url).openStream();
		} catch (Exception e) { throw new MalformedURLException();}

		return url;
	}

	/**
	 * Method which refreshes the weather data.
	 */
	public void refresh_data() {
		String current_url = current_URL + cityID + UNITS[selected_units] + OWM_APP_ID;					// current url
		String st_forecast_url = FORECAST_URL + ST_FORECAST_URL + cityID + UNITS[selected_units] + OWM_APP_ID;	// st url
		String lt_forecast_url = FORECAST_URL + LT_FORECAST_URL_1 + cityID + UNITS[selected_units] + LT_FORECAST_URL_2 + OWM_APP_ID;	// lt url

		WeatherParser parser = new WeatherParser();
		// parses each url, assigns to a forecast
		try {
			weather = parser.get_current(current_url);
			st_forecast = parser.get_st_forecast(st_forecast_url);
			lt_forecast = parser.get_lt_forecast(lt_forecast_url);
			update_data();
			curr_loc.setText(weather.get_location());
		} catch (Exception e) {
			invalid_loc.setVisible(true);				// throw exception for invalid location
			
			}

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss z");
		refresh.setText(dateFormat.format(date));	

		SwingUtilities.updateComponentTreeUI(frame);
	}

	/**
	 * Helper method which returns a formatted string containing the wind data.
	 * @param weather				- The Weather object containing the wind data
	 * @return						- A formatted string detailing the wind speed and cardinal direction
	 */
	private String wind_data(Weather weather) {
		String speed_unit;

		if (selected_units == 1) { speed_unit = " m/h";}	// convert wind data to match user unit selection
		else { speed_unit = " km/h";}

		// Converting the degree into a cardinal direction: 
		String direction;
		String[] caridnals = {" N", " NNE", " NE", " ENE", " E", " ESE", " SE", " SSE", " S", " SSW", " SW", " WSW", " W", " WNW", " NW", " NNW", " N"};

		direction = caridnals[(int)Math.round((weather.get_wind_deg() % 3600) / 225)];

		return weather.get_wind_speed() + speed_unit + direction; 	// combine wind speed and direction
	}

	/**
	 * Method scales image icon based on GUI display
	 * @param image 	- represents the image
	 * @param x			- x coordinate of image
	 * @param y			- y coordinate of image
	 * @return			- scaled image
	 */
	private ImageIcon scale_image(String image, int x, int y) {
		Image scale_image = new ImageIcon(image).getImage().getScaledInstance(x, y, Image.SCALE_DEFAULT);
		return new ImageIcon(scale_image);
	}

	/**
	 * Method creates GUI Labels
	 */
	private void create_labels() {

		// Properties of txtSearch
		txtSearch.setFont(new Font("Calibri", Font.PLAIN, 28));
		txtSearch.setToolTipText("");
		txtSearch.setBounds(420, 120, 280, 40);
		frame.getLayeredPane().add(txtSearch);
		txtSearch.setColumns(10);

		// search button
		btnSearch.setForeground(SystemColor.desktop);
		btnSearch.setIcon(scale_image("search.png", 42, 42));
		btnSearch.setBounds(705, 120, 42, 42);
		frame.getLayeredPane().add(btnSearch);

		// create label for drop down list
		loc_list.setBounds(420, 225, 250, 30);
		loc_list.setVisible(loc_list.getItemCount() > 1);
		
		lbl_loc_list.setForeground(Color.WHITE);
		lbl_loc_list.setFont(new Font("Calibri", Font.BOLD, 16));
		lbl_loc_list.setBounds(423, 200, 200, 30);
		frame.getLayeredPane().add(lbl_loc_list);
		lbl_loc_list.setVisible(loc_list.isVisible());

		// Properties of curr_loc
		curr_loc.setForeground(new Color(255, 255, 255));
		curr_loc.setFont(new Font("Calibri", Font.BOLD, 40));
		curr_loc.setBounds(420, 60, 280, 55);
		frame.getLayeredPane().add(curr_loc);
		frame.setBounds(100, 100, 1175, 675);

		// create labels for refresh timestamp
		lbl_refresh.setForeground(Color.WHITE);
		lbl_refresh.setFont(new Font("Calibri", Font.BOLD, 16));
		lbl_refresh.setBounds(810, 60, 200, 14);
		frame.getLayeredPane().add(lbl_refresh);
		refresh.setForeground(Color.WHITE);
		refresh.setBounds(940, 60, 100, 14);
		refresh.setHorizontalAlignment(SwingConstants.RIGHT);
		refresh.setFont(new Font("Calibri", Font.BOLD, 16));
		frame.getLayeredPane().add(refresh);

		// refresh button	
		btnRefresh.setForeground(SystemColor.desktop);
		btnRefresh.setIcon(scale_image("refresh.png", 40, 40));
		btnRefresh.setBounds(1075, 40, 40, 40);
		frame.getLayeredPane().add(btnRefresh);

		//Radio buttons that are used to convert between units[WIP]
		celsius.setForeground(Color.WHITE);
		celsius.setBounds(420, 170, 42, 23);
		frame.getLayeredPane().add(celsius);
		fahrenheit.setForeground(Color.WHITE);
		fahrenheit.setBounds(470, 170, 42, 23);
		frame.getLayeredPane().add(fahrenheit);
		kelvin.setForeground(Color.WHITE);
		kelvin.setBounds(520, 170, 42, 23);
		frame.getLayeredPane().add(kelvin);

		// create label for icon
		icon.setBounds(25, 0, 270, 250);
		icon.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getLayeredPane().add(icon);

		// create labels for condition
		condition.setForeground(Color.WHITE);
		condition.setFont(new Font("Calibri", Font.BOLD, 20));
		condition.setHorizontalAlignment(SwingConstants.CENTER);
		condition.setBounds(25, 80, 270, 250);
		frame.getLayeredPane().add(condition);

		// create label for temp
		temp.setHorizontalAlignment(SwingConstants.CENTER);
		temp.setFont(new Font("Calibri", Font.PLAIN, 60));
		temp.setBounds(230, 90, 180, 80);
		temp.setBackground(new Color(0,0,0,0));
		temp.setForeground(Color.WHITE);
		frame.getLayeredPane().add(temp);

		// create labels for temp_high
		lbl_temp_high.setBounds(280, 75, 24, 22);
		lbl_temp_high.setBackground(new Color(0,0,0,0));
		lbl_temp_high.setForeground(Color.WHITE);
		lbl_temp_high.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbl_temp_high.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getLayeredPane().add(lbl_temp_high);
		temp_high.setForeground(Color.WHITE);
		temp_high.setFont(new Font("Calibri", Font.PLAIN, 18));
		temp_high.setHorizontalAlignment(SwingConstants.CENTER);
		temp_high.setBounds(300, 75, 50, 22);
		temp_high.setBackground(new Color(0,0,0,0));
		frame.getLayeredPane().add(temp_high);

		// create labels for temp_low
		lbl_temp_low.setBounds(280, 155, 24, 22);
		lbl_temp_low.setBackground(new Color(0,0,0,0));
		lbl_temp_low.setForeground(Color.WHITE);
		lbl_temp_low.setFont(new Font("Calibri", Font.PLAIN, 18));
		lbl_temp_low.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getLayeredPane().add(lbl_temp_low);
		temp_low.setForeground(Color.WHITE);
		temp_low.setFont(new Font("Calibri", Font.PLAIN, 18));
		temp_low.setHorizontalAlignment(SwingConstants.CENTER);
		temp_low.setBounds(300, 155, 50, 22);
		temp_low.setBackground(new Color(0,0,0,0));
		frame.getLayeredPane().add(temp_low);

		// create labels for sunrise
		lbl_sunrise.setForeground(Color.WHITE);
		lbl_sunrise.setFont(new Font("Calibri", Font.PLAIN, 14));
		lbl_sunrise.setBounds(810, 100, 175, 20);
		frame.getLayeredPane().add(lbl_sunrise);
		sunrise.setForeground(Color.WHITE);
		sunrise.setBounds(935, 100, 100, 20);
		sunrise.setHorizontalAlignment(SwingConstants.RIGHT);
		sunrise.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getLayeredPane().add(sunrise);

		// create labels for sunset
		lbl_sunset.setForeground(Color.WHITE);
		lbl_sunset.setFont(new Font("Calibri", Font.PLAIN, 14));
		lbl_sunset.setBounds(810, 130, 175, 20);
		frame.getLayeredPane().add(lbl_sunset);
		sunset.setForeground(Color.WHITE);
		sunset.setBounds(935, 130, 100, 20);
		sunset.setHorizontalAlignment(SwingConstants.RIGHT);
		sunset.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getLayeredPane().add(sunset);

		// create labels for humidity
		lbl_humidity.setForeground(Color.WHITE);
		lbl_humidity.setFont(new Font("Calibri", Font.PLAIN, 14));
		lbl_humidity.setBounds(810, 160, 175, 20);
		frame.getLayeredPane().add(lbl_humidity);
		humidity.setForeground(Color.WHITE);
		humidity.setBounds(950, 160, 85, 20);
		humidity.setHorizontalAlignment(SwingConstants.RIGHT);
		humidity.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getLayeredPane().add(humidity);

		// create labels for pressure
		lbl_pressure.setForeground(Color.WHITE);
		lbl_pressure.setFont(new Font("Calibri", Font.PLAIN, 14));
		lbl_pressure.setBounds(810, 190, 175, 20);
		frame.getLayeredPane().add(lbl_pressure);
		pressure.setForeground(Color.WHITE);
		pressure.setBounds(950, 190, 85, 20);
		pressure.setHorizontalAlignment(SwingConstants.RIGHT);
		pressure.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getLayeredPane().add(pressure);

		// create labels for wind
		lbl_wind.setForeground(Color.WHITE);
		lbl_wind.setFont(new Font("Calibri", Font.PLAIN, 14));
		lbl_wind.setBounds(810, 220, 175, 20);
		frame.getLayeredPane().add(lbl_wind);
		wind.setForeground(Color.WHITE);
		wind.setBounds(940, 220, 95, 20);
		wind.setHorizontalAlignment(SwingConstants.RIGHT);
		wind.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getLayeredPane().add(wind);

		// create labels for precipitation
		lbl_precip.setForeground(Color.WHITE);
		lbl_precip.setFont(new Font("Calibri", Font.PLAIN, 14));
		lbl_precip.setBounds(810, 250, 175, 22);
		frame.getLayeredPane().add(lbl_precip);
		precip.setForeground(Color.WHITE);
		precip.setBounds(950, 250, 85, 22);
		precip.setHorizontalAlignment(SwingConstants.RIGHT);
		precip.setFont(new Font("Calibri", Font.PLAIN, 14));
		frame.getLayeredPane().add(precip);

		// labels for st_weather forecast row 1
		for (int i = 0; i < st_lbls.length/2; i++) {

			JLabel precip = new JLabel("Precipitation:");
			precip.setForeground(Color.WHITE);
			precip.setHorizontalAlignment(SwingConstants.CENTER);
			precip.setFont(new Font("Calibri", Font.PLAIN, 14));
			precip.setBounds(65 + 250 * i, 115, 100, 14);
			short_term.add(precip);
		}

		// labels for st_weather forecast row 2
		int c = 0;
		for(int j =4; j < st_lbls.length;j++){
			
			JLabel precip = new JLabel("Precipitation:");
			precip.setForeground(Color.WHITE);
			precip.setHorizontalAlignment(SwingConstants.CENTER);
			precip.setFont(new Font("Calibri", Font.PLAIN, 14));
			precip.setBounds(65 + 250 * c, 255, 100, 14);
			short_term.add(precip);
			c++;
		}

		// labels for lt_weather forecast
		for (int i = 0; i < lt_lbls.length; i++) {
			
			JLabel temp_high = new JLabel("Daily High:");
			temp_high.setForeground(Color.WHITE);
			temp_high.setFont(new Font("Calibri", Font.PLAIN, 12));
			temp_high.setBounds(20 + 180 * i, 180, 100, 14);
			long_term.add(temp_high);
			
			JLabel temp_min = new JLabel("Daily Low:");
			temp_min.setForeground(Color.WHITE);
			temp_min.setFont(new Font("Calibri", Font.PLAIN, 12));
			temp_min.setBounds(20 + 180 * i, 210, 100, 14);
			long_term.add(temp_min);
			
			JLabel precip = new JLabel("Precipitation:");
			precip.setForeground(Color.WHITE);
			precip.setFont(new Font("Calibri", Font.PLAIN, 12));
			precip.setBounds(20 + 180 * i, 240, 100, 14);
			long_term.add(precip);
		}
	}
	
	/**
	 * Method creates pop up for initial use of application, asks for location
	 * @return boolean value to initialize program
	 */

	public boolean initial_use(){	
		File to_load = new File("./config.ser");

		if(to_load.exists() && !to_load.isDirectory()) { return false;}
		else { return true;}	
	}

	/**
	 * Method for Weather Alerts, scans Weather object and sends alert if extreme weather ID exists in weather object
	 */
	public void weather_alert() {
		// Thunderstorm Warning - 202 ID

		if(weather.get_id() == 202) {
			JOptionPane.showMessageDialog(null, "Thunderstorm Warning in your Area!");
		}

		// Tornado Warning - 900 ID
		else if(weather.get_id() == 900) {
			JOptionPane.showMessageDialog(null, "Tornado Warning in your Area!");
		}

		// Tropical Storm Warning - 901 ID
		else if(weather.get_id() == 901) {
			JOptionPane.showMessageDialog(null, "Tropical Storm Warning in your Area!");
		}

		// Hurricane Warning - 902 ID
		else if(weather.get_id() == 902) {
			JOptionPane.showMessageDialog(null, "Hurricane Warning in your Area!");
		}
		// Cold Warning - 903 ID
		else if(weather.get_id() == 903) {
			JOptionPane.showMessageDialog(null, "Extreme Cold Warning in your Area!");
		}

		// Heat Warning - 904 ID
		else if(weather.get_id() == 904) {
			JOptionPane.showMessageDialog(null, "Extreme Heat Warning in your Area!");
		}
		// Wind Warning - 905 ID
		else if(weather.get_id() == 905) {
			JOptionPane.showMessageDialog(null, "Extreme Windy Warning in your Area!");
		}
		// Hail Warning - 906 ID
		else if (weather.get_id() == 906) {
			JOptionPane.showMessageDialog(null, "Extreme Hail Warning in your Area!");
		}
		else{
			return;						// no extreme weather alert exists, program continues running
		}
	}

	/**
	 * Method to change location within app after initialization, updates weather
	 * @param change represents user input indicating location change
	 */
	public void change_location(String change){
		Scanner input = null;
		Scanner locationScanner = null;

		while(loc_list.getItemCount() != 0){
			loc_list.removeItemAt(0);
		}

		// User inputs the city
		input = new Scanner(change);
		locationScanner = new Scanner(input.nextLine()).useDelimiter("[^\\w]");		//This is where the GUI comes in
		String[] locationInfo = new String[MAX_INPUTS];

		int i = 0;
		while (locationScanner.hasNext() && i < MAX_INPUTS) {						// creates an array of input
			location = locationScanner.next();
			if (!location.equals("")) {
				locationInfo[i] = location;
				i++;
			}
		} locationScanner.close();			

		// Generate a URL to pull the JSON data for all locations matching the input
		JSONReader reader = new JSONReader();
		JSONObject json = null;

		try { 
			json = reader.read(location(locationInfo));
			invalid_loc.setVisible(false);
		} catch (JSONException | IOException e) { 
			invalid_loc.setVisible(true);
		return;
		}																// location doesn't match API entry

		int numOptions = json.getInt("count");							// records number possible location options				
		String[] locationOptions = new String[numOptions];
		
										// If there are multiple options, ask the user to pick the right one
		if (numOptions > 1) {
			JSONArray cityList = json.getJSONArray("list");

										// creates array of possible locations

			// Populate the locationOptions array with the list of potential locations for the user to select
			adding = true;
			for (i = 0; i < locationOptions.length; i++) {
				JSONObject city = cityList.getJSONObject(i);
				String str = city.getString("name") + ", " + city.getJSONObject("sys").getString("country");
				locationOptions[i] = str; 
				loc_list.addItem(str);
			}
			adding = false;
			// Using the user's selected location, create a weatherParse object
			int selection = 0; /*index of selection made by user*/

			// Extract the locationID from the JSONArray to ensure consistency
			cityID = cityList.getJSONObject(selection).getInt("id");
			location = locationOptions[selection];
			curr_loc.setText(location);
		}

		// If there is only one option, use it
		else if (numOptions == 1) {
			cityID = json.getJSONArray("list").getJSONObject(0).getInt("id");
			location = json.getJSONArray("list").getJSONObject(0).getString("name") + ", " + json.getJSONArray("list").getJSONObject(0).getJSONObject("sys").getString("country");
			curr_loc.setText(location);
		} 
		
		else {
			invalid_loc.setVisible(true);			// if more than one location, refer to combo box
		}
		
		locationScanner.close();
		// Shows the location drop-down list only if it has more than one location to select from
		loc_list.setVisible(loc_list.getItemCount() > 1);		// combo box size
		lbl_loc_list.setVisible(loc_list.isVisible());			// populates contents
		
		refresh_data();					// refresh data based on combo box selection
	}

	/**
	 * Setter method for the city ID
	 * @param id			The city ID to be set
	 */
	public void set_city_ID(int id) {
		this.cityID = id;
	}

	/**
	 * Getter method for the city ID
	 * @return				The city ID
	 */
	public int get_city_ID() {
		return this.cityID;
	}
}
