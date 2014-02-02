package org.webcrawling;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class Controller {
    public static void main(String[] args) throws Exception {
        
            //String crawlStorageFolder = "/data/crawl/root";
            //debug: add dot. 
            String crawlStorageFolder = "./data/crawl/root";
            
            String crawlLogFile = "./data/webdata/";
            String crawlLogLinkFile = "./data/link.data";
            String agentName = "UCI IR crawler 65485399";
            
            
            int PolitenessDelay= 300;
            int MaxDepth=10;
            int numberOfCrawlers = 10;

            org.apache.log4j.PropertyConfigurator.configure("/JAVA/Web Crawling/log4j.properties");
            CrawlConfig config = new CrawlConfig();
            
         //store intermediate crawl data in this folder
        config.setCrawlStorageFolder(crawlStorageFolder);
       
        // Be polite: no more than 1 request per 300 milliseconds
		config.setPolitenessDelay(PolitenessDelay);
        
        //set the maximum crawl depth 
        // default value is -1 for unlimited depth
        config.setMaxDepthOfCrawling(MaxDepth);
        
        // set the maximum number of pages to crawl
        // default value is -1 for unlimited number of pages
        config.setMaxPagesToFetch(-1);
        
        //set the crawl to be resumable
        //do manually if need fresh crawler
        config.setResumableCrawling(true);
			    	
	    	if (args.length>0){
	    		try{
	    			numberOfCrawlers = Integer.parseInt(args[0]);
	    		}
	    		finally{
	    			numberOfCrawlers = 7;
	    		}
	    	}
	    	if (args.length>1){
	    		try{
	    			MaxDepth = Integer.parseInt(args[1]);
	    		}
	    		finally{
	    			MaxDepth = 10;
	    		}
	    	}
	   
       /*
	    * Instantiate the controller for this crawl.
	   */	
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        //set the crawler by UCI ID
        robotstxtConfig.setUserAgentName(agentName);
        
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        
      
        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         */

        controller.addSeed("http://www.ics.uci.edu/~lopes/");
        controller.addSeed("http://www.ics.uci.edu/");

        /*
         * Start the crawl. This is a blocking operation, meaning that your code
         * will reach the line after this only when crawling is finished.
         */
        MyCrawler.setLogFile(crawlLogFile);
        MyCrawler.setLinkFile(crawlLogLinkFile);
        controller.start(MyCrawler.class, numberOfCrawlers);    
}
}
