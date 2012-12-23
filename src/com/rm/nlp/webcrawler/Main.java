package com.rm.nlp.webcrawler;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			//Command line parameter to take user input of seed
			CrawlerManager crawlerManager = new CrawlerManager("http://www.oracle.com/technetwork/java");
			crawlerManager.beginCrawl();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
