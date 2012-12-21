package com.rm.nlp.webcrawler.robots;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.jsoup.Jsoup;


public class RobotsParser {

	private static String robotsTxt;
	private static ArrayList<RobotRecord> robotRecords;
	private static RobotsParser robotsParser;

	private RobotsParser() {
		robotRecords = new ArrayList<RobotRecord>();
	}

	public static RobotsParser getInstance() {
		if (robotsParser == null) {
			synchronized (RobotsParser.class) {
				robotsParser = new RobotsParser();
			}
		}
		return robotsParser;
	}

	public void retrieveRobotsFile(String url) throws IOException {
		robotsTxt = Jsoup.connect(url + "/robots.txt").get().toString();
		populateRobotRecords();
	}

	private void populateRobotRecords() {
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
				String userAgentName = lines[++i];
				while (!lines[i + 1].equalsIgnoreCase("Disallow:")
						&& !lines[i + 1].equalsIgnoreCase("Allow:")) {
					userAgentName = userAgentName.concat(lines[++i]);
				}
				RobotRecord record = new RobotRecord(userAgentName);
				while (i < lines.length
						&& !lines[i].equalsIgnoreCase("User-Agent:")) {
					if (lines[i].equalsIgnoreCase("Disallow:")) {
						record.setDisallowedUrls(lines[++i]);
					} else if (lines[i].equalsIgnoreCase("Allow:")) {
						record.setAllowedUrls(lines[++i]);
					} else if (lines[i].equalsIgnoreCase("Sitemap:")) {
						record.setSiteMapUrls(lines[++i]);
					}
					i++;
				}
				robotRecords.add(record);
			}
		}
	}

	private static ArrayList<String> getDisallowedURLsForUserAgent(
			String userAgent) {
		for (RobotRecord record : robotRecords) {
			if (record.getUserAgent().equals(userAgent)) {
				return record.getDisallowedUrls();
			}
		}
		return null;
	}

	private static ArrayList<String> getAllowedURLsForUserAgent(String userAgent) {
		for (RobotRecord record : robotRecords) {
			if (record.getUserAgent().equals(userAgent)) {
				return record.getAllowedUrls();
			}
		}
		return null;
	}

	public static boolean hasAllURLsDisallowedForUserAgent(String userAgent) {
		ArrayList<String> disallowedURLs = getDisallowedURLsForUserAgent(userAgent);
		for (String url : disallowedURLs) {
			if (url.equals("/")) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasURLDisallowedForUserAgent(String userAgent,
			String seedURL, String crawledURL) {
		ArrayList<String> disallowedURLs = getDisallowedURLsForUserAgent(userAgent);
		for (String url : disallowedURLs) {
			if (crawledURL.contains(seedURL + url)) {
				return true;
			}
		}
		return false;
	}

	public static void flushRobotRecords() {
		robotsTxt = null;
		robotRecords = new ArrayList<RobotRecord>();
	}
}