"use strict";
(function () {
    angular.module('visitorApp').controller('VisitCardDialogController', VisitCardDialogController);
    //, 'personsType', 'companies'
    VisitCardDialogController.$inject = ['$uibModalInstance', 'personService', 'visitCardService','messageService','person', 'contactPerson'];
    function VisitCardDialogController($uibModalInstance,personService, visitCardService,messageService,person,contactPerson) {
        var vm = this;
        var PERSON_TITLE = "Osoba";

        angular.merge(vm, {
            model: {
                title: PERSON_TITLE,
                visitCard:{dateFrom: new Date(), dateTo: new Date(),person:person||'', contactPerson:contactPerson||''},

                dateOptions : {
                    minDate: new Date()
                }

            },

            service: {

                searchContactPersonsSuggestions:searchContactPersonsSuggestions,
                saveOrUpdate: saveOrUpdate,
                cancel: cancel
            }

        });



        function searchContactPersonsSuggestions(text) {
            return personService.searchContactPersonsSuggestions(text);
        }

        function cancel() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveOrUpdate(visitCard) {
            if(!visitCard.contactPerson){
                visitCard.contactPerson =null;
            }
            visitCardService.printPeriodVisitCard(visitCard).then(function(response){
                var _visitCard = response;
                messageService.showInfoMessage("Poprawne wydrukowanie wizytówki dla: "+_visitCard.person.name+' '+_visitCard.person.surname);
                $uibModalInstance.close(visitCard);

            });

        }


    }
})();