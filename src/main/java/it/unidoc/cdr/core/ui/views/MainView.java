package it.unidoc.cdr.core.ui.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import it.unidoc.cdr.core.ui.backend.service.UserService;
import it.unidoc.cdr.core.ui.backend.type.UserType;
import it.unidoc.cdr.core.ui.conf.CdrUiConfiguration;
import it.unidoc.cdr.core.ui.security.SecurityUtils;

import it.unidoc.cdr.core.ui.views.cdr.ValidationListView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * @author b.amoruso
 */

@Route(value = MainView.NAME, layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class MainView extends Div implements BeforeEnterObserver {

    public static final String NAME = "mainview";
    private static final long serialVersionUID = -4694421758972269067L;
    @Autowired
    private CdrUiConfiguration conf;
    @Autowired
    @Qualifier("userService")
    private UserService service;

    public MainView() {
        add(new H1(getTranslation("app.notimplementedyet.title")));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<UserDetails> user = SecurityUtils.getAuthenticatedUser();
        if ( user.isPresent()) {
            event.rerouteTo(ValidationListView.class);
        }
    }

}
