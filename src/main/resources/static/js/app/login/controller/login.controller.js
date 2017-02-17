'use strict';
(function () {

    angular.module('loginModule').controller('LoginController', LoginController);
    LoginController.$inject = ['$scope', '$state', '$timeout','$stateParams', 'Auth'];

    function LoginController($scope, $state, $timeout,$stateParams, Auth) {
        var vm = this;



        vm.creddentials = {username: '', password: '', rememberMe: false};

        angular.merge(vm, {
            authenticationError: false,
            login: login,
        });


        function login(event, creddentials) {
            event.preventDefault();
            Auth.login({
                username: creddentials.username,
                password: creddentials.password,
                rememberMe: creddentials.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                $scope.$emit('checkAuthority');
                $state.go('dashboard',$stateParams, {reload: true});   // after success login go to home state
                //$scope.$emit('identityEvent');
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

    }
})();