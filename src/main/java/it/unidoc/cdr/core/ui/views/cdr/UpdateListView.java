package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.views.MainLayout;

@Route(value = "updateview", layout = MainLayout.class)
public class UpdateListView extends ValidationListView {
    public static final String NAME = "updateview";

    public UpdateListView() {
        setCollectionName("cdr_fse2_update");
    }
}
