package it.unidoc.cdr.core.ui.util;

/**
 * @author b.amoruso
 */
public enum IconSize {

    S("size-s"),
    M("size-m"),
    L("size-l");

    private String style;

    IconSize(String style) {
        this.style = style;
    }

    public String getClassName() {
        return style;
    }

}
