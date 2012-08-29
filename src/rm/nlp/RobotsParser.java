package rm.nlp;

import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;

public class RobotsParser {

	private String robotsTxt;
	private ArrayList<RobotRecord> robotRecords;
	private static RobotsParser robotsParser;

	private RobotsParser() {
		robotRecords = new ArrayList<RobotRecord>();
	}
	
	public static RobotsParser getInstance() {
			if(robotsParser == null) {
				synchronized(RobotsParser.class) {
					robotsParser = new RobotsParser();
				}
			}
			return robotsParser;
	}

	public void setRobotsTxt(String data) {
		robotsTxt = data;
		populateRobotRecords();
	}

	private void populateRobotRecords() {
		String[] lines = robotsTxt.split("\\s+");

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
				while(!lines[i+1].equalsIgnoreCase("Disallow:") && !lines[i+1].equalsIgnoreCase("Allow:")) {
					userAgentName = userAgentName.concat(lines[++i]);
				}
				RobotRecord record = new RobotRecord(userAgentName);
				while (i< lines.length && !lines[i].equalsIgnoreCase("User-Agent:")) {
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

		
		for(RobotRecord record: robotRecords) {
			if(record.getUserAgent().equals("*")) {
				for (String disallowedUrl : record.getDisallowUrls()) {
					System.out.println(disallowedUrl);
				}
			}
		}

	}
	
	public void flushRobotRecords() {
		robotsTxt = null;
		robotRecords = new ArrayList<RobotRecord>();
	}
	
}