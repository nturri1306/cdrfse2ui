package it.unidoc.cdr.core.ui.backend.rest.cdr.common;

public class LocalizedType {
    private String charset;
    private String lang;
    private String value;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
