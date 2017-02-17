"use strict";
(function () {
    angular.module('visitorApp').controller('PersonCreatorDialogController', PersonCreatorDialogController);
    //, 'personsType', 'companies'
    PersonCreatorDialogController.$inject = ['$uibModalInstance', 'personService','personVOFactory','person','personsType', 'companies','adminRole'];
    function PersonCreatorDialogController($uibModalInstance,personService,personVOFactory,person,personsType, companies,adminRole) {
        var vm = this;
        var PERSON_TITLE = "Osoba";

        angular.merge(vm, {
            model: {
                title: PERSON_TITLE,
                isPersonTemplate: true,
                isCompanyTemplate: false,
                person: person || personVOFactory.createEmpty(),
                personsType: personsType,
                companies: companies
            },

            service: {
                saveOrUpdate: saveOrUpdate,
                cancel: cancel
            },
            privileges:{
                adminRole:adminRole
            }

        });

        function cancel() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveOrUpdate(person) {
            if(!person.type || !person.type.id){
                person.type=  {id: 2, name: "Gość"}
            }
            personService.save(person).then(function (entry) {
                //var _message = "Poprawne utworzenie osoby:" + person.value.surname + " " + person.value.name;
                //messageService.showInfoMessage(_message);

                $uibModalInstance.close(entry.person);
            });
        }


    }
})();