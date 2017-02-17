package app.registerVisitor.model;

import app.person.model.vo.PersonVO;
import app.visitCard.model.vo.VisitCardVO;
import org.joda.time.DateTime;

/**
 * Created by matczy on 14.03.16.
 */
public class RegisterVisitorVO {
    private Long id;
    private PersonVO person;
    private PersonVO contactPerson;
    private DateTime entryDate;
    private DateTime exitDate;
    private DirectionType direction;
    private VisitCardVO visitCard;
    private String comment;
    private boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonVO getPerson() {
        return person;
    }

    public void setPerson(PersonVO person) {
        this.person = person;
    }

    public PersonVO getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(PersonVO contactPerson) {
        this.contactPerson = contactPerson;
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

    public DirectionType getDirection() {
        return direction;
    }

    public void setDirection(DirectionType direction) {
        this.direction = direction;
    }

    public VisitCardVO getVisitCard() {
        return visitCard;
    }

    public void setVisitCard(VisitCardVO visitCard) {
        this.visitCard = visitCard;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
