package com.rm.nlp.webcrawler;

import java.security.InvalidParameterException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rm.nlp.webcrawler.robots.RobotsParserThread;

public class CrawlerThread implements Runnable {

	private Thread crawlerThread;
	RobotsParserThread robotsParser;

	public CrawlerThread() {
		crawlerThread = new Thread(this, "Crawler Thread");
		robotsParser = new RobotsParserThread();
		crawlerThread.start();
	}

	public void run() {
		try {
			if (!CrawlerManager.unvisitedURLs.isEmpty()) {
				Iterator<String> iterator = CrawlerManager.unvisitedURLs
						.iterator();
				String currentURL = iterator.next();
				
				if (CrawlerUtil.hasRobotsFile(currentURL)) {
					robotsParser.setRootURL(currentURL);
					robotsParser.run();
					if (robotsParser.hasAllURLsDisallowedForUserAgent("*")) {
						// change the exception
						throw new InvalidParameterException();
					}
				}

				Document doc = Jsoup.connect(currentURL).get();
				// System.out.println(doc.html());
				Elements links = doc.select("a[href]");
				for (Element link : links) {
					String[] normalizedLink = link.attr("abs:href")
							.split("\\?");
					if (CrawlerUtil.isValidURL(normalizedLink[0])) {
						CrawlerManager.unvisitedURLs.add(normalizedLink[0]);
						System.out.println(normalizedLink[0]);
					}
				}

				CrawlerManager.unvisitedURLs.remove(currentURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
