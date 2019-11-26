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
import pl.lodz.p.spjava.offerportalforsuppliers.model.Supplier;

/**
 *
 * @author Justyna Bednarek 
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class SupplierFacade extends AbstractFacade<Supplier> {

    @PersistenceContext(unitName = "OfferPortalJavaDB_PU")
    private EntityManager entitymanager;

    @Override
    protected EntityManager getEntityManager() {
        return entitymanager;
    }

    public SupplierFacade() {
        super(Supplier.class);
    }

    public Supplier findSupplierByLogin(String login) {
        TypedQuery<Supplier> tq = getEntityManager().createNamedQuery("Supplier.findByLogin", Supplier.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
    }
    
}
