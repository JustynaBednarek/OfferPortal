package pl.lodz.p.spjava.offerportalforsuppliers.inquiry;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.offer.OfferSession;

/**
 *
 * @author Justyna Bednarek
 */
@ViewScoped
@Named("listInquirysPageBean")
public class ListInquirysPageBean implements Serializable {

    static final String GENERAL_MSG_ID = "listInquirysForm:listInquirys";

    public ListInquirysPageBean() {

    }

    @Inject
    private InquirySession inquirySession;

    @Inject
    private OfferSession offerSession;

    private boolean onlyNotComplete = false;
    private List<Inquiry> inquirys;
    private DataModel<Inquiry> inquirysDataModel;

    @PostConstruct
    private void initModel() {

        if (onlyNotComplete) {
            inquirys = inquirySession.downloadNotCompleteInquirys();
        } else {
            if (!searchNumberInquiry.isEmpty()) {
                inquirys = inquirySession.matchInquirys(searchNumberInquiry);
            } else {
                inquirys = inquirySession.downloadAllInquirys();
            }
        }
        inquirysDataModel = new ListDataModel<>(inquirys);
    }

    public boolean isOnlyNotComplete() {
        return onlyNotComplete;
    }

    public void setOnlyNotComplete(boolean onlyNotComplete) {
        this.onlyNotComplete = onlyNotComplete;
    }

    public DataModel<Inquiry> getInquirysDataModel() {
        return inquirysDataModel;
    }

    private String searchNumberInquiry = "";

    public String getSearchNumberInquiry() {
        return searchNumberInquiry;
    }

    public void setSearchNumberInquiry(String searchNumberInquiry) {
        this.searchNumberInquiry = searchNumberInquiry;
    }

    public void refresh() {
        initModel();
    }

    public void clear() {
        searchNumberInquiry = "";
    }

    public void completeInquiry() {
        inquirySession.completeInquiry(inquirysDataModel.getRowData());
        initModel();
    }

    public void notCompleteInquiry() {
        inquirySession.notCompleteInquiry(inquirysDataModel.getRowData());
        initModel();
    }

    public String previewInquiryBuyer() {

        inquirySession.downloadInquiryToPreview(inquirysDataModel.getRowData());
        return "previewInquiryBuyer";
    }

    public String previewInquirySupplier() {

        inquirySession.downloadInquiryToPreview(inquirysDataModel.getRowData());
        return "previewInquirySupplier";
    }

    public String createOfferForInquiry() {
        offerSession.downloadInquiryToCreateOffer(inquirysDataModel.getRowData());
        return "createOffer";
    }
}
