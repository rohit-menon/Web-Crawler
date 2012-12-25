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
	public void beginCrawl() throws IOException, InterruptedException {
		Runnable r1 = new CrawlerThread();
		Thread crawlerThread1 = new Thread(r1);
		crawlerThread1.setName("Crawler Thread1");
		crawlerThread1.start();


		Runnable r2 = new CrawlerThread();
		Thread crawlerThread2 = new Thread(r2);
		crawlerThread2.setName("Crawler Thread2");
		Thread.sleep(10000);
		crawlerThread2.start();
		
	}
}
