/**
 * This class is used to process and organize weather objects over multiple days. To do so, an array of weather objects is used of specified length.
 */
public class Forecast {

	private Weather[] weather_data;			// An array containing the weather objects to populate the forecast
	private int size;						// The number of entries in the forecast
	
	/**
	 * Constructor
	 * 
	 * @param size the number of weather entries
	 */
	public Forecast(int size) {
		this.size = size;
		weather_data = new Weather[size];
	}
	
	/**
	 * Getter method which returns the number of entries in the Forecast object
	 * 
	 * @return			The size of the forecast object
	 */
	public int size() {
		return size;
	}
	
	/**
	 * General purpose setter method
	 * 
	 * @param index index of forecast array
	 * @param weather Weather object to set
	 */
	public void set(int index, Weather weather) {
		weather_data[index] = weather;
	}
	
	/**
	 * General purpose getter method
	 * 
	 * @param index index of forecast array
	 * @return the weather forecast at index h
	 */
	public Weather get(int index) {
		return weather_data[index];
	}
	
}
