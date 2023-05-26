package it.unidoc.cdr.core.ui.backend.rest.fsebroker.common;

/**
 * @author b.amoruso
 */
public class TypeWrapper<T> extends BaseData {

    private T param;

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

}
