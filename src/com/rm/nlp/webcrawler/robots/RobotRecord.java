package com.rm.nlp.webcrawler.robots;

import java.util.ArrayList;

public class RobotRecord {

	private String userAgent;
	private ArrayList<String> disallowedUrls;
	private ArrayList<String> allowedUrls;
	private ArrayList<String> siteMapUrls;

	public RobotRecord(String userAgent) {
		this.userAgent = userAgent;
		this.disallowedUrls = new ArrayList<String>();
		this.allowedUrls = new ArrayList<String>();
		this.siteMapUrls = new ArrayList<String>();
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public ArrayList<String> getDisallowedUrls() {
		return disallowedUrls;
	}

	public void setDisallowedUrls(String disallowedUrl) {
		this.disallowedUrls.add(disallowedUrl);
	}

	public ArrayList<String> getAllowedUrls() {
		return allowedUrls;
	}

	public void setAllowedUrls(String allowedUrl) {
		this.allowedUrls.add(allowedUrl);
	}

	public ArrayList<String> getSiteMapUrls() {
		return siteMapUrls;
	}

	public void setSiteMapUrls(String siteMapUrls) {
		this.siteMapUrls.add(siteMapUrls);
	}
}
