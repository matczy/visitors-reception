package app.registerVisitor.model;

import app.login.AbstractAuditingEntity;
import app.person.model.entity.Person;
import app.visitCard.model.VisitCard;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "REGISTER_VISITOR")
@Indexed
public class RegisterVisitor extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
//    @Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.YES, store = Store.NO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION", nullable = false)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private DirectionType direction;

    @OneToOne(targetEntity = Person.class)
    @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID")
    @IndexedEmbedded
    private Person person;

    @OneToOne(targetEntity = Person.class)
    @JoinColumn(name = "CONTACT_PERSON_ID", referencedColumnName = "ID")
    @IndexedEmbedded
    private Person contactPerson;


    @OneToOne(targetEntity = VisitCard.class)
    @JoinColumn(name = "VISIT_CARD_ID", referencedColumnName = "ID")
    @IndexedEmbedded
    private VisitCard visitCard;


//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "entry_date", nullable = true)
//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
//    @DateBridge(resolution = Resolution.YEAR)
    private DateTime entryDate;


//    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name = "exit_date", nullable = true)
//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
//    @DateBridge(resolution = Resolution.YEAR)
    private DateTime exitDate;

    private String comment;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public DirectionType getDirection() {
        return direction;
    }

    public void setDirection(DirectionType direction) {
        this.direction = direction;
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

    public VisitCard getVisitCard() {
        return visitCard;
    }

    public void setVisitCard(VisitCard visitCard) {
        this.visitCard = visitCard;
    }

    public DateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(DateTime entryDate) {
        this.entryDate = entryDate;
    }

    public DateTime getExitDate() {
        return exitDate;
    }

    public void setExitDate(DateTime exitDate) {
        this.exitDate = exitDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
