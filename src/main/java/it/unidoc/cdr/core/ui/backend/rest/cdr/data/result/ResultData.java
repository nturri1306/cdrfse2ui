package it.unidoc.cdr.core.ui.backend.rest.cdr.data.result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author b.amoruso
 */
public class ResultData {
    private Pageable pageable;
    private Boolean last;
    private Integer totalElements;
    private Integer totalPages;
    private Integer numberOfElements;
    private Boolean first;
    private Integer number;
    private Integer size;
    private Sort sort;
    private Boolean empty;

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Boolean getLast() {
        return last;
    }

    public void setLast(Boolean last) {
        this.last = last;
    }

    public Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Integer totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(Integer numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public Boolean getFirst() {
        return first;
    }

    public void setFirst(Boolean first) {
        this.first = first;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public static class Pageable {

        private Sort sort;
        private Integer pageNumber;
        private Integer pageSize;
        private Integer offset;
        private Boolean unpaged;
        private Boolean paged;

        public Sort getSort() {
            return sort;
        }

        public void setSort(Sort sort) {
            this.sort = sort;
        }

        public Integer getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getOffset() {
            return offset;
        }

        public void setOffset(Integer offset) {
            this.offset = offset;
        }

        public Boolean getUnpaged() {
            return unpaged;
        }

        public void setUnpaged(Boolean unpaged) {
            this.unpaged = unpaged;
        }

        public Boolean getPaged() {
            return paged;
        }

        public void setPaged(Boolean paged) {
            this.paged = paged;
        }
    }

    public static class Sort {

        private Boolean sorted;
        private Boolean unsorted;
        private Boolean empty;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Boolean getSorted() {
            return sorted;
        }

        public void setSorted(Boolean sorted) {
            this.sorted = sorted;
        }

        public Boolean getUnsorted() {
            return unsorted;
        }

        public void setUnsorted(Boolean unsorted) {
            this.unsorted = unsorted;
        }

        public Boolean getEmpty() {
            return empty;
        }

        public void setEmpty(Boolean empty) {
            this.empty = empty;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}
