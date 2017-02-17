(function () {
    "use strict";
    angular.module('visitorApp').factory('registerVisitorService', registerVisitorService);

    registerVisitorService.$inject = ['printerCardService','registerVisitorVOFactory','Restangular','$q'];

    function registerVisitorService(printerCardService,registerVisitorVOFactory,Restangular,$q) {

        var service = angular.merge(this, {
            register: register,
            registerByVisitCard:registerByVisitCard,
            registerExitAction:registerExitAction,
            multipleRegister:multipleRegister,
            filterData:filterData,
            countAllVisitors:countAllVisitors,
            countAllVisitorsOnObject:countAllVisitorsOnObject,
            countTodayVisits:countTodayVisits,
            getLast30DaysVisitData:getLast30DaysVisitData,
            getLastDayByHourVisits:getLastDayByHourVisits,
            getLast30DaysLabel:getLast30DaysLabel,
            getLastDayLabel:getLastDayLabel
        });

        return service;


        function register(person, contactPerson,visitCard, direction) {
            var deffered =$q.defer();
            if(person.name && person.surname && person.documentIdentifier){
                //TODO przeniesc ten kawalek
                if(!person.company || !person.company.id){
                    person.company = {id:-1,name:person.company}
                }
                if(!contactPerson.id || !contactPerson.id){
                    contactPerson=null;
                }
                if(contactPerson && ((contactPerson.id === person.id)||(person.name ===contactPerson.name && person.surname=== contactPerson.surname && person.documentIdentifier===contactPerson.documentIdentifier))){
                    deffered.reject('Wybrana osoba nie może być jednocześnie odoba kontaktową');
                    return deffered.promise;
                }else{
                    return  Restangular.all('register/visitor').post({person:person, contactPerson:contactPerson,visitCard:visitCard, direction:direction})
                        .then(function (response) {
                            printerCardService.print(response.registerVisitor);
                            return response.registerVisitor;
                        });
                }

            }else{
                deffered.reject('Proszę uzupełnić wszystkie wymagane pola osoby odwiedzającej');
                return deffered.promise;
            }
        }

        //TODO dodac sprawdzenie czy wszystkie dane poprawne
        function multipleRegister(visitorsList, direction) {
            visitorsList.forEach(function(visitor){

                    if(!visitor.contactPerson || !visitor.contactPerson.id){
                        visitor.contactPerson=null;
                    }
                visitor.direction = direction;

            });


//TODO drukowanie wywolywac w kontrollerach
            return  Restangular.all('register/visitor/multi').post(visitorsList)
                .then(function (response) {
                    response.forEach(function(result){
                        printerCardService.print(result.registerVisitor);
                    });
                    return response;
                });
        }

        function registerByVisitCard(visitCard, action){
            var deferred = $q.defer();
            if(visitCard.id && visitCard.person.name && visitCard.person.surname && visitCard.person.documentIdentifier){
                return  Restangular.all('register/visitor/byVisitCard').post({visitCard:visitCard, direction:action})
                    .then(function (response) {
                        return response.registerVisitor;
                    });
            }else{
                deferred.reject('Błąd wpisanych danych osoby odwiedzającej');
                return deferred.promise;
            }

        }


        function filterData(searchText, typeShowData,page, resultOnPage){
            if(searchText){
                return Restangular.all('register/visitor').customGET('search',{searchText: searchText, typeShowData:typeShowData, page:page, resultOnPage:resultOnPage}).then(function (results) {
                    results.content = results.content.map(function(elem){
                        return registerVisitorVOFactory.createRegisterVisitorVO(elem);
                    });
                    return results;
                });
            }else{
                return Restangular.all('register/visitor').customGET('search',{typeShowData:typeShowData,page:page, resultOnPage:resultOnPage}).then(function (results) {
                    results.content = results.content.map(function (elem) {
                        return registerVisitorVOFactory.createRegisterVisitorVO(elem);
                    });
                    return results;
                });
            }

        }



        function registerExitAction(registerVisitor){
            return Restangular.all('register/visitor/exit').customPOST(registerVisitor.id);
        }


        function countAllVisitors(){
            return Restangular.all('register/visitor/count').customGET().then(function (result) {
                return  result;
            });
        }

        function countAllVisitorsOnObject(){
            return Restangular.all('register/visitor/onObject/count').customGET().then(function (result) {
                return  result;
            });
        }

        function countTodayVisits(){
            return Restangular.all('register/visitor/today/count').customGET().then(function (result) {
                return  result;
            });
        }

        function getLast30DaysVisitData(){
            return Restangular.all('register/visitor/last/30/days/visits/count').customGET().then(function (result) {
                return result.map(function(elem){
                    var date =elem[0];
                    var value = elem[1];
                    return {date:date,value:value}
                })
            });
        }

        function getLast30DaysLabel(){
            return Restangular.all('register/visitor/last/30/days').customGET().then(function (result) {
                return result;
            });
        }
        
        function getLastDayLabel(){
            return Restangular.all('register/visitor/last/1/days/byHour').customGET().then(function (result) {
                return result;
            });
        }
        
        function getLastDayByHourVisits(){
            return Restangular.all('register/visitor/last/1/days/byHour/visits/count').customGET().then(function (result) {
                return result.map(function(elem){
                    var date =elem[0];
                    var value = elem[1];
                    return {date:date,value:value}
                })
            });
        }
        


    }
})();
