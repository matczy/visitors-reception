(function () {
    "use strict";
    angular.module('visitorApp').factory('visitCardVOFactory', visitCardVOFactory);

    visitCardVOFactory.$inject = [ 'personVOFactory','$log'];

    function visitCardVOFactory(personVOFactory, $log) {

        var factory = angular.merge(this, {
            createEmpty: createEmpty,
            createVisitCardVO: createVisitCardVO,
            createVisitCardVOEntity : createVisitCardVOEntity
        });

        return factory;

        function createEmpty() {
            var _visitCardVO = Object.create(VisitCardVO);
            _visitCardVO.init(null,personVOFactory.createEmpty(), personVOFactory.createEmpty(),'','',false);
            $log.info("created empty visitCardVO object");

            return _visitCardVO;
        }

        function createVisitCardVO(entity) {
            var _visitCardVO = Object.create(VisitCardVO);
            _visitCardVO.init(entity.id, entity.person, entity.contactPerson, entity.dateFrom, entity.dateTo, entity.active, entity.createdDate, entity.createdBy,entity.number);

            return _visitCardVO;
        }

        function createVisitCardVOEntity(person, contactPerson){
            var _visitCardVO = Object.create(VisitCardVO);
            _visitCardVO.init(null,person, contactPerson,null,null,true);

            return _visitCardVO;
        }

    }
})();


