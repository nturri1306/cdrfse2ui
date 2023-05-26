package it.unidoc.cdr.core.ui.backend.rest.fsebroker.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author b.amoruso
 */
public class StatusData {

    private boolean active;
    private boolean invalidPassword;
    private String userId;
    private String organizationId;
    private String userRole;
    private List<String> userStructure;
    private String exception;
    private String endpoint;
    private boolean basic;
    private String keyStoreType;
    private String keyStoreProvider;
    private List<Cert> certificate = new ArrayList<>();

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(boolean invalid) {
        this.invalidPassword = invalid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public List<String> getUserStructure() {
        return userStructure;
    }

    public void setUserStructure(List<String> userStructure) {
        this.userStructure = userStructure;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public boolean isBasic() {
        return basic;
    }

    public void setBasic(boolean basic) {
        this.basic = basic;
    }

    public String getKeyStoreType() {
        return keyStoreType;
    }

    public void setKeyStoreType(String keyStoreType) {
        this.keyStoreType = keyStoreType;
    }

    public String getKeyStoreProvider() {
        return keyStoreProvider;
    }

    public void setKeyStoreProvider(String keyStoreProvider) {
        this.keyStoreProvider = keyStoreProvider;
    }

    public List<Cert> getCertificate() {
        return certificate;
    }

    public void setCertificate(List<Cert> certificate) {
        this.certificate = certificate;
    }

    public static class Cert {

        private String cn;
        private String expireDate;
        private boolean expired;
        private boolean notYetValid;

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public boolean isNotYetValid() {
            return notYetValid;
        }

        public void setNotYetValid(boolean notYetValid) {
            this.notYetValid = notYetValid;
        }

        public String getCn() {
            return cn;
        }

        public void setCn(String cn) {
            this.cn = cn;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

    }

}
