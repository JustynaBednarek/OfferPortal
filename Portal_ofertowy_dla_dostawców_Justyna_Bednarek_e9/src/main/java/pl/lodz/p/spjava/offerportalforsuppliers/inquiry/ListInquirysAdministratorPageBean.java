package pl.lodz.p.spjava.offerportalforsuppliers.inquiry;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;

/**
 *
 * @author Justyna Bednarek
 */
@ViewScoped
@Named("listInquirysAdministratorPageBean")
public class ListInquirysAdministratorPageBean implements Serializable {

    public static final String GENERAL_MSG_ID = "listInquirysAdministratorForm:listInquirysAdministrator";

    public ListInquirysAdministratorPageBean() {

    }

    @Inject
    private InquirySession inquirySession;
    
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

}
