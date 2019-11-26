package pl.lodz.p.spjava.offerportalforsuppliers.offer;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("createOfferPageBean")
public class CreateOfferPageBean {

    public CreateOfferPageBean() {
    }

    @Inject
    private OfferSession offerSession;

    private final Offer offer = new Offer();

    public Offer getOffer() {
        return offer;
    }

    public String createOffer() {
        return offerSession.createOffer(offer);
    }
}
