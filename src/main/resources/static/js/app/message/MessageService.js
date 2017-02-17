(function () {
    "use strict";
    angular.module('visitorApp').factory('messageService', messageService);

    messageService.$inject = ['$log'];

    function messageService($log) {

        var service = angular.merge(this, {
            showInfoMessage: showInfoMessage,
            showWarningMessage:showWarningMessage,
            showErrorMessage:showErrorMessage
        });

        return service;

        function showInfoMessage(message) {
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": true,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut",
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "15000",
                "extendedTimeOut": "1000"
            };
            toastr.info(message);
        }

        function showWarningMessage(message) {
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": true,
                "progressBar": false,
                "positionClass": "toast-top-right",
                "preventDuplicates": false,
                "onclick": null,
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut",
                "showDuration": "300",
                "hideDuration": "1000",
                "timeOut": "15000",
                "extendedTimeOut": "1000"
            };
            toastr.warning(message);
        }

        function showErrorMessage(message) {
            toastr.options = {
                "closeButton": true,
                "debug": false,
                "newestOnTop": true,
                "progressBar": false,
                "positionClass": "toast-top-left",
                "preventDuplicates": false,
                "onclick": null,
                "showEasing": "swing",
                "hideEasing": "linear",
                "showMethod": "fadeIn",
                "hideMethod": "fadeOut",
                "timeOut": "105000"

            };
            toastr.error(message);
        }


    }
})();


