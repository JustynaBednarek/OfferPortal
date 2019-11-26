package pl.lodz.p.spjava.offerportalforsuppliers.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 *
 * @author Justyna Bednarek
 */
@MappedSuperclass
public abstract class AbstractEntity {

    protected static final long serialVersionUID = 1L;

    protected abstract Object getId();

    protected abstract Object getBusinessKey();

    @Version
    @Column(name = "version")
    private int version;

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this.getClass().isAssignableFrom(obj.getClass())) {
            return this.getBusinessKey().equals(((AbstractEntity) obj).getBusinessKey());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "AbstractEntity{" + "version=" + version + '}';
    }

    @Override
    public int hashCode() {
        return getBusinessKey().hashCode();
    }

}
