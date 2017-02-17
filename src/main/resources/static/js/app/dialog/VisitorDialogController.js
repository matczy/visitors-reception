"use strict";
(function () {
    angular.module('visitorApp').controller('VisitorDialogController', VisitorDialogController);
    //, 'personsType', 'companies'
    VisitorDialogController.$inject = ['$uibModalInstance', 'personService', 'companyService','person','contactPerson', 'adminRole'];
    function VisitorDialogController($uibModalInstance,personService, companyService,person,contactPerson, adminRole) {
        var vm = this;
        var TITLE = "Edytuj";

        angular.merge(vm, {
            model: {
                title: TITLE,
                person: person || '',
                contactPerson: contactPerson
            },
            searcher:{
                searchContactPersonsSuggestions:searchContactPersonsSuggestions,
                searchCompanySuggestions:searchCompanySuggestions
            },
            service: {

                saveOrUpdate: saveOrUpdate,
                cancel: cancel
            },
            privileges:{
                adminRole:adminRole
            }

        });

        function searchContactPersonsSuggestions(text) {
            return personService.searchContactPersonsSuggestions(text);
        }
        function searchCompanySuggestions(text) {
            return companyService.findAll(text).then(function(companies){
                return $filter('filter')(companies, text)
            });
        }
        function cancel() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveOrUpdate(person, contactPerson) {
               $uibModalInstance.close({person:person, contactPerson:contactPerson});
        }


    }
})();