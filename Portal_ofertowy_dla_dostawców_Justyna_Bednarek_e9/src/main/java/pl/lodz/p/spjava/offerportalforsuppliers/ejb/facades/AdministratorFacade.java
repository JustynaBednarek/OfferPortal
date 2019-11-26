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
import pl.lodz.p.spjava.offerportalforsuppliers.model.Administrator;

/**
 *
 * @author Justyna Bednarek
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AdministratorFacade extends AbstractFacade<Administrator> {

    @PersistenceContext(unitName = "OfferPortalJavaDB_PU")
    private EntityManager entitymanager;

    @Override
    protected EntityManager getEntityManager() {
        return entitymanager;
    }

    public AdministratorFacade() {
        super(Administrator.class);
    }

    public Administrator findAdministratorByLogin(String login) {
        TypedQuery<Administrator> tq = getEntityManager().createNamedQuery("Administrator.findByLogin", Administrator.class);
        tq.setParameter("login", login);
        return tq.getSingleResult();
    }

}
