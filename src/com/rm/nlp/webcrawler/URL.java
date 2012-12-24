package com.rm.nlp.webcrawler;

public class URL {
	
	private String seedURL;
	private String URL;
	
	
	public URL(String rootURL, String URL) {
		this.seedURL = rootURL;
		this.URL = URL;
	}
	
	public URL(String seedURL) {
		this.seedURL = seedURL;
	}
	
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public String getSeedURL() {
		return seedURL;
	}
	public void setSeedURL(String seedURL) {
		this.seedURL = seedURL;
	}

}
