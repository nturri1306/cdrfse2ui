package it.unidoc.cdr.core.ui.backend.rest.cdr.data;


import org.apache.logging.log4j.util.Strings;

import java.util.*;

/**
 * TODO
 *
 * @author n.turri
 */
public class DocumentData extends CommonData {

    private Date creation;
    private Date serviceStartTime;
    private Date serviceStopTime;
    private List<Object> extraMetadata = new ArrayList<>();
    private List<String> legalAuthenticator;
    private List<String> sourcePatientInfo;

    private String hash;

    private String languageCode;

    private String mimeType;

    private String repositoryUniqueId;
    private String sourcePatientId;
    private String uri;


    private int size;


    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }


    public List<Object> getExtraMetadata() {
        return extraMetadata;
    }

    public void setExtraMetadata(List<Object> extraMetadata) {
        this.extraMetadata = extraMetadata;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLegalAuthenticator() {
        if (Objects.nonNull(legalAuthenticator) && legalAuthenticator.size() > 0)
            return legalAuthenticator.get(0);

        return Strings.EMPTY;
    }

    public void setLegalAuthenticator(List<String> legalAuthenticator) {
        this.legalAuthenticator = legalAuthenticator;
    }


    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    public String getRepositoryUniqueId() {
        return repositoryUniqueId;
    }

    public void setRepositoryUniqueId(String repositoryUniqueId) {
        this.repositoryUniqueId = repositoryUniqueId;
    }

    public Date getServiceStartTime() {
        return serviceStartTime;
    }

    public void setServiceStartTime(Date serviceStartTime) {
        this.serviceStartTime = serviceStartTime;
    }

    public Date getServiceStopTime() {
        return serviceStopTime;
    }

    public void setServiceStopTime(Date serviceStopTime) {
        this.serviceStopTime = serviceStopTime;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSourcePatientId() {
        return sourcePatientId;
    }

    public void setSourcePatientId(String sourcePatientId) {
        this.sourcePatientId = sourcePatientId;
    }

    public List<String> getSourcePatientInfo() {
        return sourcePatientInfo;
    }

    public void setSourcePatientInfo(List<String> sourcePatientInfo) {
        this.sourcePatientInfo = sourcePatientInfo;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }


}
