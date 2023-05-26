package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

import java.util.Date;

public class WithSubmit {

    private String submitId;
    private Date submitCreated;
    private boolean localOnly;

    public String getSubmitId() {
        return submitId;
    }

    public void setSubmitId(String submitId) {
        this.submitId = submitId;
    }

    public Date getSubmitCreated() {
        return submitCreated;
    }

    public void setSubmitCreated(Date submitCreated) {
        this.submitCreated = submitCreated;
    }

    public boolean isLocalOnly() {
        return localOnly;
    }

    public void setLocalOnly(boolean localOnly) {
        this.localOnly = localOnly;
    }

}
