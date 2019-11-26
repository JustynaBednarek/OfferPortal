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
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades.OfferFacade;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades.SupplierFacade;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.OfferException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

/**
 *
 * @author Justyna Bednarek
 */
@Stateful
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@RolesAllowed({"Supplier", "Buyer", "Administrator"})
public class OfferEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @Inject
    private OfferFacade offerFacade;

    @Inject
    private SupplierFacade supplierFacade;

    @Inject
    private AccountEndpoint accountEndpoint;

    @Resource
    private SessionContext sctx;

    private Offer offerState;

    @RolesAllowed("Supplier")
    public void createOffer(Offer offer, Inquiry inquiry) throws AppBaseException {
        if (offerFacade.checkMyOffersForInquiry(sctx.getCallerPrincipal().getName(), inquiry.getNumberInquiry())) {
            throw OfferException.createWithSupplierAlreadyAddedOffer(offer, null);
        }
        if (!inquiry.isUptoDate()) {
            throw OfferException.createWithInquiryIsUpToDate(offer, null);
        }
        if (inquiry.isCompleted()) {
            throw OfferException.createWithInquiryIsCompleted(offer, null);
        }

        offer.setAccepted(false);
        offer.setSupplier(supplierFacade.findSupplierByLogin(sctx.getCallerPrincipal().getName()));
        offer.setInquiry(inquiry);
        inquiry.getOffers().add(offer);
        offerFacade.create(offer);
    }

    @RolesAllowed({"Buyer", "Administrator"})
    public void acceptOffer(Offer offer) {
        Account myAccount = accountEndpoint.downloadMyAccount();
        Offer o = offerFacade.find(offer.getId());
        o.setAccepted(true);
        o.setWhoAccepted(myAccount);
    }

    @RolesAllowed("Administrator")
    public void notAcceptOffer(Offer offer) {
        Account myAccount = accountEndpoint.downloadMyAccount();
        Offer o = offerFacade.find(offer.getId());
        o.setAccepted(false);
        o.setWhoAccepted(myAccount);
    }

    public Offer findNumberOffer(String numberOffer) {
        return offerFacade.findNumberOffer(numberOffer);
    }

    public Offer downloadOfferToPreview(Offer offer) {
        offerState = findNumberOffer(offer.getNumberOffer());
        return offerState;
    }

    public List<Offer> downloadMyOffers() {
        return offerFacade.findMyOffers(sctx.getCallerPrincipal().getName());
    }

    public List<Offer> downloadAllOffers() {
        return offerFacade.findAll();
    }

    public List<Offer> matchOffer(String numberOfferPattern) {
        return offerFacade.matchOffer(numberOfferPattern);
    }

    public List<Offer> downloadAcceptedOnly() {
        return offerFacade.findOnlyAcceptedOffers();
    }

    public List<Offer> matchMyOffer(String numberOfferPattern) {
        return offerFacade.matchMyOffer(numberOfferPattern);
    }

    public List<Offer> downloadMyAcceptedOnly() {
        return offerFacade.findOnlyMyAcceptedOffers(sctx.getCallerPrincipal().getName());
    }

    public List<Offer> sortOffersByPriceForInquiry(Inquiry inquiry) {
        return offerFacade.sortOffersByPriceForInquiry(inquiry);
    }

}
