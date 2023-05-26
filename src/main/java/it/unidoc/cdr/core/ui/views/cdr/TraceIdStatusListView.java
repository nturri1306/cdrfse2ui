package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.views.MainLayout;

@Route(value = "traceidstatusview", layout = MainLayout.class)
public class TraceIdStatusListView extends BaseListView {
    public static final String NAME = "traceidstatusview";

    public TraceIdStatusListView() {
        setCollectionName("cdr_fse2_traceidstatus");
    }
}
