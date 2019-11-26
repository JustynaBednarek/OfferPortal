package pl.lodz.p.spjava.offerportalforsuppliers.account;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.AccountUtils;

/**
 *
 * @author Justyna Bednarek 
 */
@RequestScoped
@Named("detailsMyAccountPageBean")
public class DetailsMyAccountPageBean {

    public DetailsMyAccountPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    @PostConstruct
    private void init() {
        account = accountSession.downloadMyAccount();
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
    
}
