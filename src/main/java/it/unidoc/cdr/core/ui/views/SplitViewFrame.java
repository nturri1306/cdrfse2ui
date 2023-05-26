package it.unidoc.cdr.core.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import it.unidoc.cdr.core.ui.components.FlexBoxLayout;

/**
 * A view frame that establishes app design guidelines. It consists of four
 * parts:
 * <ul>
 * <li>Topmost {@link #setViewHeader(Component...) header}</li>
 * <li>Center {@link #setViewContent(Component...) content}</li>
 * <li>Center {@link #setViewDetails(Component...) details}</li>
 * <li>Bottom {@link #setViewFooter(Component...) footer}</li>
 * </ul>
 *
 * @author b.amoruso
 */
@CssImport("./styles/components/view-frame.css")
public class SplitViewFrame extends Composite<Div> implements HasStyle {

    private static final long serialVersionUID = -8927287943789257592L;

    private String CLASS_NAME = "view-frame";

    private Div header;

    private FlexBoxLayout wrapper;
    private Div content;
    private Div details;

    private Div footer;

    public SplitViewFrame() {
        setClassName(CLASS_NAME);

        header = new Div();
        header.setClassName(CLASS_NAME + "__header");

        wrapper = new FlexBoxLayout();
        wrapper.setClassName(CLASS_NAME + "__wrapper");

        content = new Div();
        content.setClassName(CLASS_NAME + "__content");

        details = new Div();
        details.setClassName(CLASS_NAME + "__details");

        footer = new Div();
        footer.setClassName(CLASS_NAME + "__footer");

        wrapper.add(content, details);
        getContent().add(header, wrapper, footer);
    }

    /**
     * Sets the header slot's components.
     */
    public void setViewHeader(Component... components) {
        header.removeAll();
        header.add(components);
    }

    /**
     * Sets the content slot's components.
     */
    public void setViewContent(Component... components) {
        content.removeAll();
        content.add(components);
    }

    /**
     * Sets the detail slot's components.
     */
    public void setViewDetails(Component... components) {
        details.removeAll();
        details.add(components);
    }

    public void setViewDetailsPosition(Position position) {
        if (position.equals(Position.RIGHT)) {
            wrapper.setFlexDirection(FlexDirection.ROW);

        } else if (position.equals(Position.BOTTOM)) {
            wrapper.setFlexDirection(FlexDirection.COLUMN);
        }
    }

    /**
     * Sets the footer slot's components.
     */
    public void setViewFooter(Component... components) {
        footer.removeAll();
        footer.add(components);
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        MainLayout.get().getAppBar().reset();
    }

    public enum Position {
        RIGHT, BOTTOM
    }

}
