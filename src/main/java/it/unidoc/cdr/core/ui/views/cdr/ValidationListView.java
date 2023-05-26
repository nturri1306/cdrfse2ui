package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.internal.Pair;
import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.BaseData;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.CdrFse2Validation;
import it.unidoc.cdr.core.ui.backend.rest.cdr.common.RestPageRequest;
import it.unidoc.cdr.core.ui.backend.rest.cdr.data.result.ResultData;
import it.unidoc.cdr.core.ui.exporter.ExporterWrapper;
import it.unidoc.cdr.core.ui.util.UIUtils;
import it.unidoc.cdr.core.ui.views.MainLayout;
import java.util.Set;

@Route(value = "validationview", layout = MainLayout.class)
public class ValidationListView extends CdrServiceView<CdrFse2Validation> {
    public static final String NAME = "validationview";

    String collectionName = "cdr_fse2_validation";

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    private String getCollectionName() {
        return this.collectionName;
    }

    protected void addColumns(Grid<CdrFse2Validation> grid) {
        addColumns(grid, CdrFse2Validation.class, this.collectionName);
    }

    public static boolean isNotNullOrEmpty(Object str) {
        return (str != null && !str.toString().isEmpty());
    }

    public void refresh() {
        closeDetails();
        Pair<String, String> pair = getDateFilter();
        try {
            if (isNotNullOrEmpty(this.filterHolder.getFilter("statusCode"))) {
                int code = Integer.valueOf(this.filterHolder.getFilter("statusCode").toString()).intValue();
                this.grid.setItems(this.dbRepository.getValidationItems(this.collectionName, pair, code));
            } else if (isByError()) {
                this.grid.setItems(this.dbRepository.getValidationItems(this.collectionName, pair, -1));
            } else {
                this.grid.setItems(this.dbRepository.getValidationItems(this.collectionName, pair, 0));
            }
            setSize((int)this.grid.getGenericDataView().getItems().count());
        } catch (Exception exception) {}
    }

    protected ResultData query(boolean asupdate, String begin, String end, RestPageRequest restPageRequest) throws Exception {
        return null;
    }

    protected void remove(Set<String> items) throws Exception {}

    protected String getDetailsHeader(CdrFse2Validation data) {
        return null;
    }

    protected ComponentEventListener<ClickEvent<Button>> createAction(Div header, Div content) {
        return null;
    }

    protected FormLayout makeDetails(CdrFse2Validation data) {
        FormLayout form = new FormLayout();
        form.addClassNames(new String[] { "padding-b-l", "padding-h-l", "padding-t-s" });
        form.setResponsiveSteps(new FormLayout.ResponsiveStep[] { new FormLayout.ResponsiveStep("0", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP), new FormLayout.ResponsiveStep("50%", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP) });
        form.add(new Component[] { (Component)getTextArea(data.getBody(), getTranslation("app.cdr.view.error.payload", new Object[0])) });
        form.add(new Component[] { (Component)getTextArea(data.getResponse(), getTranslation("app.cdr.view.error.response", new Object[0])) });
        form.add(new Component[] { (Component)getTextArea(data.getBearerToken(), "bearToken") });
        form.add(new Component[] { (Component)getTextArea(data.getClaimsToken(), "claimsToken") });
        return form;
    }

    protected void selected(Set<CdrFse2Validation> items) {}

    protected ExporterWrapper<CdrFse2Validation> makeExporterWrapper() {
        return (c, i) -> c.getKey().equals("id") ? UIUtils.nullToEmpty(i.getId()) : null;
    }
}
