package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import java.util.Date;

import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsFilterHeaderGridColumns;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.Annotations.AsToggleableGridColumn;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseFse2 extends BaseData {
    @AsToggleableGridColumn(enabled = true)
    private String _id;

    @AsFilterHeaderGridColumns(enabled = true)
    private int statusCode;

    private Date created;

    @AsToggleableGridColumn(enabled = true)
    private Date requested;

    @AsToggleableGridColumn(enabled = true)
    private String response;

    @AsToggleableGridColumn(enabled = true)
    private Date sent;

    @AsToggleableGridColumn(enabled = true)
    private String bearerToken;

    @AsToggleableGridColumn(enabled = true)
    private String claimsToken;

    private String serviceUrl;

    @AsToggleableGridColumn(enabled = true)
    private String _class;

}
