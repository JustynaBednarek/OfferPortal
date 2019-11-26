package pl.lodz.p.spjava.offerportalforsuppliers.exception;

import javax.ejb.ApplicationException;

/**
 *
 * @author Justyna Bednarek
 */
@ApplicationException(rollback = true)
abstract public class AppBaseException extends Exception {

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }

}
