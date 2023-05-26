package it.unidoc.cdr.core.ui.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.HandlerHelper.RequestType;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.shared.ApplicationConstants;
import it.unidoc.cdr.core.ui.backend.type.UserType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author b.amoruso
 */
public final class SecurityUtils {

    public static void logout() {
        UI.getCurrent().getPage().setLocation(SecurityConfig.LOGOUT_URL);

        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
                VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
    }

    public static Optional<UserDetails> getAuthenticatedUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        if (principal instanceof UserDetails)
            return Optional.of((UserDetails) context.getAuthentication().getPrincipal());

        // Anonymous or no authentication.
        return Optional.empty();
    }

    public static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);

        return parameterValue != null && Stream.of(RequestType.values()).anyMatch(
                r -> r.getIdentifier().equals(parameterValue));
    }

    public static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null &&
                !(authentication instanceof AnonymousAuthenticationToken) &&
                authentication.isAuthenticated();
    }

    public static boolean isSuperUser(UserDetails user) {
        return hasRole(user, UserType.Role.SUPERUSER);
    }

    public static boolean isAdministrator(UserDetails user) {
        return hasRole(user, UserType.Role.ADMIN);
    }

    public static boolean hasRole(UserDetails user, UserType.Role role) {
        return user.getAuthorities().stream().filter(
                        u -> u.getAuthority().contains(role.toString())).
                findFirst().isPresent();
    }

}
