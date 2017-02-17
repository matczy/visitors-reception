package app.person.model.entity;


import app.company.model.Company;
import app.login.AbstractAuditingEntity;
import app.person.model.Enums.PersonType;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Indexed
public class Person extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    @Column(name = "SURNAME", nullable = false)
    private String surname;

    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
    @SortableField
    @Column(name = "NAME", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = true)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private PersonType type;

    @IndexedEmbedded
    @ManyToOne(optional = true, targetEntity = Company.class, cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID", referencedColumnName = "ID")
    private Company company;

    @Column(name = "PHONE", nullable = true)
    private String phone;

    @Column(name = "EMAI", nullable = true)
    private String email;

    @Column(name = "DOCUMENT_IDENTIFIER", nullable = true)
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO)
    private String documentIdentifier;

    @Column(name = "ACCESS", nullable = true)
    private Boolean access;

    @Column(nullable = false)
    private boolean confirmEmailAvailable  = true;


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

    public PersonType getType() {
        return type;
    }

    public void setType(PersonType type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getAccess() {
        return access;
    }

    public void setAccess(Boolean access) {
        this.access = access;
    }

    public String getDocumentIdentifier() {
        return documentIdentifier;
    }

    public void setDocumentIdentifier(String documentIdentifier) {
        this.documentIdentifier = documentIdentifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getConfirmEmailAvailable() {
        return confirmEmailAvailable;
    }

    public void setConfirmEmailAvailable(boolean confirmEmailAvailable) {
        this.confirmEmailAvailable = confirmEmailAvailable;
    }
}
