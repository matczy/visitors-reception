'use strict';

(function () {
    "use strict";
    angular.module('loginModule').factory('Account', Account);

    Account.$inject = ['Restangular'];

    function Account(Restangular) {

        var service = angular.merge(this, {
            getAccount: getAccount,
            isAuthenticate: isAuthenticate
        });

        return service;

        function isAuthenticate(){
            return Restangular.all('api').customGET('authenticate');
        }

        function getAccount() {
            return Restangular.all('api').customGET('account');
        }


    }
})();

