package pl.lodz.p.spjava.offerportalforsuppliers.offer;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.endpoints.InquiryEndpoint;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.endpoints.OfferEndpoint;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.OfferException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@SessionScoped
@Named("offerSession")
public class OfferSession implements Serializable {

    private static final Logger LOG = Logger.getLogger(OfferSession.class.getName());

    @Inject
    private OfferEndpoint offerEndpoint;

    @Inject
    private InquiryEndpoint inquiryEndpoint;

    public String log() {
        ContextUtils.invalidateSession();

        return "cancelAction";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }

    private Offer offerPreview;
    private Inquiry inquirySelectedToCreateOffer;

    public Offer getOfferPreview() {
        return offerPreview;
    }

    public OfferSession() {
    }

    public String createOffer(Offer offer) {
        try {
            offerEndpoint.createOffer(offer, inquirySelectedToCreateOffer);
            inquirySelectedToCreateOffer = null;
            return "success";
        } catch (OfferException oe) {
            if (OfferException.KEY_SUPPLIER_ALREADY_ADDED_OFFER.equals(oe.getMessage())) {
                ContextUtils.emitInternationalizedMessage("numberOffer", OfferException.KEY_SUPPLIER_ALREADY_ADDED_OFFER);
            }
            if (OfferException.KEY_DB_NUMBEROFFER_EXIST.equals(oe.getMessage())) {
                ContextUtils.emitInternationalizedMessage("numberOffer", OfferException.KEY_DB_NUMBEROFFER_EXIST);

            } else {
                Logger.getLogger(OfferSession.class.getName()).log(Level.SEVERE, "Throwing an exception of a created query using methods: ", oe.getClass());
            }

            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(OfferSession.class.getName()).log(Level.SEVERE, "Create exception  in the action method: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public void acceptOffer(Offer Offer) {
        offerEndpoint.acceptOffer(Offer);
        ContextUtils.emitSuccessMessage(ListOffersBuyerPageBean.GENERAL_MSG_ID);
    }

    public void notAcceptOffer(Offer Offer) {
        offerEndpoint.notAcceptOffer(Offer);
        ContextUtils.emitSuccessMessage(ListOffersBuyerPageBean.GENERAL_MSG_ID);
    }

    public void downloadOfferToPreview(Offer Offer) {
        offerPreview = offerEndpoint.downloadOfferToPreview(Offer);
    }

    public List<Offer> downloadAllOffers() {
        return offerEndpoint.downloadAllOffers();
    }

    public List<Offer> downloadMyOffers() {
        return offerEndpoint.downloadMyOffers();
    }

    public List<Offer> matchOffer(String numberOfferPattern) {
        return offerEndpoint.matchOffer(numberOfferPattern);
    }

    public List<Offer> matchMyOffer(String numberOfferPattern) {
        return offerEndpoint.matchMyOffer(numberOfferPattern);
    }

    public List<Offer> downloadMyAcceptedOnly() {
        return offerEndpoint.downloadMyAcceptedOnly();
    }

    public void downloadInquiryToCreateOffer(Inquiry Inquiry) {
        inquirySelectedToCreateOffer = inquiryEndpoint.downloadInquiryToPreview(Inquiry);
    }

    public List<Offer> downloadAcceptedOnly() {
        return offerEndpoint.downloadAcceptedOnly();
    }

}
