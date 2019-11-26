package pl.lodz.p.spjava.offerportalforsuppliers.exception;

import javax.persistence.OptimisticLockException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;

/**
 *
 * @author Justyna Bednarek
 */
public class AccountException extends AppBaseException {

    static final public String KEY_ACCOUNT_OPTIMISTIC_LOCK = "error.account.optimisticlock";
    static final public String KEY_DB_CONSTRAINT = "error.account.db.constraint.uniq.login";

    public void main(String[] args) {

    }

    public AccountException(String message) {
        super(message);
    }

    public AccountException(String message, Throwable cause) {
        super(message, cause);
    }

    private Account account;

    public Account getAccount() {
        return account;
    }

    static public AccountException createWithAccountOptimisticLockKey(Account account, OptimisticLockException cause) {
        AccountException ae = new AccountException(KEY_ACCOUNT_OPTIMISTIC_LOCK, cause);
        ae.account = account;
        return ae;
    }

    static public AccountException createWithDbCheckConstraintKey(Account account, Throwable cause) {
        AccountException ae = new AccountException(KEY_DB_CONSTRAINT, cause);
        ae.account = account;
        return ae;
    }
}
