package it.unidoc.cdr.core.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author b.amoruso
 */

@CssImport("./styles/components/navi-menu.css")
public class NaviMenu extends Div {

    private static final long serialVersionUID = -7131355845683751960L;

    public NaviMenu() {
        String CLASS_NAME = "navi-menu";

        setClassName(CLASS_NAME);
    }

    protected void addNaviItem(NaviItem item) {
        add(item);
    }

    protected void addNaviItem(NaviItem parent, NaviItem item) {
        parent.addSubItem(item);

        addNaviItem(item);
    }

    public void filter(String filter) {
        getNaviItems().forEach(naviItem -> {
            boolean matches = naviItem.getText().toLowerCase().contains(filter.toLowerCase());

            naviItem.setVisible(matches);
        });
    }

    public NaviItem addNaviItem(String text,
                                Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(VaadinIcon icon, String text,
                                Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(icon, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(Image image, String text,
                                Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(image, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(NaviItem parent, String text,
                                Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(text, navigationTarget);
        addNaviItem(parent, new NaviItem(text, navigationTarget));
        return item;
    }

    public List<NaviItem> getNaviItems() {
        return getChildren().map(c -> (NaviItem)c).collect(Collectors.toList());
    }

}
