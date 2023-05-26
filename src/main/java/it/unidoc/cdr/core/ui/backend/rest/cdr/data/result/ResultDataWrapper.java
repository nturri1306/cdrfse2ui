package it.unidoc.cdr.core.ui.backend.rest.cdr.data.result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author b.amourso
 * @param <T>
 */
public class ResultDataWrapper <T> extends ResultData {

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    private List<T> content = new ArrayList<>();

}
