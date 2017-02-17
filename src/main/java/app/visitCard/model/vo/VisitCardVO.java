package app.visitCard.model.vo;

import app.person.model.vo.PersonVO;
import org.joda.time.DateTime;

import java.io.Serializable;

public class VisitCardVO  implements Serializable {

    private VisitCardVO() {
    }

    private Long id;

    private PersonVO person;

    private PersonVO contactPerson;

    private DateTime dateFrom;

    private DateTime dateTo;

    private Boolean active;

    private  DateTime createdDate;

    private String createdBy;

    private String number;

    public static VisitCardVO create() {
        return new VisitCardVO();
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
