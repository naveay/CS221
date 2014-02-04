package org.webcrawling;

import java.util.List;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
public class Controller {
    public static void main(String[] args) throws Exception {
            String crawlStorageFolder = "/data/crawl/root";
           String crawlLogFile = "/data/webdata/";
            String crawlLogLinkFile = "/data/link.data";

            int numberOfCrawlers = 10;
            org.apache.log4j.PropertyConfigurator.configure("/JAVA/Web Crawling/log4j.properties");
            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setUserAgentString("UCI IR crawler 36321876 65485399");
    		/*
    		 * Be polite: Make sure that we don't send more than 1 request per
    		 * second (1000 milliseconds between requests).
    		 */
    		config.setPolitenessDelay(300);
    		//config.setResumableCrawling(true);
    		//config.setMaxDepthOfCrawling(1);
            /*
             * Instantiate the controller for this crawl.
             */
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

            /*
             * For each crawl, you need to add some seed urls. These are the first
             * URLs that are fetched and then the crawler starts following links
             * which are found in these pages
             */
            controller.addSeed("http://www.ics.uci.edu/");
            //controller.addSeed("http://www.ics.uci.edu/~lopes/");
            /*
             * Start the crawl. This is a blocking operation, meaning that your code
             * will reach the line after this only when crawling is finished.
             */
            //MyCrawler.setLogFile(crawlLogFile);
            //MyCrawler.setLinkFile(crawlLogLinkFile);
            controller.start(MyCrawler.class, numberOfCrawlers);   
            
            List<Object> crawlersLocalData=controller.getCrawlersLocalData();
    		CrawlStat stat = (CrawlStat)crawlersLocalData.get(0);
    		System.out.println("Number of subdomain: "+stat.getsubDomain());
    		stat.file();
    		System.out.println("Unique url: "+stat.getUrl());
    		System.out.println("Longest URL:  "+stat.longest_url+", "+stat.longest_text);
    		System.out.println("Domain number: "+stat.getDomain());
    		stat.print();
    		System.out.println("=========================");
    		stat.print_two();
    		stat.time();
    }
}
