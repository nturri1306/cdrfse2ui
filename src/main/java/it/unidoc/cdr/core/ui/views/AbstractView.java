package it.unidoc.cdr.core.ui.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.GridSortOrder;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.FlexLayout.FlexDirection;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.CallbackDataProvider;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializablePredicate;
import com.vaadin.flow.function.ValueProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.server.StreamResource;
import it.unidoc.cdr.core.ui.components.Badge;
import it.unidoc.cdr.core.ui.components.FlexBoxLayout;
import it.unidoc.cdr.core.ui.components.detailsdrawer.DetailsDrawer;
import it.unidoc.cdr.core.ui.components.detailsdrawer.DetailsDrawerFooter;
import it.unidoc.cdr.core.ui.components.detailsdrawer.DetailsDrawerHeader;
import it.unidoc.cdr.core.ui.exporter.CsvFileBuilder;
import it.unidoc.cdr.core.ui.exporter.ExporterWrapper;
import it.unidoc.cdr.core.ui.exporter.FileBuilder;
import it.unidoc.cdr.core.ui.exporter.XlsxFileBuilder;
import it.unidoc.cdr.core.ui.layout.size.Horizontal;
import it.unidoc.cdr.core.ui.layout.size.Top;
import it.unidoc.cdr.core.ui.security.SecurityUtils;
import it.unidoc.cdr.core.ui.util.UIUtils;
import it.unidoc.cdr.core.ui.util.css.BoxSizing;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * @param <T>
 * @author b.amoruso
 */
public abstract class AbstractView<T> extends SplitViewFrame implements AfterNavigationObserver {

    private static final long serialVersionUID = -1896814245217551823L;
    private SubMenu columnsMenu;
    public Grid<T> grid;

    private final Collection<TextField> filters = new ArrayList<>();
    private ListDataProvider<T> provider;
    private Badge lbInfo;
    private DetailsDrawer detailsDrawer;
    private DetailsDrawerHeader detailsDrawerHeader;

    private HorizontalLayout hlExport;
    private Anchor xlsxAnchor;
    private Anchor csvAnchor;
    private final AtomicBoolean isManualSearch = new AtomicBoolean(true);

    /*add for implements override methods in subclasses */
//    protected  Grid<T> getGrid()
//    {
//        return  grid;
//    }
    protected SubMenu getColumnsMenu() {
        return columnsMenu;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);

