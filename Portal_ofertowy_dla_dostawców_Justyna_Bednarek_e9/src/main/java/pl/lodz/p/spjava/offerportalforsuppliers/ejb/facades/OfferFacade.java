package pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.OfferException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Offer_;

/**
 *
 * @author Justyna Bednarek
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class OfferFacade extends AbstractFacade<Offer> {

    @PersistenceContext(unitName = "OfferPortalJavaDB_PU")
    private EntityManager entitymanager;

    @Resource
    private SessionContext sctx;

    @Inject
    private SupplierFacade supplierFacade;

    public OfferFacade() {
        super(Offer.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entitymanager;
    }

    @Override
    public void create(Offer entity) throws AppBaseException {
        try {
            super.create(entity);
            entitymanager.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw OfferException.createWithDbCheckNumberOfferKey(entity, ex);
            } else {
                throw ex;
            }
        }
    }

    public Offer findNumberOffer(String numberOffer) {
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Offer> query = cb.createQuery(Offer.class);
        Root<Offer> from = query.from(Offer.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Offer_.numberOffer), numberOffer));
        TypedQuery<Offer> tq = entitymanager.createQuery(query);

        return tq.getSingleResult();
    }

    public List<Offer> matchOffer(String numberOfferPattern) {

        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Offer> query = cb.createQuery(Offer.class);
        Root<Offer> from = query.from(Offer.class);
        query = query.select(from);
        Predicate criteria = cb.conjunction();
        if (null != numberOfferPattern && !(numberOfferPattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Offer_.numberOffer), '%' + numberOfferPattern + '%'));
        }

        query = query.where(criteria);
        TypedQuery<Offer> tq = entitymanager.createQuery(query);
        return tq.getResultList();
    }

    public List<Offer> matchMyOffer(String numberOfferPattern) {

        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Offer> query = cb.createQuery(Offer.class);
        Root<Offer> from = query.from(Offer.class);
        query = query.select(from);
        Predicate criteria = cb.conjunction();
        criteria = cb.and(criteria, cb.equal(from.get(Offer_.supplier), supplierFacade.findSupplierByLogin(sctx.getCallerPrincipal().getName())));
        if (null != numberOfferPattern && !(numberOfferPattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Offer_.numberOffer), '%' + numberOfferPattern + '%'));
        }

        query = query.where(criteria);
        TypedQuery<Offer> tq = entitymanager.createQuery(query);
        return tq.getResultList();
    }

    public List<Offer> findMyOffers(String login) {
        TypedQuery<Offer> tq = entitymanager.createNamedQuery("Offer.findForSupplier", Offer.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }

    public List<Offer> findOnlyAcceptedOffers() {
        TypedQuery<Offer> tq = entitymanager.createNamedQuery("Offer.findOnlyAcceptedOffers", Offer.class);
        return tq.getResultList();
    }

    public List<Offer> findOnlyMyAcceptedOffers(String login) {
        TypedQuery<Offer> tq = entitymanager.createNamedQuery("Offer.findOnlyMyAcceptedOffers", Offer.class);
        tq.setParameter("login", login);
        return tq.getResultList();
    }

    public List<Offer> sortOffersByPriceForInquiry(Inquiry inquiry) {
        TypedQuery<Offer> tq = entitymanager.createNamedQuery("Offer.sortOffersByPriceForInquiry", Offer.class);
        tq.setParameter("inquiry", inquiry);
        return tq.getResultList();
    }

    public boolean checkMyOffersForInquiry(String login, String numberInquiry) {
        TypedQuery<Offer> tq = entitymanager.createNamedQuery("Offer.findForSupplierAndInquiry", Offer.class);
        tq.setParameter("login", login);
        tq.setParameter("numberInquiry", numberInquiry);
        return !(tq.getResultList().isEmpty());
    }

}
