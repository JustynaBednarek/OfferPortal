package pl.lodz.p.spjava.offerportalforsuppliers.account;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.AccountUtils;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("editAccountPageBean")
public class EditAccountPageBean {

    public EditAccountPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    @PostConstruct
    private void init() {
        account = accountSession.getAccountEdit();
    }
    private Account account = new Account();

    public Account getAccount() {
        return account;
    }

    public boolean isSupplier() {
        return AccountUtils.isSupplier(account);
    }

    public boolean isBuyer() {
        return AccountUtils.isBuyer(account);
    }

    public boolean isAdministrator() {
        return AccountUtils.isAdministrator(account);
    }

    public String saveAccount() throws AppBaseException {
        return accountSession.saveAccountAfterEditing(account);
    }
}
