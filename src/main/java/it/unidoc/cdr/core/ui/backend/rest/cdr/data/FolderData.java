package it.unidoc.cdr.core.ui.backend.rest.cdr.data;


import java.util.*;

/**
 * TODO
 *
 * @author n.turri
 */
public class FolderData extends CommonData {


    private Date lastUpdateTime;
    private Date submissionUpdate;
    private List<Object> extraMetadata = new ArrayList<>();


    public Date getSubmissionUpdate() {
        return submissionUpdate;
    }

    public void setSubmissionUpdate(Date submissionUpdate) {
        this.submissionUpdate = submissionUpdate;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }


    public List<Object> getExtraMetadata() {
        return extraMetadata;
    }

    public void setExtraMetadata(List<Object> extraMetadata) {
        this.extraMetadata = extraMetadata;
    }


}
