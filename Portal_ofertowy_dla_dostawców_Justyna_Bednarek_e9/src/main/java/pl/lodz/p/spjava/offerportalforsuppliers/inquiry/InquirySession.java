package pl.lodz.p.spjava.offerportalforsuppliers.inquiry;

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
import pl.lodz.p.spjava.offerportalforsuppliers.exception.InquiryException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;
import pl.lodz.p.spjava.offerportalforsuppliers.web.utils.ContextUtils;

/**
 *
 * @author Justyna Bednarek
 */
@SessionScoped
@Named("inquirySession")
public class InquirySession implements Serializable {

    private static final Logger LOG = Logger.getLogger(InquirySession.class.getName());

    public InquirySession() {
    }

    private Inquiry inquiryCreate;
    private Inquiry inquiryPreview;

    @Inject
    private InquiryEndpoint inquiryEndpoint;

    @Inject
    private OfferEndpoint offerEndpoint;

    public String log() {
        ContextUtils.invalidateSession();

        return "cancelAction";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }

    public Inquiry getInquiryPreview() {
        return inquiryPreview;
    }

    public String createInquiry(Inquiry inquiry) {
        try {
            inquiryCreate = inquiry;
            inquiryEndpoint.createInquiry(inquiryCreate);
            inquiryCreate = null;
            return "success";
        } catch (InquiryException ie) {
            if (InquiryException.KEY_DB_NUMBERINQUIRY_EXIST.equals(ie.getMessage())) {
                ContextUtils.emitInternationalizedMessage("numberInquiry", InquiryException.KEY_DB_NUMBERINQUIRY_EXIST);
            } else {
                Logger.getLogger(InquirySession.class.getName()).log(Level.SEVERE, "Throwing an exception of a created query using methods: ", ie.getClass());
            }
            return null;
        } catch (AppBaseException abe) {
            Logger.getLogger(InquirySession.class.getName()).log(Level.SEVERE, "Create exception  in the action method: ", abe.getClass());
            if (ContextUtils.isInternationalizationKeyExist(abe.getMessage())) {
                ContextUtils.emitInternationalizedMessage(null, abe.getMessage());
            }
            return null;
        }
    }

    public void completeInquiry(Inquiry Inquiry) {
        inquiryEndpoint.completeInquiry(Inquiry);
        ContextUtils.emitSuccessMessage(ListInquirysPageBean.GENERAL_MSG_ID);
    }

    public void notCompleteInquiry(Inquiry Inquiry) {
        inquiryEndpoint.notCompleteInquiry(Inquiry);
        ContextUtils.emitSuccessMessage(ListInquirysPageBean.GENERAL_MSG_ID);
    }

    public void downloadInquiryToPreview(Inquiry Inquiry) {
        inquiryPreview = inquiryEndpoint.downloadInquiryToPreview(Inquiry);
    }

    public List<Inquiry> downloadAllInquirys() {
        return inquiryEndpoint.downloadAllInquirys();
    }

    public List<Inquiry> matchInquirys(String numberInquiryPattern) {
        return inquiryEndpoint.matchInquirys(numberInquiryPattern);
    }

    public List<Inquiry> downloadNotCompleteInquirys() {
        return inquiryEndpoint.downloadInquirysNotComplete();
    }

    public List<Offer> sortOffersByPriceForInquiry(Inquiry inquiry) {
        return offerEndpoint.sortOffersByPriceForInquiry(inquiry);
    }
}
