package rm.nlp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author rohitm 
 * Crawler which takes seed URL and processes queue of URLs
 */

public class Crawler {

	private final String seed;
	private LinkedHashSet<String> visitedURLs;
	
	/**
	 * 
	 * @param url Seed URL for crawling
	 */
	public Crawler(String url){
		this.seed = url;
		this.visitedURLs = new LinkedHashSet<String>();
		
		// Add the seed URL to list of URLs already visited
		this.visitedURLs.add(this.getSeed());
	}

	public String getSeed() {
		return this.seed;
	}

	/**
	 * Method starting the crawling operation
	 * 
	 * @throws IOException
	 */
	public void beginCrawl() throws IOException, MalformedURLException {
		if(!isValidURL(this.getSeed()))
			throw new MalformedURLException();
		else if (hasRobotsFile(this.getSeed())){
			
		} else {
			Document doc = Jsoup.connect(this.getSeed()).get();
			System.out.println(doc.html());
		}	
	}
	
	/**
	 * Check URL to be crawled is valid
	 * @param url 
	 * @return 
	 */
	private static boolean isValidURL(String url) {
		// Check protocol for URL is http
		if(url.matches("^http:.*")) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean hasRobotsFile(String url) throws IOException {
		Document doc = Jsoup.connect(url + "/robots.txt").get();
		if(doc == null) {
			return false;
		} else {
			System.out.println(doc.html());
			return true;
		}
	}
	
	public static void main(String args[]) {
		try {
			Crawler crawler = new Crawler("http://stackoverflow.com");
			crawler.beginCrawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
