package it.unidoc.cdr.core.ui.backend.rest.cdr.data;

import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseData;

import java.util.Date;
import java.util.List;

public class ForwardData extends BaseData {

    private Date event;
    private String to;
    private String payload;
    private Date localUpdated;
    private String registryErrorCode;
    private String registryErrorDescription;
    private List<String> documents;

    public String getRegistryErrorCode() {
        return registryErrorCode;
    }

    public void setRegistryErrorCode(String registryErrorCode) {
        this.registryErrorCode = registryErrorCode;
    }

    public String getRegistryErrorDescription() {
        return registryErrorDescription;
    }

    public void setRegistryErrorDescription(String registryErrorDescription) {
        this.registryErrorDescription = registryErrorDescription;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }


    public Date getEvent() {
        return event;
    }

    public void setEvent(Date event) {
        this.event = event;
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

    public Date getLocalUpdated() {
        return localUpdated;
    }

    public void setLocalUpdated(Date localUpdated) {
        this.localUpdated = localUpdated;
    }

}
