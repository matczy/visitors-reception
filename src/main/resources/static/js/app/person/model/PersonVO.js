
var PersonVO = {
    init: function (id, surname, name, company, phone,email, documentIdentifier, type,confirmEmailAvailable) {
        this.id = id || null;
        this.surname = surname || '';
        this.name = name || '';
        this.company = company || '';
        this.phone = phone || '';
        this.documentIdentifier = documentIdentifier || '';
        this.type = type || '';
        this.email = email || '';
        this.confirmEmailAvailable = confirmEmailAvailable;

    }
};