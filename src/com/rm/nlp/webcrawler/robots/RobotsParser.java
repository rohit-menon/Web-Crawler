package com.rm.nlp.webcrawler.robots;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang.ArrayUtils;
import org.jsoup.Jsoup;

import com.rm.nlp.webcrawler.URL;

public class RobotsParser {

	public static HashMap<String, RobotRecord> robotRecords = new HashMap<String, RobotRecord>();

	public static synchronized void retrieveRobotsFile(String seedURL)
			throws IOException {
		String robotsTxt = Jsoup.connect(seedURL + "/robots.txt").get()
				.toString();

		String[] lines = Jsoup.parse(robotsTxt).text().split("\\s+");

		// Remove comments and empty lines
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].equals("#")) {
				lines = (String[]) ArrayUtils.remove(lines, i);
				while (!lines[i].equals("User-agent:")
						&& !lines[i].equals("Sitemap:")) {
					lines = (String[]) ArrayUtils.remove(lines, i);
				}
			}
		}

		// Classify URLs to disallowed, allowed and Sitemaps
		for (int i = 0; i < lines.length;) {
			if (lines[i].equalsIgnoreCase("User-Agent:")) {
				RobotRecord record = new RobotRecord(new URL(seedURL));
				String userAgentName = lines[++i];
				if (lines[i + 1].equalsIgnoreCase("Sitemap:")) {
					record.setSiteMapURLs(new URL(seedURL, seedURL
							+ lines[i + 2]));
					i = i + 3;
				} else {
					while (!lines[i + 1].equalsIgnoreCase("Disallow:")
							&& !lines[i + 1].equalsIgnoreCase("Allow:")) {
						userAgentName = userAgentName.concat(lines[++i]);
					}
				}
				record.setUserAgent(userAgentName);
				while (i < lines.length
						&& !lines[i].equalsIgnoreCase("User-Agent:")) {
					if (lines[i].equalsIgnoreCase("Disallow:")) {
						record.setDisallowedURLs(new URL(seedURL, seedURL
								+ lines[++i]));
					} else if (lines[i].equalsIgnoreCase("Allow:")) {
						record.setAllowedURLs(new URL(seedURL, seedURL
								+ lines[++i]));
					} else if (lines[i].equalsIgnoreCase("Sitemap:")) {
						record.setSiteMapURLs(new URL(seedURL, seedURL
								+ lines[++i]));
					}
					i++;
				}
				robotRecords.put(seedURL, record);
				break;
			}
		}
	}

	public static ArrayList<URL> getDisallowedURLsForUserAgent(String seedURL,
			String userAgent) {
		RobotRecord record = robotRecords.get(seedURL);
		if (record.getUserAgent().equals(userAgent)) {
			return record.getDisallowedURLs();
		}
		return null;
	}

	public static ArrayList<URL> getAllowedURLsForUserAgent(String seedURL,
			String userAgent) {
		RobotRecord record = robotRecords.get(seedURL);
		if (record.getUserAgent().equals(userAgent)) {
			return record.getAllowedURLs();
		}
		return null;
	}

	public static boolean hasAllURLsDisallowedForUserAgent(String seedURL,
			String userAgent) {
		ArrayList<URL> disallowedURLs = getDisallowedURLsForUserAgent(seedURL,
				userAgent);
		for (URL url : disallowedURLs) {
			if (url.getURL().equals("/")) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasURLDisallowedForUserAgent(String seedURL,
			String userAgent, String crawledURL) {
		ArrayList<URL> disallowedURLs = getDisallowedURLsForUserAgent(seedURL,
				userAgent);
		for (URL url : disallowedURLs) {
			if (crawledURL.contains(url.getURL())) {
				return true;
			}
		}
		return false;
	}
}