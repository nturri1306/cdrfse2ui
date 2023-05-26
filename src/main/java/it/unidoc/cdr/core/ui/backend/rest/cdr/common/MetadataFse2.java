package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


public class MetadataFse2 extends BaseFse2 {
    @Annotations.AsFilterHeaderGridColumns(enabled = true)
    private String body;

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return this.body;
    }
}
