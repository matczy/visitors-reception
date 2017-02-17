'use strict';
(function () {

    angular.module('loginModule').controller('ResetRequestController', ResetRequestController);
    ResetRequestController.$inject = ['Auth','$timeout'];

    function ResetRequestController(Auth,$timeout) {
        var vm = this;

        vm.success = null;
        vm.error = null;
        vm.errorEmailNotExists = null;
        vm.resetAccount = {};

        $timeout(function (){angular.element('[ng-model="ctrl.resetAccount.email"]').focus();});

        angular.merge(vm, {
            requestReset: requestReset
        });



        function requestReset() {
            vm.error = null;
            vm.errorEmailNotExists = null;
            console.log("start reset password");

            Auth.resetPasswordInit(vm.resetAccount.email).then(function () {
                vm.success = 'OK';
            }).catch(function (response) {
                vm.success = null;
                if (response.status === 400 && response.data === 'e-mail address not registered') {
                    vm.errorEmailNotExists = 'ERROR';
                } else {
                    vm.error = 'ERROR';
                }
            });
        }

    }
})();
