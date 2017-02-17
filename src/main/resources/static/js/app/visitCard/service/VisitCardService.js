(function () {
    "use strict";
    angular.module('visitorApp').factory('visitCardService', visitCardService);

    visitCardService.$inject = ['visitCardVOFactory', 'Restangular','$q'];

    function visitCardService(visitCardVOFactory, Restangular, $q) {

        var service = angular.merge(this, {
            //findVisitCardForPerson: findVisitCardForPerson,
            //save: save,
            filterData:filterData,
            printVisitCard:printVisitCard,
            printPeriodVisitCard:printPeriodVisitCard,
            deactivate:deactivate,
            countActiveVisitCard:countActiveVisitCard,
            // searchSuggestions:searchSuggestions,
            findVisitCardBySearchText:findVisitCardBySearchText
        });

        return service;

        //
        // function searchSuggestions(searchText) {
        //     if(searchText){
        //         return  Restangular.all('visitCard').customGETLIST('suggestions', {searchText: searchText}).then(function (visitCards) {
        //             return visitCards.map(function(elem){
        //                 var visitCard = elem.visitCard;
        //                 return visitCardVOFactory.createVisitCardVO(visitCard);
        //             })
        //         });
        //     }else{
        //         return  Restangular.all('visitCard').customGETLIST('suggestions').then(function (visitCards) {
        //             return visitCards.map(function(elem){
        //                 var visitCard = elem.visitCard;
        //                 return visitCardVOFactory.createVisitCardVO(visitCard);
        //             })
        //         });
        //     }        
        // }
        
        function findVisitCardBySearchText(searchText){
            if(searchText){
                return  Restangular.one('visitCard').customGET('byNumber',{searchText: searchText}).then(function (result) {
                        return visitCardVOFactory.createVisitCardVO(result.visitCard);
                });
            }else{
                var deferred = $q.defer();
                deferred.reject("Nie znalezioni przepustki");
                return deferred.promise;
            }
        }

        function filterData(searchText, typeShowData,page, resultOnPage){
            if(searchText){
                return Restangular.all('visitCard').customGET('search',{searchText: searchText, typeShowData:typeShowData, page:page, resultOnPage:resultOnPage}).then(function (results) {
                    results.content = results.content.map(function(elem){
                        return visitCardVOFactory.createVisitCardVO(elem);
                    });
                    return results;
                });
            }else{
                return Restangular.all('visitCard').customGET('search',{typeShowData:typeShowData, page:page, resultOnPage:resultOnPage}).then(function (results) {
                    results.content = results.content.map(function(elem){
                        return visitCardVOFactory.createVisitCardVO(elem);
                    });
                    return results;
                });
            }

        }



        function printVisitCard(person, contactPerson, numberOdDays){
            if(!contactPerson || !contactPerson.id){
                contactPerson=null;
            }
            if(!person.comapny){
                person.company = {id:-1, name:''};
            }
            return Restangular.all('visitCard/manually/print').post({person:person, contactPerson:contactPerson, numberOfDays:numberOdDays}).then(function (result) {
                return  visitCardVOFactory.createVisitCardVO(result.visitCard);
            });
        }

        function printPeriodVisitCard(visitCard){
            return Restangular.all('visitCard/period/print').post(visitCard).then(function (result) {
                return  visitCardVOFactory.createVisitCardVO(result.visitCard);
            });
        }

        function deactivate(visitCard){
            return Restangular.all('visitCard').customDELETE(visitCard.id).then(function (result) {
                return  visitCardVOFactory.createVisitCardVO(result.visitCard);
            });
        }

        function countActiveVisitCard(){
            return Restangular.all('visitCard').customGET('active/count').then(function (result) {
                console.log(result);
                return  result;
            });
        }
    }
})();



//
//function findVisitCardForPerson(id) {
//   return Restangular.all('visitCard').one('person', id).get().then(function (result) {
//        var _visitCard;
//        if (result.length == 0) {
//            _visitCard = [{
//                value: objectFactory.createVisitCardVO(),
//                display: 'Brak ważnej wejściówki'
//            }];
//        } else {
//
//            _visitCard = result.map(function (entry) {
//                return {
//                    value: objectFactory.createVisitCardVOFromServerEntity(entry.visitCard),
//                    display: 'Ważna od: ' + moment.utc(entry.visitCard.dateFrom.millis).format('YYYY/MM/DD') + '  do ' + moment.utc(entry.visitCard.dateTo.millis).format('YYYY/MM/DD')
//                };
//
//            });
//        }
//    });
//}
