package info.justingrimes.datagathering;

import java.io.IOException;

public interface StockQueryExecutor {
    String search(String ticker) throws IOException, InterruptedException;
}
