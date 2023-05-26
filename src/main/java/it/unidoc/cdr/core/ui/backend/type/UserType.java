package it.unidoc.cdr.core.ui.backend.type;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author b.amoruso
 */
public class UserType implements Serializable {

    private static final long serialVersionUID = 4766379201323472658L;
    private String username;
    private String name;
    private String surname;
    private String hashedPassword;
    private boolean enabled;
    private boolean expired;
    private String email;
    private boolean menuRegistry;
    private boolean menuRepository;
    private boolean notification;
    private boolean weekly;
    private Set<Role> roles = new HashSet<>();
    private String pictureUrl;
    public UserType() {
    }

    public UserType(String username) {
        setUsername(username);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isMenuRegistry() {
        return menuRegistry;
    }

    public void setMenuRegistry(boolean menuRegistry) {
        this.menuRegistry = menuRegistry;
    }

    public boolean isMenuRepository() {
        return menuRepository;
    }

    public void setMenuRepository(boolean menuRepository) {
        this.menuRepository = menuRepository;
    }

    public boolean isNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isWeekly() {
        return weekly;
    }

    public void setWeekly(boolean weekly) {
        this.weekly = weekly;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setPassword(String password) {
        setHashedPassword(new BCryptPasswordEncoder().encode(password));
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, getHashedPassword());
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String url) {
        this.pictureUrl = url;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public void clone(UserType o) {
        setUsername(o.getUsername());
        setHashedPassword(o.getHashedPassword());
        setName(o.getName());
        setEmail(o.getEmail());
        setSurname(o.getSurname());
        setPictureUrl(o.getPictureUrl());
        setMenuRegistry(o.isMenuRegistry());
        setMenuRepository(o.isMenuRepository());
        setEnabled(o.isEnabled());
        setExpired(o.isExpired());
        setNotification(o.isNotification());
        setWeekly(o.isWeekly());
        setRoles(o.getRoles());
    }

    public void reset() {
        clone(new UserType());
    }

    @Override
    public String toString() {
        return username;
    }

    public enum Role {

        USER, ADMIN, SUPERUSER;

        private String label;

        public static Role toRole(String role) {
            for (Role r : values())
                if (r.name().equalsIgnoreCase(role))
                    return r;

            return USER;
        }

        public String label() {
            return label != null ? label : name();
        }

        public void label(String label) {
            this.label = label;
        }

    }

}
