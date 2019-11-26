package pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
import pl.lodz.p.spjava.offerportalforsuppliers.exception.InquiryException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Inquiry_;

/**
 *
 * @author Justyna Bednarek
 */
@Stateless
@LocalBean
@Interceptors(LoggingInterceptor.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class InquiryFacade extends AbstractFacade<Inquiry> {

    @PersistenceContext(unitName = "OfferPortalJavaDB_PU")
    private EntityManager entitymanager;

    public InquiryFacade() {
        super(Inquiry.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entitymanager;
    }

    @Override
    public void create(Inquiry entity) throws AppBaseException {
        try {
            super.create(entity);
            entitymanager.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw InquiryException.createWithDbCheckNumberInquiryKey(entity, ex);
            } else {
                throw ex;
            }
        }
    }

    public Inquiry findNumberInquiry(String numberInquiry) {
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Inquiry> query = cb.createQuery(Inquiry.class);
        Root<Inquiry> from = query.from(Inquiry.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Inquiry_.numberInquiry), numberInquiry));
        TypedQuery<Inquiry> tq = entitymanager.createQuery(query);       
        return tq.getSingleResult();
    }

    public List<Inquiry> matchInquirys(String numberInquiryPattern) {

        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Inquiry> query = cb.createQuery(Inquiry.class);
        Root<Inquiry> from = query.from(Inquiry.class);
        query = query.select(from);
        Predicate criteria = cb.conjunction();
        System.out.println("criteria ???" + numberInquiryPattern);
        if (null != numberInquiryPattern && !(numberInquiryPattern.isEmpty())) {
            System.out.println("criteria set");
            criteria = cb.and(criteria, cb.like(from.get(Inquiry_.numberInquiry), '%' + numberInquiryPattern + '%'));
        }
        query = query.where(criteria);
        TypedQuery<Inquiry> tq = entitymanager.createQuery(query);   
        return tq.getResultList();
    }

    public List<Inquiry> findInquirysNotComplete() {
        TypedQuery<Inquiry> tq = entitymanager.createNamedQuery("Inquiry.findNotComplete", Inquiry.class);
        return tq.getResultList();
    }

}
