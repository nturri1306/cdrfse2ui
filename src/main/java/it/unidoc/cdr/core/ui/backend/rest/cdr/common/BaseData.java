package it.unidoc.cdr.core.ui.backend.rest.cdr.common;

public class BaseData {
    private String id;
    private boolean removed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

}
