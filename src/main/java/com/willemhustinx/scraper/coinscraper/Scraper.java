package com.willemhustinx.scraper.coinscraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Scraper {

    final static Logger logger = LoggerFactory.getLogger(Scraper.class);

    String scrapeUrl;

    public Scraper(String url) {
        this.scrapeUrl = url;
    }

    public String scrape() throws IOException {
        logger.debug("Start Scraping");
        logger.debug("url: " + this.scrapeUrl);

        URL url = new URL(this.scrapeUrl);
        Scanner scan = new Scanner(url.openStream());

        String content = new String();
        while (scan.hasNext())
            content += scan.nextLine();
        scan.close();

        logger.debug("scraping result: " + content);
        logger.debug("scraping stopped");

        return content;
    }
}
