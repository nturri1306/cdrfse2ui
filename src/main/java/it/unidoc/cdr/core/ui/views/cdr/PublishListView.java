package it.unidoc.cdr.core.ui.views.cdr;

import com.vaadin.flow.router.Route;
import it.unidoc.cdr.core.ui.views.MainLayout;

@Route(value = "publishview", layout = MainLayout.class)
public class PublishListView extends ValidationListView {
    public static final String NAME = "publishview";

    public PublishListView() {
        setCollectionName("cdr_fse2_publish");
    }
}
