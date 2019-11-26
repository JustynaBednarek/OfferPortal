package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Justyna Bednarek
 */
@Entity
@Table(name = "DataSupplier")
@DiscriminatorValue("SUPPLIER")
@NamedQueries({
    @NamedQuery(name = "Supplier.findAll", query = "SELECT d FROM Supplier d")
    ,
    @NamedQuery(name = "Supplier.findByLogin", query = "SELECT d FROM Supplier d WHERE d.login = :login")})
public class Supplier extends Account implements Serializable {

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(name = "addressCompany", unique = false, nullable = false, length = 64)
    private String addressCompany;

    public Supplier() {
    }

    public String getAddressCompany() {
        return addressCompany;
    }

    public void setAddressCompany(String addressCompany) {
        this.addressCompany = addressCompany;
    }

    @Override
    public String toString() {
        return "Supplier{" + ", addressCompany=" + addressCompany + '}';
    }

    @OneToMany(mappedBy = "supplier")
    private final List<Offer> offers = new ArrayList<>();

    public List<Offer> getOffers() {
        return offers;
    }

}
