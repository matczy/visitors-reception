'use strict';
(function () {

    angular.module('visitorApp').controller('MainController', MainController);

    MainController.$inject = ['$scope', 'Account'];

    function MainController($scope, Account) {

        var vm = this;
         angular.merge(vm, {
             user:{
                 name:'',
                 surname:''
             },
             isCollapsedSidebar:true
        });


        vm.sidebarToggle = {
            left: false
        };


        $scope.$on('checkAuthority', function (event) {
            $scope.$broadcast('updateAuthority');

            Account.getAccount().then(function(data){
                vm.user.name = data.firstName;
                vm.user.surname = data.lastName;
            });
        });

        (function(){
            Account.getAccount().then(function(data){
                vm.user.name = data.firstName;
                vm.user.surname = data.lastName;
            });
        })()



    }
})();






