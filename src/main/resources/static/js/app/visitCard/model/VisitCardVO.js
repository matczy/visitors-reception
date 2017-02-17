/**
 * Created by Dachu on 2015-10-08.
 */
var VisitCardVO = {
    init: function (id,person, contactPerson, dateFrom, dateTo, active, createdDate, createdBy, number) {
        this.id = id;
        this.person = person;
        this.contactPerson = contactPerson;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.active = active;
        this.createdDate = createdDate||'';
        this.createdBy = createdBy||'';
        this.number = number;

    }
};