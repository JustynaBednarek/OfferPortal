package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Justyna Bednarek
 */
@Entity
@Table(name = "Inquiry")
@TableGenerator(name = "InquiryIdGen", table = "GENERATOR", pkColumnName = "ENTITY_NAME", valueColumnName = "ID_RANGE", pkColumnValue = "Inquiry", initialValue = 100)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("INQUIRY")
@NamedQueries({
    @NamedQuery(name = "Inquiry.findNotComplete", query = "SELECT d FROM Inquiry d WHERE d.completed=false")
    ,
    @NamedQuery(name = "Inquiry.findAll", query = "SELECT d FROM Inquiry d")
    ,
    @NamedQuery(name = "Inquiry.findByNumberInquiry", query = "SELECT d FROM Inquiry d WHERE d.numberInquiry = :numberInquiry")
})
public class Inquiry extends AbstractEntity implements Serializable {

    @OneToMany(mappedBy = "inquiry")
    private final List<Offer> offers = new ArrayList<>();

    public Inquiry() {

    }

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "InquiryIdGen")
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "numberInquiry", nullable = false, unique = true, updatable = false)
    private String numberInquiry;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "currency", table = "Inquiry", nullable = false)
    private String currency;

    @ManyToOne
    private Buyer buyer;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "descriptionInquiry", table = "Inquiry", nullable = false)
    private String descriptionInquiry;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "dateProcedureStarted", table = "Inquiry", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateProcedureStarted;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "dateProcedureEnd", table = "Inquiry", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateProcedureEnd;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "deliveryAddress", table = "Inquiry", nullable = false)
    private String deliveryAddress;

    @Column(name = "completed", nullable = false)
    private boolean completed;

    @JoinColumn(nullable = true)
    @ManyToOne
    private Account whoCompleted;

    public Account getWhoCompleted() {
        return whoCompleted;
    }

    public void setWhoCompleted(Account whoCompleted) {
        this.whoCompleted = whoCompleted;
    }

    public boolean isUptoDate() {
        return new Date().before(dateProcedureEnd);
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescriptionInquiry() {
        return descriptionInquiry;
    }

    public void setDescriptionInquiry(String descriptionInquiry) {
        this.descriptionInquiry = descriptionInquiry;
    }

    public Date getDateProcedureStarted() {
        return dateProcedureStarted;
    }

    public void setDateProcedureStarted(Date dateProcedureStarted) {
        this.dateProcedureStarted = dateProcedureStarted;
    }

    public Date getDateProcedureEnd() {
        return dateProcedureEnd;
    }

    public void setDateProcedureEnd(Date dateProcedureEnd) {
        this.dateProcedureEnd = dateProcedureEnd;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getNumberInquiry() {
        return numberInquiry;
    }

    public void setNumberInquiry(String numberInquiry) {
        this.numberInquiry = numberInquiry;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return numberInquiry;
    }

    @Override
    public String toString() {
        return "Inquiry{" + "offers=" + offers + ", id=" + id + ", numberInquiry=" + numberInquiry + ", currency=" + currency + ", buyer=" + buyer + ", descriptionInquiry=" + descriptionInquiry + ", dateProcedureStarted=" + dateProcedureStarted + ", dateProcedureEnd=" + dateProcedureEnd + ", deliveryAddress=" + deliveryAddress + ", completed=" + completed + ", administrator=" + whoCompleted + '}';
    }

}
