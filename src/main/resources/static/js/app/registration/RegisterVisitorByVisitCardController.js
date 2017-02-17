"use strict";
(function () {
    angular.module('visitorApp').controller('RegisterVisitorByVisitCardController', RegisterVisitorController);

    RegisterVisitorController.$inject = ['$scope','$filter','personService','companyService','visitCardService', 'registerVisitorService','messageService'];
    function RegisterVisitorController($scope,$filter,personService,companyService, visitCardService, registerVisitorService,messageService) {
        var vm = this;

        angular.merge(vm, {
            model: {
                contactPerson:''

            },
            searcher: {
                visitCard:'',
               // searchVisitCardsSuggestions:searchVisitCardsSuggestions
            },
            service: {
                registerByVisitCard:registerByVisitCard,
                trySelectVisitCard:trySelectVisitCard
            }


    });


        $scope.$watch(angular.bind(vm,
            function () { return vm.searcher.visitCard;}),
            function(newVisitCard){
                if(newVisitCard.id){
                    vm.model.visitCard = newVisitCard;
                }else if(newVisitCard === ''){
                    vm.model.visitCard='';
                }
            });


        function trySelectVisitCard(){
                visitCardService.findVisitCardBySearchText(vm.searcher.visitCard ).then(function(data){
                    vm.model.visitCard = data;
                    if(!vm.model.visitCard.active){
                        messageService.showWarningMessage("Przepustka  o numerze "+vm.model.visitCard.id +" nie aktywna");
                    }
                }, function(errorResponse){
                    messageService.showWarningMessage("Nie znaleziono przepustki o numerze "+vm.searcher.visitCard);
                })

        }
        //
        // function searchVisitCardsSuggestions(text){
        //     return visitCardService.searchSuggestions(text);
        // }
        //

        function registerByVisitCard(visitCard, action){
            registerVisitorService.registerByVisitCard(visitCard, action).then(function(response){
                vm.model.visitCard = '';
                vm.searcher.visitCard= '';
                if(action==='ENTRY'){
                    messageService.showInfoMessage('Poprawne wejście osoby '+response.person.surname+' '+response.person.name);
                }else{
                    messageService.showInfoMessage('Poprawne wyjście osoby '+response.person.surname+' '+response.person.name);
                }
            }, function(errorResponse){
                messageService.showErrorMessage(errorResponse.data.message);

            });
        }
    }
})();