package info.justingrimes.datagathering;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface SubjectAggregator {
    String getJSON() throws JsonProcessingException;
}
