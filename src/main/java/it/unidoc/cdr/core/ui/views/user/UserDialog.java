package it.unidoc.cdr.core.ui.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import it.unidoc.cdr.core.ui.backend.service.UserService;
import it.unidoc.cdr.core.ui.backend.type.UserType;
import it.unidoc.cdr.core.ui.backend.type.UserType.Role;
import it.unidoc.cdr.core.ui.util.UIUtils;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

/**
 * @author b.amoruso
 */
public class UserDialog  implements Serializable {

    private static final long serialVersionUID = 1234889123054560402L;

    private TextField tfUsername;
    private TextField tfName;
    private TextField tfEmail;
    private TextField tfSurname;
    private Checkbox ckMenuRegistry;
    private Checkbox ckMenuRepository;
    private Checkbox ckNotification;
    private Checkbox ckWeekly;
    private MultiSelectListBox<Role> roles;
    private Checkbox ckEnabled;
    private Checkbox ckExpired;

    private ChangePasswordDialog pwDialog;
    private String current;
    private UserService service;

    public UserDialog(UserService service) {
        this.service = service;
    }

    public Optional<UserType> findBy(String username) {
        return service.findBy(username);
    }

    public void show(UserType user, String title) {
        pwDialog = new ChangePasswordDialog();
        current = user.getUsername();

        MessageDialog dlg = new MessageDialog().setTitle(title);
        dlg.getContent().add(initPane(dlg, user));
        dlg.addButtonToLeft().
            text(dlg.getTranslation("app.save.title")).
            icon(VaadinIcon.CHECK_CIRCLE.create()).
            onClick(e -> {
                try {
                    check(tfUsername.getValue(), dlg.getTranslation("app.login.username"));
                    check(tfName.getValue(), dlg.getTranslation("app.login.user.name"));
                    check(tfEmail.getValue(), dlg.getTranslation("app.login.user.email"));

                    if (roles.getSelectedItems().size() == 0)
                        throw new Exception(dlg.getTranslation("app.login.user.roles"));

                    if (isAdmin(tfUsername.getValue()) && (
                            !roles.getSelectedItems().contains(Role.ADMIN) ||
                                    !roles.getSelectedItems().contains(Role.SUPERUSER)))
                        throw new IOException(dlg.getTranslation("app.login.error.adminrole"));

                    // Check if it is an add new request...
                    if (user.getUsername() == null &&
                            (!isSameUser(tfUsername.getValue()) &&
                                    service.findBy(tfUsername.getValue()).isPresent()))
                        throw new IOException(dlg.getTranslation(
                                "app.login.error.alreadypresent", tfUsername.getValue()));

                    if (UIUtils.isEmptyOrNull(user.getHashedPassword()) || !pwDialog.hasPassword())
                        throw new IOException(dlg.getTranslation("app.login.error.passwordnotset"));

                    // Update user data....
                    user.setUsername(tfUsername.getValue());
                    user.setName(tfName.getValue());
                    user.setSurname(tfSurname.getValue());
                    user.setEmail(tfEmail.getValue());
                    user.setNotification(ckNotification.getValue());
                    user.setMenuRegistry(ckMenuRegistry.getValue());
                    user.setMenuRepository(ckMenuRepository.getValue());
                    user.setWeekly(ckWeekly.getValue());
                    user.setEnabled(ckEnabled.getValue());
                    user.setExpired(ckExpired.getValue());
                    user.setRoles(roles.getSelectedItems());

                    service.update(user);

                    dlg.close();
                } catch (IOException ex) {
                    UIUtils.showMessage(dlg.getTranslation("app.login.error", ex.getMessage()));
                } catch (Exception ex) {
                    UIUtils.showMessage(
                            dlg.getTranslation("app.login.requiredfield", ex.getMessage()));
                }
            });
        dlg.addButton().
            text(dlg.getTranslation("app.close.title")).
            icon(VaadinIcon.CLOSE_CIRCLE.create()).
            closeOnClick();
        dlg.setWidth("400px");
        dlg.open();
    }

    private boolean isSameUser(String username) {
        return current.toLowerCase().equals(username.toLowerCase());
    }

