
(function () {
    angular.module('visitorApp', ['loginModule',
        'ui.router',
        'ngSanitize',
        'ngMaterial',
        'restangular',
        'datePicker' ,
        'ui.bootstrap',
        'localytics.directives',
        'chart.js','ngCookies',
        'tmh.dynamicLocale',
        'pascalprecht.translate',
        'angularUtils.directives.dirPagination',
        'angularMoment'
    ]);

    angular.module('visitorApp').run(function ($rootScope, $location, $window, $http, $state,$translate, Auth, Principal,Language) {


        $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
            if (Principal.isIdentityResolved()) {
                Auth.authorize();
            }

            // Update the language
            Language.getCurrent().then(function (language) {
                $translate.use(language);
            });
        });

        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
            var titleKey = 'visitorApp';
            // Remember previous state unless we've been redirected to login or we've just
            // reset the state memory after logout. If we're redirected to login, our
            // previousState is already set in the authExpiredInterceptor. If we're going
            // to login directly, we don't want to be sent to some previous state anyway
            if (toState.name != 'login' && $rootScope.previousStateName) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            }


            // Set the page title key to the one configured in state or use default one
            if (toState.data.pageTitle) {
                titleKey = toState.data.pageTitle;
            }
            $window.document.title = titleKey;

        });

        $rootScope.back = function () {
            // If previous state is 'activate' or do not exist go to 'home'
            if ($state.get($rootScope.previousStateName) === null) {
                $state.go('registrationVisitor');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    });



})();

