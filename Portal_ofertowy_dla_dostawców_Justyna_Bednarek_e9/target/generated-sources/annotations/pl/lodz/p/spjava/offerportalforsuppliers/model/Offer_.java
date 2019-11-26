package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Buyer;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Supplier;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-21T17:43:19")
@StaticMetamodel(Offer.class)
public class Offer_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Offer, Double> unitPrice;
    public static volatile SingularAttribute<Offer, String> numberOffer;
    public static volatile SingularAttribute<Offer, Integer> quantity;
    public static volatile SingularAttribute<Offer, Boolean> accepted;
    public static volatile SingularAttribute<Offer, String> measureUnit;
    public static volatile SingularAttribute<Offer, String> authorOffer;
    public static volatile SingularAttribute<Offer, Date> dateOffer;
    public static volatile SingularAttribute<Offer, String> nameItem;
    public static volatile SingularAttribute<Offer, Buyer> buyer;
    public static volatile SingularAttribute<Offer, Inquiry> inquiry;
    public static volatile SingularAttribute<Offer, Supplier> supplier;
    public static volatile SingularAttribute<Offer, Long> id;
    public static volatile SingularAttribute<Offer, Account> whoAccepted;

}