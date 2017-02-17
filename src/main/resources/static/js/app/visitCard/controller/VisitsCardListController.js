"use strict";
(function () {
    angular.module('visitorApp').controller('VisitsCardListController', VisitsCardListController);

    VisitsCardListController.$inject = ['visitCardService','messageService','printerCardService', 'dialogFactory'];
    function VisitsCardListController(visitCardService,messageService,printerCardService,dialogFactory) {
        var vm = this;
        angular.merge(vm, {
            model:{visitCards:[], 
                typeShowData : 'ALL',
                paging:{
                    pageno: 1,
                    total_count: 0,
                    itemsPerPage: 7
                }
            },

            searcher:{searchText:''},

            service: {
                deactivate:deactivate,
                print:print,
                showDetails:showDetails,
                callServerForData:callServerForData
            }
    });

        function callServerForData(pageno) {
            visitCardService.filterData(vm.searcher.searchText, vm.model.typeShowData, pageno-1, vm.model.paging.itemsPerPage).then(function (results) {
                vm.model.visitCards = results.content;
                vm.model.paging.total_count = results.totalElements;
            });
        }

        

        function deactivate(visitCard){
            visitCardService.deactivate(visitCard).then(function(visitCard){
                messageService.showInfoMessage("Karta zostałą zdezaktywowana");
                callServerForData(1);
            });
        }

        function print(visitCard){
            printerCardService.print(visitCard);
        }

        function showDetails(visitCard){
            dialogFactory.detailsVisitCard(visitCard);

        }

        callServerForData(vm.model.paging.pageno);

    }
})();