package rm.nlp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * @author rohitm Crawler which takes seed URL and processes queue of URLs
 */

public class Crawler {

	private final String seedURL;
	public static LinkedHashSet<String> visitedURLs;
	public static LinkedHashSet<String> unvisitedURLs;
	private RobotsParser robotsParser;

	/**
	 * 
	 * @param url Seed URL for crawling
	 * 
	 */
	public Crawler(String url) {
		seedURL = url;
		visitedURLs = new LinkedHashSet<String>();
		unvisitedURLs = new LinkedHashSet<String>();
		robotsParser = RobotsParser.getInstance();

		// Add the seed URL to list of URLs already visited
		visitedURLs.add(seedURL);
	}

	/**
	 * Method starting the crawling operation
	 * 
	 * @throws IOException
	 */
	public void beginCrawl() throws IOException, MalformedURLException {
		if (!isValidURL(seedURL)) {
			throw new MalformedURLException();
		}
		if (hasRobotsFile(seedURL)) {
			robotsParser.retrieveRobotsFile(seedURL);
		}
		Document doc = Jsoup.connect(seedURL).get();
		System.out.println(doc.html());
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String[] normalizedLink = link.attr("abs:href").split("\\?");
			if (isValidURL(normalizedLink[0])) {
				unvisitedURLs.add(normalizedLink[0]);
				System.out.println(normalizedLink[0]);
			}
		}

		// Introduce Thread to handle this
		if (!RobotsParser.hasAllURLsDisallowedForUserAgent("*")) {
			Iterator<String> itr = unvisitedURLs.iterator();
			while (itr.hasNext()) {
				// if(!RobotsParser.isDisallowedURLForUserAgent("*", ,
				// itr.next())) {

				// }
			}
		}
	}

	/**
	 * Check if URL has HTTP Protocol
	 * 
	 * @param url
	 * @return
	 */
	private static boolean isValidURL(String url) {
		if (url.matches("^http:.*")) {
			return true;
		} else {
			return false;
		}
	}

	private boolean hasRobotsFile(String url) throws IOException {
		Connection.Response response = Jsoup.connect(url + "/robots.txt")
				.execute();
		return (response.statusCode() == 200);
	}

	public static void main(String args[]) {
		try {
			Crawler crawler = new Crawler("http://java.sun.com");
			crawler.beginCrawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
