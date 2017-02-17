(function () {
    angular.module('visitorApp').component('showCreatePersonButton',{
        restrict: 'EA',
            bindings: {
                person:'=',
                persons:'=',
                paging:'='
            },
            replace: true,

            controller: 'ShowCreatePersonButton as ctrl',
            templateUrl: 'views/my/person/component/createButton.html'
    });







    angular.module('visitorApp').controller('ShowCreatePersonButton', ShowCreatePersonButton);

    ShowCreatePersonButton.$inject = ['dialogFactory', 'companyService','messageService', 'personService'];
        function ShowCreatePersonButton(dialogFactory,companyService,messageService,personService) {
            var vm = this;
            angular.merge(vm, {
                showDialog:showDialog
            });

            function showDialog(person){
                dialogFactory.createPersonCreatorDialog( {
                        person:vm.person,
                        personsType: personService.findAllPersonType(),
                        companies: companyService.findAll(),
                        adminRole:true
                    })
                    .then(function (person) {
                        //TODO zmienic to ak aby patrzylo w ktorym trybie jestesmy
                        if(vm.persons){
                            personService.filterData('','ALL',vm.paging.pageno-1,vm.paging.itemsPerPage).then(function(results){
                                vm.persons = results.content;
                                vm.paging.total_count = results.totalElements;
                            });
                        }
                        if(vm.person){
                            vm.person = person;
                        }
                        messageService.showInfoMessage("Poprawne utworzenie osoby :"+person.surname+' '+person.name);

                    });
            }

        }

})();