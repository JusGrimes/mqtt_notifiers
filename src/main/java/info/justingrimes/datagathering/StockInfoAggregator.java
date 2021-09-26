package info.justingrimes.datagathering;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StockInfoAggregator implements SubjectAggregator {
    private static final Logger log = LogManager.getLogger();

    private final List<StockInfo> stockInfoList;
    private final StockQueryExecutor stockQueryExecutor;

    public StockInfoAggregator(List<String> tickers,
                               StockQueryExecutor stockQueryExecutor) throws IOException, InterruptedException {
        this.stockQueryExecutor = stockQueryExecutor;
        stockInfoList = new ArrayList<>();
        for (String tickerName :
                tickers) {
            stockInfoList.add(queryStockInfo(tickerName));
        }
    }

    private StockInfo queryStockInfo(String tickerName) throws InterruptedException, IOException {

        String rawJson;
        TimeZone timeZone = null;

        try {
            rawJson = stockQueryExecutor.search(tickerName);
        } catch (IOException | InterruptedException e) {
            log.warn(e);
            throw e;
        }

        var mapper = new ObjectMapper();
        var root = mapper.readTree(rawJson);
        JsonNode timeSeriesNodes = null;

        Iterator<Map.Entry<String, JsonNode>> it = root.fields();
        
        //find TZ info and segregate the timeseries info
        while (it.hasNext()) {
            var entry = it.next();
            if (entry.getKey().equals("Meta Data")) {
                timeZone = TimeZone.getTimeZone(
                        entry.getValue()
                                .get("6. Time Zone").asText());
            }
            if (entry.getKey().startsWith("Time Series")) timeSeriesNodes = entry.getValue();
        }

        if (timeSeriesNodes == null || timeZone == null) throw new IOException("couldn't parse json");
        return new StockInfo(tickerName,createTimeSeriesList(timeZone, timeSeriesNodes));
    }

    private List<TimeSeriesUnit> createTimeSeriesList(TimeZone timeZone, JsonNode timeSeriesNodes) {
        List<TimeSeriesUnit> timeSeriesUnits = new ArrayList<>();

        Iterator<Map.Entry<String, JsonNode>> timeSeriesIT = timeSeriesNodes.fields();

        while (timeSeriesIT.hasNext()) {
            Map.Entry<String, JsonNode> entry = timeSeriesIT.next();
            String fieldName = entry.getKey();
            double close = entry.getValue().get("4. close").asDouble();
            var formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss");
            var zonedDateTime = ZonedDateTime.ofLocal(LocalDateTime.from(formatter.parse(fieldName)), timeZone.toZoneId(), null);

            timeSeriesUnits.add(new TimeSeriesUnit(
                    Instant.from(zonedDateTime)
                    , close));
        }

        return timeSeriesUnits;
    }

    @Override
    public String getMarshalledResponse() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(stockInfoList);
    }
}
