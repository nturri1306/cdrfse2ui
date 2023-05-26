package it.unidoc.cdr.core.ui.backend.rest.cdr.data;


import java.util.*;

public class SubmissionData extends CommonData {


    private Date updated;

    private String intendedRecipient;


    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getIntendedRecipient() {
        return intendedRecipient;
    }

    public void setIntendedRecipient(String intendedRecipient) {
        this.intendedRecipient = intendedRecipient;
    }


}
