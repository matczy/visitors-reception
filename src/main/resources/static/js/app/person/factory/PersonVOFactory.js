(function () {
    "use strict";
    angular.module('visitorApp').factory('personVOFactory', personVOFactory);

    personVOFactory.$inject = [];

    function personVOFactory() {

        var factory = angular.merge(this, {
            createEmpty: createEmpty,
            createPersonVO :createPersonVO
        });

        return factory;

        function createEmpty() {
            var _personVO = Object.create(PersonVO);
            _personVO.init();
            return _personVO;
        }

        function createPersonVO(person) {
            var _personVO = Object.create(PersonVO);
            _personVO.init(person.id, person.surname, person.name, person.company, person.phone,person.email, person.documentIdentifier, person.type, person.confirmEmailAvailable);

            return _personVO;
        }


    }
})();


