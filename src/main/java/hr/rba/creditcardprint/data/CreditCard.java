package hr.rba.creditcardprint.data;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

/**
 * Entity in DB represents Credit Card.
 */
@Entity()
public class CreditCard  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String oib;
    private Status status;

    /**
     * Credit card constructor.
     */
    public CreditCard() { }

    public Long getId() {
        return id;
    }

    public void setId(final Long entityId) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String surname) {
        this.lastName = surname;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(final String identification) {
        this.oib = identification;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(final Status creditCardStatus) {
        this.status = creditCardStatus;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreditCard that = (CreditCard) o;
        return Objects.equals(id, that.id)
                && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(oib, that.oib)
                && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, oib, status);
    }

    @Override
    public String toString() {
        return "CreditCard{"
                + "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", oib='" + oib + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
