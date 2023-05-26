package it.unidoc.cdr.core.ui.backend.rest.cdr.data;

public class DocumentFileData extends DocumentData {
    private String fileName;
    private String fileReference;
    private String fileStrategy;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileReference() {
        return fileReference;
    }

    public void setFileReference(String fileReference) {
        this.fileReference = fileReference;
    }

    public String getFileStrategy() {
        return fileStrategy;
    }

    public void setFileStrategy(String fileStrategy) {
        this.fileStrategy = fileStrategy;
    }

}
