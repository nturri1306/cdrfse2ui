package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

import java.util.List;

/**
 * @author b.amoruso
 */
public class OutcomeType extends WithSubmit {

    private String tempIdentifier;
    private List<String> tempIdentifiers;
    private String beginDateTime;
    private String endDateTime;
    private String details;

    public String getTempIdentifier() {
        return tempIdentifier;
    }

    public void setTempIdentifier(String tempIdentifier) {
        this.tempIdentifier = tempIdentifier;
    }

    public String getBeginDateTime() {
        return beginDateTime;
    }

    public void setBeginDateTime(String beginDateTime) {
        this.beginDateTime = beginDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public  List<String> getTempIdentifiers() {
        return tempIdentifiers;
    }

    public void setTempIdentifiers(List<String> tempIdentifier) {
        this.tempIdentifiers = tempIdentifiers;
    }

}
