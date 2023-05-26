package it.unidoc.cdr.core.ui.views.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.codecamp.vaadin.components.messagedialog.MessageDialog;
import it.unidoc.cdr.core.ui.backend.type.UserType;
import it.unidoc.cdr.core.ui.util.UIUtils;

/**
 * @author b.amoruso
 */
public class ChangePasswordDialog {

    private boolean checkOldPassword;

    private MessageDialog dlg;
    private PasswordField tfOldPassword;
    private PasswordField tfNewPassword;
    private PasswordField tfNewPasswordAgain;
    private Label lbNotEqualPassword;
    private Button btSave;

    public void show(UserType user) {
        dlg = new MessageDialog();

        dlg.getContent().add(createContent());
        btSave = dlg.addButtonToLeft().
                text(dlg.getTranslation("app.save.title")).
                icon(VaadinIcon.CHECK_CIRCLE.create()).
                onClick(e -> {
                    boolean check = true;

                    if (isCheckOldPassword())
                        check = UIUtils.isEmptyOrNull(tfOldPassword.getValue()) ?
                                false : user.checkPassword(tfOldPassword.getValue());

                    if (check) {
                        if (!tfNewPassword.getValue().equals(tfNewPasswordAgain.getValue())) {
                            lbNotEqualPassword.setVisible(true);
                        } else {
                            try {
                                user.setPassword(evaluate(tfNewPassword.getValue()));

                                dlg.close();
                            } catch (Exception ex) {
                                UIUtils.showMessage(ex.getMessage());
                            }
                        }
                    } else
                        UIUtils.showMessage(dlg.getTranslation("app.login.error.oldpassword"));
                }).getButton();
        dlg.addButton().
                text(dlg.getTranslation("app.close.title")).
                icon(VaadinIcon.CLOSE_CIRCLE.create()).
                closeOnClick();
        dlg.setWidth("360px");

        enable(false);

        dlg.open();
    }

    public boolean isCheckOldPassword() {
        return this.checkOldPassword;
    }

    public void setCheckOldPassword(boolean check) {
        this.checkOldPassword = check;
    }

    public boolean hasPassword() {
        return tfNewPasswordAgain == null || !UIUtils.isEmptyOrNull(tfNewPasswordAgain.getValue());
    }

    private FormLayout createContent() {
        tfOldPassword = new PasswordField(dlg.getTranslation("app.login.oldpassword"));
        tfOldPassword.setEnabled(checkOldPassword);

        tfNewPassword = new PasswordField(
                dlg.getTranslation("app.login.newpassword"));
        tfNewPassword.setValueChangeMode(ValueChangeMode.EAGER);
        tfNewPassword.addValueChangeListener(e ->
                passwordMatch(e.getValue(), tfNewPasswordAgain.getValue()));
        tfNewPassword.setRequiredIndicatorVisible(true);

        tfNewPasswordAgain = new PasswordField(
                dlg.getTranslation("app.login.newpasswordagain"));
        tfNewPasswordAgain.setRequiredIndicatorVisible(true);
        tfNewPasswordAgain.setValueChangeMode(ValueChangeMode.EAGER);
        tfNewPasswordAgain.addValueChangeListener(e ->
                passwordMatch(e.getValue(), tfNewPassword.getValue()));
        lbNotEqualPassword = new Label(
                dlg.getTranslation("app.login.error.notequalpassword"));

        tfNewPasswordAgain.setValue("");
        lbNotEqualPassword.setVisible(false);

        FormLayout content = new FormLayout();
        content.add(tfOldPassword);
        content.add(tfNewPassword);
        content.add(tfNewPasswordAgain);
        content.add(lbNotEqualPassword);

        return content;
    }

    private void passwordMatch(String value, String toCheck) {
        boolean equals = toCheck.equals(value);

        lbNotEqualPassword.setVisible(!equals);

        enable(equals);
    }

    private void enable(boolean enabled) {
        btSave.setEnabled(enabled);
    }

    private String evaluate(String password) throws Exception {
        int MINLENGHT = 8;

        if (UIUtils.isEmptyOrNull(password) || password.length() < MINLENGHT)
            throw new Exception(dlg.getTranslation("app.login.error.length", MINLENGHT));

        if (!password.matches(".*\\d.*"))
            throw new Exception(dlg.getTranslation("app.login.error.digit"));

        if (!password.matches(".*[A-Z].*"))
            throw new Exception(dlg.getTranslation("app.login.error.upperalpha"));

        return password;
    }

}
