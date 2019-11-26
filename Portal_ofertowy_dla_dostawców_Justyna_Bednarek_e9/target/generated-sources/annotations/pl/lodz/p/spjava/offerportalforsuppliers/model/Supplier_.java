package pl.lodz.p.spjava.offerportalforsuppliers.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-11-21T17:43:19")
@StaticMetamodel(Supplier.class)
public class Supplier_ extends Account_ {

    public static volatile ListAttribute<Supplier, Offer> offers;
    public static volatile SingularAttribute<Supplier, String> addressCompany;

}