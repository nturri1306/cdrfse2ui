package it.unidoc.cdr.core.ui.exporter;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.DataCommunicator;
import com.vaadin.flow.data.provider.Query;
import org.openehealth.ipf.commons.core.modules.api.ExtractorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @param <T>
 * @author b.amoruso
 */
public abstract class FileBuilder<T> implements Serializable

{

    private Logger log = LoggerFactory.getLogger(getClass());

    private File file;
    private Grid<T> grid;
    private ExporterWrapper<T> wrapper;
    private Collection<Grid.Column<T>> columns;
    private boolean headerRowBuilt = false;

    public FileBuilder(Grid<T> grid, ExporterWrapper<T> wrapper) {
        this.grid = grid;
        this.wrapper = wrapper;

        setExportableColumnFilter();

        if (columns.isEmpty())
            throw new ExporterException(
                    "No exportable column found. Use setKey on column");
    }

    public InputStream build() {
        try {

            setExportableColumnFilter();

            initTempFile();
            resetContent();
            buildFileContent();
            writeToFile();

            return new FileInputStream(file);
        } catch (IOException ex) {
            throw new ExtractorException("Build error", ex);
        }
    }

    /**
     * Set columns list by filters
     */
    private void setExportableColumnFilter() {
        columns = grid.getColumns().stream().filter(this::isExportable).collect(Collectors.toList());
    }

    private boolean isExportable(Grid.Column<T> column) {
        return column.isVisible() && column.getKey() != null && !column.getKey().isEmpty();
    }

    private void initTempFile() throws IOException {

        if (file == null || file.delete())
            file = createTempFile();

    }

    private void buildFileContent() {
        buildRows();
        buildFooter();
    }

    protected File file() {
        return file;
    }

    void buildColumnHeaderCell(String header) {
    }

    void onNewRow() {
    }

    void onNewCell() {
    }

    void buildFooter() {
    }

    public abstract String getFileExtension();

    abstract void buildCell(Object value);

    abstract void writeToFile();

    protected void resetContent() {
    }

    private void buildHeaderRow() {
        columns.forEach(column -> {
            onNewCell();
            buildColumnHeaderCell(column.getKey());
        });

        headerRowBuilt = true;
    }

    @SuppressWarnings("unchecked")
    private void buildRows() {
        Object filter = null;

        try {
            Method method = DataCommunicator.class.getDeclaredMethod("getFilter");
            method.setAccessible(true);

            filter = method.invoke(grid.getDataCommunicator());
        } catch (Exception e) {
            log.error("Unable to get filter from DataCommunicator");
        }


       /*for backend methods with paging */
        if (grid.getDataProvider().toString().indexOf("CallbackDataProvider")>=0) {

            grid.getGenericDataView().getItems().forEach(t -> {
                try {

                    onNewRow();
                    buildRow(t);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        else {
            Query streamQuery = new Query<>(0,
                    grid.getDataProvider().size(new Query(filter)),
                    grid.getDataCommunicator().getBackEndSorting(),
                    grid.getDataCommunicator().getInMemorySorting(), null);
            Stream<T> dataStream = getDataStream(streamQuery);

            dataStream.forEach(t -> {
                onNewRow();
                buildRow(t);
            });
        }

        //reset the boolean parameter of the header row for the next file to export
        headerRowBuilt = false;
    }

    private void buildRow(T item) {
        if (!headerRowBuilt) {
            buildHeaderRow();
            onNewRow();
        }

        columns.forEach(column -> {
            onNewCell();
            buildCell(wrapper.apply(column, item));
        });
    }

    private File createTempFile() throws IOException {
        return File.createTempFile("TMP", getFileExtension());
    }

    int getNumberOfColumns() {
        return columns.size();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Stream<T> getDataStream(Query newQuery) {
        Stream<T> stream = grid.getDataProvider().fetch(newQuery);
        if (stream.isParallel()) {
            log.debug(
                    "Data provider {} has returned " + "parallel stream on 'fetch' call",
                    grid.getDataProvider().getClass());

            stream = stream.collect(Collectors.toList()).stream();
            assert !stream.isParallel();
        }
        return stream;
    }

}
