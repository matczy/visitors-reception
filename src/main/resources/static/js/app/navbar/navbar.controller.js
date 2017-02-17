'use strict';
(function () {

    angular.module('visitorApp').controller('NavbarController', NavbarController);
    NavbarController.$inject = ['$state', 'Auth', '$scope'];

    function NavbarController($state, Auth, $scope) {
        var vm = this;

        angular.merge(vm, {
            logout: logout,
            login: login,
            register: register,
        });

        function logout() {
            Auth.logout();
            $scope.$emit('checkAuthority');
            $state.go('registrationVisitor');
        }

        function login() {
            $state.go('login');
        }

        function register() {
            $state.go('register');
        }

        




    }
})();
