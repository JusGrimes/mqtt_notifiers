package info.justingrimes.datagathering;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IntraDayStockPriceQueryExecutor implements StockQueryExecutor {
    private static final Logger log = LogManager.getLogger();

    private static HttpClient client;

    private final String uriFormatString;
    private final String apiKey;

    public IntraDayStockPriceQueryExecutor(String apiKey, IntraDayTimeSeriesInterval interval) {
        this.apiKey = apiKey;

        client = HttpClient.newHttpClient();

        //symbol, interval, apikey
        this.uriFormatString = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY" +
                "&symbol=%s" +
                "&interval=" + interval +
                "&apikey=%s";
    }

    // Returns raw JSON String
    @Override
    public String search(String ticker) throws IOException, InterruptedException {
        log.trace("Searching for stock info on {}", ticker);
        URI formattedUri = URI.create(String.format(uriFormatString, ticker, apiKey));
        var request = HttpRequest.newBuilder(formattedUri).build();

        int statusCode;
        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if ((statusCode = response.statusCode()) != 200)
            throw new IOException(String.format("status code returned: %d URL: %s",
                    statusCode,
                    String.format(uriFormatString, ticker, "XXXXXXX")));
        return response.body();
    }
}
