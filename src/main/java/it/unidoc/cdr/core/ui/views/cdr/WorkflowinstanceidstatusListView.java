package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.views.MainLayout;

@Route(value = "workflowinstanceidstatusview", layout = MainLayout.class)
public class WorkflowinstanceidstatusListView extends BaseListView {
    public static final String NAME = "workflowinstanceidstatusview";

    public WorkflowinstanceidstatusListView() {
        setCollectionName("cdr_fse2_workflowinstanceidstatus");
    }
}
