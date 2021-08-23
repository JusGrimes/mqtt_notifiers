package info.justingrimes.datagathering;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class StockInfoAggregator implements SubjectAggregator {
    private final List<StockInfo> stockInfoList;

    public StockInfoAggregator(List<String> tickers) {
        stockInfoList = new ArrayList<>();
        for (String tickerName :
                tickers) {
            StockInfo stockInfo = queryStockInfo(tickerName);
        }
    }

    private StockInfo queryStockInfo(String tickerName) {
        //TODO get stock info and history from API
        return null;
    }

    @Override
    public String getJSON() throws JsonProcessingException {
        var objectMapper = new ObjectMapper()
        return objectMapper.writeValueAsString(stockInfoList);
    }
}
