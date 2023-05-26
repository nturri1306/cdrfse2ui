package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsFilterHeaderGridColumns;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsToggleableGridColumn;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CdrFse2Validation extends BaseFse2 {
    @AsFilterHeaderGridColumns(enabled = true)
    private String body;

    @AsFilterHeaderGridColumns(enabled = true)
    private String fileName;

    @AsFilterHeaderGridColumns(enabled = true)
    private String patient;

    @AsFilterHeaderGridColumns(enabled = true)
    private String author;

    @AsFilterHeaderGridColumns(enabled = true)
    private String custodian;

    @AsFilterHeaderGridColumns(enabled = true)
    private String authenticator;

    @AsToggleableGridColumn(enabled = true)
    private String structureId;
}


