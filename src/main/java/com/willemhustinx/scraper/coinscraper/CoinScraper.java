package com.willemhustinx.scraper.coinscraper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class CoinScraper {

    static final Logger logger = LoggerFactory.getLogger(CoinScraper.class);

    private Properties properties;

    public static void main(String[] args) {
        CoinScraper coinScraper = new CoinScraper();
        coinScraper.run();
    }

    public void run() {

        logger.debug("Coinscraper Started");

        this.loadProperties();
        DatabaseService databaseService = new DatabaseService(this.properties.getProperty("database.url"), this.properties.getProperty("database.user"), this.properties.getProperty("database.password"), this.properties.getProperty("database.name"));

        Scraper scraper = new Scraper(this.properties.getProperty("scrapeUrl"));
        String scraperResult = null;
        try {
            scraperResult = scraper.scrape();
        } catch (Exception e) {
            logger.error("Exception", e);
        }
        Map<String, CoinData> dataMap = CoinDataService.createList(scraperResult);

        String[] coins = properties.getProperty("coins").split(",");

        for (String coin : coins) {
            logger.debug(coin);
            databaseService.addCointoDatabase(dataMap.get(coin));
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
            logger.error("IOException", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error("IOException", e);
                }
            }
        }
    }

    public String getProperty(String key) {
        return this.properties.getProperty(key);
    }
}
