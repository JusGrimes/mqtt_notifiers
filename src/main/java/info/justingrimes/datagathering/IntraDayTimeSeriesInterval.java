package info.justingrimes.datagathering;

public enum IntraDayTimeSeriesInterval {
    ONE_MINUTE(1),
    FIVE_MINUTES(5),
    FIFTEEN_MINUTES(15),
    THIRTY_MINUTES(30),
    SIXTY_MINUTES(60);

    private final String intervalString;

    IntraDayTimeSeriesInterval(int i) {
        intervalString = Integer.toString(i)+"min";
    }

    @Override
    public String toString() {
        return intervalString;
    }
}
