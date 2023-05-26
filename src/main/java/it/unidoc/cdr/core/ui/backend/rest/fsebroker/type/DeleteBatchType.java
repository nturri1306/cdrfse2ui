package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class DeleteBatchType extends BatchType {

    private final static int TYPE = 1;

    public DeleteBatchType(boolean local) {
        setType(TYPE);
        setLocal(local);
    }

}
