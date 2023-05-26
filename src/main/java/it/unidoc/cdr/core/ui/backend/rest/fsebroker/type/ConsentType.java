package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class ConsentType {

    private String informativeIdentifier;
    private String consents;
    private String assistedTaxCode;
    private String parentAssistedTaxCode;

    public String getAssistedTaxCode() {
        return assistedTaxCode;
    }

    public void setAssistedTaxCode(String assistedTaxCode) {
        this.assistedTaxCode = assistedTaxCode;
    }

    public String getParentAssistedTaxCode() {
        return parentAssistedTaxCode;
    }

    public void setParentAssistedTaxCode(String parentAssistedTaxCode) {
        this.parentAssistedTaxCode = parentAssistedTaxCode;
    }

    public String getInformativeIdentifier() {
        return informativeIdentifier;
    }

    public void setInformativeIdentifier(String informativeIdentifier) {
        this.informativeIdentifier = informativeIdentifier;
    }

    public String getConsents() {
        return consents;
    }

    public void setConsents(String consents) {
        this.consents = consents;
    }

}
