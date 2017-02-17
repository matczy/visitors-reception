"use strict";
(function () {
    angular.module('visitorApp').controller('MultiRegisterVisitorController', MultiRegisterVisitorController);

    MultiRegisterVisitorController.$inject = ['$scope','$filter','personService','companyService','visitCardService', 'registerVisitorService','messageService','dialogFactory','moment'];
    function MultiRegisterVisitorController($scope,$filter,personService,companyService, visitCardService, registerVisitorService,messageService, dialogFactory,moment) {
        var vm = this;
var end = moment().endOf('day');
        angular.merge(vm, {
            model: {
                person:'',
                contactPerson:'',
                visitCard:{
                    dateFrom:new Date(),
                    dateTo:new Date().setHours(23,59,59,999)
                },
                visitorsList :[]

            },
            searcher: {
                person:'',
                visitCard:'',
                searchPersonsSuggestions: searchPersonsSuggestions,
                searchContactPersonsSuggestions: searchContactPersonsSuggestions,
                searchCompanySuggestions:searchCompanySuggestions
            },
            service: {
                addToVisitorsList:addToVisitorsList,
                deleteFromList:deleteFromList,
                showEditDialog:showEditDialog,
                register:register,
                manuallyChange:manuallyChange
            },
            size:(function(){
                console.log(angular.element("#form_content").innerHeight()+'px')
                return angular.element("#form_content").innerHeight()+'px';
            })()


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



        function addToVisitorsList(person, contactPerson){
            if(person.name && person.surname && person.documentIdentifier){
                if(_checkPersonIsNotDuplicate(person)){
                    if(_checkPersonIsDifferentThanContactPerson(person, contactPerson)){
                        var copyPerson = angular.copy(person);
                        if(copyPerson.company && !copyPerson.company.id){
                            copyPerson.company ={id:-1,name:copyPerson.company}
                        }else if(!copyPerson.company){
                            copyPerson.company ={id:-1,name:''}
                        }
                        console.log(vm.model.visitCard)
                        vm.model.visitorsList.unshift({person:copyPerson, contactPerson:angular.copy(contactPerson), visitCard:angular.copy(vm.model.visitCard)});
                        messageService.showInfoMessage('Poprawne dodanie osoby do listy '+person.surname+' '+person.name);
                        $scope.form.$setPristine();
                        $scope.form.$setValidity();
                        $scope.form.$setUntouched();
                        vm.model.person.name = '';
                        vm.model.person.surname = '';
                        vm.model.person.phone = '';
                        vm.model.person.email = '';
                        vm.model.person.documentIdentifier = '';
                    }else{
                        messageService.showWarningMessage('Wybrana osoba nie może być jednocześnie osobą kontaktową');
                    }

                }else{
                    messageService.showWarningMessage('Wpisana osoba znajduje się juz na liście');
                }

            }else{
                messageService.showInfoMessage('Proszę o uzupelnienie danych wymaganych');
            }
        }



        function deleteFromList(index){
            vm.model.visitorsList.splice(index,1);
        }

        function showEditDialog(visitor, index){
            dialogFactory.editVisitor( {
                    person:visitor.person,
                    contactPerson : visitor.contactPerson,
                    adminRole:false
                })
                .then(function (visitor) {
                   vm.model.visitorsList[index] = visitor;
                   messageService.showInfoMessage("Poprawne edytowanie osoby :" +visitor.person.surname+' '+visitor.person.name);
                });
        }

        function _checkPersonIsNotDuplicate(person){
            for(var i=0; i<vm.model.visitorsList.length;i++){
               var personOnList = vm.model.visitorsList[i].person;
                if(personOnList.name ===person.name && personOnList.surname ===person.surname && personOnList.documentIdentyfier ===person.documentIdentyfier){
                    return false;
                }
            }
            return true;
        }

        function _checkPersonIsDifferentThanContactPerson(person, contactPerson) {
            return !(contactPerson && ((contactPerson.id === person.id) || (person.name === contactPerson.name && person.surname === contactPerson.surname && person.documentIdentifier === contactPerson.documentIdentifier)));

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
        function register(){
            registerVisitorService.multipleRegister(vm.model.visitorsList, 'ENTRY').then(function(result){
                vm.searcher.person = '';
                vm.model.person='';
                vm.model.contactPerson='';
                vm.model.visitorsList = [];

                messageService.showInfoMessage("Poprawne zarejestrowania wejscia "+result.length+ ' osób');

            });
        }




    }
})();
