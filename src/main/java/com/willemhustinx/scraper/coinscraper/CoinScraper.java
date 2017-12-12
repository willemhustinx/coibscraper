package com.willemhustinx.scraper.coinscraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CoinScraper {

    final static Logger logger = LoggerFactory.getLogger(CoinScraper.class);

    private Properties properties;

    public static void main(String[] args) {
        CoinScraper coinScraper = new CoinScraper();
        coinScraper.run();
    }

    public void run() {

        logger.debug("Coinscraper Started");

        this.loadProperties();
        Scraper scraper = new Scraper(this.properties.getProperty("scrapeUrl"));
        String result;
        try {
            result = scraper.scrape();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadProperties() {
        logger.debug("Start loading properties");
        this.properties = new Properties();
        InputStream input = null;

        try {
            input = CoinScraper.class.getClassLoader().getResourceAsStream("config.properties");
            this.properties.load(input);
            logger.debug("properties loaded");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }
}
