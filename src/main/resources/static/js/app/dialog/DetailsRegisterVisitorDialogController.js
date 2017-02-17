"use strict";
(function () {
    angular.module('visitorApp').controller('DetailsRegisterVisitorDialogController', DetailsRegisterVisitorDialogController);
    DetailsRegisterVisitorDialogController.$inject = ['$uibModalInstance', 'data'];
    function DetailsRegisterVisitorDialogController($uibModalInstance,data) {
        var vm = this;

        angular.merge(vm, {
            model: {
                registerVisitor: data || ''
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