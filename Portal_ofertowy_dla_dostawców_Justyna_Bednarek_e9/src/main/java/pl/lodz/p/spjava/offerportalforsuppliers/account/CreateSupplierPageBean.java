package pl.lodz.p.spjava.offerportalforsuppliers.account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Supplier;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("createSupplierPageBean")
public class CreateSupplierPageBean {

    public CreateSupplierPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    private final Supplier account = new Supplier();

    public Supplier getAccount() {
        return account;
    }

    private String passwordRepeat = "";

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String create() {
        if (!(passwordRepeat.equals(account.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("createSupplierForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        return accountSession.createSupplier(account);
    }
    
}
