package it.unidoc.cdr.core.ui.backend.service;

import it.unidoc.cdr.core.ui.backend.type.UserType;

import java.io.*;
import java.util.Base64;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

/**
 * @author b.amoruso
 */
public class LocalUserService implements UserService {

    private final Properties props;
    private File file;

    public LocalUserService(File file) throws IOException {
        boolean initialized = init(file);

        this.file = file;
        this.props = new Properties();

        load();

        if (initialized)
            update(UserService.makeDefault());
    }

    @Override
    public Set<String> findAll() {
        return props.stringPropertyNames();
    }

    @Override
    public Optional<UserType> findBy(String username) {
        return username != null && props.containsKey(username) ?
                Optional.of(decode(props.getProperty(username))) :
                Optional.empty();
    }

    @Override
    public boolean checkPassword(UserType user) {
        Optional<UserType> u = findBy(user.getUsername());

        return !u.isPresent() ? false :
                user.getHashedPassword().equals(u.get().getHashedPassword());
    }

    @Override
    public void update(UserType user) throws IOException {
        props.setProperty(user.getUsername(), encode(user));

        flush();
    }

    @Override
    public void delete(UserType user) throws IOException {
        props.remove(user.getUsername());

        flush();
    }

    private boolean init(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();

            return true;
        }

        return false;
    }

    private void flush() throws IOException {
        try (final OutputStream outputstream = new FileOutputStream(file)) {
            props.store(outputstream, "");

            outputstream.close();

            load();
        }
    }

    private void load() throws IOException {
        props.load(new FileInputStream(file));
    }

    private String encode(UserType user) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(user);
            oos.flush();
            oos.close();

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        }
    }

    private UserType decode(String raw) {
        UserType user = new UserType();

        byte[] data = Base64.getDecoder().decode(raw);
        UserType o = null;

        try (ObjectInputStream ois = new ObjectInputStream(
                new ByteArrayInputStream(data))) {
            o = (UserType) ois.readObject();

            ois.close();

            user.clone(o);

            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
