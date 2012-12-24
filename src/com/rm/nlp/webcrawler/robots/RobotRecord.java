package com.rm.nlp.webcrawler.robots;

import java.util.ArrayList;
import com.rm.nlp.webcrawler.URL;

public class RobotRecord {

	private URL seedURL;
	private String userAgent;
	private ArrayList<URL> disallowedURLs;
	private ArrayList<URL> allowedURLs;
	private ArrayList<URL> siteMapURLs;

	public RobotRecord(URL seedURL) {
		this.seedURL = seedURL;
		this.disallowedURLs = new ArrayList<URL>();
		this.allowedURLs = new ArrayList<URL>();
		this.siteMapURLs = new ArrayList<URL>();
	}

	public URL getSeedURL() {
		return seedURL;
	}

	public void setSeedURL(URL seedURL) {
		this.seedURL = seedURL;
	}
	
	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public ArrayList<URL> getDisallowedURLs() {
		return disallowedURLs;
	}

	public void setDisallowedURLs(URL disallowedURL) {
		this.disallowedURLs.add(disallowedURL);
	}

	public ArrayList<URL> getAllowedURLs() {
		return allowedURLs;
	}

	public void setAllowedURLs(URL allowedURL) {
		this.allowedURLs.add(allowedURL);
	}

	public ArrayList<URL> getSiteMapURLs() {
		return siteMapURLs;
	}

	public void setSiteMapURLs(URL siteMapURLs) {
		this.siteMapURLs.add(siteMapURLs);
	}
}
