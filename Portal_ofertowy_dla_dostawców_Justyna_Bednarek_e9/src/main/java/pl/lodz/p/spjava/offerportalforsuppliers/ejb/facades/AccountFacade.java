package pl.lodz.p.spjava.offerportalforsuppliers.ejb.facades;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor.PerformanceInterceptor;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AccountException;
import pl.lodz.p.spjava.offerportalforsuppliers.exception.AppBaseException;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account;
import pl.lodz.p.spjava.offerportalforsuppliers.model.Account_;

/**
 *
 * @author Justyna Bednarek
 */
@Stateless
@LocalBean
@Interceptors({LoggingInterceptor.class, PerformanceInterceptor.class})
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class AccountFacade extends AbstractFacade<Account> {

    @PersistenceContext(unitName = "OfferPortalJavaDB_PU")
    private EntityManager entitymanager;

    @Override
    protected EntityManager getEntityManager() {
        return entitymanager;
    }

    public AccountFacade() {
        super(Account.class);
    }

    @Override
    public void create(Account entity) throws AppBaseException {
        try {
            super.create(entity);
            entitymanager.flush();
        } catch (PersistenceException ex) {
            if (ex.getCause() instanceof DatabaseException && ex.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                throw AccountException.createWithDbCheckConstraintKey(entity, ex);
            } else {
                throw ex;
            }
        }
    }

    public Account findLogin(String login) {
        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        query = query.where(cb.equal(from.get(Account_.login), login));
        TypedQuery<Account> tq = entitymanager.createQuery(query);

        return tq.getSingleResult();
    }

    public List<Account> matchAccounts(String loginPattern, String nameCompanyPattern, String taxNumberCompanyPattern, String namePattern, String surnamePattern,
            String emailPattern) {

        CriteriaBuilder cb = entitymanager.getCriteriaBuilder();
        CriteriaQuery<Account> query = cb.createQuery(Account.class);
        Root<Account> from = query.from(Account.class);
        query = query.select(from);
        Predicate criteria = cb.conjunction();
        if (null != loginPattern && !(loginPattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.login), '%' + loginPattern + '%'));
        }
        if (null != nameCompanyPattern && !(nameCompanyPattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.nameCompany), '%' + nameCompanyPattern + '%'));
        }
        if (null != taxNumberCompanyPattern && !(taxNumberCompanyPattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.taxNumberCompany), '%' + taxNumberCompanyPattern + '%'));
        }
        if (null != namePattern && !(namePattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.name), '%' + namePattern + '%'));
        }
        if (null != surnamePattern && !(surnamePattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.surname), '%' + surnamePattern + '%'));
        }
        if (null != emailPattern && !(emailPattern.isEmpty())) {
            criteria = cb.and(criteria, cb.like(from.get(Account_.email), '%' + emailPattern + '%'));
        }

        query = query.where(criteria);
        TypedQuery<Account> tq = entitymanager.createQuery(query);
        return tq.getResultList();
    }

    @Override
    public void edit(Account account) throws AppBaseException {
        try {
            getEntityManager().merge(account);
            getEntityManager().flush();
        } catch (OptimisticLockException oe) {
            throw AccountException.createWithAccountOptimisticLockKey(account, oe);
        }
    }
}
