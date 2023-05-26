package it.unidoc.cdr.core.ui.exporter;

/**
 * @author b.amoruso
 */
public class ExporterException extends RuntimeException {

    private static final long serialVersionUID = -9091217125120162596L;

    ExporterException(String message) {
        super(message);
    }

    ExporterException(String message, Exception e) {
        super(message, e);
    }

}
