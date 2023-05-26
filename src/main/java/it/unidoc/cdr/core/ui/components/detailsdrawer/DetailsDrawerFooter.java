package it.unidoc.cdr.core.ui.components.detailsdrawer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.shared.Registration;
import it.unidoc.cdr.core.ui.components.FlexBoxLayout;
import it.unidoc.cdr.core.ui.layout.size.Horizontal;
import it.unidoc.cdr.core.ui.layout.size.Right;
import it.unidoc.cdr.core.ui.layout.size.Vertical;
import it.unidoc.cdr.core.ui.util.LumoStyles;

/**
 * @author b.amoruso
 */
public class DetailsDrawerFooter extends FlexBoxLayout {

    private static final long serialVersionUID = -8788030364539261533L;

    public DetailsDrawerFooter() {
        setBackgroundColor(LumoStyles.Color.Contrast._5);
        setPadding(Horizontal.RESPONSIVE_L, Vertical.S);
        setSpacing(Right.S);
        setWidthFull();
    }

    public Registration addButton(Button button,
                                  ComponentEventListener<ClickEvent<Button>> listener) {

        add(button);

        return button.addClickListener(listener);
    }

}
