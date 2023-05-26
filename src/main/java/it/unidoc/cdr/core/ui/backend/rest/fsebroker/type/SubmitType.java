package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class SubmitType extends UpdateType {

    // Base
    private String associationtype;
    private String mimetype;
    private String document;
    private String filename;
    private String hash;

    public String getAssociationtype() {
        return associationtype;
    }

    public void setAssociationtype(String associationtype) {
        this.associationtype = associationtype;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String name) {
        this.filename = name;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    @Override
    public String toString() {
        return getMimetype();
    }

}
