package pl.lodz.p.spjava.offerportalforsuppliers.exception;

import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

/**
 *
 * @author Justyna Bednarek
 */
public class OfferException extends AppBaseException {

    static final public String KEY_DB_NUMBEROFFER_EXIST = "error.offer.db.constraint.uniq.numberOffer";
    static final public String KEY_DB_CONSTRAINT = "error.offer.db.constraint";
    static final public String KEY_OFFER_ALREADY_ACCEPTED = "error.offer.already.accepted";
    static final public String KEY_SUPPLIER_ALREADY_ADDED_OFFER = "error.offer.supplier.already.added.offer";
    static final public String KEY_INQUIRY_IS_UP_TO_DATE = "error.offer.iquiry.is.up.to.date";
    static final public String KEY_INQUIRY_IS_COMPLETED = "error.offer.inquiry.is.completed";

    private OfferException(String message) {
        super(message);
    }

    private OfferException(String message, Throwable cause) {
        super(message, cause);
    }

    private Offer offer;

    public Offer getOffer() {
        return offer;
    }

    static public OfferException createWithDbCheckNumberOfferKey(Offer offer, Throwable cause) {
        OfferException oe = new OfferException(KEY_DB_NUMBEROFFER_EXIST, cause);
        oe.offer = offer;
        return oe;
    }

    static public OfferException createWithDbCheckConstraintKey(Offer offer, Throwable cause) {
        OfferException oe = new OfferException(KEY_DB_CONSTRAINT, cause);
        oe.initCause(cause);
        oe.offer = offer;
        return oe;
    }

    static public OfferException createWithOfferAlreadyAccepted(Offer offer) {
        OfferException oe = new OfferException(KEY_OFFER_ALREADY_ACCEPTED);
        oe.offer = offer;
        return oe;
    }

    static public OfferException createWithSupplierAlreadyAddedOffer(Offer offer, Throwable cause) {
        OfferException oe = new OfferException(KEY_SUPPLIER_ALREADY_ADDED_OFFER, cause);
        oe.offer = offer;
        return oe;
    }

    static public OfferException createWithInquiryIsUpToDate(Offer offer, Throwable cause) {
        OfferException oe = new OfferException(KEY_INQUIRY_IS_UP_TO_DATE, cause);
        oe.offer = offer;
        return oe;
    }

    static public OfferException createWithInquiryIsCompleted(Offer offer, Throwable cause) {
        OfferException oe = new OfferException(KEY_INQUIRY_IS_COMPLETED, cause);
        oe.offer = offer;
        return oe;
    }

}
