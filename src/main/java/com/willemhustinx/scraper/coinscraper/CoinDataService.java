package com.willemhustinx.scraper.coinscraper;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class CoinDataService {

    static final Logger logger = LoggerFactory.getLogger(CoinDataService.class);

    private CoinDataService() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, CoinData> createList(String jsonString) {

        Map<String, CoinData> dataMap = new HashMap<String, CoinData>();
        CoinData coinData;
        JSONParser jsonParser = new JSONParser();

        try {
            Object obj = jsonParser.parse(jsonString);

            JSONArray jsonArray = (JSONArray) obj;

            Iterator<JSONObject> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = iterator.next();

                coinData = new CoinData();
                coinData.setId((String) jsonObject.get("id"));
                coinData.setName((String) jsonObject.get("name"));
                coinData.setPriceEUR(Double.parseDouble(((String) jsonObject.get("price_eur"))));
                coinData.setPriceUSD(Double.parseDouble(((String) jsonObject.get("price_usd"))));
                coinData.setLastUpdated(Long.parseLong(((String) jsonObject.get("last_updated"))));

                dataMap.put(coinData.getId(), coinData);

                logger.debug("Coindata created: {}", coinData.toString());
            }
        } catch (ParseException e) {
            logger.error("ParseException", e);
        }

        return dataMap;

    }
}
