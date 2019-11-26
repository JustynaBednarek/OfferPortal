package pl.lodz.p.spjava.offerportalforsuppliers.account;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Buyer;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("createBuyerPageBean")
public class CreateBuyerPageBean {

    public CreateBuyerPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    private final Buyer account = new Buyer();

    public Buyer getAccount() {
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
        System.out.println(passwordRepeat);
        System.out.println(account.getPassword());
        if (!(passwordRepeat.equals(account.getPassword()))) {
            ContextUtils.emitInternationalizedMessage("createBuyerForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        return accountSession.createBuyer(account);
    }
    
}
