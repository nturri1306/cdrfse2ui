package it.unidoc.cdr.core.ui.backend.rest.fsebroker;

import it.unidoc.cdr.core.ui.backend.rest.fsebroker.common.BaseData.ResponseState;

/**
 * @author b.amoruso
 */
public class ApiResponse<T> {

    private ResponseState state;
    private String description;
    private T result;

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

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
