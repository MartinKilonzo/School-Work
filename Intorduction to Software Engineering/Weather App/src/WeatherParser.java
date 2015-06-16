/**
 * This class parses JSON objects and assigns their data to the appropriate fields in weather objects, populating them with data.
 */
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

public class WeatherParser {

	/**
	 * Constructor - creates empty WeatherParser object
	 */
	public WeatherParser() {}
	
	// returns weather object with current weather data
	/**
	 * Method which retrieves the weather data from a URL, converts it to a JSON object and populates a weather object.
	 * 
	 * @param url			The URL containing the data
	 * @return				A Weather object populated with data
	 * @throws Exception	Thrown if things go amiss.
	 */
	public Weather get_current(String url) throws Exception {

		Weather weatherObj = new Weather();   // weather object to store weather data

		try {
			// read api and store weather data in weatherObj
			JSONReader reader = new JSONReader();
			JSONObject data = reader.read(url);
			
			weatherObj.set_data_time(data.getLong("dt"));		// Store the time of the data
			
			JSONObject sys = data.getJSONObject("sys");			
			weatherObj.set_sunrise(sys.getInt("sunrise"));		// Store the sunrise time
			weatherObj.set_sunset(sys.getInt("sunset"));		// Store the sunset time

			weatherObj.set_location(data.getString("name"), sys.getString("country"));		// Store the location details

			// Determine the appropriate icon to use based on the weather code and time of day
			int time = data.getInt("dt");
			Date date = new Date(time);
			SimpleDateFormat dateFormat = new SimpleDateFormat("H");
			int hour = Integer.parseInt(dateFormat.format(date));

			JSONArray weather = data.getJSONArray("weather");

			int id = weather.getJSONObject(0).getInt("id");
			weatherObj.set_id(id);

			// set icons according to condition code
			if (id>=800 && id<805) {
				switch(id) {
				case 800: if(hour>=18 || hour<6) { weatherObj.set_icon("clear_day.png");break;}
				else { weatherObj.set_icon("clear_night.png");break;}
				case 801: if(hour>=18 || hour<6) { weatherObj.set_icon("fewclouds_day.png");break;}
				else { weatherObj.set_icon("fewclouds_night.png");break;}
				case 802: weatherObj.set_icon("scattered_clouds.png");break;
				case 803:
				case 804: weatherObj.set_icon("broken_clouds.png"); 
				}
			}
			if (id>=900 && id<100) {
				switch(id) {
				case 900:
				case 901:
				case 902: weatherObj.set_icon("tornado.png");break;
				case 903: weatherObj.set_icon("cold.png");break;
				case 904: weatherObj.set_icon("hot.png");break;
				case 905: weatherObj.set_icon("wind.png");break;
				case 906: weatherObj.set_icon("hail.png");break;
				case 951: weatherObj.set_icon("calm.png");break;
				case 952: 
				case 953:
				case 954:
				case 955: weatherObj.set_icon("breeze.png");break;
				case 956:
				case 957:
				case 958:
				case 959: weatherObj.set_icon("windy.png");break;
				case 960:
				case 961:
				case 962: weatherObj.set_icon("tornado.png");

				}
			}
			else if (id>=200 && id<300) { weatherObj.set_icon("thunderstorm.png");}
			else if (id>=300 && id<600) { weatherObj.set_icon("rain.png");}
			else if (id>=600 && id<700) { weatherObj.set_icon("snow.png");}
			else if (id>=700 && id<800) { weatherObj.set_icon("mist.png");}

			weatherObj.set_condition(weather.getJSONObject(0).getString("description"));

			JSONObject main = data.getJSONObject("main");
			weatherObj.set_temp(main.getDouble("temp"));			// Store the current temperature
			weatherObj.set_temp_min(main.getDouble("temp_min"));	// Store the minimum temperature
			weatherObj.set_temp_max(main.getDouble("temp_max"));	// Store the maxiumum temperature
			weatherObj.set_humidity(main.getInt("humidity"));		// Store the humidity levels
			weatherObj.set_pressure(main.getInt("pressure"));		// Store the air pressure

			JSONObject wind = data.getJSONObject("wind");		
			weatherObj.set_wind_speed(wind.getDouble("speed"));		// Store the wind speed
			weatherObj.set_wind_deg(wind.getDouble("deg"));			// Store the wind direction

			// Store precipitation data if it exists
			try { weatherObj.set_precip(data.getJSONObject("rain").getDouble("3h"));}
			catch (Exception e) {}

			try { weatherObj.set_precip(data.getJSONObject("snow").getDouble("3h"));}
			catch (Exception e) {}

		} catch (Exception e) { throw e;}

		return weatherObj;   // return the weatherObj
	}		

