package com.rm.nlp.webcrawler;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.rm.nlp.webcrawler.robots.RobotsParser;

public class CrawlerThread implements Runnable {

	public void run() {
		try {
			if (!CrawlerManager.unvisitedURLs.isEmpty()) {
				Iterator<URL> iterator = CrawlerManager.unvisitedURLs
						.iterator();
				URL currentURL = iterator.next();
				CrawlerManager.unvisitedURLs.remove(currentURL);

				String finalURL = null;
				boolean isAllowed = false;

				// Seed URL
				if (currentURL.getURL() == null) {
					finalURL = currentURL.getSeedURL();
					isAllowed = true;
				} else if (!RobotsParser.hasURLDisallowedForUserAgent(
						currentURL.getSeedURL(), "*", currentURL.getURL())) {
					finalURL = currentURL.getURL();
					isAllowed = true;
				}

				System.out.println("-----------------------------------");
				System.out.println(finalURL);
				System.out.println("-----------------------------------");

				if (isAllowed && !CrawlerUtil.checkRobots(finalURL)) {
					Document doc = Jsoup.connect(finalURL).get();
					// System.out.println(doc.html());
					Elements links = doc.select("a[href]");
					for (Element link : links) {
						String[] normalizedLink = link.attr("abs:href").split(
								"\\?");
						if (CrawlerUtil.isValidURL(normalizedLink[0]) 
								 && !CrawlerUtil.isPresent(normalizedLink[0])) {
							CrawlerManager.unvisitedURLs.add(new URL(finalURL,
									normalizedLink[0]));
							System.out.println(normalizedLink[0]);
						}
					}
				}

				CrawlerManager.visitedURLs.add(currentURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out
				.println("----------------------------------------------------------");
	}
}
