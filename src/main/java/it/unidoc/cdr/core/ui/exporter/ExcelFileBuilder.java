package it.unidoc.cdr.core.ui.exporter;

import com.vaadin.flow.component.grid.Grid;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * @param <T>
 * @author b.amoruso
 */
public class ExcelFileBuilder<T> extends FileBuilder<T> {

    private static final String EXCEL_FILE_EXTENSION = ".xls";

    private Workbook workbook;
    private Sheet sheet;
    private int rowNr;
    private int colNr;
    private Row row;
    private Cell cell;
    private CellStyle boldStyle;

    public ExcelFileBuilder(Grid<T> grid, ExporterWrapper<T> wrapper) {
        super(grid, wrapper);
    }

    @Override
    public String getFileExtension() {
        return EXCEL_FILE_EXTENSION;
    }

    @Override
    protected void writeToFile() {
        try {
            workbook.write(new FileOutputStream(file()));
        } catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error("Error writing excel file", e);
        }

    }

    @Override
    protected void onNewRow() {
        row = sheet.createRow(rowNr);
        rowNr++;
        colNr = 0;
    }

    @Override
    protected void onNewCell() {
        cell = row.createCell(colNr);
        colNr++;
    }

    @Override
    protected void buildCell(Object value) {
        if (value == null) {
            cell.setBlank();
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Calendar) {
            cell.setCellValue((Calendar) value);
        } else if (value instanceof Date) {

            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yy hh:mm"));
            cell = row.createCell(1);
            cell.setCellValue((Date) value);
            cell.setCellStyle(cellStyle);

        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    @Override
    protected void buildColumnHeaderCell(String header) {
        buildCell(header);
        cell.setCellStyle(getBoldStyle());
    }

    private CellStyle getBoldStyle() {
        if (boldStyle == null) {
            Font bold = workbook.createFont();
            bold.setBold(true);

            boldStyle = workbook.createCellStyle();
            boldStyle.setFont(bold);
        }
        return boldStyle;
    }

    @Override
    protected void buildFooter() {
        for (int i = 0; i < getNumberOfColumns(); i++) {
            sheet.autoSizeColumn(i);
        }
    }

    @Override
    protected void resetContent() {
        workbook = createWorkbook();
        sheet = workbook.createSheet();
        colNr = 0;
        rowNr = 0;
        row = null;
        cell = null;
        boldStyle = null;
    }

    protected Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

}
