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
	 * @throws MalformedURLException
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
	public void crawl() throws IOException, MalformedURLException {
		if(!this.getSeed().matches("^http:.*"))
			throw new MalformedURLException();
		else {
			Document doc = Jsoup.connect(this.getSeed()).get();
			System.out.println(doc.html());
		}
	}

	public static void main(String args[]) {
		try {
			Crawler crawler = new Crawler("http://stackoverflow.com");
			crawler.crawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
