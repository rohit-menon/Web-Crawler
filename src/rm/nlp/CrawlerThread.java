package rm.nlp;

public class CrawlerThread implements Runnable{
	
	private Thread t;
	
	public CrawlerThread() {
		t = new Thread(this, "Crawler Thread");
		t.start();
	}
	
	public void run() {
		try {
			if(!Crawler.unvisitedURLs.isEmpty()) {
				
			}
		} catch(Exception e) {
			
		}
	}

}
