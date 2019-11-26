package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Justyna Bednarek
 */
@Entity
@Table(name = "DataBuyer")
@DiscriminatorValue("BUYER")
@NamedQueries({
    @NamedQuery(name = "Buyer.findAll", query = "SELECT d FROM Buyer d")
    ,
    @NamedQuery(name = "Buyer.findByLogin", query = "SELECT b FROM Buyer b WHERE b.login = :login")
})
public class Buyer extends Account implements Serializable {

    @OneToMany(mappedBy = "buyer")
    private final List<Inquiry> inquiries = new ArrayList<>();


}
