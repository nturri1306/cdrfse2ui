package it.unidoc.cdr.core.ui.exporter;

import com.vaadin.flow.component.grid.Grid.Column;

/**
 * @param <T>
 * @author b.amoruso
 */
@FunctionalInterface
public interface ExporterWrapper<T> {

    public Object apply(Column<T> column, T item);

}
