package it.unidoc.cdr.core.ui.backend.service;

import it.unidoc.cdr.core.ui.backend.type.UserType;
import it.unidoc.cdr.core.ui.backend.type.UserType.Role;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

/**
 * @author b.amoruso
 */
public interface UserService {

    public static final String ADMIN = "admin";

    public static UserType makeDefault() {
        String id = ADMIN;
        UserType user = new UserType(id);
        user.setEnabled(true);
        user.setPassword(id);
        user.setName("Administrator");
        user.addRole(Role.ADMIN);
        user.addRole(Role.SUPERUSER);

        return user;
    }

    public Set<String> findAll();

    public Optional<UserType> findBy(String username);

    public boolean checkPassword(UserType user);

    public void update(UserType user) throws IOException;

    public void delete(UserType user) throws IOException;

}
