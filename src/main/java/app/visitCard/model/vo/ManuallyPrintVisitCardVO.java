package app.visitCard.model.vo;

import app.person.model.vo.PersonVO;

import java.io.Serializable;

public class ManuallyPrintVisitCardVO implements Serializable {

    private ManuallyPrintVisitCardVO() {
    }

    private Long id;

    private PersonVO person;

    private PersonVO contactPerson;

    private Long registerVisitorId;

    private int numberOfDays;

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

    public Long getRegisterVisitorId() {
        return registerVisitorId;
    }

    public void setRegisterVisitorId(Long registerVisitorId) {
        this.registerVisitorId = registerVisitorId;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }
}
