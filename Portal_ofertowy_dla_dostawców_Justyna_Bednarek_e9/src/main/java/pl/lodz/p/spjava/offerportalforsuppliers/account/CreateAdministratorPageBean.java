package pl.lodz.p.spjava.offerportalforsuppliers.account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Administrator;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("createAdministratorPageBean")
public class CreateAdministratorPageBean {

    public CreateAdministratorPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    private final Administrator account = new Administrator();

    public Administrator getAccount() {
        return account;
    }

    private String passwordRepeat = "";

    public String getpasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String create() {
        if (!(passwordRepeat.equals(account.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("createAdministratorForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        return accountSession.createAdministrator(account);
    }
    
}
