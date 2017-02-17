"use strict";
(function () {
    angular.module('visitorApp').controller('DetailsDialogController', DetailsDialogController);
    DetailsDialogController.$inject = ['$uibModalInstance', 'data'];
    function DetailsDialogController($uibModalInstance,data) {
        var vm = this;

        angular.merge(vm, {
            model: {
                data: data || ''
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