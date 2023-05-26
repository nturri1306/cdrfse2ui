package it.unidoc.cdr.core.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasElement;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.ErrorHandler;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.lumo.Lumo;
import it.unidoc.cdr.core.ui.backend.rest.fsebroker.common.BaseData.ResponseState;
import it.unidoc.cdr.core.ui.backend.service.UserService;
import it.unidoc.cdr.core.ui.backend.type.UserType;
import it.unidoc.cdr.core.ui.components.FlexBoxLayout;
import it.unidoc.cdr.core.ui.components.navigation.bar.AppBar;
import it.unidoc.cdr.core.ui.components.navigation.bar.TabBar;
import it.unidoc.cdr.core.ui.components.navigation.drawer.NaviDrawer;
import it.unidoc.cdr.core.ui.components.navigation.drawer.NaviItem;
import it.unidoc.cdr.core.ui.components.navigation.drawer.NaviMenu;
import it.unidoc.cdr.core.ui.conf.CdrUiConfiguration;
import it.unidoc.cdr.core.ui.security.SecurityUtils;
import it.unidoc.cdr.core.ui.util.UIUtils;
import it.unidoc.cdr.core.ui.util.css.Overflow;
import it.unidoc.cdr.core.ui.views.cdr.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author b.amoruso
 */
@CssImport(value = "./styles/components/charts.css", themeFor = "vaadin-chart", include = "vaadin-chart-default-theme")
@CssImport(value = "./styles/components/floating-action-button.css", themeFor = "vaadin-button")
@CssImport(value = "./styles/components/grid.css", themeFor = "vaadin-grid")
@CssImport("./styles/lumo/border-radius.css")
@CssImport("./styles/lumo/icon-size.css")
@CssImport("./styles/lumo/margin.css")
@CssImport("./styles/lumo/padding.css")
@CssImport("./styles/lumo/shadow.css")
@CssImport("./styles/lumo/spacing.css")
@CssImport("./styles/lumo/typography.css")
@CssImport("./styles/misc/box-shadow-borders.css")
@CssImport(value = "./styles/styles.css", include = "lumo-badge")
@JsModule("@vaadin/vaadin-lumo-styles/badge")
@PageTitle("Main")
public class MainLayout extends FlexBoxLayout implements RouterLayout, AfterNavigationObserver {

    private static final long serialVersionUID = 3383590321137880407L;

    private static final Logger log = LoggerFactory.getLogger(MainLayout.class);
    private static final String CLASS_NAME = "root";

    private FlexBoxLayout row;
    private NaviDrawer naviDrawer;
    private FlexBoxLayout column;

    private Div appHeaderInner;
    private FlexBoxLayout viewContainer;

    private boolean navigationTabs = false;
    private TabBar tabBar;
    private AppBar appBar;

    @Autowired
    private CdrUiConfiguration conf;
    @Autowired
    @Qualifier("userService")
    private UserService service;

    public MainLayout() {
        VaadinSession.getCurrent().setErrorHandler(
                (ErrorHandler) errorEvent -> {
                    log.error("Uncaught UI exception", errorEvent.getThrowable());

                    UIUtils.showMessage(getTranslation(Messages.APP_ERROR_INTERNAL));
                });

        addClassName(CLASS_NAME);
        setFlexDirection(FlexDirection.COLUMN);
        setSizeFull();

        // Initialise the UI building blocks
        initStructure();

        // Configure the headers and footers (optional)
        initHeadersAndFooters();
    }

    public static MainLayout get() {
        return (MainLayout) UI.getCurrent().getChildren().filter(
                        component -> component.getClass() == MainLayout.class).
                findFirst().get();
    }

    @PostConstruct
    private void init() {
        appBar.setUserService(service);

        // Populate the navigation drawer
        initNaviItems();
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        initLabels();

        if (navigationTabs)
            afterNavigationWithTabs(event);
        else
            afterNavigationWithoutTabs(event);

//		if (conf.isFseBrokerEnabled())
//			getUI().get().navigate(SubmitView.NAME);
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        this.viewContainer.getElement().appendChild(content.getElement());
    }

    public NaviDrawer getNaviDrawer() {
        return naviDrawer;
    }

    public AppBar getAppBar() {
        return appBar;
    }

