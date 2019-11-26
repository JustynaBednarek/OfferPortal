package pl.lodz.p.spjava.offerportalforsuppliers.exception;

import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;

/**
 *
 * @author Justyna Bednarek
 */
public class InquiryException extends AppBaseException {

    static final public String KEY_DB_NUMBERINQUIRY_EXIST = "error.inquiry.db.constraint.uniq.numberInquiry";
    static final public String KEY_DB_CONSTRAINT = "error.inquiry.db.constraint";
    static final public String KEY_INQUIRY_ALREADY_COMPLETED = "error.inquiry.already.completed";
    static final public String KEY_NO_STATE_IN_EJB_ENDPOINT = "error.inquiry.no.state.in.ejb.endpoint";

    private InquiryException(String message) {
        super(message);
    }

    private InquiryException(String message, Throwable cause) {
        super(message, cause);
    }

    private Inquiry inquiry;

    public Inquiry getInquiry() {
        return inquiry;
    }

    static public InquiryException createWithDbCheckNumberInquiryKey(Inquiry inquiry, Throwable cause) {
        InquiryException ie = new InquiryException(KEY_DB_NUMBERINQUIRY_EXIST, cause);
        ie.inquiry = inquiry;
        return ie;
    }

    static public InquiryException createWithDbCheckConstraintKey(Inquiry inquiry, Throwable cause) {
        InquiryException ie = new InquiryException(KEY_DB_CONSTRAINT, cause);
        ie.initCause(cause);
        ie.inquiry = inquiry;
        return ie;
    }

    static public InquiryException createWithInquiryAlreadyCompleted(Inquiry inquiry) {
        InquiryException ie = new InquiryException(KEY_INQUIRY_ALREADY_COMPLETED);
        ie.inquiry = inquiry;
        return ie;
    }

    static public InquiryException createWithNoStateInEJBEndpoint(Inquiry inquiry) {
        InquiryException ie = new InquiryException(KEY_NO_STATE_IN_EJB_ENDPOINT);
        ie.inquiry = inquiry;
        return ie;
    }

}
