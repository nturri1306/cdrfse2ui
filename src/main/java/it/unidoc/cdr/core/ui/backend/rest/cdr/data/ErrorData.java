package it.unidoc.cdr.core.ui.backend.rest.cdr.data;


import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseData;

import java.util.Date;

public class ErrorData extends BaseData {

    private Date event;
    private String transaction;
    private String from;
    private String to;
    private String payload;
    private String response;

    public Date getEvent() {
        return event;
    }

    public void setEvent(Date event) {
        this.event = event;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
