package it.unidoc.cdr.core.ui.exporter;

import com.vaadin.flow.component.grid.Grid;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @param <T>
 * @author b.amoruso
 */
public class XlsxFileBuilder<T> extends ExcelFileBuilder<T> {

    private static final String EXCEL_FILE_EXTENSION = ".xlsx";

    public XlsxFileBuilder(Grid<T> grid, ExporterWrapper<T> wrapper) {
        super(grid, wrapper);
    }

    @Override
    public String getFileExtension() {
        return EXCEL_FILE_EXTENSION;
    }

    @Override
    protected Workbook createWorkbook() {
        return new XSSFWorkbook();
    }

}
