"use strict";
(function () {
    angular.module('visitorApp').controller('PersonsListController', PersonsListController);

    PersonsListController.$inject = ['personService','messageService','dialogFactory'];
    function PersonsListController(personService,messageService,dialogFactory) {
        var vm = this;
        angular.merge(vm, {
            model:{
                persons:[],
                paging:{
                pageno: 1,
                total_count: 0,
                itemsPerPage: 7
            }
            },

            searcher:{searchText:''},
            typeShowData : 'ALL',
            
            service: {
                callServerForData:callServerForData,
                deletePerson:deletePerson,
                changeConfirmEmailBehaviour:changeConfirmEmailBehaviour,
                showDetails:showDetails
            }
    });

        function callServerForData(pageno) {
            personService.filterData(vm.searcher.searchText, vm.model.typeShowData, pageno-1, vm.model.paging.itemsPerPage).then(function (results) {
                vm.model.persons = results.content;
                vm.model.paging.total_count = results.totalElements;
            });
        }
        

        function deletePerson(person){
            personService.deletePerson(person).then(function(){
                messageService.showInfoMessage("Poprawne usunięcie osoby :"+person.surname+' '+person.name);
                vm.model.persons = personService.findAll();
            });
        }

        function changeConfirmEmailBehaviour(value, personId){
            personService.changeConfirmEmailBehaviour(value,personId).then(function(data){
                messageService.showInfoMessage("Poprawne edycja configuracji powiadomien email");

            });
        }

        function showDetails(visitor){
            dialogFactory.detailsVisitor(visitor)
        }
        callServerForData(vm.model.paging.pageno);

    }
})();