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
@Named("listOffersAdministratorPageBean")
public class ListOffersAdministratorPageBean implements Serializable {

    static final String GENERAL_MSG_ID = "listOffersAdministratorForm:listOffersAdministrator";

    public ListOffersAdministratorPageBean() {
    }

    private boolean acceptedOnly = false;
    private List<Offer> offers;
    private DataModel<Offer> offersDataModel;

    @PostConstruct
    private void initModel() {

        if (acceptedOnly) {
            offers = offerSession.downloadAcceptedOnly();
        } else {
            if (!searchNumberOffer.isEmpty()) {
                offers = offerSession.matchOffer(searchNumberOffer);
            } else {
                offers = offerSession.downloadAllOffers();
            }
        }
        offersDataModel = new ListDataModel<>(offers);
    }

    @Inject
    private OfferSession offerSession;

    public DataModel<Offer> getOffersDataModel() {
        return offersDataModel;
    }

    public boolean isAcceptedOnly() {
        return acceptedOnly;
    }

    public void setAcceptedOnly(boolean acceptedOnly) {
        this.acceptedOnly = acceptedOnly;
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

    public void acceptedOffer() {
        offerSession.acceptOffer(offersDataModel.getRowData());
        initModel();
    }

    public void notAcceptedOffer() {
        offerSession.notAcceptOffer(offersDataModel.getRowData());
        initModel();
    }
}
