package com.willemhustinx.scraper.coinscraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class Scraper {

    static final Logger logger = LoggerFactory.getLogger(Scraper.class);

    String scrapeUrl;

    public Scraper(String url) {
        this.scrapeUrl = url;
    }

    public String scrape() throws IOException {
        logger.debug("Start Scraping");
        logger.debug("url: {}", this.scrapeUrl);

        URL url = new URL(this.scrapeUrl);
        Scanner scan = new Scanner(url.openStream());

        StringBuilder bld = new StringBuilder();
        while (scan.hasNext()) {
            bld.append(scan.nextLine());
        }
        String content = bld.toString();
        scan.close();

        logger.debug("scraping result: {}", content);
        logger.debug("scraping stopped");

        return content;
    }
}
