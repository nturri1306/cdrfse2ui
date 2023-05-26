package it.unidoc.cdr.core.ui.components;

import com.vaadin.flow.component.orderedlayout.FlexComponent;
import it.unidoc.cdr.core.ui.util.FontSize;
import it.unidoc.cdr.core.ui.util.FontWeight;
import it.unidoc.cdr.core.ui.util.LumoStyles;
import it.unidoc.cdr.core.ui.util.UIUtils;
import it.unidoc.cdr.core.ui.util.css.BorderRadius;

/**
 * @author b.amoruso
 */
public class Initials extends FlexBoxLayout {

    private static final long serialVersionUID = 8427948439255540871L;

    private String CLASS_NAME = "initials";

    public Initials(String initials) {
        setAlignItems(FlexComponent.Alignment.CENTER);
        setBackgroundColor(LumoStyles.Color.Contrast._10);
        setBorderRadius(BorderRadius.L);
        setClassName(CLASS_NAME);
        UIUtils.setFontSize(FontSize.S, this);
        UIUtils.setFontWeight(FontWeight._600, this);
        setHeight(LumoStyles.Size.M);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setWidth(LumoStyles.Size.M);

        add(initials);
    }

}
