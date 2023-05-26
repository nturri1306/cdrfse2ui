package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MetadataFse2 extends BaseFse2 {
    @Annotations.AsFilterHeaderGridColumns(enabled = true)
    private String body;

}
