package pl.lodz.p.spjava.offerportalforsuppliers.account;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;

/**
 *
 * @author Justyna Bednarek 
 */
@ViewScoped
@Named("listAccountPageBean")
public class ListAccountPageBean implements Serializable{

    static final String GENERAL_MSG_ID = "listAccountForm:listAccount";

    public ListAccountPageBean() {
    }

    @Inject
    private AccountSession accountSession;

    @PostConstruct
    private void initModel() {
        accounts = accountSession.matchAccounts(searchLogin, searchNameCompany, searchTaxNumberCompany, searchName, searchSurname, searchEmail);
        accountsDataModel = new ListDataModel<>(accounts);
    }

    private List<Account> accounts;
    private DataModel<Account> accountsDataModel;

    public DataModel<Account> getAccountsDataModel() {
        return accountsDataModel;
    }

    private String searchLogin = "";
    private String searchNameCompany = "";
    private String searchTaxNumberCompany = "";
    private String searchName = "";
    private String searchSurname = "";
    private String searchEmail = "";

    public String getSearchLogin() {
        return searchLogin;
    }

    public String getSearchNameCompany() {
        return searchNameCompany;
    }

    public void setSearchNameCompany(String searchNameCompany) {
        this.searchNameCompany = searchNameCompany;
    }

    public String getSearchTaxNumberCompany() {
        return searchTaxNumberCompany;
    }

    public void setSearchTaxNumberCompany(String searchTaxNumberCompany) {
        this.searchTaxNumberCompany = searchTaxNumberCompany;
    }

    public void setSearchLogin(String searchLogin) {
        this.searchLogin = searchLogin;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchSurname() {
        return searchSurname;
    }

    public void setSearchSurname(String searchSurname) {
        this.searchSurname = searchSurname;
    }

    public String getSearchEmail() {
        return searchEmail;
    }

    public void setSearchEmail(String searchEmail) {
        this.searchEmail = searchEmail;
    }

    public void refresh() {
        initModel();
    }

    public void clear() {
        searchLogin = "";
        searchNameCompany = "";
        searchTaxNumberCompany = "";
        searchName = "";
        searchSurname = "";
        searchEmail = "";
    }

    public void activateAccount() {
        accountSession.activateAccount(accountsDataModel.getRowData());
        initModel();
    }

    public void deactivateAccount() {
        accountSession.deactivateAccount(accountsDataModel.getRowData());
        initModel();
    }

    public String editAccount() {
        accountSession.downloadAccountToEditing(accountsDataModel.getRowData());
        return "editAccount";
    }

    public String startChangingPassword() {
        return accountSession.startChangingPassword(accountsDataModel.getRowData());
    }

}