	/**
	 * Method which retrieves the weather data from a URL, converts it to a JSON object
	 * and populates a short term forecast with the data.
	 * 
	 * @param url			The URL containing the data
	 * @return				A short term forecast object containing the weather information for the next 24 hours
	 * @throws Exception	Thrown if things go amiss.
	 */
	public Forecast get_st_forecast(String url) throws Exception {

		JSONObject data = new JSONReader().read(url);		// Fetches the data from the url

		Forecast st_forecast = new Forecast(8);				// Creates a short-term Forecast of 8 entries

		JSONArray list = data.getJSONArray("list");	
		
		for (int i = 0; i< st_forecast.size(); i++) {
			
			Weather weather_entry = new Weather();			// The weather object that will hold each periods data
			JSONObject entry = list.getJSONObject(i);

			weather_entry.set_data_time(entry.getLong("dt"));				// Store the time of the data
			
			JSONArray weather = entry.getJSONArray("weather");
			weather_entry.set_id(weather.getJSONObject(0).getInt("id"));
			
			
			// Assign the appropriate icon absed on the weather code
			int id = weather.getJSONObject(0).getInt("id");
			weather_entry.set_id(id);

			if(id>=800 && id<805) {
				switch(id) {
				case 800: weather_entry.set_icon("clear_day.png");break;
				case 801: weather_entry.set_icon("fewclouds_day.png");break;
				case 802: weather_entry.set_icon("scattered_clouds.png");break;
				case 803:
				case 804: weather_entry.set_icon("broken_clouds.png"); 
				}
			}
			else if(id>=200 && id<300) { weather_entry.set_icon("thunderstorm.png");}
			else if(id>=300 && id<600) { weather_entry.set_icon("rain.png");}
			else if(id>=600 && id<700) { weather_entry.set_icon("snow.png");}
			else if(id>=700 && id<800) { weather_entry.set_icon("mist.png");}

			weather_entry.set_location(data.getJSONObject("city").getString("name"), data.getJSONObject("city").getString("country"));
			weather_entry.set_condition(weather.getJSONObject(0).getString("description"));
			
			// Sets temperature information	
			JSONObject main = entry.getJSONObject("main");
			weather_entry.set_temp(main.getDouble("temp"));
			weather_entry.set_temp_min(main.getDouble("temp_min"));
			weather_entry.set_temp_max(main.getDouble("temp_max"));
			
			// Add precipitation data if applicable
			try { weather_entry.set_precip(data.getJSONObject("rain").getDouble("3h"));}
			catch (Exception e) {}

			try { weather_entry.set_precip(data.getJSONObject("snow").getDouble("3h"));}
			catch (Exception e) {}
			
			// Adds the weather entry into the forecast
			st_forecast.set(i, weather_entry);
		}

		return st_forecast;   // return the short term forecast
	}
	
	/**
	 * Method which retrieves the weather data from a URL, converts it to a JSON object
	 * and populates a long term forecast with the data.
	 * 
	 * @param url			The URL containing the data
	 * @return				A long term forecast object containing the weather information for the next seven days
	 * @throws Exception	Thrown if things go amiss.
	 */
	public Forecast get_lt_forecast(String url) throws Exception {

		JSONObject data = new JSONReader().read(url);			// Reads the data from the url

		Forecast lt_forecast = new Forecast(7);					// Creates a long-term forecast object containing a week's worth of entries
		
		JSONArray list = data.getJSONArray("list");
		
		for (int i = 0; i < lt_forecast.size(); i++) {
			
			Weather weather_entry = new Weather();				// The weather object that will hold each entry's data
			JSONObject entry = list.getJSONObject(i);
			
			weather_entry.set_data_time(entry.getLong("dt"));	// The time of the weather data
			
			JSONArray weather = entry.getJSONArray("weather");
			weather_entry.set_id(weather.getJSONObject(0).getInt("id"));

			// Assign the appropriate icon based on the condition code
			int id = weather.getJSONObject(0).getInt("id");
			weather_entry.set_id(id);

			if(id>=800 && id<805) {
				switch(id) {
				case 800: weather_entry.set_icon("clear_day.png");break;
				case 801: weather_entry.set_icon("fewclouds_day.png");break;
				case 802: weather_entry.set_icon("scattered_clouds.png");break;
				case 803:
				case 804: weather_entry.set_icon("broken_clouds.png"); 
				}
			}
			else if(id>=200 && id<300) { weather_entry.set_icon("thunderstorm.png");}
			else if(id>=300 && id<600) { weather_entry.set_icon("rain.png");}
			else if(id>=600 && id<700) { weather_entry.set_icon("snow.png");}
			else if(id>=700 && id<800) { weather_entry.set_icon("mist.png");}

			// Sets location information
			weather_entry.set_location(data.getJSONObject("city").getString("name"), data.getJSONObject("city").getString("country"));
			weather_entry.set_condition(weather.getJSONObject(0).getString("description"));
			
			// Sets temperature information
			JSONObject temp = entry.getJSONObject("temp");
			weather_entry.set_temp(temp.getDouble("day"));
			weather_entry.set_temp_min(temp.getDouble("min"));
			weather_entry.set_temp_max(temp.getDouble("max"));
			
			// Add precipitation data if applicable
			try { weather_entry.set_precip(data.getJSONObject("rain").getDouble("3h"));}
			catch (Exception e) {}

			try { weather_entry.set_precip(data.getJSONObject("snow").getDouble("3h"));}
			catch (Exception e) {}
			
			// Adds the weather entry into the forecast
			lt_forecast.set(i, weather_entry);
		}

		return lt_forecast;   // Return the short term forecast
	}
}
