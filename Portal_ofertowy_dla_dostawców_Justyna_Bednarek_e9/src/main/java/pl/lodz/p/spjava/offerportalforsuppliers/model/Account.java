package pl.lodz.p.spjava.offerportalforsuppliers.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Justyna Bednarek
 */
@Entity
@Table(name = "Account")
@SecondaryTable(name = "PersonalData")
@TableGenerator(name = "AccountIdGen", table = "GENERATOR", pkColumnName = "ENTITY_NAME", valueColumnName = "ID_RANGE", pkColumnValue = "Account", initialValue = 100)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
@DiscriminatorValue("ACCOUNT")
public class Account extends AbstractEntity implements Serializable {

    public Account() {
    }

    @Override
    protected Object getBusinessKey() {
        return login;
    }
    @Id
    @Column(name = "id", updatable = false)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "AccountIdGen")
    private Long id;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(name = "login", length = 32, nullable = false, unique = true, updatable = false)
    private String login;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, message = "{constraint.string.length.tooshort}")
    @Column(name = "password", length = 256, nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "type", updatable = false)
    private String type;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 2, max = 64, message = "{constraint.string.length.notinrange}")
    @Column(name = "nameCompany", table = "PersonalData", length = 64, nullable = false)
    private String nameCompany;

    @NotNull(message = "{constraint.notnull}")
    @Pattern(regexp = "^[0-9]{10}$", message = "{constraint.string.incorrectNIP}")
    @Column(name = "taxNumberCompany", table = "PersonalData", nullable = false, length = 10)
    private String taxNumberCompany;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(name = "name", table = "PersonalData", length = 32, nullable = false)
    private String name;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 3, max = 32, message = "{constraint.string.length.notinrange}")
    @Column(name = "surname", table = "PersonalData", length = 32, nullable = false)
    private String surname;

    @NotNull(message = "{constraint.notnull}")
    @Size(min = 6, max = 64, message = "{constraint.string.length.notinrange}")
    @Pattern(regexp = "^[_A-z0-9-]+(\\.[_A-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$", message = "{constraint.string.incorrectemail}")
    @Column(name = "email", table = "PersonalData", length = 64, nullable = false)
    private String email;

    @NotNull(message = "{constraint.notnull}")
    @Size(max = 12, message = "{constraint.string.length.toolong}")
    @Column(name = "phoneNumber", table = "PersonalData", length = 12, nullable = true)
    private String phoneNumber;

    @Override
    public Long getId() {
        return id;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getTaxNumberCompany() {
        return taxNumberCompany;
    }

    public void setTaxNumberCompany(String taxNumberCompany) {
        this.taxNumberCompany = taxNumberCompany;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getType() {
        return type;
    }

}
