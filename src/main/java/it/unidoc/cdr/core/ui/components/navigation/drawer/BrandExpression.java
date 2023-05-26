package it.unidoc.cdr.core.ui.components.navigation.drawer;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import it.unidoc.cdr.core.ui.util.UIUtils;

/**
 * @author b.amoruso
 */

@CssImport("./styles/components/brand-expression.css")
public class BrandExpression extends Div {

    private static final long serialVersionUID = 1679418322655544575L;

    public BrandExpression(String logoPath) {
        this(null, logoPath);
    }

    public BrandExpression(String text, String logoPath) {
        String CLASS_NAME = "brand-expression";
        setClassName(CLASS_NAME);

        Image logo = new Image(logoPath, "");
        logo.setClassName(CLASS_NAME + "__logo");

        add(logo);

        if (text != null) {
            Label title = UIUtils.createH3Label(text);
            title.addClassName(CLASS_NAME + "__title");

            logo.setAlt(text);

            add(title);
        }
    }

}
