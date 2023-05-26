package it.unidoc.cdr.core.ui.backend.rest.cdr.common;

import java.util.Date;
import java.util.List;

public class ClassificationType {

    private String classificationNode;
    private String classificationScheme;
    private String classifiedObject;
    private List<String> extraMetadata;
    private String id;
    private Date localUpdated;
    private LocalizedType name;
    private String nodeRepresentation;
    private List<SlotType> slots;

    public String getClassificationNode() {
        return classificationNode;
    }

    public void setClassificationNode(String classificationNode) {
        this.classificationNode = classificationNode;
    }

    public String getClassificationScheme() {
        return classificationScheme;
    }

    public void setClassificationScheme(String classificationScheme) {
        this.classificationScheme = classificationScheme;
    }

    public String getClassifiedObject() {
        return classifiedObject;
    }

    public void setClassifiedObject(String classifiedObject) {
        this.classifiedObject = classifiedObject;
    }

    public List<String> getExtraMetadata() {
        return extraMetadata;
    }

    public void setExtraMetadata(List<String> extraMetadata) {
        this.extraMetadata = extraMetadata;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getLocalUpdated() {
        return localUpdated;
    }

    public void setLocalUpdated(Date localUpdated) {
        this.localUpdated = localUpdated;
    }

    public LocalizedType getName() {
        return name;
    }

    public void setName(LocalizedType name) {
        this.name = name;
    }

    public String getNodeRepresentation() {
        return nodeRepresentation;
    }

    public void setNodeRepresentation(String nodeRepresentation) {
        this.nodeRepresentation = nodeRepresentation;
    }

    public List<SlotType> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotType> slots) {
        this.slots = slots;
    }

}
