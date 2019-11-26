package pl.lodz.p.spjava.offerportalforsuppliers.offer;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

/**
 *
 * @author Justyna Bednarek
 */
@ViewScoped
@Named("listOffersSupplierPageBean")
public class ListOffersSupplierPageBean implements Serializable{

    static final String GENERAL_MSG_ID = "listOffersSupplierForm:listOffersSupplier";

    public ListOffersSupplierPageBean() {
    }

    private boolean myAcceptedOnly = false;
    private List<Offer> offers;
    private DataModel<Offer> offersDataModel;

    @Inject
    private OfferSession offerSession;

    @PostConstruct
    private void initModel() {

        if (myAcceptedOnly) {
            offers = offerSession.downloadMyAcceptedOnly();
        } else {
            if (!searchNumberOffer.isEmpty()) {
                offers = offerSession.matchMyOffer(searchNumberOffer);
            } else {
                offers = offerSession.downloadMyOffers();
            }
        }
        offersDataModel = new ListDataModel<>(offers);
    }

    public DataModel<Offer> getOffersDataModel() {
        return offersDataModel;
    }

    public boolean isMyAcceptedOnly() {
        return myAcceptedOnly;
    }

    public void setMyAcceptedOnly(boolean myAcceptedOnly) {
        this.myAcceptedOnly = myAcceptedOnly;
    }

    private String searchNumberOffer = "";

    public String getSearchNumberOffer() {
        return searchNumberOffer;
    }

    public void setSearchNumberOffer(String searchNumberOffer) {
        this.searchNumberOffer = searchNumberOffer;
    }

    public void refresh() {
        initModel();
    }

    public void clear() {
        searchNumberOffer = "";
    }

    public String previewOfferSupplier() {
        offerSession.downloadOfferToPreview(offersDataModel.getRowData());
        return "previewOfferSupplier";
    }
}
