package pl.lodz.p.spjava.offerportalforsuppliers.account;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("changePasswordAccountPageBean")
public class ChangePasswordAccountPageBean {

    public ChangePasswordAccountPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    private Account account;

    public Account getAccount() {
        return account;
    }

    @PostConstruct
    private void init() {
        account = accountSession.getAccountChangePassword();
        account.setPassword(new String());
    }

    private String passwordRepeat = "";

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String changePassword() {
        if (!(passwordRepeat.equals(account.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("changePasswordAccountForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        return accountSession.changePasswordAccount(account.getPassword());
    }
    
}
