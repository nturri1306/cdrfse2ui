package it.unidoc.cdr.core.ui.views.cdr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterHolder {
    private Map<String, String> filters = new HashMap<>();

    public void setFilter(String key, String value) {
        this.filters.put(key, value);
    }

    public Object getFilter(String key) {
        return this.filters.get(key);
    }

    public List<String> getFilterValues() {
        return (List<String>)this.filters.keySet().stream()
                .filter(key -> Objects.nonNull(this.filters.get(key)))
                .map(key -> encodeFilterValue(key, this.filters.get(key)))
                .collect(Collectors.toList());
    }

    private String encodeFilterValue(Object key, Object value) {
        return "" + key + ":" + key;
    }
}
