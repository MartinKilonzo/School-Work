/**
 * This class converts data from a URL into a JSON object.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONReader {

	//**  Constructor  **//
	/**
	 * Creates an empty JSONReader object
	 */
	public JSONReader() {}

	/**
	 * Converts a URL into a JSON file
	 * 
	 * @param url 				The URL containing the text to convert
	 * @return					The JSON representing the content of the URL
	 * @throws IOException		Thrown if the URL is malformed
	 * @throws JSONException	Thrown if the URL contains nothing or is not of proper form	
	 */
	public JSONObject read(String url) throws IOException, JSONException {

		InputStream input = new URL(url).openStream();
		
		// Read through the input stream and append the data to a string containing all of the URL's data to be used to create a JSONObject
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();

			int current;
			while ((current = reader.read()) != -1) {
				sb.append((char) current);
			}

			String jsonText = sb.toString();		
			JSONObject json = new JSONObject(jsonText);
			return json;

		} finally { input.close();}
	}

}