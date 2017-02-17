"use strict";
(function () {
    angular.module('visitorApp').controller('DetailsVisitCardDialogController', DetailsVisitCardDialogController);
    DetailsVisitCardDialogController.$inject = ['$uibModalInstance', 'data'];
    function DetailsVisitCardDialogController($uibModalInstance,data) {
        var vm = this;

        angular.merge(vm, {
            model: {
                visitCard: data || ''
            },
          
            service: {
                cancel: cancel
            }
            

        });


        function cancel() {
            $uibModalInstance.dismiss('cancel');
        }


        
    }
})();