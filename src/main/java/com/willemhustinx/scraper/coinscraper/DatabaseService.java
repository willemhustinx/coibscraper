package com.willemhustinx.scraper.coinscraper;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class DatabaseService {

    static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    private InfluxDB influxDB;

    public DatabaseService(String url, String username, String password, String dbName) {
        logger.debug("start db connection");
        this.influxDB = InfluxDBFactory.connect(url, username, password);
        //influxDB.createDatabase(dbName);
        influxDB.setDatabase(dbName);
        logger.debug("database {} created", dbName);
    }

    public void addCointoDatabase(CoinData coinData) {
        logger.debug("storing: {}", coinData);

        Point point = Point.measurement(coinData.getId())
                .time(coinData.getLastUpdated(), TimeUnit.SECONDS)
                .addField("priceEUR", coinData.getPriceEUR())
                .addField("priceUSD", coinData.getPriceUSD())
                .build();

        influxDB.write(point);

        logger.debug("coin: {} added to database", coinData);
    }
}
