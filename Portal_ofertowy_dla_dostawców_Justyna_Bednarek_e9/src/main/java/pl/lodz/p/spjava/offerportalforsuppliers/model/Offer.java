package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Cacheable;
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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Justyna Bednarek
 */
@Entity
@Cacheable(false)
@Table(name = "Offer", uniqueConstraints = @UniqueConstraint(columnNames = {"Supplier_ID", "Inquiry_ID"}))
@TableGenerator(name = "OfferIdGen", table = "GENERATOR", pkColumnName = "ENTITY_NAME", valueColumnName = "ID_RANGE", pkColumnValue = "Offer", initialValue = 100)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorValue("OFFER")
@NamedQueries({
    @NamedQuery(name = "Offer.findOnlyAcceptedOffers", query = "SELECT o FROM Offer o WHERE o.accepted=true")
    ,
    @NamedQuery(name = "Offer.findOnlyMyAcceptedOffers", query = "SELECT o FROM Offer o WHERE o.accepted=true AND o.supplier.login=:login")
    ,
    @NamedQuery(name = "Offer.findAll", query = "SELECT o FROM Offer o")
    ,
    @NamedQuery(name = "Offer.findForSupplier", query = "SELECT o FROM Offer o WHERE o.supplier.login=:login")
    ,
    @NamedQuery(name = "Offer.findForSupplierAndInquiry", query = "SELECT o FROM Offer o WHERE o.supplier.login=:login AND o.inquiry.numberInquiry=:numberInquiry")
    ,
    @NamedQuery(name = "Offer.findByNumberOffer", query = "SELECT o FROM Offer o WHERE o.numberOffer=:numberOffer")
    ,
    @NamedQuery(name = "Offer.sortOffersByPriceForInquiry", query = "SELECT o FROM Offer o WHERE o.inquiry=:inquiry ORDER BY o.unitPrice ASC")})
public class Offer extends AbstractEntity implements Serializable {

    public Offer() {

    }

    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "OfferIdGen")
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "numberOffer", nullable = false, unique = true, updatable = false)
    private String numberOffer;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Inquiry inquiry;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "dateOffer", table = "Offer", nullable = false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOffer;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "authorOffer", nullable = false, unique = false, updatable = false)
    private String authorOffer;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "nameItem", nullable = false, unique = false, updatable = false)
    private String nameItem;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "quantity", nullable = false, unique = false, updatable = false)
    private int quantity;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "measureUnit", nullable = false, unique = false, updatable = false)
    private String measureUnit;

    @NotNull(message = "{constraint.notnull}")
    @Column(name = "unitPrice", nullable = false, unique = false, updatable = false)
    private double unitPrice;

    @Column(name = "accepted", nullable = false)
    private boolean accepted;

    @JoinColumn(nullable = true)
    @ManyToOne
    private Account whoAccepted;

    public Account getWhoAccepted() {
        return whoAccepted;
    }

    public void setWhoAccepted(Account whoAccepted) {
        this.whoAccepted = whoAccepted;
    }

    @JoinColumn(nullable = true)
    @ManyToOne
    private Buyer buyer;

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Inquiry getInquiry() {
        return inquiry;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getNumberOffer() {
        return numberOffer;
    }

    public void setNumberOffer(String numberOffer) {
        this.numberOffer = numberOffer;
    }

    public Date getDateOffer() {
        return dateOffer;
    }

    public void setDateOffer(Date dateOffer) {
        this.dateOffer = dateOffer;
    }

    public String getAuthorOffer() {
        return authorOffer;
    }

    public void setAuthorOffer(String authorOffer) {
        this.authorOffer = authorOffer;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    protected Object getBusinessKey() {
        return numberOffer;
    }

   }
