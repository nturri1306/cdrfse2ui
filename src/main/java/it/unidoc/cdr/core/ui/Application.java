package it.unidoc.cdr.core.ui;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import it.unidoc.cdr.core.ui.conf.CdrUiConfiguration;
import it.unidoc.cdr.core.ui.util.UIUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author b.amoruso
 */
@SpringBootApplication
@ComponentScan("it.unidoc.cdr.core")
@EnableConfigurationProperties(CdrUiConfiguration.class)
@PWA(name = "CDR-UI", shortName = "CDR-UI", iconPath = UIUtils.IMG_PATH + "favicon.png", backgroundColor = "#233348", themeColor = "#233348")
@Viewport("width=device-width, minimum-scale=1.0, initial-scale=1.0, user-scalable=yes")
@NpmPackage(value = "lumo-css-framework", version = "^4.0.10")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {

    private static final long serialVersionUID = 6304151372647386640L;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        System.setProperty("vaadin.i18n.provider", TranslationProvider.class.getName());
    }

}
