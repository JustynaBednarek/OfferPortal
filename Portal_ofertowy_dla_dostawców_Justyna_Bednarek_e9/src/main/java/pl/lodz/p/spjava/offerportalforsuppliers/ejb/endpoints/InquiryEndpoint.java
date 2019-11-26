package pl.lodz.p.spjava.offerportalforsuppliers.ejb.endpoints;

import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades.BuyerFacade;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades.InquiryFacade;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;

/**
 *
 * @author Justyna Bednarek
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@RolesAllowed({"Buyer", "Supplier", "Administrator"})
public class InquiryEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Inject
    private InquiryFacade inquiryFacade;

    @Inject
    private BuyerFacade buyerFacade;

    @Inject
    private AccountEndpoint accountEndpoint;

    @Resource
    private SessionContext sctx;

    private Inquiry inquiryState;

    @RolesAllowed("Buyer")
    public void createInquiry(Inquiry inquiry) throws AppBaseException {
        inquiry.setCompleted(false);
        inquiry.setBuyer(buyerFacade.findBuyerByLogin(sctx.getCallerPrincipal().getName()));
        inquiryFacade.create(inquiry);
    }

    @RolesAllowed({"Buyer", "Administrator"})
    public void completeInquiry(Inquiry inquiry) {
        Account myAccount = accountEndpoint.downloadMyAccount();
        Inquiry i = inquiryFacade.find(inquiry.getId());
        i.setCompleted(true);
        i.setWhoCompleted(myAccount);
    }

    @RolesAllowed("Administrator")
    public void notCompleteInquiry(Inquiry inquiry) {
        Account myAccount = accountEndpoint.downloadMyAccount();
        Inquiry i = inquiryFacade.find(inquiry.getId());
        i.setCompleted(false);
        i.setWhoCompleted(myAccount);
    }

    public Inquiry findNumberInquiry(String numberInquiry) {
        return inquiryFacade.findNumberInquiry(numberInquiry);
    }

    public Inquiry downloadInquiryToPreview(Inquiry inquiry) {
        inquiryState = findNumberInquiry(inquiry.getNumberInquiry());
        return inquiryState;
    }

    public List<Inquiry> downloadAllInquirys() {
        return inquiryFacade.findAll();
    }

    public List<Inquiry> matchInquirys(String numberInquiryPattern) {
        return inquiryFacade.matchInquirys(numberInquiryPattern);
    }

    public List<Inquiry> downloadInquirysNotComplete() {
        return inquiryFacade.findInquirysNotComplete();
    }

}
