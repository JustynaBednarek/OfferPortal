package pl.lodz.p.spjava.offerportalforsuppliers.account;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.endpoints.AccountEndpoint;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AccountException;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Administrator;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Buyer;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Supplier;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@SessionScoped
@Named("accountSession")
public class AccountSession implements Serializable {

    private static final Logger LOG = Logger.getLogger(AccountSession.class.getName());

    @Inject
    private AccountEndpoint accountEndpoint;

    private Supplier supplierCreate;
    private Buyer buyerCreate;
    private Administrator administratorCreate;
    private Account accountEdit;
    private Account accountChangePassword;

    public String log() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }

    public Account getAccountChangePassword() {
        return accountChangePassword;
    }

    public Account getAccountEdit() {
        return accountEdit;
    }

    public AccountSession() {
    }

    public String createSupplier(Supplier supplier) {
        try {
            supplierCreate = supplier;
            accountEndpoint.createAccount(supplierCreate);
            supplierCreate = null;
            return "success";
        } catch (AccountException ae) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createSupplierForm:login", AccountException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Reporting the exception of the Supplier created using the method: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Create an exception type supplier in the action method: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String createBuyer(Buyer buyer) {
        try {
            buyerCreate = buyer;
            accountEndpoint.createAccount(buyerCreate);
            buyerCreate = null;
            return "success";
        } catch (AccountException ae) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createBuyerForm:login", AccountException.KEY_DB_CONSTRAINT);
            } else {
                Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Reporting the exception of the Buyer created using the method: ", ae.getClass());
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Create an exception type buyer in the action method: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String createAdministrator(Administrator administrator) {
        try {
            administratorCreate = administrator;
            accountEndpoint.createAccount(administratorCreate);
            administratorCreate = null;
            return "success";
        } catch (AccountException ae) {
            if (AccountException.KEY_DB_CONSTRAINT.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("createAdministratorForm:login", AccountException.KEY_DB_CONSTRAINT);

            } else {
                Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Reporting the exception of the Administrator created using the method: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Create an exception type administrator in the action method: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public void activateAccount(Account Account) {
        accountEndpoint.activateAccount(Account);
        ContextUtils.emitSuccessMessage(ListAccountPageBean.GENERAL_MSG_ID);
    }

    public void deactivateAccount(Account Account) {
        accountEndpoint.deactivateAccount(Account);
        ContextUtils.emitSuccessMessage(ListAccountPageBean.GENERAL_MSG_ID);
    }

    public void downloadAccountToEditing(Account Account) {
        accountEdit = accountEndpoint.downloadAccoutToEdition(Account);
    }

    public String saveAccountAfterEditing(Account Account) {
        try {
            accountEndpoint.saveAccountAfterEdition(Account);
            return "success";
        } catch (AccountException ae) {
            if (AccountException.KEY_ACCOUNT_OPTIMISTIC_LOCK.equals(ae.getMessage())) {
                ContextUtils.emitInternationalizedMessage("editAccountForm:login", AccountException.KEY_ACCOUNT_OPTIMISTIC_LOCK);
            } else {
                Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Reporting the exception saved using the method: ", ae);
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(AccountSession.class.getName()).log(Level.SEVERE, "Create an exception in the action method: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public String startChangingPassword(Account account) {
        this.accountChangePassword = account;
        return "changePassword";
    }

    public String changePasswordAccount(String password) {
        accountEndpoint.changePassword(accountChangePassword, password);
        return "success";
    }

    public List<Account> downloadAllAccounts() {
        return accountEndpoint.downloadAllAccounts();
    }

    public List<Account> matchAccounts(String loginPattern, String nameCompanyPattern, String taxNumberCompanyPattern, String namePattern, String surnamePattern,
            String emailPattern
    ) {
        return accountEndpoint.matchAccounts(loginPattern, nameCompanyPattern, taxNumberCompanyPattern, namePattern, surnamePattern,
                emailPattern);
    }

    public Account downloadMyAccount() {
        return accountEndpoint.downloadMyAccount();
    }

}
