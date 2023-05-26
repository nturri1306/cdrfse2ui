package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public abstract class BatchType {

    private String identifiers;
    private int type;
    private boolean local;

    public String getIdentifiers() {
        return identifiers;
    }

    public void setIdentifiers(String identifiers) {
        this.identifiers = identifiers;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

}
