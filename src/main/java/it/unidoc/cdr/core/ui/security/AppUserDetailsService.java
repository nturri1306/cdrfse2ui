package it.unidoc.cdr.core.ui.security;

import it.unidoc.cdr.core.ui.backend.service.LocalUserService;
import it.unidoc.cdr.core.ui.backend.service.UserService;
import it.unidoc.cdr.core.ui.backend.type.UserType;
import it.unidoc.cdr.core.ui.conf.CdrUiConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author b.amoruso
 */

@Service
public class AppUserDetailsService implements UserDetailsService {

    private UserService service;
    @Autowired
    private CdrUiConfiguration conf;

    @Bean
    public UserService userService() throws IOException {
        if (service == null)
            service = new LocalUserService(conf.getUsersFile());

        return service;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserType> user = userService().findBy(username);

            if (user.isEmpty() || (!user.get().isEnabled() || user.get().isExpired()))
                throw new UsernameNotFoundException(username);
            else {
                UserType u = user.get();

                return new User(
                        u.getUsername(), u.getHashedPassword(), getAuthorities(u));
            }
        } catch (IOException e) {
            throw new UsernameNotFoundException(username);
        }
    }

    private List<GrantedAuthority> getAuthorities(UserType user) {
        return user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

}
