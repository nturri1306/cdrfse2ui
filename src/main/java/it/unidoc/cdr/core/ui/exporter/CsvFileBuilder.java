package it.unidoc.cdr.core.ui.exporter;

import com.vaadin.flow.component.grid.Grid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @param <T>
 * @author b.amoruso
 */
public class CsvFileBuilder<T> extends FileBuilder<T> {

    private Logger log = LoggerFactory.getLogger(getClass());

    private FileWriter writer;
    private int rowNr;
    private int colNr;
    private String token;

    public CsvFileBuilder(Grid<T> grid, ExporterWrapper<T> wrapper) {
        this(grid, wrapper, ";");
    }

    public CsvFileBuilder(Grid<T> grid, ExporterWrapper<T> wrapper, String token) {
        super(grid, wrapper);

        this.token = token;
    }

    @Override
    protected void resetContent() {
        try {
            colNr = 0;
            rowNr = 0;
            writer = new FileWriter(file());
        } catch (IOException e) {
            throw new ExporterException("Unable to reset content", e);
        }
    }

    @Override
    protected void buildCell(Object value) {
        //generic string to append
        String stringToAppen = null;

        try {
            if (value == null) {
                stringToAppen = "";
            } else if (value instanceof Boolean) {
                stringToAppen = ((Boolean) value).toString();
            } else if (value instanceof Calendar) {
                stringToAppen = ((Calendar) value).toString();
            } else if (value instanceof Date) {
                stringToAppen = (new SimpleDateFormat("dd/MM/yy hh:mm", Locale.ITALIAN).format((Date) value)).toString();
            } else if (value instanceof Double) {
                stringToAppen = ((Double) value).toString();
            } else {
                stringToAppen = value.toString();
            }

            writer.append(stringToAppen);

        } catch (IOException e) {
            log.error("Error building cell " + value, e);
        }
    }

    @Override
    public String getFileExtension() {
        return ".csv";
    }

    @Override
    protected void writeToFile() {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new ExporterException("Failed to write to file", e);
        } finally {
            cleanupResource();
        }
    }

    private void cleanupResource() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            log.error("Unable to close the writer for CSV file", e);
        }
    }

    @Override
    protected void onNewRow() {
        if (rowNr > 0) {
            try {
                writer.append("\n");
            } catch (IOException e) {
                throw new ExporterException("Unable to create a new line", e);
            }
        }
        rowNr++;
        colNr = 0;
    }

    @Override
    protected void onNewCell() {
        if (colNr > 0 && colNr < getNumberOfColumns()) {
            try {
                writer.append(token);
            } catch (IOException e) {
                throw new ExporterException("Unable to create a new cell", e);
            }
        }
        colNr++;
    }

    @Override
    protected void buildColumnHeaderCell(String header) {
        try {
            writer.append(header);
        } catch (IOException e) {
            throw new ExporterException("Unable to build a header cell", e);
        }
    }

}
