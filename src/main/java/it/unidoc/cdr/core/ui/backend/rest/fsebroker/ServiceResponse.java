package it.unidoc.cdr.core.ui.backend.rest.fsebroker;

import it.unidoc.cdr.core.ui.backend.rest.fsebroker.common.BaseData.ResponseState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author b.amoruso
 */
public class ServiceResponse {

    private ResponseState state;
    private String description;
    private List<String> results = new ArrayList<>();

    public ResponseState getState() {
        return state;
    }

    public void setState(ResponseState state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getResults() {
        return results;
    }

    public void setResults(List<String> results) {
        this.results = results;
    }

}
