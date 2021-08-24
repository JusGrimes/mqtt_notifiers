package info.justingrimes.datagathering;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StockInfoAggregator implements SubjectAggregator {
    private static final Logger log = LogManager.getLogger();

    private final List<StockInfo> stockInfoList;
    private final StockQueryExecutor stockQueryExecutor;
    private final JsonFactory factory = JsonFactory.builder().build();

    public StockInfoAggregator(List<String> tickers,
                               StockQueryExecutor stockQueryExecutor) {
        this.stockQueryExecutor = stockQueryExecutor;
        stockInfoList = new ArrayList<>();
        for (String tickerName :
                tickers) {
            StockInfo stockInfo = queryStockInfo(tickerName);
        }
    }

    private StockInfo queryStockInfo(String tickerName) throws InterruptedException, IOException {

        String rawJson;
        try {
            rawJson = stockQueryExecutor.search(tickerName);
        } catch (IOException | InterruptedException e) {
            log.warn(e);
            throw e;
        }
        var parser = factory.createParser(rawJson);


    }

    @Override
    public String getMarshalledResponse() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(stockInfoList);
    }
}
