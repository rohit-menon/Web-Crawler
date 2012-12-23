package com.rm.nlp.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.Iterator;
import java.util.LinkedHashSet;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rm.nlp.webcrawler.robots.RobotsParser;

/**
 * 
 * @author rohitm Crawler which takes seed URL and processes queue of URLs
 */

public class CrawlerManager {

	private final String seedURL;
	public static LinkedHashSet<String> visitedURLs;
	public static LinkedHashSet<String> unvisitedURLs;
	private RobotsParser robotsParser;

	/**
	 * @param url
	 * Seed URL for crawling
	 */
	public CrawlerManager(String url) throws MalformedURLException{
		if (CrawlerUtil.isValidURL(url)) {
			this.seedURL = url;
			visitedURLs = new LinkedHashSet<String>();
			unvisitedURLs = new LinkedHashSet<String>();
			robotsParser = RobotsParser.getInstance();

			// Add the seed URL to list of URLs already visited
			visitedURLs.add(seedURL);
		}
		else
			throw new MalformedURLException();
	}

	/**
	 * Method starting the crawling operation
	 * @throws IOException
	 */
	public void beginCrawl() throws IOException, InvalidParameterException {
		if (CrawlerUtil.hasRobotsFile(seedURL)) {
			robotsParser.retrieveRobotsFile(seedURL);
			//check if can be parsed
			if(robotsParser.hasAllURLsDisallowedForUserAgent("*")){
				// change the exception
				throw new InvalidParameterException();
			}
		}
		
		Document doc = Jsoup.connect(seedURL).get();
		System.out.println(doc.html());
		Elements links = doc.select("a[href]");
		for (Element link : links) {
			String[] normalizedLink = link.attr("abs:href").split("\\?");
			if (CrawlerUtil.isValidURL(normalizedLink[0])) {
				unvisitedURLs.add(normalizedLink[0]);
				System.out.println(normalizedLink[0]);
			}
		}
	}
}
