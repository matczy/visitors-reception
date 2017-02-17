package app.person.model.vo;


import app.common.vo.IdNameVO;
import app.company.model.vo.CompanyVO;

import java.io.Serializable;


public final class PersonVO implements Serializable {


    public static PersonVO create(){
        return new PersonVO();
    }
    private PersonVO() {
    }
    private Long id;

    private String surname;

    private String name;

    private IdNameVO type;

    private CompanyVO company;

    private String phone;

    private String email;

    private String documentIdentifier;

    private Boolean access;

    private boolean confirmEmailAvailable;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyVO getCompany() {
        return company;
    }

    public void setCompany(CompanyVO company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public IdNameVO getType() {
        return type;
    }

    public void setType(IdNameVO type) {
        this.type = type;
    }

    public String getDocumentIdentifier() {
        return documentIdentifier;
    }

    public void setDocumentIdentifier(String documentIdentifier) {
        this.documentIdentifier = documentIdentifier;
    }

    public boolean getConfirmEmailAvailable() {
        return confirmEmailAvailable;
    }

    public void setConfirmEmailAvailable(boolean confirmEmailAvailable) {
        this.confirmEmailAvailable = confirmEmailAvailable;
    }
}
