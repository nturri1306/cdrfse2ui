package it.unidoc.cdr.core.ui.backend.rest.cdr.common;


import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;
import java.util.List;


/**
 * @author b.amoruso
 */
public class RestPageRequest {

    @QueryParam("page")
    @DefaultValue("0")
    @PositiveOrZero
    private int page;

    @QueryParam("size")
    @DefaultValue("100")
    @Positive
    private int size;

    @QueryParam("ascending")
    @DefaultValue("true")
    private boolean ascending;

    @QueryParam("property")
    private String property;

    @QueryParam("filter")
    private List<String> filter = new ArrayList<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public String isProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void addFilter(String name, String value) {
        filter.add(name + ":" + value);
    }

    @Override
    public String toString() {
        return "RestPageRequest{" +
                "page=" + page +
                ", size=" + size +
                ", ascending=" + ascending +
                ", property='" + property + '\'' +
                '}';
    }

}
