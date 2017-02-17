package app.visitCard.model;

import app.login.AbstractAuditingEntity;
import app.person.model.entity.Person;
import org.hibernate.search.annotations.*;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//import org.hibernate.search.bridge.builtin.NumericEncodingDateBridge;
@Entity
@Indexed
public class VisitCard  extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
//    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    private Long id;
    @NotNull
    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String number;

    @ManyToOne(optional = true, targetEntity = Person.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    @IndexedEmbedded
    private Person person;

    @ManyToOne(optional = true, targetEntity = Person.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "CONTACT_PERSON_ID", referencedColumnName = "ID")
    @IndexedEmbedded
    private Person contactPerson;

    @NotNull
//    @Type(type = "org.joda.time.contrib.hibernate.PersistentLocalTimeAsMillisInteger")
    @Column(name = "date_from", nullable = true)
    //@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
//    @FieldBridge(impl = NumericEncodingDateBridge.class)
    private DateTime dateFrom;

    @NotNull
//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "date_to", nullable = true)
    //@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
//    @FieldBridge(impl = NumericEncodingDateBridge.class)
    private DateTime dateTo;

    private boolean manuallyDeactivated = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(Person contactPerson) {
        this.contactPerson = contactPerson;
    }

    public DateTime getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(DateTime dateFrom) {
        this.dateFrom = dateFrom;
    }

    public DateTime getDateTo() {
        return dateTo;
    }

    public void setDateTo(DateTime dateTo) {
        this.dateTo = dateTo;
    }

    public boolean isManuallyDeactivated() {
        return manuallyDeactivated;
    }

    public void setManuallyDeactivated(boolean manuallyDeactivated) {
        this.manuallyDeactivated = manuallyDeactivated;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
