package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class ConsentStateType {

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

}
