package it.unidoc.cdr.core.ui.backend.rest.fsebroker.common;

import java.util.Date;
import java.util.Objects;

/**
 * @author b.amoruso
 */
public class BaseData {

    private String id;
    private Date created;
    private String source;
    private ResponseState state;
    private String reason;
    private String user;
    private String structure;
    private boolean removed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public ResponseState getState() {
        return state;
    }

    public void setState(ResponseState state) {
        this.state = state;
    }

    public String getReason() {
        return Objects.nonNull(reason)? reason.replace("null ","").replace("null",""):"";
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public enum ResponseState {

        SUCCESS("00"),
        SUCCESS_WITHALERTS("10"),
        OUTCOME_ERROR("12"),
        AUTHENTICATION_ERROR("11"),
        UNKNOWN_APPLICATION("01"),
        INVALID_HASH("02"),
        INVALID_BINARYCONTENT("03"),
        XMLSCHEMA_ERROR("04"),
        INVALID_REQUEST("05"),
        COMMUNICATION_ERROR("06");

        private final String code;
        private String label;

        ResponseState(String code) {
            this.code = code;
        }

        public String code() {
            return code;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public boolean isSuccess() {
            return ResponseState.SUCCESS.equals(this) ||
                    ResponseState.SUCCESS_WITHALERTS.equals(this);
        }

        @Override
        public String toString() {
            return getLabel();
        }

    }

}
