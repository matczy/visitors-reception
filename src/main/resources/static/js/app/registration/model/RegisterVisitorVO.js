
var RegisterVisitorVO = {
    init: function (id,entryDate,exitDate, person,contactPerson, visitCard, comment) {
        this.id = id || null;
        this.entryDate = entryDate || '';
        this.exitDate = exitDate || '';
        this.person = person || '';
        this.contactPerson = contactPerson || '';
        this.visitCard = visitCard||'';
        this.comment = comment||'';
    }
};