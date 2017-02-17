(function () {
    "use strict";
    angular.module('visitorApp').factory('profileVOFactory', profileVOFactory);

    profileVOFactory.$inject = ['$log'];

    function profileVOFactory($log) {

        var factory = angular.merge(this, {
            createEmpty: createEmpty,
            createProfileVOFromServerEntity: createProfileVOFromServerEntity
        });

        return factory;

        function createEmpty() {
            var _profileVO = Object.create(ProfileVO);
            _profileVO.init(null);

            return _profileVO;
        }

        function createProfileVOFromServerEntity(entity) {
            var _profileVO = Object.create(ProfileVO);
            _profileVO.init(entity.name);

            return _profileVO;
        }
    }
})();


