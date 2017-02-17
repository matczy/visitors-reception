(function () {
    angular.module('visitorApp').component('createVisitCardButton',{
        restrict: 'EA',
            bindings: {
                visitor:'=',
                contactPerson:'='
            },
            replace: true,

            controller: 'CreateVisitCardButtonController as ctrl',
            templateUrl: 'views/my/visitCard/component/createButton.html'
    });







    angular.module('visitorApp').controller('CreateVisitCardButtonController', CreateVisitCardButtonController);

    CreateVisitCardButtonController.$inject = ['dialogFactory', 'visitCardService','messageService'];
        function CreateVisitCardButtonController(dialogFactory,visitCardService,messageService) {
            var vm = this;
            angular.merge(vm, {
                showVisitCardCreator:showVisitCardCreator,
                printVisitCard:printVisitCard

            });


            function printVisitCard(visitor, contactPerson, numberOdDays){
                visitCardService.printVisitCard(visitor, contactPerson, numberOdDays).then(function(response){
                    messageService.showInfoMessage('Poprawny wydruk przepustki');
                })
            }

            function showVisitCardCreator(visitor, contactPerson){
                dialogFactory.createVisitCardDialog(visitor, contactPerson)
                    .then(function () {
                        messageService.showInfoMessage("Poprawne utworzenie przepustki");
                    });
            }


        }

})();