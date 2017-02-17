(function () {
    "use strict";
    angular.module('visitorApp').factory('personService', personService);

    personService.$inject = ['personVOFactory','Restangular','$q'];

    function personService(personVOFactory,Restangular, $q) {

        var service = angular.merge(this, {
            searchPersonsSuggestions: searchPersonsSuggestions,
            searchContactPersonsSuggestions: searchContactPersonsSuggestions,
            findAllPersonType: findAllPersonType,
            filterData:filterData,
            save:save,
            update:update,
            deletePerson:deletePerson,
            changeConfirmEmailBehaviour:changeConfirmEmailBehaviour

        });

        return service;


        function searchPersonsSuggestions(searchText) {
            return _getPersonsSuggestions(searchText, 'search');
        }


        function searchContactPersonsSuggestions(searchText) {
            return _getPersonsSuggestions(searchText, 'search/contactPerson');
        }


        function _getPersonsSuggestions(searchText, urlTemplate) {
            if(searchText){
                return  Restangular.all('person').customGETLIST(urlTemplate, {searchText: searchText}).then(function (persons) {
                    return persons.map(function(elem){
                        var person = elem.person;
                        return personVOFactory.createPersonVO(person);
                    })
                });
            }else{
                return  Restangular.all('person').customGETLIST(urlTemplate).then(function (persons) {
                    return persons.map(function(elem){
                        var person = elem.person;
                        return personVOFactory.createPersonVO(person);
                    })
                });
            }

        }




        function filterData(searchText, typeShowData,page, resultOnPage){
            if(searchText){
                return Restangular.all('person').customGET('filter',{searchText: searchText, typeShowData:typeShowData, page:page, resultOnPage:resultOnPage}).then(function (results) {
                    results.content = results.content.map(function(elem){
                        return personVOFactory.createPersonVO(elem);
                    });
                    return results;
                });
            }else{
                return Restangular.all('person').customGET('filter',{typeShowData:typeShowData, page:page, resultOnPage:resultOnPage}).then(function (results) {
                    results.content = results.content.map(function(elem){
                        return personVOFactory.createPersonVO(elem);
                    });
                    return results;
                });
            }

        }
        
        

        function findAllPersonType() {
           return  Restangular.all('person').customGETLIST('type/search').then(function (results) {
               return results.map(function(elem){
                   return {id:elem.id,name:elem.name};
               });
           });
        }

        function save(person) {
            if(!person.company.id){
                person.company={id:-1,name:person.company}
            }
            return Restangular.all('person').post(person);
        }

        function update(person) {
            if(!person.company.id){
                person.company={id:-1,name:person.company}
            }
            return Restangular.all('person').customPUT(person);
        }

        function deletePerson(person) {
            return Restangular.all('person').customDELETE(person.id);
        }

        function changeConfirmEmailBehaviour(value, personId){
            return Restangular.one('person',personId).one('confirmationEmail').get({confirmEmail:value});
        }

    }
})();




