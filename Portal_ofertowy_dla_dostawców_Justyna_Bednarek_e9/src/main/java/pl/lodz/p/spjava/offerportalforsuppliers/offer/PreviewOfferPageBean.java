package pl.lodz.p.spjava.offerportalforsuppliers.offer;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

/**
 *
 * @author Justyna Bednarek
 */
@RequestScoped
@Named("previewOfferPageBean")
public class PreviewOfferPageBean {

    public PreviewOfferPageBean() {
    }

    @Inject
    private OfferSession offerSession;

    @PostConstruct
    private void init() {
        offer = offerSession.getOfferPreview();
    }
    private Offer offer = new Offer();

    public Offer getOffer() {
        return offer;
    }

}
