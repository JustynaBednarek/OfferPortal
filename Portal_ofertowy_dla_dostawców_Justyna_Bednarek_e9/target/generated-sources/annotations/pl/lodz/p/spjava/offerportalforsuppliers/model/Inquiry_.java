package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Buyer;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-21T17:43:19")
@StaticMetamodel(Inquiry.class)
public class Inquiry_ extends AbstractEntity_ {

    public static volatile ListAttribute<Inquiry, Offer> offers;
    public static volatile SingularAttribute<Inquiry, String> numberInquiry;
    public static volatile SingularAttribute<Inquiry, String> deliveryAddress;
    public static volatile SingularAttribute<Inquiry, String> currency;
    public static volatile SingularAttribute<Inquiry, Account> whoCompleted;
    public static volatile SingularAttribute<Inquiry, Long> id;
    public static volatile SingularAttribute<Inquiry, Date> dateProcedureStarted;
    public static volatile SingularAttribute<Inquiry, Boolean> completed;
    public static volatile SingularAttribute<Inquiry, Date> dateProcedureEnd;
    public static volatile SingularAttribute<Inquiry, Buyer> buyer;
    public static volatile SingularAttribute<Inquiry, String> descriptionInquiry;

}