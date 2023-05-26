package it.unidoc.cdr.core.ui.backend.rest.cdr.common;

import it.unidoc.cdr.core.helper.PatientIdHelper;
import org.openehealth.ipf.commons.ihe.xds.core.metadata.Identifiable;

public class PatientIdType {

    private String id;
    private String identifierTypeCode;
    private String namespaceId;
    private String universalId;
    private String universalIdType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIdentifierTypeCode() {
        return identifierTypeCode;
    }

    public void setIdentifierTypeCode(String identifierTypeCode) {
        this.identifierTypeCode = identifierTypeCode;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }
    public String getUniversalId() {
        return universalId;
    }

    public void setUniversalId(String universalId) {
        this.universalId = universalId;
    }

    public String getUniversalIdType() {
        return universalIdType;
    }

    public void setUniversalIdType(String universalIdType) {
        this.universalIdType = universalIdType;
    }

    public Identifiable toIdentifiable() {
        return PatientIdHelper.makePatientId(
            getId(), getIdentifierTypeCode(), getNamespaceId(), getUniversalId());
    }

}