    private FormLayout initPane(MessageDialog dlg, UserType user) {
        String current = user.getUsername();
        pwDialog.setCheckOldPassword(true);

        tfUsername = new TextField();
        tfUsername.setValue(current);
        tfUsername.setEnabled(false);
        tfUsername.setRequired(true);
        tfUsername.setWidthFull();
        Button btPassword = new Button(
                dlg.getTranslation("app.login.user.setpassword"), e -> pwDialog.show(user));

        tfName = new TextField();
        tfName.setValue(UIUtils.nullToEmpty(user.getName()));
        tfName.setRequired(true);
        tfName.setWidthFull();
        tfSurname = new TextField();
        tfSurname.setValue(UIUtils.nullToEmpty(user.getSurname()));
        tfSurname.setWidthFull();
        tfEmail = new TextField();
        tfEmail.setValue(UIUtils.nullToEmpty(user.getEmail()));
        tfEmail.setRequired(true);
        tfEmail.setWidthFull();
        ckMenuRegistry = new Checkbox(dlg.getTranslation("app.cdr.registry.title"));
        ckMenuRegistry.setValue(user.isMenuRegistry());
        ckMenuRepository = new Checkbox(dlg.getTranslation("app.cdr.repository.title"));
        ckMenuRepository.setValue(user.isMenuRepository());
        ckNotification = new Checkbox(dlg.getTranslation("app.login.user.notification"));
        ckNotification.setValue(user.isNotification());
        ckWeekly = new Checkbox(dlg.getTranslation("app.login.user.weekly"));
        ckWeekly.setValue(user.isWeekly());
        ckEnabled = new Checkbox(dlg.getTranslation("app.login.user.enabled"));
        ckEnabled.setValue(user.isEnabled());
        ckEnabled.setEnabled(false);
        ckExpired = new Checkbox(dlg.getTranslation("app.login.user.expired"));
        ckExpired.setValue(user.isExpired());
        ckExpired.setEnabled(false);
        roles = new MultiSelectListBox<Role>();
        roles.setItems(Role.values());
        roles.select(user.getRoles());
        roles.setEnabled(isAdmin(current));

        FormLayout pane = new FormLayout();
        pane.addFormItem(tfUsername, dlg.getTranslation("app.login.username"));
        pane.add(btPassword);
        pane.addFormItem(tfName, dlg.getTranslation("app.login.user.name"));
        pane.addFormItem(tfSurname, dlg.getTranslation("app.login.user.surname"));
        pane.addFormItem(tfEmail, dlg.getTranslation("app.login.user.email"));
        pane.addFormItem(roles, dlg.getTranslation("app.login.user.roles"));
        pane.add(new HorizontalLayout(ckMenuRegistry, ckMenuRepository));
        pane.add(new HorizontalLayout(ckEnabled, ckExpired));
        pane.add(new HorizontalLayout(ckNotification, ckWeekly));

        ComboBox<String> ckUsers = new ComboBox<>();
        ckUsers.setWidthFull();

        boolean isAdmin = user.getRoles().contains(Role.ADMIN);

        Button btAdd = UIUtils.createButton(VaadinIcon.PLUS_CIRCLE, ButtonVariant.LUMO_PRIMARY);
        UIUtils.setTooltip(dlg.getTranslation("app.addnew.title"), btAdd);
//			dlg.getTranslation("app.addnew.title"), VaadinIcon.PLUS_CIRCLE);
        btAdd.setVisible(isAdmin);

//		Button btDelete = UIUtils.createContrastButton(
//			dlg.getTranslation("app.delete.title"), VaadinIcon.CLOSE_CIRCLE); 
        Button btDelete = UIUtils.createButton(VaadinIcon.CLOSE_CIRCLE, ButtonVariant.LUMO_SUCCESS);
        UIUtils.setTooltip(dlg.getTranslation("app.delete.title"), btDelete);
        btDelete.addClickListener(e -> {
            btAdd.setEnabled(true);

            try {
                if (isSameUser(user.getUsername()) || isAdmin(user.getUsername()))
                    throw new Exception(dlg.getTranslation(
                            "app.login.error.delete"));

                service.delete(user);

                ckUsers.setItems(service.findAll());
                ckUsers.setValue(current);
            } catch (IOException ex) {
                UIUtils.showMessage(dlg.getTranslation("app.login.error", ex.getMessage()));
            } catch (Exception ex) {
                UIUtils.showMessage(ex.getMessage());
            }
        });
        btDelete.setVisible(isAdmin);

        btAdd.addClickListener(e -> {
            user.reset();

            ckUsers.setValue(null);

            e.getSource().setEnabled(false);
            btDelete.setEnabled(false);

            init(null, false);

            pwDialog.setCheckOldPassword(false);
        });
        btAdd.setVisible(isAdmin);

        ckUsers.setVisible(isAdmin);
        ckUsers.setItems(service.findAll());
        ckUsers.addValueChangeListener(evn -> {
            Optional<UserType> u = service.findBy(evn.getValue());

            if (u.isPresent()) {
                user.clone(u.get());

                init(user, isSameUser(user.getUsername()));

                pwDialog.setCheckOldPassword(!isAdmin(current));

                btAdd.setEnabled(true);
                btDelete.setEnabled(true);
            }
        });

        VerticalLayout vlManage = new VerticalLayout();
        vlManage.add(new Label(dlg.getTranslation("app.login.manageusers")));
        vlManage.add(new HorizontalLayout(ckUsers, btAdd, btDelete));
        vlManage.setMargin(true);

        pane.add(vlManage);

        return pane;
    }

    private boolean isAdmin(String username) {
        return username.equalsIgnoreCase(UserService.ADMIN);
    }

    private void init(UserType user, boolean isSameUser) {
        roles.deselectAll();
        ckEnabled.setEnabled(!isSameUser);
        ckExpired.setEnabled(!isSameUser);

        if (user == null) {
            tfUsername.setValue("");
            tfUsername.setEnabled(true);
            tfName.setValue("");
            tfEmail.setValue("");
            tfSurname.setValue("");
            ckMenuRegistry.setValue(false);
            ckMenuRepository.setValue(false);
            ckEnabled.setValue(true);
            ckExpired.setValue(false);
            ckNotification.setValue(false);
            ckWeekly.setValue(false);
            roles.select(Role.USER);
        } else {
            tfUsername.setValue(user.getUsername());
            tfUsername.setEnabled(false);
            tfName.setValue(user.getName());
            tfEmail.setValue(UIUtils.nullToEmpty(user.getEmail()));
            tfSurname.setValue(UIUtils.nullToEmpty(user.getSurname()));
            ckMenuRegistry.setValue(user.isMenuRegistry());
            ckMenuRepository.setValue(user.isMenuRepository());
            ckEnabled.setValue(user.isEnabled());
            ckExpired.setValue(user.isExpired());
            ckNotification.setValue(user.isNotification());
            ckWeekly.setValue(user.isWeekly());
            roles.select(user.getRoles());
        }
    }

    private void check(String value, String label) throws Exception {
        if (UIUtils.isEmptyOrNull(value))
            throw new Exception(label);
    }

}
