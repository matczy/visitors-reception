(function () {
    angular.module('visitorApp').component('displayPersonRow',{
        restrict: 'EA',
            bindings: {
                person:'='
            },
            replace: true,

            controller: 'DisplayPersonRowController as ctrl',
            templateUrl: 'views/my/person/component/displayPersonRow.html'
    });







    angular.module('visitorApp').controller('DisplayPersonRowController', DisplayPersonRowController);

    DisplayPersonRowController.$inject = [];
        function DisplayPersonRowController() {
            var vm = this;

        }

})();
