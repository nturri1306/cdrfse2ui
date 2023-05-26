package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class UpdateType {

    private String documentOid;
    private String documentPreviousOid;
    private String parentdocumentuuid;
    private String documentuuid;
    private String submissionsetuuid;
    private String classcode;
    private String healthcarefacilitytypecode;
    private String confidentialitycode;
    private String formatcode;
    private String typecode;
    private String contenttypecode;
    private String practicesettingcode;
    private String assistedcode;
    private String authenticatortime;
    private String authorfiscalcode;
    private String authorrole;
    private String authortelecom;
    private String startperformance;
    private String endperformance;

    public String getDocumentOid() {
        return documentOid;
    }

    public void setDocumentOid(String documentOid) {
        this.documentOid = documentOid;
    }

    public String getDocumentPreviousOid() {
        return documentPreviousOid;
    }

    public void setDocumentPreviousOid(String documentPreviousOid) {
        this.documentPreviousOid = documentPreviousOid;
    }

    public String getParentdocumentuuid() {
        return parentdocumentuuid;
    }

    public void setParentdocumentuuid(String parentdocumentuuid) {
        this.parentdocumentuuid = parentdocumentuuid;
    }

    public String getDocumentuuid() {
        return documentuuid;
    }

    public void setDocumentuuid(String documentuuid) {
        this.documentuuid = documentuuid;
    }

    public String getSubmissionsetuuid() {
        return submissionsetuuid;
    }

    public void setSubmissionsetuuid(String submissionsetuuid) {
        this.submissionsetuuid = submissionsetuuid;
    }

    public String getClasscode() {
        return classcode;
    }

    public void setClasscode(String code) {
        this.classcode = code;
    }

    public String getPracticesettingcode() {
        return practicesettingcode;
    }

    public void setPracticesettingcode(String practicesettingcode) {
        this.practicesettingcode = practicesettingcode;
    }

    public String getHealthcarefacilitytypecode() {
        return healthcarefacilitytypecode;
    }

    public void setHealthcarefacilitytypecode(String code) {
        this.healthcarefacilitytypecode = code;
    }

    public String getConfidentialitycode() {
        return confidentialitycode;
    }

    public void setConfidentialitycode(String code) {
        this.confidentialitycode = code;
    }

    public String getFormatcode() {
        return formatcode;
    }

    public void setFormatcode(String code) {
        this.formatcode = code;
    }

    public String getTypecode() {
        return typecode;
    }

    public void setTypecode(String code) {
        this.typecode = code;
    }

    public String getContenttypecode() {
        return contenttypecode;
    }

    public void setContenttypecode(String code) {
        this.contenttypecode = code;
    }

    public String getAssistedcode() {
        return assistedcode;
    }

    public void setAssistedcode(String assistedCode) {
        this.assistedcode = assistedCode;
    }

    public String getAuthenticatortime() {
        return authenticatortime;
    }

    public void setAuthenticatortime(String authenticatorTime) {
        this.authenticatortime = authenticatorTime;
    }

    public String getAuthorfiscalcode() {
        return authorfiscalcode;
    }

    public void setAuthorfiscalcode(String authorFiscalCode) {
        this.authorfiscalcode = authorFiscalCode;
    }

    public String getAuthorrole() {
        return authorrole;
    }

    public void setAuthorrole(String authorrole) {
        this.authorrole = authorrole;
    }

    public String getAuthortelecom() {
        return authortelecom;
    }

    public void setAuthortelecom(String authorTelecom) {
        this.authortelecom = authorTelecom;
    }

    public String getStartperformance() {
        return startperformance;
    }

    public void setStartperformance(String startPerformance) {
        this.startperformance = startPerformance;
    }

    public String getEndperformance() {
        return endperformance;
    }

    public void setEndperformance(String endPerformance) {
        this.endperformance = endPerformance;
    }

}
