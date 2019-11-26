package pl.lodz.p.spjava.offerportalforsuppliers.ejb.endpoints;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.SessionContext;

/**
 *
 * @author Justyna Bednarek 
 */
abstract public class AbstractEndpoint {

    protected static final Logger LOGGER = Logger.getGlobal();

    @Resource
    protected SessionContext sctx;

    private String transactionId;

    public void afterBegin() throws EJBException {
        transactionId = Long.toString(System.currentTimeMillis())
                + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        LOGGER.log(Level.INFO, "Transaction TXid={0} started on {1}, identity: {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }

    public void beforeCompletion() throws EJBException {
        LOGGER.log(Level.INFO, "Transaction TXid={0} before approval in {1}, identity {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName()});
    }

    public void afterCompletion(boolean committed) throws EJBException {
        LOGGER.log(Level.INFO, "Transaction TXid={0} finished in {1} via {3}, identity {2}",
                new Object[]{transactionId, this.getClass().getName(), sctx.getCallerPrincipal().getName(),
                    committed ? "APPROVAL" : "CANCELLATION"});
    }
    
}
