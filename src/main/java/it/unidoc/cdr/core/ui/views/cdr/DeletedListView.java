package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.views.MainLayout;

@Route(value = "deletedview", layout = MainLayout.class)
public class DeletedListView extends BaseListView {
    public static final String NAME = "deletedview";

    public DeletedListView() {
        setCollectionName("cdr_fse2_delete");
    }
}
