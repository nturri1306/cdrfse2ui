package it.unidoc.cdr.core.ui.backend.service.cdr;

import it.unidoc.cdr.core.helper.RouteHelper;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;

/**
 * @author b.amoruso
 */
public class UrlHelper {

    public static final String URL = "_URL_HEADER";

    public static void encode(Exchange e, boolean audit) {
        String url = e.getIn().getHeader(URL, String.class);

        url = RouteHelper.make(url, audit).getFlatUri();

        e.getIn().setHeader(URL, url);
    }

    public static String getUrl(String component) {
        return component + "://${header." + URL + "}";
    }

    public static <R, T> R request(ProducerTemplate producer, String name,
                                   T request, String url, Class<R> clazz) {
        return producer.requestBodyAndHeader(name, request, URL, url, clazz);
    }

}
