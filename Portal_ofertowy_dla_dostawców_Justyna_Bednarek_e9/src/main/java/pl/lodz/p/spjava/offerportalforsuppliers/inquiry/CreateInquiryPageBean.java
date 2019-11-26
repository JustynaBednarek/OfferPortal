package pl.lodz.p.spjava.offerportalforsuppliers.inquiry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("createInquiryPageBean")
public class CreateInquiryPageBean {

    public CreateInquiryPageBean() {
    }

    @Inject
    private InquirySession inquirySession;

    private final Inquiry inquiry = new Inquiry();

    public Inquiry getInquiry() {
        return inquiry;
    }

    public String createInquiry() {
        return inquirySession.createInquiry(inquiry);
    }

}
