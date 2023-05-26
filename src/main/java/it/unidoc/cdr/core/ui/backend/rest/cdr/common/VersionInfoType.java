package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


/**
 *
 * @author n.turri
 */
public class VersionInfoType {
    private String comment;
    private String versionName;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

}
