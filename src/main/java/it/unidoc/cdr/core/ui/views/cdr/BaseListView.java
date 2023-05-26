package it.unidoc.cdr.core.ui.views.cdr;


import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.internal.Pair;
import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseFse2;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.RestPageRequest;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.result.ResultData;
import it.unidoc.cdr.core.ui.exporter.ExporterWrapper;
import it.unidoc.cdr.core.ui.util.UIUtils;
import it.unidoc.cdr.core.ui.views.MainLayout;
import java.util.Set;

@Route(value = "baselistview", layout = MainLayout.class)
public class BaseListView extends CdrServiceView<BaseFse2> {
    public static final String NAME = "baselistview";

    String collectionName = "cdr_fse2_traceidstatus";

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    private String getCollectionName() {
        return this.collectionName;
    }

    protected void addColumns(Grid<BaseFse2> grid) {
        addColumns(grid, BaseFse2.class, this.collectionName);
    }

    public static boolean isNotNullOrEmpty(Object str) {
        return (str != null && !str.toString().isEmpty());
    }

    public void refresh() {
        closeDetails();
        Pair<String, String> pair = getDateFilter();
        try {
            this.grid.setItems(this.dbRepository.getBaseItems(this.collectionName, pair, 0));
            setSize((int)this.grid.getGenericDataView().getItems().count());
        } catch (Exception exception) {}
    }

    protected ResultData query(boolean asupdate, String begin, String end, RestPageRequest restPageRequest) throws Exception {
        return null;
    }

    protected void remove(Set<String> items) throws Exception {}

    protected String getDetailsHeader(BaseFse2 data) {
        return null;
    }

    protected ComponentEventListener<ClickEvent<Button>> createAction(Div header, Div content) {
        return null;
    }

    protected FormLayout makeDetails(BaseFse2 data) {
        FormLayout form = new FormLayout();
        TextArea textArea2 = new TextArea();
        textArea2.setLabel(getTranslation("app.cdr.view.error.response", new Object[0]));
        textArea2.setValue(UIUtils.toPrettyPrint(data.getResponse()));
        textArea2.setHeight("230px");
        textArea2.setWidth("100%");
        textArea2.getElement().getStyle().set("color", "#C71585");
        textArea2.setReadOnly(true);
        form.add(new Component[] { (Component)textArea2 });
        return form;
    }

    protected void selected(Set<BaseFse2> items) {}

    protected ExporterWrapper<BaseFse2> makeExporterWrapper() {
        return (c, i) -> c.getKey().equals("id") ? UIUtils.nullToEmpty(i.getId()) : null;
    }



}