        initAppBar();
        setViewContent(createContent());
        setViewDetails(createDetailsDrawer());
        setViewDetailsPosition(Position.BOTTOM);
    }

    /**
     * Distinguishes manual calls from automatic calls when scrolling,
     * reset the scroll, and reset the grid to avoid errors
     */
    protected void initManualSearch() {
        grid.scrollToStart();
        grid.setItems(DataProvider.ofCollection(new ArrayList<>()));

        isManualSearch.set(true);
    }

    public void setSize(int size) {
        this.lbInfo.setText(getTranslation("app.items.title", new Object[] { Integer.valueOf(size) }));
    }

    protected boolean isManualSearch() {
        return isManualSearch.get();
    }

    protected void asAutoSearch() {
        isManualSearch.set(false);
    }

    protected GridSortOrder<T> getSortOrder() {
        if (grid.getSortOrder().size() > 0)
            return grid.getSortOrder().get(0);

        return null;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        refresh();

        updateState();
    }

    public <V> void filter(ValueProvider<T, V> valueProvider, V value) {
        if (provider == null)
            return;

        if (value == null)
            provider.clearFilters();
        else
            provider.setFilterByValue(valueProvider, value);

        updateState();
    }

    public void deselectAll() {
        grid.deselectAll();
    }

    public boolean addFilter(SerializablePredicate<T> predicate) {
        if (provider == null)
            return false;

        // The method addFilter() remove selection. Than fire event with deslectAll()...
        deselectAll();

        // Add filter remove selection...
        provider.addFilter(predicate);

        return true;
    }

    public abstract void refresh();

    public void refresh(Collection<T> items) {
        if (items == null)
            items = new ArrayList<>();

        if (provider == null) {
            provider = DataProvider.ofCollection(items);

            grid.setItems(provider);
        } else {
            provider.getItems().clear();
            provider.getItems().addAll(items);
            provider.refreshAll();
        }

        updateState();
    }

    public Set<T> getItems() {
//        Stream<T> stream = provider.fetch(new Query<>(provider.getFilter()));
//
//        return stream.collect(Collectors.toList());
        return grid.getSelectedItems();
    }

    protected void setItems(CallbackDataProvider.FetchCallback<T, Void> fetchCallback) {
        grid.setItems(fetchCallback);
    }

    public void clearHeaders(boolean disable) {
        filters.forEach(c -> {
            c.setValue("");
            c.setReadOnly(disable);
        });
    }

    protected Optional<UserDetails> getAuthenticatedUser() {
        return SecurityUtils.getAuthenticatedUser();
    }

    protected String checkIfEmpty(String value, String label) throws IOException {
        if (value == null || value.length() == 0)
            throw new IOException(label);

        return value;
    }

    protected String compose(String token, String left, String right) {
        String result = "";

        if (left != null && left.length() > 0)
            result = left;

        if (right != null && right.length() > 0)
            result += (result.length() > 0 ? token : "") + right;

        return result;
    }

    protected Component makeToolBar() {
        return null;
    }


    protected void addColumnAsToggleable(Column<?> column, String label) {
        addColumnAsToggleable(column, label, false);
    }

    protected void addColumnAsToggleable(Grid.Column<?> column, String label, boolean visible) {
        column.setVisible(visible);

        Checkbox checkbox = new Checkbox(label);
        checkbox.setValue(column.isVisible());
        checkbox.addValueChangeListener(e -> {
            column.setVisible(e.getValue());

            grid.recalculateColumnWidths();
        });

        columnsMenu.addItem(checkbox);
    }
    protected HeaderRow getHeaderRow() {
        grid.getHeaderRows().clear();

        return grid.getHeaderRows().size() > 1 ? grid.getHeaderRows().get(1) : grid.appendHeaderRow();
    }



    protected TextField createFilterHeader(Grid.Column<?> col, Consumer<String> consumer, HeaderRow header) {
        Label label = new Label(col.getKey());
        label.getStyle().set("font-weight", "bold");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(new TextFieldVariant[] { TextFieldVariant.LUMO_SMALL });
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(e -> consumer.accept((String)e.getValue()));
        VerticalLayout headerLayout = new VerticalLayout(new Component[] { (Component)label, (Component)textField });
        headerLayout.setSpacing(false);
        headerLayout.setMargin(false);
        headerLayout.setAlignItems(FlexComponent.Alignment.START);
        ((HeaderRow.HeaderCell)header.getCell(col)).setComponent((Component)headerLayout);
        this.filters.add(textField);
        return textField;
    }

    protected <R> Grid.Column getColumn(ValueProvider<T, R> valueProvider, String title, String key) {
        Grid.Column c = grid.addColumn(new ComponentRenderer<>(item -> {
                    String value = "";

                    if (Objects.nonNull(valueProvider.apply(item)))
                        value = valueProvider.apply(item).toString();

                    Span span = new Span(value);
                    span.getElement().setProperty("title", value);

                    if (key.equals("statusCode"))
                        if (!value.equals("200") && !value.equals("201")) {
                            span.getStyle().set("background-color", "#FF0000");
                            span.getStyle().set("color", "#FFFFFF");
                        } else {
                            span.getStyle().set("background-color", "#00FF00");
                            span.getStyle().set("color", "#FFFFFF");
                        }


                    return span;
                })).
                setKey(key).
                setResizable(true).
                setSortable(true).
                setAutoWidth(true);

        if (Objects.nonNull(title))
            c.setHeader(title);

        return c;
    }

    protected abstract String getDetailsHeader(T data);

    protected abstract void addColumns(Grid<T> grid);

    protected abstract FormLayout makeDetails(T data);

    protected abstract Component makeDetailsTool(T data, boolean isLocal);

    protected abstract void selected(Set<T> items);

    protected abstract ExporterWrapper<T> makeExporterWrapper();

    protected void initAppBar() {
        // Nothing to be done...
    }

    protected void closeDetails() {
        detailsDrawer.hide();
    }

    private Component createContent() {
        lbInfo = new Badge();
//		lbInfo.setMinWidth(120, Unit.PIXELS);

        HorizontalLayout hlLabel = new HorizontalLayout(lbInfo);
        hlLabel.setMargin(false);
        hlLabel.setSpacing(false);
        hlLabel.setWidthFull();

        hlExport = new HorizontalLayout();
        hlExport.setMargin(false);
        hlExport.setWidthFull();
        hlExport.setJustifyContentMode(JustifyContentMode.END);

        HorizontalLayout header = new HorizontalLayout(hlLabel, hlExport, createMenuToggle());
        header.setMargin(false);
        header.setAlignItems(Alignment.BASELINE);
        header.setWidthFull();

        Component toolBar = makeToolBar();

        FlexBoxLayout content = new FlexBoxLayout(header, createGrid());
        if (toolBar != null)
            content.add(toolBar);

        content.setBoxSizing(BoxSizing.BORDER_BOX);
        content.setFlexDirection(FlexDirection.COLUMN);
        content.setHeightFull();
        content.setPadding(Horizontal.RESPONSIVE_X, Top.RESPONSIVE_X);

        xlsxAnchor = makeExportAnchor(
                getTranslation("app.export.xls"),
                VaadinIcon.TABLE.create(),
                new XlsxFileBuilder<>(grid, makeExporterWrapper()));

        //xlsxAnchor.getElement().addEventListener("click", e -> regenerateAnchors());

        csvAnchor = makeExportAnchor(
                getTranslation("app.export.csv"),
                VaadinIcon.TWIN_COL_SELECT.create(),
                new CsvFileBuilder<>(grid, makeExporterWrapper()));

        //csvAnchor.getElement().addEventListener("click", e -> regenerateAnchors());

        hlExport.add(xlsxAnchor, csvAnchor);

        hlExport.setJustifyContentMode(JustifyContentMode.END);

        return content;
    }

    private Grid<T> createGrid() {
        grid = new Grid<>();
        grid.addAttachListener(e ->
                grid.getElement().executeJs(
                        "this.querySelector('vaadin-grid-flow-selection-column').frozen = true"));
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(event -> selected(event.getAllSelectedItems()));
        grid.addItemClickListener(e -> showDetails(e.getItem()));
        grid.addItemDoubleClickListener(e -> {
            // Check if already selected...
            Optional<T> item = grid.getSelectedItems().stream().filter(
                    i -> i.equals(e.getItem())).findAny();

            if (item.isPresent())
                grid.deselect(e.getItem());
            else
                grid.select(e.getItem());

            showDetails(e.getItem());
        });

        grid.setSizeFull();

        addColumns(grid);

        return grid;
    }

    private Component createMenuToggle() {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);

        MenuItem menuItem = menuBar.addItem(VaadinIcon.ELLIPSIS_DOTS_V.create());
        UIUtils.setTooltip(getTranslation("app.columns.outline"), menuItem);

        menuItem.addClickListener(e -> {
            // Add no column outline Label if no menu is present...
            if (e.getSource().getSubMenu().getItems().size() == 0)
                UIUtils.showMessage(getTranslation("app.columns.nooutline"));
        });

        columnsMenu = menuItem.getSubMenu();

        return menuBar;
    }

    protected void updateState() {
        updateState((Integer)null);
    }

    protected void updateState(Integer size) {
        lbInfo.setText(getTranslation("app.items.title", size != null ?
                size : (provider != null ? provider.size(new Query<>(provider.getFilter())) : 0)));
    }

    /* alternative method for generate different export name file*/
    private Anchor makeExportAnchor(String toolTip, Icon icon, FileBuilder<T> builder) {
        Button downloadButton = new Button(icon);
        Anchor download = new Anchor();
        download.add(downloadButton);
        //download.getElement().setAttribute("download", "true");
        UIUtils.setTooltip(toolTip, download);

        downloadButton.addClickListener(e -> {
            var uid=UUID.randomUUID();
            var resource =new StreamResource(
                    uid + builder.getFileExtension(),
                    builder::build);

            download.setHref(resource);
            download.setId(uid.toString());

            com.vaadin.flow.component.page.Page page = com.vaadin.flow.component.UI.getCurrent().getPage();
            page.executeJs("document.getElementById('"+uid+"').click();");

        });

        return  download;
    }

  /*  private Anchor makeExportAnchor(String toolTip, Icon icon, FileBuilder<T> builder) {

        Anchor a = new Anchor(new StreamResource(
                UUID.randomUUID() + builder.getFileExtension(),
                builder::build), "");

        a.getElement().setAttribute("download", true);
        a.removeAll();
        a.setTarget(AnchorTarget.BLANK);
        a.add(new Button(icon));

        UIUtils.setTooltip(toolTip, a);

        return a;
    }
*/

    private DetailsDrawer createDetailsDrawer() {
        detailsDrawer = new DetailsDrawer(DetailsDrawer.Position.BOTTOM);

        // Header
        detailsDrawerHeader = new DetailsDrawerHeader("");
        detailsDrawerHeader.addCloseListener(e -> closeDetails());
        detailsDrawer.setHeader(detailsDrawerHeader);

        // Footer
        DetailsDrawerFooter footer = new DetailsDrawerFooter();
        footer.addButton(
                UIUtils.createPrimaryButton(getTranslation("app.close.title")),
                e -> closeDetails());

        detailsDrawer.setFooter(footer);

        return detailsDrawer;
    }

    private void showDetails(T data) {
        detailsDrawerHeader.setHeader(getDetailsHeader(data), makeDetailsTool(data, true));
        detailsDrawer.setContent(makeDetails(data));
        detailsDrawer.show();
    }

}
