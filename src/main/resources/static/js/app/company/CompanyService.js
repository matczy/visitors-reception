(function () {
    "use strict";
    angular.module('visitorApp').factory('companyService', companyService);

    companyService.$inject = ['personVOFactory','Restangular','$q'];

    function companyService(personVOFactory,Restangular, $q) {

        var service = angular.merge(this, {
            findAll:findAll
        });

        return service;



        function findAll(){
           return Restangular.all('company').getList().then(function (results) {
               return results.map(function(elem){
                   var company = elem.company;
                   return {id:company.id,name:company.name};
               });
           });
        }




    }
})();




