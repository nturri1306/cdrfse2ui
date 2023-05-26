package it.unidoc.cdr.core.ui;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

import static java.lang.System.setProperty;

/**
 * Service listener invoked from Vaadin automatically.
 * To enable this mechanism add a file named "com.vaadin.flow.server.VaadinServiceInitListener"
 * into resources/META-INF/services
 *
 * @author b.amoruso
 */
public class ApplicationServiceInitListener implements VaadinServiceInitListener {

    private static final long serialVersionUID = -820402429428955338L;

    @Override
    public void serviceInit(ServiceInitEvent e) {
        setProperty("vaadin.i18n.provider", TranslationProvider.class.getName());
    }

}
