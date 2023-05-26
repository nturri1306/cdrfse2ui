package it.unidoc.cdr.core.ui.components;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import it.unidoc.cdr.core.ui.util.UIUtils;

/**
 * https://vaadin.com/docs/v14/flow/styling/lumo/badges
 *
 * @author b.amoruso
 */
public class Badge extends Composite<Span> {

    private static final long serialVersionUID = 4465263784337131553L;
    private Span span = new Span();
    private String text = "";
    public Badge() {
        UIUtils.setTheme("badge", span);

        setPrimary(true);
    }

    public Badge(String text) {
        this();
        setText(text);
    }

    /**
     * Set the text content of the badge, remove all other content.
     *
     * @param text String value.
     */
    public void setText(String text) {
        this.text = text;
        span.setText(text);
    }

    /**
     * Set the badge variant type. {@link BadgeVariant}
     *
     * @param variant Badge variant.
     */
    public void setVariant(BadgeVariant variant) {
        for (BadgeVariant v : BadgeVariant.values()) {
            if (v.getVariant() != null)
                getElement().getThemeList().remove(v.getVariant());
        }

        if (variant != BadgeVariant.NORMAL)
            getElement().getThemeList().add(variant.getVariant());
    }

    /**
     * Set Badge to be primary, more prominent color.
     *
     * @param primary True for primary.
     */
    public void setPrimary(boolean primary) {
        setTheme(primary, "primary");
    }

    /**
     * Set Badge to be pill shaped.
     *
     * @param pill True for pill.
     */
    public void setPill(boolean pill) {
        setTheme(pill, "pill");
    }

    /**
     * Set Badge to be small. Can be combined with primary and pill.
     *
     * @param small True for small.
     */
    public void setSmall(boolean small) {
        setTheme(small, "small");
    }

    /**
     * Set icon to be wrapped before the text.
     *
     * @param icon Icon component.
     */
    public void setIcon(Icon icon) {
        setIcon(icon, true);
    }

    /**
     * Set icon to be wrapped before or after the text.
     *
     * @param icon  Icon component.
     * @param first True for icon to appear before text.
     */
    public void setIcon(Icon icon, boolean first) {
        getElement().removeAllChildren();

        if (first) {
            span.add(icon);
            span.add(new Span(text));
        } else {
            span.add(new Span(text));
            span.add(icon);
        }
    }

    @Override
    protected Span initContent() {
        return span;
    }

    private void setTheme(boolean add, String theme) {
        if (add)
            getElement().getThemeList().add(theme);
        else
            getElement().getThemeList().remove(theme);
    }

    /**
     * Badge variants
     */
    public enum BadgeVariant {
        /**
         * Default variant
         */
        NORMAL(null),
        /**
         * Success type, usually green
         */
        SUCCESS("success"),
        /**
         * Error type, usually red
         */
        ERROR("error"),
        /**
         * Contrast type, usually black
         */
        CONTRAST("contrast");

        private String variant;

        BadgeVariant(String variant) {
            this.variant = variant;
        }

        public String getVariant() {
            return variant;
        }

    }

}
