(function () {
    "use strict";
    angular.module('visitorApp').factory('profileService', profileService);

    profileService.$inject = ['profileVOFactory', 'Restangular', '$q', '$log'];

    function profileService(profileVOFactory, Restangular, $q, $log) {
        var service = angular.merge(this, {
            findAll: findAll
        });

        return service;


        function findAll() {
            var deffered = $q.defer();
            Restangular.all('profile').getList().then(function (results) {

                var _profiles = results.map(function (entry) {
                    return profileVOFactory.createProfileVOFromServerEntity(entry.authority)

                });
                deffered.resolve(_profiles);
            });
            return deffered.promise;
        }


    }
})();