    private void initLabels() {
        ResponseState.SUCCESS.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_SUCCESS));
        ResponseState.SUCCESS_WITHALERTS.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_SUCCESSWITHALERTS));
        ResponseState.OUTCOME_ERROR.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_OUTCOMEERROR));
        ResponseState.INVALID_BINARYCONTENT.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_INVALIDBINARYCONTENT));
        ResponseState.INVALID_HASH.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_INVALIDHASH));
        ResponseState.INVALID_REQUEST.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_INVALIDREQUEST));
        ResponseState.UNKNOWN_APPLICATION.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_UNKNOWNAPPLICATION));
        ResponseState.XMLSCHEMA_ERROR.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_XMLSCHEMAERROR));
        ResponseState.COMMUNICATION_ERROR.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_COMMUNICATIONERROR));
        ResponseState.AUTHENTICATION_ERROR.setLabel(
                getTranslation(Messages.APP_FSEBROKER_STATE_AUTHENTICATIONERROR));
    }

    /**
     * Initialise the required components and containers.
     */
    private void initStructure() {
        naviDrawer = new NaviDrawer();

        viewContainer = new FlexBoxLayout();
        viewContainer.addClassName(CLASS_NAME + "__view-container");
        viewContainer.setOverflow(Overflow.HIDDEN);

        column = new FlexBoxLayout(viewContainer);
        column.addClassName(CLASS_NAME + "__column");
        column.setFlexDirection(FlexDirection.COLUMN);
        column.setFlexGrow(1, viewContainer);
        column.setOverflow(Overflow.HIDDEN);

        row = new FlexBoxLayout(naviDrawer, column);
        row.addClassName(CLASS_NAME + "__row");
        row.setFlexGrow(1, column);
        row.setOverflow(Overflow.HIDDEN);
        add(row);
        setFlexGrow(1, row);
    }

    /**
     * Initialize the navigation items.
     */
    private void initNaviItems() {
        NaviMenu menu = this.naviDrawer.getMenu();
        Optional<UserDetails> user = SecurityUtils.getAuthenticatedUser();
        if (user.isPresent()) {
            Optional<UserType> uType = this.service.findBy(((UserDetails)user.get()).getUsername());
            if (uType.isPresent()) {
                menu.addNaviItem("Validation", ValidationListView.class);
                menu.addNaviItem("Publication", PublishListView.class);
                menu.addNaviItem("Updated", UpdateListView.class);
                menu.addNaviItem("Metadata", MetaDataListView.class);
                menu.addNaviItem("Deleted", DeletedListView.class);
                menu.addNaviItem("WorkFlowInstanceId", WorkflowinstanceidstatusListView.class);
                menu.addNaviItem("TraceId", TraceIdStatusListView.class);
            }
        }
    }

    /**
     * Configure the app's inner and outer headers and footers.
     */
    private void initHeadersAndFooters() {
        // setAppHeaderOuter();
        // setAppFooterInner();
        // setAppFooterOuter();

        // Default inner header setup:
        // - When using tabbed navigation the view title, user avatar and main menu button will appear in the TabBar.
        // - When tabbed navigation is turned off they appear in the AppBar.

        appBar = new AppBar("");

        // Tabbed navigation
        if (navigationTabs) {
            tabBar = new TabBar();
            UIUtils.setTheme(Lumo.DARK, tabBar);

            // Shift-click to add a new tab
            for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
                item.addClickListener(e -> {
                    if (e.getButton() == 0 && e.isShiftKey()) {
                        tabBar.setSelectedTab(tabBar.addClosableTab(item.getText(), item.getNavigationTarget()));
                    }
                });
            }
            appBar.getAvatar().setVisible(false);
            setAppHeaderInner(tabBar, appBar);

            // Default navigation
        } else {
            UIUtils.setTheme(Lumo.DARK, appBar);
            setAppHeaderInner(appBar);
        }
    }

    private void setAppHeaderInner(Component... components) {
        if (appHeaderInner == null) {
            appHeaderInner = new Div();
            appHeaderInner.addClassName("app-header-inner");
            column.getElement().insertChild(0, appHeaderInner.getElement());
        }

        appHeaderInner.removeAll();
        appHeaderInner.add(components);
    }

    private void afterNavigationWithTabs(AfterNavigationEvent e) {
        NaviItem active = getActiveItem(e);
        if (active == null) {
            if (tabBar.getTabCount() == 0) {
             //   tabBar.addClosableTab("", CdrXdsSearch.class);
            }
        } else {
            if (tabBar.getTabCount() > 0) {
                tabBar.updateSelectedTab(active.getText(),
                        active.getNavigationTarget());
            } else {
                tabBar.addClosableTab(active.getText(),
                        active.getNavigationTarget());
            }
        }
        appBar.getMenuIcon().setVisible(false);
    }

    private NaviItem getActiveItem(AfterNavigationEvent e) {
        for (NaviItem item : naviDrawer.getMenu().getNaviItems()) {
            if (item.isHighlighted(e)) {
                return item;
            }
        }

        return null;
    }

    private void afterNavigationWithoutTabs(AfterNavigationEvent e) {
        NaviItem active = getActiveItem(e);
        if (active != null) {
            getAppBar().setTitle(active.getText());
        }
    }

}
