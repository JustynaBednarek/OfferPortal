package pl.lodz.p.spjava.offerportalforsuppliers.web.utils;

import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Administrator;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Buyer;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Supplier;

/**
 *
 * @author Justyna Bednarek 
 */
public class AccountUtils {

    /**
     *
     * @param account
     * @return
     */
    public static boolean isAdministrator(Account account) {
        return (account instanceof Administrator);
    }

    /**
     *
     * @param account
     * @return
     */
    public static boolean isBuyer(Account account) {
        return (account instanceof Buyer);
    }

    /**
     *
     * @param account
     * @return
     */
    public static boolean isSupplier(Account account) {
        return (account instanceof Supplier);
    }

    /**
     *
     * @param source entity containing data from the edit form
     * @param goal target entity
     */
    public static void rewriteDataAfterEditing(Account source, Account goal) {

        if (null == source || null == goal) {
            return;
        }

        source.setNameCompany(source.getNameCompany());
        source.setTaxNumberCompany(source.getTaxNumberCompany());
        source.setName(source.getName());
        source.setSurname(source.getSurname());
        source.setEmail(source.getEmail());
        source.setPhoneNumber(source.getPhoneNumber());

        if (isAdministrator(source) && isAdministrator(goal)) {
            Administrator sourceAdministrator = (Administrator) source;
            Administrator goalAdministrator = (Administrator) goal;
            goalAdministrator.setAlarmNumber(sourceAdministrator.getAlarmNumber());
        }

        if (isSupplier(source) && isSupplier(goal)) {
            Supplier sourceSupplier = (Supplier) source;
            Supplier goalSupplier = (Supplier) goal;
            goalSupplier.setAddressCompany(sourceSupplier.getAddressCompany());

        }

        if (isBuyer(source) && isBuyer(goal)) {
            Buyer sourceBuyer = (Buyer) source;
            Buyer goalBuyer = (Buyer) goal;

        }
    }

}
