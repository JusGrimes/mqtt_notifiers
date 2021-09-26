package info.justingrimes.datagathering;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;

public class TimeSeriesUnit implements Comparable<TimeSeriesUnit> {
    private final Instant utcInstant;
    private final double value;

    public TimeSeriesUnit(Instant instant, double value) {
        utcInstant = instant;
        this.value = value;
    }

    public Instant getUtcInstant() {
        return utcInstant;
    }

    public double getValue() {
        return value;
    }


    @Override
    public int compareTo(TimeSeriesUnit o) {
        return utcInstant.compareTo(o.getUtcInstant());
    }


    // equals method doesn't match compareTo because it is compared based on the timeSeriesUnit for sorting purposes
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        final var stdError =  0.000001;
        if (o == null || getClass() != o.getClass()) return false;
        TimeSeriesUnit that = (TimeSeriesUnit) o;
        return (Math.abs(value - that.value) < stdError ) && utcInstant.equals(that.utcInstant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utcInstant);
    }
    @Override
    public String toString() {
        return "TimeSeriesUnit{" +
                "utcInstant=" + utcInstant.atZone(ZoneId.systemDefault()) +
                ", value=" + value +
                '}';
    }
}
