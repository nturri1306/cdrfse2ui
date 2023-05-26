package it.unidoc.cdr.core.ui.backend.rest.fsebroker.type;

/**
 * @author b.amoruso
 */
public class OutcomeBatchType extends BatchType {

    private final static int TYPE = 2;

    public OutcomeBatchType(boolean local) {
        setType(TYPE);
        setLocal(local);
    }

}
