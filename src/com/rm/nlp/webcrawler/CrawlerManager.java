package com.rm.nlp.webcrawler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * 
 * @author Rohit Menon 
 * Crawler which takes seed URL and processes queue of URLs
 */

public class CrawlerManager {

	public static ArrayList<URL> visitedURLs;
	public static ArrayList<URL> unvisitedURLs;

	/**
	 * @param url
	 *            Seed URL for crawling
	 */
	public CrawlerManager(String seedURL) throws MalformedURLException {
		if (CrawlerUtil.isValidURL(seedURL)) {
			visitedURLs = new ArrayList<URL>();
			unvisitedURLs = new ArrayList<URL>();

			// Add the seed URL to list of URLs already visited
			unvisitedURLs.add(new URL(seedURL));
		} else
			throw new MalformedURLException();
	}

	/**
	 * Method starting the crawling operation
	 * 
	 * @throws IOException
	 */
	public void beginCrawl() throws IOException, InvalidParameterException {
		Runnable r = new CrawlerThread();
		Thread crawlerThread = new Thread(r);
		crawlerThread.setName("Crawler Thread");
		crawlerThread.start();
	}
}
