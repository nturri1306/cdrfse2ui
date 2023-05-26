package it.unidoc.cdr.core.ui.views.user;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import static it.unidoc.cdr.core.ui.util.UIUtils.IMG_PATH;

/**
 * @author b.amoruso
 */
@Route(LoginView.NAME)
@PageTitle("CDR")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    public static final String NAME = "login";

    private static final long serialVersionUID = -6635663571822117230L;

    private final LoginForm form;

//	@Autowired
//	private MailManager mail;

    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        form = new LoginForm();
        form.setI18n(i18n());
        form.setAction("login");
        form.setForgotPasswordButtonVisible(false);

        H1 title = new H1();
        title.getStyle().set("color", "var(--lumo-base-color)");
        title.add(new Image(IMG_PATH + "logo_wide.png", getTranslation("app.title")));
        add(title, form);
//		add(title, form, new Button("TEST", e -> {
//			mail.addAttachment("greenpass.pdf", new FileSystemResource(new File("C:\\Users\\Utente\\Desktop\\248aa7d0-486c-4bd6-804c-2c7ceb6e3678.pdf")));
//			mail.addAttachment("name.txt", new FileSystemResource(new File("C:\\Users\\Utente\\Desktop\\Versamento vaglia.txt")));
//
//			String template = "mail.vm";
//			Map<String, Object> vars = new HashMap<>();
//			vars.put("name", "TST");
////			vars.put("subscriptionDate", new Date());
////			vars.put("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
//			vars.put("imageResourceName", new FileSystemResource(new File("C:\\Users\\Utente\\Desktop\\test.png")));
//
//			try {
//				mail.sendAsHtml(template, vars, "Green pass", "bamoruso@hotmail.it", "bamoruso@gmail.com");
//			} catch (MessagingException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}));
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // inform the user about an authentication error
        if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error"))
            form.setError(true);
    }

    private LoginI18n i18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("");
        i18nForm.setUsername(getTranslation("app.login.username"));
        i18nForm.setPassword(getTranslation("app.login.password"));
        i18nForm.setSubmit(getTranslation("app.login.submit"));
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle(getTranslation("app.login.error.login"));
        i18nErrorMessage.setMessage(getTranslation("app.login.help.login"));
        i18n.setErrorMessage(i18nErrorMessage);

        return i18n;
    }

}
