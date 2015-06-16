/**
 * This class defines the Weather objects, which are the individual objects that hold all the weather information for a specific point in time.
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * This class handles and organizes the weather data at a specific time
 */

public class Weather implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int id, pressure, humidity;
	private long data_time, sunrise, sunset;
	private String location, condition, icon;
	private double precip, temp, temp_min, temp_max, wind_speed, wind_deg;

	/**
	 * Constructor -
	 * Creates an empty Weather object
	 */
	public Weather() {
		this.data_time = 0;
		this.sunrise = 0;
		this.sunset = 0;

		this.id = 0;
		this.location = null;
		this.condition = null;
		this.icon = null;

		this.temp = 0;
		this.temp_min = 0;
		this.temp_max = 0;

		this.pressure = 0;
		this.humidity = 0;
		this.wind_speed = 0;
		this.wind_deg = 0;
		this.precip = 0;
	}
	
	/**
	 * Sets the UNIX data time
	 * @param time
	 */
	public void set_data_time(long time) { 
		this.data_time = time;
	}
	
	/**
	 * Sets the sunrise time
	 * @param sunrise
	 */
	public void set_sunrise(int sunrise) { 
		this.sunrise = sunrise;
	}

	/**
	 * Sets the sunset time
	 * @param sunset
	 */
	public void set_sunset(int sunset) { 
		this.sunset = sunset;
	}

	/**
	 * Sets the weather id
	 * @param id weather condition id
	 */
	public void set_id(int id) { 
		this.id = id;
	}

	/**
	 * Sets the location
	 * @param city				- The city to set
	 * @param country			- The country to set
	 */
	public void set_location(String city, String country) { 
		this.location = city + ", " + country;
	}

	/**
	 * Sets the current weather condition
	 * @param condition weather description (e.g., light rain)
	 */
	public void set_condition(String condition) { 
		this.condition = condition;
	}

	/**
	 * Sets the icon for the current weather condition
	 * @param icon name of image file
	 */
	public void set_icon(String icon) { 
		this.icon = icon;
	}

	/**
	 * Sets the current temperature
	 * @param temp the temperature to set
	 */
	public void set_temp(double temp) { 
		this.temp = Math.round(temp);
	}

	/**
	 * Sets the minimum temperature
	 * @param temp_min the minimum temperature
	 */
	public void set_temp_min(double temp_min) { 
		this.temp_min = Math.round(temp_min);
	}

	/**
	 * Sets the maximum temperature
	 * @param temp_max the maximum temperature
	 */
	public void set_temp_max(double temp_max){ 
		this.temp_max = Math.round(temp_max);
	}

	/**
	 * Sets the current air pressure
	 * @param pressure the air pressure
	 */
	public void set_pressure(int pressure) { 
		this.pressure = pressure;
	}

	/**
	 * Sets the humidity
	 * @param humidity the humidity level
	 */
	public void set_humidity(int humidity) { 
		this.humidity = humidity;
	}

	/**
	 * Sets the current wind speed
	 * @param wind_speed the wind speed
	 */
	public void set_wind_speed(double wind_speed) { 
		this.wind_speed = wind_speed;
	}

	/**
	 * Sets the current wind direction
	 * @param wind_deg wind direction in degrees
	 */
	public void set_wind_deg(double wind_deg) { 
		this.wind_deg = wind_deg;
	}

	/**
	 * Sets the precipitation
	 * @param precip the amount of precipitation
	 */
	public void set_precip(double precip) { 
		this.precip = precip;
	}
	
	/**
	 * Getter for the current time of the weather data
	 * @return the time in hours and minutes of the data
	 */
	public String get_time() {
		Date date = new Date(this.data_time * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a z");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}
	
	/**
	 * Getter for the short-term time of the weather data
	 * @return the time in hours and minutes of the data
	 */
	public String get_st_time() {
		Date date = new Date(this.data_time * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}
	
	/**
	 * Getter for the long-term date of the weather data
	 * @return the date of the weather data
	 */
	public String get_lt_date() {
		Date date = new Date(this.data_time * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEEEEEE");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

	/**
	 * Returns the sunrise time in h:mm:ss format
	 * @return	Returns the time of the sunrise
	 */
	public String get_sunrise() {
		Date date = new Date(this.sunrise * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a z");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

	/**
	 * Returns the sunset time in h:mm:ss format
	 * @return	Returns the time of the sunrise
	 */
	public String get_sunset() {
		Date date = new Date(this.sunset * 1000);
		SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a z");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		return dateFormat.format(date);
	}

	/**
	 * Getter method which returns the location ID.
	 * @return the weather condition id
	 */
	public int get_id() { 
		return id;
	}

	/**
	 * Getter method which returns the location
	 */
	public String get_location() { 
		return this.location;
	}

	/**
	 * Getter method which returns a description of the current weather condition.
	 * @return the current weather condition
	 */
	public String get_condition() { 
		return condition;
	}

	/**
	 * Getter method which returns the icon representing the current weather condition.
	 * @return the icon for the current weather condition
	 */
	public String get_icon() { 
		return icon;
	}

	/**
	 * Getter method which returns the current temperature.
	 * @return the current temperature
	 */
	public double get_temp() { 
		return temp;
	}

	/**
	 * Getter method which returns the low temperature.
	 * @return the minimum temperature
	 */
	public double get_temp_min() { 
		return temp_min;
	}

	/**
	 * Getter method which returns the high temperature.
	 * @return the maximum temperature
	 */
	public double get_temp_max() { 
		return temp_max;
	}

	/**
	 * Getter method which returns the air pressure.
	 * @return the air pressure
	 */
	public int get_pressure() { 
		return pressure;
	}

	/**
	 * Getter method which returns the humidity.
	 * @return the humidity
	 */
	public int get_humidity() { 
		return humidity;
	}

	/**
	 * Getter method which returns the wind speed.
	 * @return the wind speed
	 */
	public double get_wind_speed() { 
		return wind_speed;
	}

	/**
	 * Getter method which returns the wind direction.
	 * return the wind direction
	 */
	public double get_wind_deg() { 
		return wind_deg;
	}

	/**
	 * Getter method which returns the precipitation.
	 * @return the precipitation
	 */
	public double get_precip() { 
		return precip;
	}
}
