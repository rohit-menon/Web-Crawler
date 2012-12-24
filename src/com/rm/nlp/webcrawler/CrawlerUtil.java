package com.rm.nlp.webcrawler;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import com.rm.nlp.webcrawler.robots.RobotsParser;

public class CrawlerUtil {

	/**
	 * Check if URL has HTTP Protocol
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isValidURL(String url) {
		if (url.matches("^http:.*")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean hasRobotsFile(String url) throws IOException {
		Connection.Response response = Jsoup.connect(url + "/robots.txt")
				.execute();
		return (response.statusCode() == 200);
	}
	
	public static boolean checkRobots(String url) throws IOException{
		if (CrawlerUtil.hasRobotsFile(url)) {
			RobotsParser.retrieveRobotsFile(url);
			if (RobotsParser.hasAllURLsDisallowedForUserAgent(url, "*")) {
				return true;
			}
		}
		return false;
	}
	
}
