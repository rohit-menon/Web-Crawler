package com.rm.nlp.webcrawler;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			CrawlerManager crawler = new CrawlerManager("http://java.sun.com");
			crawler.beginCrawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
