package it.unidoc.cdr.core.ui.components.detailsdrawer;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import it.unidoc.cdr.core.ui.components.FlexBoxLayout;
import it.unidoc.cdr.core.ui.layout.size.Horizontal;
import it.unidoc.cdr.core.ui.layout.size.Right;
import it.unidoc.cdr.core.ui.layout.size.Vertical;
import it.unidoc.cdr.core.ui.util.BoxShadowBorders;
import it.unidoc.cdr.core.ui.util.UIUtils;

/**
 * @author b.amoruso
 */
public class DetailsDrawerHeader extends FlexBoxLayout {

    private static final long serialVersionUID = 4699234680699043415L;

    private Button close;
    private Label title;
    private HorizontalLayout extra;

    public DetailsDrawerHeader(String title) {
        addClassName(BoxShadowBorders.BOTTOM);
        setFlexDirection(FlexDirection.COLUMN);
        setWidthFull();

        this.close = UIUtils.createTertiaryInlineButton(VaadinIcon.CLOSE);
        UIUtils.setLineHeight("1", this.close);

        this.title = UIUtils.createH4Label(title);
        this.extra = new HorizontalLayout();
        this.extra.setMargin(false);
        this.extra.setSpacing(false);

        FlexBoxLayout wrapper = new FlexBoxLayout(this.close, this.title, this.extra);
        wrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        wrapper.setPadding(Horizontal.RESPONSIVE_L, Vertical.M);
        wrapper.setSpacing(Right.L);

        add(wrapper);
    }

    public DetailsDrawerHeader(String title, Tabs tabs) {
        this(title);

        add(tabs);
    }

    public void setHeader(String title) {
        setHeader(title, null);
    }

    public void setHeader(String title, Component extra) {
        this.title.setText(title);

        if (extra != null) {
            this.extra.removeAll();
            this.extra.add(extra);
        }
    }

    public void addCloseListener(ComponentEventListener<ClickEvent<Button>> listener) {
        this.close.addClickListener(listener);
    }

}
