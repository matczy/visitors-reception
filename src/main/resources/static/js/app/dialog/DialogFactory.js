(function () {
    "use strict";
    angular.module('visitorApp').factory('dialogFactory', dialogFactory);

    dialogFactory.$inject = ['$uibModal'];

    function dialogFactory($uibModal) {

        var service = angular.merge(this, {
            createPersonCreatorDialog: createPersonCreatorDialog,
            createVisitCardDialog:createVisitCardDialog,
            editVisitor:editVisitor,
            detailsRegisterVisitorHistory:detailsRegisterVisitorHistory,
            detailsVisitCard:detailsVisitCard,
            detailsVisitor:detailsVisitor
        });

        return service;

        function createPersonCreatorDialog(model) {

            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/my/dialog/person/createPersonDialog.tmpl.html',
                controller: 'PersonCreatorDialogController as ctrl',
                size: 'lg',
                resolve: {
                    person:function(){
                        return model.person
                    },
                    personsType: model.personsType,
                    companies: model.companies,
                    adminRole : function(){
                        return model.adminRole;
                    }
                }
            });

            return modalInstance.result;
        }

        function createVisitCardDialog(person, contactPerson){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/my/dialog/visitCard/createVisitCardDialog.tmpl.html',
                controller: 'VisitCardDialogController as ctrl',
                size: 'lg',
                resolve: {
                    person:function(){
                        return person
                    }, contactPerson:function(){
                        if(contactPerson && contactPerson.id){
                            return  contactPerson
                        }else{
                            return '';
                        }
                    }
                }
            });

            return modalInstance.result;
        }

        function editVisitor(model){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/my/dialog/person/createPersonDialog.tmpl.html',
                controller: 'VisitorDialogController as ctrl',
                size: 'lg',
                resolve: {
                    person:function(){
                        return model.person;
                    },
                    contactPerson:function(){
                        if(model.contactPerson && model.contactPerson.id){
                            return  model.contactPerson;
                        }else{
                            return '';
                        }
                    }
                    , adminRole : function(){
                        return model.adminRole;
                    }
                }
            });

            return modalInstance.result;
        }

        function detailsRegisterVisitorHistory(model){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/my/dialog/registerVisitor/showDetails.tmpl.html',
                controller: 'DetailsDialogController as ctrl',
                size: 'lg',
                resolve: {
                    data:function(){
                        return model;
                    }
                }
            });

            return modalInstance.result;
        }
        
        function detailsVisitCard(visitCrd){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/my/dialog/visitCard/showDetails.tmpl.html',
                controller: 'DetailsDialogController as ctrl',
                size: 'lg',
                resolve: {
                    data:function(){
                        return visitCrd;
                    }
                }
            });
            return modalInstance.result;
        }

        function detailsVisitor(visitor){
            var modalInstance = $uibModal.open({
                animation: true,
                templateUrl: 'views/my/dialog/person/showDetails.tmpl.html',
                controller: 'DetailsDialogController as ctrl',
                size: 'lg',
                resolve: {
                    data:function(){
                        return visitor;
                    }
                }
            });
            return modalInstance.result;
        }
    }
})();


