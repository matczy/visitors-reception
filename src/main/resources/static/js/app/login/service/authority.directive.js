'use strict';

angular.module('loginModule')
    .directive('hasAnyAuthority', ['Principal', function (Principal) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var setVisible = function () {
                        element.removeClass('hidden');
                    },
                    setHidden = function () {
                        element.addClass('hidden');
                    },
                    defineVisibility = function (reset) {
                        var result;
                        if (reset) {
                            setVisible();
                        }

                        result = Principal.hasAnyAuthority(authorities);
                        if (result) {
                            setVisible();
                        } else {
                            setHidden();
                        }
                    },
                    authorities = attrs.hasAnyAuthority.replace(/\s+/g, '').split(',');

                if (authorities.length > 0) {
                    defineVisibility(true);
                }
            }
        };
    }])
    .directive('hasAuthority', ['Principal', function (Principal) {
        return {
            restrict: 'A',
            link: function (scope, element, attrs) {
                var setVisible = function () {
                        element.removeClass('hidden');
                    },
                    setHidden = function () {
                        element.addClass('hidden');
                    },
                    defineVisibility = function (reset) {

                        if (reset) {
                            setVisible();
                        }

                        Principal.hasAuthority(authority)
                            .then(function (result) {
                                if (result) {
                                    setVisible();
                                } else {
                                    setHidden();
                                }
                            });
                    },
                    authority = attrs.hasAuthority.replace(/\s+/g, '');

                if (authority.length > 0) {
                    defineVisibility(true);
                }
            }
        };
    }])

.directive('showIfAuthenticated', ['Principal', function (Principal) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {

            var setVisible = function () {
                    element.removeClass('hidden');
                },
                setHidden = function () {
                    element.addClass('hidden');
                },
                defineVisibility = function (showIfAuthenticated) {
                    Principal.checkAuthenticate().then(function(result){
                        if(result){
                            if(showIfAuthenticated==="true"){
                                setVisible();
                            }else{
                                setHidden();
                            }
                        }else{
                            if(showIfAuthenticated==="true"){
                                setHidden();
                            }else{
                                setVisible();
                            }
                        }
                    });


                },
                showIfAuthenticated = attrs.showIfAuthenticated;

            defineVisibility(showIfAuthenticated);

            scope.$on('updateAuthority', function (event) {
                console.log("ALA");
                defineVisibility(showIfAuthenticated);
            });
        }
    };
}]);
