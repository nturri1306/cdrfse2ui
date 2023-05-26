package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class SubmitBatchType extends BatchType {

    private final static int TYPE = 0;

    public SubmitBatchType(boolean local) {
        setType(TYPE);
        setLocal(local);
    }

}
