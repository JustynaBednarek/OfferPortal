package pl.lodz.p.spjava.offerportalforsuppliers.web.utils;

import java.security.Principal;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Justyna Bednarek
 */
public class ContextUtils {

    /**
     * Creates a new instance of AttributesUtils
     */
    public ContextUtils() {
    }

    /**
     * Returns FacesContext object - FacesServlet servlet context
     *
     * @return
     */
    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     * Searches for an attribute with the given name in the context of the
     * application
     *
     * @param attributeName
     * @return
     */
    public static Object getApplicationAttribute(String attributeName) {
        return getContext().getApplicationMap().get(attributeName);
    }

    /**
     * Searches for an attribute with the given name in the context of the
     * session
     *
     * @param attributeName
     * @return
     */
    public static Object getSessionAttribute(String attributeName) {
        return getContext().getSessionMap().get(attributeName);
    }

    /**
     * Searches for an attribute with the given name in the context of the
     * request
     *
     * @param attributeName
     * @return
     */
    public static Object getRequestAttribute(String attributeName) {
        return getContext().getRequestMap().get(attributeName);
    }

    /**
     * Searches for an initialization parameter with the given name
     *
     * @param paramName
     * @return
     */
    public static String getContextParameter(String paramName) {
        return getContext().getInitParameter(paramName);
    }

    /**
     * Closes the current session
     */
    public static void invalidateSession() {
        ((HttpSession) getContext().getSession(true)).invalidate();
    }

    /**
     * Returns the ID of the current session
     *
     * @return
     */
    public static String getSessionID() {
        HttpSession session = (HttpSession) getContext().getSession(true);
        return session.getId();
    }

    /**
     * Returns the name of the logged in user
     *
     * @return
     */
    public static String getUserName() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return (null == p ? "!NO AUTHENTICATION!" : p.getName());
    }

    /**
     * Returns a resource (ResourceBundle) with the path pointed to by the
     * parameter resourceBundle.path
     *
     * @return
     */
    public static ResourceBundle getDefaultBundle() {
        String bundlePath = getContextParameter("resourceBundle.path");
        if (null == bundlePath) {
            return null;
        } else {
            return ResourceBundle.getBundle(bundlePath, FacesContext.getCurrentInstance().getViewRoot().getLocale());
        }
    }

    public static boolean isInternationalizationKeyExist(String key) {
        try {
            return ContextUtils.getDefaultBundle().getString(key) != null && !"".equals(ContextUtils.getDefaultBundle().getString(key));
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public static void emitInternationalizedMessage(String id, String key) {
        FacesMessage msg = new FacesMessage(ContextUtils.getDefaultBundle().getString(key));
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(id, msg);
    }

    public static void emitSuccessMessage(String id) {
        emitInternationalizedMessage(id, "general.success.message");
    }

    public static void emitErrorMessage(String id) {
        emitInternationalizedMessage(id, "error.inquiry.already.completed");
    }

}
