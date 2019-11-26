package pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Buyer;

/**
 *
 * @author Justyna Bednarek 
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BuyerFacade extends AbstractFacade<Buyer> {

    @PersistenceContext(unitName = "OfferPortalJavaDB_PU")
    private EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public BuyerFacade() {
        super(Buyer.class);
    }

    public Buyer findBuyerByLogin(String login) {
        TypedQuery<Buyer> tq = getEntityManager().createNamedQuery("Buyer.findByLogin", Buyer.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
    }

}
