'use strict';

angular.module('visitorApp')
    .controller('RegisterController', function ($scope, $state, $timeout, Auth, profiles) {
        var vm = this;
        vm.success = null;
        vm.error = null;
        vm.doNotMatch = null;
        vm.errorUserExists = null;
        vm.registerAccount = {};
        vm.profiles = profiles;
        // $timeout(function (){angular.element('[#login]').focus();});

        vm.register = function () {
            if (vm.registerAccount.password !== vm.registerAccount.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = 'en';
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'login already in use') {
                        vm.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        };
    });
