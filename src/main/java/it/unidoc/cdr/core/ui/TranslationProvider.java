package it.unidoc.cdr.core.ui;

import com.vaadin.flow.i18n.I18NProvider;
import it.unidoc.cdr.core.ui.views.MainLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.*;

/**
 * Simple implementation of {@link I18NProvider}.
 *
 * @author b.amoruso
 */
public class TranslationProvider implements I18NProvider {

    public static final String BUNDLE_PREFIX = "messages";
    private static final long serialVersionUID = -3218485959013807737L;
    private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
    public final Locale LOCALE_IT = new Locale("it", "IT");
    public final Locale LOCALE_EN = new Locale("en", "GB");
    private List<Locale> locales = Collections.unmodifiableList(
            Arrays.asList(LOCALE_IT, LOCALE_EN));

    @Override
    public List<Locale> getProvidedLocales() {
        return locales;
    }

    @Override
    public String getTranslation(String key, Locale locale, Object... params) {
        if (key == null) {
            log.warn("Got lang request for key with null value!");

            return "";
        }

        // TODO: fix-it
	  	//final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, locale);
        final ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PREFIX, LOCALE_IT);

        String value;
        try {
            value = bundle.getString(key);
        } catch (final MissingResourceException e) {
            return "!" + locale.getLanguage() + ": " + key;
        }

        if (params.length > 0)
            value = MessageFormat.format(value, params);

        return value;
    }

}
