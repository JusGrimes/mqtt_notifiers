package info.justingrimes.datagathering;

import java.time.Instant;
import java.util.Objects;

public class TimeSeriesUnit implements Comparable<TimeSeriesUnit> {
    private final Instant utcDateTime;
    private final double value;

    public TimeSeriesUnit(Instant instant, double value) {
        utcDateTime = instant;
        this.value = value;
    }

    public Instant getUtcDateTime() {
        return utcDateTime;
    }

    public double getValue() {
        return value;
    }


    @Override
    public int compareTo(TimeSeriesUnit o) {
        return utcDateTime.compareTo(o.getUtcDateTime());
    }


    // equals method doesn't match compareTo because it is compared based on the timeSeriesUnit for sorting purposes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        final var stdError =  0.000001;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeriesUnit that = (TimeSeriesUnit) o;
        return (Math.abs(value - that.value) < stdError ) && utcDateTime.equals(that.utcDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utcDateTime);
    }
}
