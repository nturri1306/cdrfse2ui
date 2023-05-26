package it.unidoc.cdr.core.ui.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "standalone")
public class StandaloneConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${server.http.port}")
    private int httpPort;

    @Value("${server.http.interface}")
    private String httpInterface;

    @Bean
    public WebServerFactoryCustomizer<UndertowServletWebServerFactory> containerCustomizer() {
        log.info("\tStarting standalone (HTTP) over interface {} and port {}", httpInterface, httpPort);

        return factory -> ((UndertowServletWebServerFactory)factory).
            getBuilderCustomizers().add(b -> b.addHttpListener(httpPort, httpInterface));
    }

}