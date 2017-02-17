"use strict";
(function () {
    angular.module('visitorApp').controller('RegisterVisitorController', RegisterVisitorController);

    RegisterVisitorController.$inject = ['$scope','$filter','personService','companyService','visitCardService', 'registerVisitorService','messageService'];
    function RegisterVisitorController($scope,$filter,personService,companyService, visitCardService, registerVisitorService,messageService) {
        var vm = this;

        angular.merge(vm, {
            model: {
                contactPerson:'',
                person:'',
                visitCard:{
                    dateFrom:new Date(),
                    dateTo:new Date().setHours(23,59,59,999)
                },

            },
            searcher: {
                person:'',
                searchPersonsSuggestions: searchPersonsSuggestions,
                searchContactPersonsSuggestions: searchContactPersonsSuggestions,
                searchCompanySuggestions:searchCompanySuggestions,
            },
            service: {
                register:register,
                manuallyChange:manuallyChange
            }


    });


        $scope.$watch(angular.bind(vm,
            function () { return vm.searcher.person;}),
            function(newPerson){
            if(newPerson.id){
                vm.model.person = newPerson;
            }else if(newPerson === ''){
                vm.model.person='';
            }
        });



        function manuallyChange(){
            if(vm.model.person && vm.model.person.id){
                vm.model.person=''
            }
        }
        
        function searchPersonsSuggestions(text) {
            return personService.searchPersonsSuggestions(text);
        }

        function searchContactPersonsSuggestions(text) {
            return personService.searchContactPersonsSuggestions(text);
        }
        function searchCompanySuggestions(text) {
            return companyService.findAll(text).then(function(companies){
                return $filter('filter')(companies, text)
            });
        }




        //TODO jesli nei znalazl ktos osoby kontanktowej to mimo ze wpisal to nie zapisywac tylko jak ma id
        function register(person, contactPerson){
            registerVisitorService.register(person, contactPerson,vm.model.visitCard, 'ENTRY').then(function(response){
                vm.model.person = '';
                vm.model.contactPerson = '';
                vm.searcher.person= '';
                messageService.showInfoMessage('Poprawne wejście osoby '+response.person.surname+' '+response.person.name);
                $scope.form.$setPristine();
                $scope.form.$setValidity();
                $scope.form.$setUntouched();
            }, function(errorResponse){
                messageService.showErrorMessage(errorResponse);

            });

        }

       
    }
})();