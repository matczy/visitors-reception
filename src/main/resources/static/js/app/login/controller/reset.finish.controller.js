
'use strict';
(function () {

    angular.module('loginModule').controller('ResetFinishController', ResetFinishController);
    ResetFinishController.$inject = ['Auth','$timeout','$stateParams'];

    function ResetFinishController(Auth,$timeout,$stateParams) {
        var vm = this;
        vm.keyMissing = $stateParams.key === undefined;
        vm.doNotMatch = null;

        vm.resetAccount = {};
        $timeout(function (){angular.element('[ng-model="ctrl.resetAccount.password"]').focus();});

        angular.merge(vm, {
            finishReset: finishReset
        });



        function finishReset() {
            if (vm.resetAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                Auth.resetPasswordFinish({key: $stateParams.key, newPassword: vm.resetAccount.password}).then(function () {
                    vm.success = 'OK';
                    vm.doNotMatch = null;
                    vm.error = '';
                }).catch(function (response) {
                    vm.success = null;
                    vm.error = 'ERROR';

                });
            }
        }

    }
})();
