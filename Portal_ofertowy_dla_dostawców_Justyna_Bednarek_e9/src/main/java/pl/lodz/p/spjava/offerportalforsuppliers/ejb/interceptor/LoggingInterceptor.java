package pl.lodz.p.spjava.offerportalforsuppliers.ejb.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 *
 * @author Justyna Bednarek
 */
public class LoggingInterceptor {

    @Resource
    private SessionContext sessionContext;

    @AroundInvoke
    public Object additionalInvokeForMethod(InvocationContext invocation) throws Exception {
        StringBuilder sb = new StringBuilder("Business method processing  ")
                .append(invocation.getTarget().getClass().getName()).append('.')
                .append(invocation.getMethod().getName());
        sb.append(" with identity: ").append(sessionContext.getCallerPrincipal().getName());
        try {
            Object[] parameters = invocation.getParameters();
            if (null != parameters) {
                for (Object param : parameters) {
                    if (param != null) {
                        sb.append(" with parameter ").append(param.getClass().getName()).append('=').append(param);
                    } else {
                        sb.append(" no value (null)");
                    }
                }
            }

            Object result = invocation.proceed();

            if (result != null) {
                sb.append(" returned ").append(result.getClass().getName()).append('=').append(result);
            } else {
                sb.append(" null returned");
            }

            return result;
        } catch (Exception ex) {
            sb.append(" an exception occurred: ").append(ex);
            throw ex; //resubmit an exception
        } finally {
            Logger.getGlobal().log(Level.INFO, sb.toString());
        }
    }
}
