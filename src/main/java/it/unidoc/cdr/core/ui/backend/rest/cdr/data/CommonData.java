package it.unidoc.cdr.core.ui.backend.rest.cdr.data;

import it.unidoc.cdr.core.ui.backend.rest.cdr.common.*;
import org.apache.logging.log4j.util.Strings;

import java.util.*;

public class CommonData extends BaseData {

    private boolean approved;
    private boolean deprecated;
    private boolean submitted;
    private String home;
    private String lid;
    private String objectType;
    private String originalId;
    private String status;
    private String uniqueId;
    private String sourceId;
    private ArrayList<SlotType> slots;
    private Date localUpdated;
    private List<ClassificationType> classifications = new ArrayList<>();
    private LocalizedType description;
    private LocalizedType name;
    private Object referenceIdList;
    private PatientIdType patientId;
    private VersionInfoType versionInfo;


    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    private ArrayList<SlotType> getSlots() {
        return slots;
    }

    public void setSlots(ArrayList<SlotType> slots) {
        this.slots = slots;
    }

    public List<ClassificationType> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<ClassificationType> classifications) {
        this.classifications = classifications;
    }

    /*get value name.value from Classification list property */

    public String getClassificationValue(String scheme) {
        ClassificationType c = getClassification(scheme);

        if (Objects.nonNull(c))
            return c.getName().getValue();

        return Strings.EMPTY;
    }

    public ClassificationType getClassification(String scheme) {
        Optional<ClassificationType> cl = classifications.stream().filter(
                c -> c.getClassificationScheme().equals(scheme)).findAny();

        return cl.orElse(null);
    }

    public PatientIdType getPatientId() {
        return patientId;
    }

    public void setPatientId(PatientIdType patientId) {
        this.patientId = patientId;
    }


    public Date getLocalUpdated() {
        return localUpdated;
    }

    public void setLocalUpdated(Date localUpdated) {
        this.localUpdated = localUpdated;
    }

    public VersionInfoType getVersionInfo() {
        return versionInfo;
    }

    public String getVersionName() {

        return Objects.nonNull(versionInfo) ? versionInfo.getVersionName() : "";
    }

    public void setVersionInfo(VersionInfoType versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public LocalizedType getDescription() {
        return description;
    }

    public void setDescription(LocalizedType description) {
        this.description = description;
    }

    public String getDescriptionValue() {
        return Objects.nonNull(description) ? description.getValue() : "";
    }

    public LocalizedType getName() {
        return name;
    }

    public void setName(LocalizedType name) {
        this.name = name;
    }

    public String getNameValue() {
        return Objects.nonNull(name) ? name.getValue() : "";
    }


    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public void setDeprecated(boolean deprecated) {
        this.deprecated = deprecated;
    }

    public Object getReferenceIdList() {
        return referenceIdList;
    }

    public void setReferenceIdList(Object referenceIdList) {
        this.referenceIdList = referenceIdList;
    }


    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }


    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }


    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }


}
