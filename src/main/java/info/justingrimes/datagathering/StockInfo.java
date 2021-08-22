package info.justingrimes.datagathering;

import java.util.List;

public class StockInfo {
    private final String ticker;

    private List<TimeSeriesUnit> stockHistory;

    public StockInfo(String ticker, List<TimeSeriesUnit> stockHistory) {
        this.ticker = ticker;
        this.stockHistory = stockHistory;
    }

    public String getTicker() {
        return ticker;
    }

    public void setStockHistory(List<TimeSeriesUnit> stockHistory) {
        this.stockHistory = stockHistory;
    }

    public List<TimeSeriesUnit> getStockHistory() {
        return stockHistory;
    }
}