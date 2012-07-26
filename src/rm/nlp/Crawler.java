package rm.nlp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;

/**
 * 
 * @author rohitm
 * Crawler which takes seed URL and processes queue of URLs 
 */

public class Crawler {

	private URL seed;
	private LinkedHashSet<String> visitedURLs;

	public Crawler() {
		this.seed = null;
		this.visitedURLs = new LinkedHashSet<String>();
	}

	public void setSeed(String url) throws MalformedURLException {
		this.seed = new URL(url);
	}

	/**
	 * Method starting the crawling operation
	 * @throws IOException
	 */
	public void crawl() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				this.seed.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		in.close();
	}

	public static void main(String args[]) {
		try{
			
			Crawler crawler = new Crawler();
			crawler.setSeed("http:\\stackoverflow.com");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
