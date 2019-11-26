package pl.lodz.p.spjava.offerportalforsuppliers.inquiry;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

/**
 *
 * @author Justyna Bednarek 
 */
@RequestScoped
@Named("previewInquiryPageBean")
public class PreviewInquiryPageBean {

    static final String GENERAL_MSG_ID = "previewInquiryForm:previewInquiry";

    public PreviewInquiryPageBean() {
    }

    @Inject
    private InquirySession inquirySession;

    @PostConstruct
    private void init() {
        inquiry = inquirySession.getInquiryPreview();
    }
    private Inquiry inquiry = new Inquiry();

    public Inquiry getInquiry() {
        return inquiry;
    }
    
    public List<Offer> getOrderOffersForInquiry(){
        return inquirySession.sortOffersByPriceForInquiry(inquiry);
    }

}
