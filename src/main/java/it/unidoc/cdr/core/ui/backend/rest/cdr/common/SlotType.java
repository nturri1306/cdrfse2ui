package it.unidoc.cdr.core.ui.backend.rest.cdr.common;

import java.util.List;

public class SlotType {

    private boolean extra;
    private String id;
    private String name;
    private List<String> values;

    public boolean isExtra() {
        return extra;
    }

    public void setExtra(boolean extra) {
        this.extra = extra;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}
