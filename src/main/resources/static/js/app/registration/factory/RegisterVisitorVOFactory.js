(function () {
    "use strict";
    angular.module('visitorApp').factory('registerVisitorVOFactory', registerVisitorVOFactory);

    registerVisitorVOFactory.$inject = ['personVOFactory','visitCardVOFactory'];

    function registerVisitorVOFactory(personVOFactory,visitCardVOFactory) {

        var factory = angular.merge(this, {
            createEmpty: createEmpty,
            createRegisterVisitorVO :createRegisterVisitorVO
        });

        return factory;

        function createEmpty() {
            var _registerVisitorVO = Object.create(RegisterVisitorVO);
            _registerVisitorVO.init();
            return _registerVisitorVO;
        }

        function createRegisterVisitorVO(registerVisitor) {
            var _registerVisitorVO = Object.create(RegisterVisitorVO);
            var _contactPerson = registerVisitor.contactPerson || personVOFactory.createEmpty();
            var _visitCard = registerVisitor.visitCard || visitCardVOFactory.createEmpty();
            _registerVisitorVO.init(registerVisitor.id,registerVisitor.entryDate,registerVisitor.exitDate, registerVisitor.person,_contactPerson, _visitCard,registerVisitor.comment);
            return _registerVisitorVO;
        }


    }
})();


