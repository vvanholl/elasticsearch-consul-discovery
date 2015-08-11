package utils;/**
 * Created by jigar.joshi on 8/10/15.
 */

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Utility {
	/**
	 * reads content from URL by making
	 *
	 * @param urlString url to read the content from (for example: http://www.google.com
	 * @return response returned in form of String
	 * @throws IOException if fails to parse URL, make call to URL
	 */
	public static String readUrl(String urlString) throws IOException {
		StringBuffer result = new StringBuffer();
		BufferedReader br = null;
		InputStream inputStream = null;
		try {
			URL url = new URL(urlString);
			URLConnection urlConnection = url.openConnection();
			inputStream = urlConnection.getInputStream();
			br = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
		} finally {
			closeQuitely(inputStream);
			closeQuitely(br);
		}
		return result.toString();
	}

	/**
	 * closes closeable resource quitely, null safe and any exception raised is ignored
	 *
	 * @param closeable closeable to close
	 */
	public static void closeQuitely(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception ignore) {
			}
		}
	}
}
