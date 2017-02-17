"use strict";
(function () {
    angular.module('visitorApp').controller('HistoryRegistrationListController', HistoryRegistrationListController);

    HistoryRegistrationListController.$inject = ['registerVisitorService','messageService', 'dialogFactory'];
    function HistoryRegistrationListController(registerVisitorService,messageService, dialogFactory) {
        var vm = this;

        angular.merge(vm, {
            model: {
                historyData: [],
                typeShowData : 'ALL',
                paging:{
                    pageno: 1,
                    total_count: 0,
                    itemsPerPage: 7
                }
            },

            searcher: {searchText: ''},

            service: {
                registerExitAction: registerExitAction,
                //searchByText:searchByText,
                showDetails:showDetails,
                callServerForData:callServerForData
                
            }
        });


        function showDetails(data){
            dialogFactory.detailsRegisterVisitorHistory(data);
        }

        function registerExitAction(registerVisitor) {
            registerVisitorService.registerExitAction(registerVisitor).then(function (response) {
                messageService.showInfoMessage('Poprawne zarejestrowanie wyjścia ' + response.registerVisitor.person.name +" "+response.registerVisitor.person.surname);
                callServerForData(1);
            });
        }

        function callServerForData(pageno) {
            registerVisitorService.filterData(vm.searcher.searchText, vm.model.typeShowData, pageno-1, vm.model.paging.itemsPerPage).then(function (results) {
                vm.model.historyData = results.content;
                vm.model.paging.total_count = results.totalElements;
            });
        }

        callServerForData(vm.model.paging.pageno);



    }

})();