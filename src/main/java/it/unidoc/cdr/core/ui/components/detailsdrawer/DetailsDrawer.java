package it.unidoc.cdr.core.ui.components.detailsdrawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import it.unidoc.cdr.core.ui.components.FlexBoxLayout;

/**
 * @author b.amoruso
 */

@CssImport("./styles/components/details-drawer.css")
public class DetailsDrawer extends FlexBoxLayout {

    private static final long serialVersionUID = -4013439882146685863L;

    private String CLASS_NAME = "details-drawer";

    private FlexBoxLayout header;
    private FlexBoxLayout content;
    private FlexBoxLayout footer;

    public DetailsDrawer(Position position, Component... components) {
        setClassName(CLASS_NAME);
        setPosition(position);

        header = new FlexBoxLayout();
        header.setClassName(CLASS_NAME + "__header");

        content = new FlexBoxLayout(components);
        content.setClassName(CLASS_NAME + "__content");
        content.setFlexDirection(FlexDirection.COLUMN);

        footer = new FlexBoxLayout();
        footer.setClassName(CLASS_NAME + "__footer");

        add(header, content, footer);
    }

    public FlexBoxLayout getHeader() {
        return this.header;
    }

    public void setHeader(Component... components) {
        this.header.removeAll();
        this.header.add(components);
    }

    public void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    public void setFooter(Component... components) {
        this.footer.removeAll();
        this.footer.add(components);
    }

    public void setPosition(Position position) {
        getElement().setAttribute("position", position.name().toLowerCase());
    }

    public void hide() {
        getElement().setAttribute("open", false);
    }

    public void show() {
        getElement().setAttribute("open", true);
    }

    public enum Position {
        BOTTOM, RIGHT
    }

}
