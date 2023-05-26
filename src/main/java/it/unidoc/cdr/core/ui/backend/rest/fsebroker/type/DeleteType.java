package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

import java.util.Objects;

/**
 * @author b.amoruso
 */
public class DeleteType extends WithSubmit {

    private String identifier;
    private String assistedTaxCode;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = Objects.nonNull(identifier) ? identifier:"";
    }

    public String getAssistedTaxCode() {
        return assistedTaxCode;
    }

    public void setAssistedTaxCode(String assistedCode) {
        this.assistedTaxCode = assistedCode;
    }

}
