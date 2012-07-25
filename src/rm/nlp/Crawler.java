package rm.nlp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Crawler {

	public static void main(String args[]) {

		try {
			URL seed = new URL("http://stackoverflow.com/");
			BufferedReader in = new BufferedReader(new InputStreamReader(
					seed.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null)
				System.out.println(inputLine);
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
