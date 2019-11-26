package pl.lodz.p.spjava.offerportalforsuppliers.ejb.endpoints;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades.AccountFacade;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.AccountUtils;

/**
 *
 * @author Justyna Bednarek
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@RolesAllowed({"Administrator", "Buyer", "Supplier"})
public class AccountEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Inject
    private AccountFacade accountFacade;

    private Account accountState;

    public Account downloadMyAccount() {
        return findLogin(downloadMyLogin());
    }

    public String downloadMyLogin() throws IllegalStateException {
        return sctx.getCallerPrincipal().getName();
    }

    @RolesAllowed("Administrator")
    public void createAccount(Account account) throws AppBaseException {
        account.setActive(true);
        account.setPassword(hashPassword(account.getPassword()));
        accountFacade.create(account);
    }

    public void activateAccount(Account account) {
        Account k = accountFacade.find(account.getId());
        k.setActive(true);
    }

    public void deactivateAccount(Account account) {
        Account k = accountFacade.find(account.getId());
        k.setActive(false);
    }

    @RolesAllowed("Administrator")
    public List<Account> downloadAllAccounts() {
        return accountFacade.findAll();
    }

    @RolesAllowed("Administrator")
    public List<Account> matchAccounts(String loginPattern, String nameCompanyPattern, String taxNumberCompanyPattern, String namePattern, String surnamePattern,
            String emailPattern) {
        return accountFacade.matchAccounts(loginPattern, nameCompanyPattern, taxNumberCompanyPattern, namePattern, surnamePattern, emailPattern);
    }

    public Account findLogin(String login) {
        return accountFacade.findLogin(login);
    }

    public Account downloadAccoutToEdition(Account account) {
        accountState = accountFacade.findLogin(account.getLogin());
        return accountState;
    }

    public void saveAccountAfterEdition(Account account) throws AppBaseException {
        if (null == accountState) {
            throw new IllegalArgumentException("No account loaded for modification");
        }
        if (!accountState.equals(account)) {
            throw new IllegalArgumentException("Modified account not compatible with loaded account");
        }
        AccountUtils.rewriteDataAfterEditing(account, accountState);
        accountFacade.edit(accountState);
        accountState = null;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < encodedhash.length; i++) {
                stringBuffer.append(Integer.toString((encodedhash[i] & 0xff) + 0x100, 16).substring(1));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Account.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void changePassword(Account account, String password) {
        Account k = accountFacade.find(account.getId());
        k.setPassword(hashPassword(password));
    }
}
