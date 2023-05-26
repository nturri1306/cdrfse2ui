package it.unidoc.cdr.core.ui.security;

import com.vaadin.flow.spring.security.VaadinDefaultRequestCache;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import it.unidoc.cdr.core.ui.views.user.LoginView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author b.amoruso
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurityConfigurerAdapter {

    public static final String LOGIN_URL = "/" + LoginView.NAME;
    public static final String LOGOUT_URL = LOGIN_URL;

    private static final String LOGIN_PROCESSING_URL = LOGIN_URL;
    private static final String LOGIN_FAILURE_URL = LOGIN_URL + "?error";

    @Autowired
    private VaadinDefaultRequestCache requestCache;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Vaadin handles CSRF internally
        http.csrf().disable().
                // Register our CustomRequestCache, which saves unauthorized access attempts, so
                // the user is redirected after login.
                        requestCache().
                requestCache(requestCache).
                // Restrict access to our application.
                        and().
                authorizeRequests().
                // Allow all Vaadin internal requests.
                        requestMatchers(SecurityUtils::isFrameworkInternalRequest).
                permitAll().
                // Allow all requests by logged-in users.
                        anyRequest().
                authenticated().
                // Configure the login page.
                        and().
                formLogin().
                loginPage(LOGIN_URL).
                permitAll().
                loginProcessingUrl(LOGIN_PROCESSING_URL).
                failureUrl(LOGIN_FAILURE_URL)
                // Configure logout
                .and().
                logout().
                logoutSuccessUrl(LOGOUT_URL);
    }

    /**
     * Allows access to static resources, bypassing Spring Security.
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                // Client-side JS
                "/VAADIN/**",
                // the standard favicon URI
                "/favicon.ico",
                // the robots exclusion standard
                "/robots.txt",
                // web application manifest
                "/manifest.webmanifest", "/sw.js", "/offline.html",
                // icons and images
                "/icons/**", "/images/**", "/styles/**",
                // (development mode) H2 debugging console
                "/h2-console/**");
    }

}
