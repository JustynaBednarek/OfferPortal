package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Justyna Bednarek
 */
@Entity
@Table(name = "DataAdministrator")
@DiscriminatorValue("ADMINISTRATOR")
@NamedQueries({
    @NamedQuery(name = "Administrator.findAll", query = "SELECT d FROM Administrator d")
    ,
    @NamedQuery(name = "Administrator.findByAlarmNumber", query = "SELECT d FROM Administrator d WHERE d.alarmNumber = :alarmNumber")
    ,
    @NamedQuery(name = "Administrator.findByLogin", query = "SELECT b FROM Administrator b WHERE b.login = :login")
})
public class Administrator extends Account implements Serializable {

    @NotNull
    @Size(max = 12, message = "{constraint.string.length.toolong}")
    @Column(name = "AlarmNumber", unique = true, nullable = false, length = 12)
    private String alarmNumber;

    public Administrator() {
    }

    public String getAlarmNumber() {
        return alarmNumber;
    }

    public void setAlarmNumber(String number) {
        this.alarmNumber = number;
    }
}
