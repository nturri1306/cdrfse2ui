package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsFilterHeaderGridColumns;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsToggleableGridColumn;

public class CdrFse2Validation extends BaseFse2 {
    @AsFilterHeaderGridColumns(enabled = true)
    private String body;

    @AsFilterHeaderGridColumns(enabled = true)
    private String fileName;

    @AsFilterHeaderGridColumns(enabled = true)
    private String patient;

    @AsFilterHeaderGridColumns(enabled = true)
    private String author;

    @AsFilterHeaderGridColumns(enabled = true)
    private String custodian;

    @AsFilterHeaderGridColumns(enabled = true)
    private String authenticator;

    @AsToggleableGridColumn(enabled = true)
    private String structureId;

    public void setBody(String body) {
        this.body = body;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCustodian(String custodian) {
        this.custodian = custodian;
    }

    public void setAuthenticator(String authenticator) {
        this.authenticator = authenticator;
    }

    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    public String getBody() {
        return this.body;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getPatient() {
        return this.patient;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getCustodian() {
        return this.custodian;
    }

    public String getAuthenticator() {
        return this.authenticator;
    }

    public String getStructureId() {
        return this.structureId;
    }
}
