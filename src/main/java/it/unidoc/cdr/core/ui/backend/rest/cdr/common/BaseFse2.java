package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import java.util.Date;

import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsFilterHeaderGridColumns;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsToggleableGridColumn;

public class BaseFse2 extends BaseData {
    @AsToggleableGridColumn(enabled = true)
    private String _id;

    @AsFilterHeaderGridColumns(enabled = true)
    private int statusCode;

    private Date created;

    @AsToggleableGridColumn(enabled = true)
    private Date requested;

    @AsToggleableGridColumn(enabled = true)
    private String response;

    @AsToggleableGridColumn(enabled = true)
    private Date sent;

    @AsToggleableGridColumn(enabled = true)
    private String bearerToken;

    @AsToggleableGridColumn(enabled = true)
    private String claimsToken;

    private String serviceUrl;

    @AsToggleableGridColumn(enabled = true)
    private String _class;

    public void set_id(String _id) {
        this._id = _id;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setRequested(Date requested) {
        this.requested = requested;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public void setClaimsToken(String claimsToken) {
        this.claimsToken = claimsToken;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String get_id() {
        return this._id;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public Date getCreated() {
        return this.created;
    }

    public Date getRequested() {
        return this.requested;
    }

    public String getResponse() {
        return this.response;
    }

    public Date getSent() {
        return this.sent;
    }

    public String getBearerToken() {
        return this.bearerToken;
    }

    public String getClaimsToken() {
        return this.claimsToken;
    }

    public String getServiceUrl() {
        return this.serviceUrl;
    }

    public String get_class() {
        return this._class;
    }
}
