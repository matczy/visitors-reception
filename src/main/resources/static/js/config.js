angular.module('visitorApp').config(function ($stateProvider, $urlRouterProvider, $httpProvider,$locationProvider, $mdThemingProvider,$translateProvider,tmhDynamicLocaleProvider) {

    //enable CSRF
    $httpProvider.defaults.xsrfCookieName = 'CSRF-TOKEN';
    $httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
    $mdThemingProvider.theme('myTheme').primaryPalette('red');

    $urlRouterProvider.otherwise('/registration/visitor');

    $stateProvider
        .state('site', {
            'abstract': true,
            resolve: {
                authorize: ['Auth',
                    function (Auth) {
                        return Auth.authorize();
                    }
                ],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('login', {
            parent: 'site',
            url: '/login',
            data: {
                authorities: [],
                pageTitle: 'Sign in'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/login.html',
                    controller: 'LoginController as login'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('login');
                    return $translate.refresh();
                }]
            }
        })
        .state('forgotPassword', {
            parent: 'site',
            url: '/reset/request',
            data: {
                authorities: [],
                pageTitle: 'Reset password'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/resetPassword.html',
                    controller: 'ResetRequestController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('account');
                    return $translate.refresh();
                }]
            }
        })
        .state('finishReset', {
            parent: 'site',
            url: '/reset/finish?key',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/reset.finish.html',
                    controller: 'ResetFinishController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('account');
                    return $translate.refresh();
                }]
            }
        })

        .state('register', {
            parent: 'site',
            url: '/register',
            data: {
                authorities: ['ROLE_RECEPTION','ROLE_ADMIN'],
                pageTitle: 'Registration'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/register/registerView.html',
                    controller: 'RegisterController as ctrl'
                }
            },
            resolve: {
                profiles: function (profileService) {
                    return profileService.findAll();
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('register');
                    return $translate.refresh();
                }]
            }
        })
        .state('accessdenied', {
            parent: 'site',
            url: '/accessdenied',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/accessdenied/accessdenied.html'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('access');
                    return $translate.refresh();
                }]
            }
        })
        .state('registrationVisitor', {
            parent: 'site',
            url: '/registration/visitor',
            data: {
                authorities: [],
                pageTitle: 'registrationVisitor'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/registrationVisitor.html',
                    controller: 'RegisterVisitorController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registrationVisitor');
                    return $translate.refresh();
                }]
            }
        })
        .state('registrationVisitorByPerson', {
            parent: 'site',
            url: '/registration/visitor/person',
            data: {
                authorities: [],
                pageTitle: 'registrationVisitor'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/registerVisitor/registrationVisitorByPerson.html',
                    controller: 'RegisterVisitorController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registrationVisitor');
                    return $translate.refresh();
                }]
            }
        })
        .state('multiRegistrationVisitors', {
        parent: 'site',
        url: '/registration/visitor/multi',
        data: {
            authorities: [],
            pageTitle: 'registrationVisitor'
        },
        views: {
            'content@': {
                templateUrl: 'views/my/registerVisitor/multiRegistrationVisitors.html',
                controller: 'MultiRegisterVisitorController as ctrl'
            }
        },
        resolve: {
            translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                $translatePartialLoader.addPart('registrationVisitor');
                return $translate.refresh();
            }]
        }
    })
        .state('registrationVisitorByVisitCard', {
            parent: 'site',
            url: '/registration/visitor/visitCard',
            data: {
                authorities: [],
                pageTitle: 'registrationVisitor'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/registerVisitor/registrationVisitorByVisitCard.html',
                    controller: 'RegisterVisitorByVisitCardController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registrationVisitor');
                    return $translate.refresh();
                }]
            }
        })
        .state('dashboard', {
            parent: 'site',
            url: '/dashboard',
            data: {
                authorities: ['ROLE_RECEPTION','ROLE_ADMIN'],
                pageTitle: 'dashboard'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/dashboard.html',
                    controller: 'DashboardController as ctrl'
                }
            },
            resolve: {
                allVisits: function(registerVisitorService){
                   return registerVisitorService.countAllVisitors();
                },
                visitorsOnObject: function(registerVisitorService){
                    return registerVisitorService.countAllVisitorsOnObject();
                },
                activeVisitCard: function (visitCardService) {
                    return visitCardService.countActiveVisitCard();
                },
                todayVisits : function(registerVisitorService){
                    return registerVisitorService.countTodayVisits();
                },
                daysLabels:function(registerVisitorService){
                    return registerVisitorService.getLast30DaysLabel();
                },
                last30DaysVisitData: function(registerVisitorService){
                    return registerVisitorService.getLast30DaysVisitData();
                },
                hourLabels:function(registerVisitorService){
                    return registerVisitorService.getLastDayLabel();
                },
                lastDayByHour :function(registerVisitorService){
                    return registerVisitorService.getLastDayByHourVisits();
                },
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('dashboard');
                    return $translate.refresh();
                }]

            }
        })
        .state('visitorsList', {
            parent: 'site',
            url: '/visitors',
            data: {
                authorities: ['ROLE_RECEPTION','ROLE_ADMIN'],
                pageTitle: 'visitorsList'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/visitorsList.html',
                    controller: 'PersonsListController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('visitorsList');
                    return $translate.refresh();
                }]
            }
        })
        .state('visitCardsList', {
            parent: 'site',
            url: '/visitCards',
            data: {
                authorities: ['ROLE_RECEPTION','ROLE_ADMIN'],
                pageTitle: 'visitCardsList'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/visitCardsList.html',
                    controller: 'VisitsCardListController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('visitCardsList');
                    return $translate.refresh();
                }]
            }
        })
        .state('registrationHistoryList', {
            parent: 'site',
            url: '/registration/history',
            data: {
                authorities: ['ROLE_RECEPTION','ROLE_ADMIN'],
                pageTitle: 'registrationHistoryList'
            },
            views: {
                'content@': {
                    templateUrl: 'views/my/historyVisitorsList.html',
                    controller: 'HistoryRegistrationListController as ctrl'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('registrationHistoryList');
                    return $translate.refresh();
                }]
            }
        });


    $httpProvider.interceptors.push('errorHandlerInterceptor');
    $httpProvider.interceptors.push('authExpiredInterceptor');

    $translateProvider.useLoader('$translatePartialLoader', {
        urlTemplate: 'i18n/{lang}/{part}.json'
    });

    $translateProvider.preferredLanguage('pl');
    $translateProvider.fallbackLanguage('pl');
    $translateProvider.useCookieStorage();
    $translateProvider.useSanitizeValueStrategy('escaped');
    //$translateProvider.addInterpolation('$translateMessageFormatInterpolation');
    tmhDynamicLocaleProvider.localeLocationPattern('/3rd_party/bower_components/angular-i18n/angular-locale_{{locale}}.js');
    tmhDynamicLocaleProvider.useCookieStorage();
    tmhDynamicLocaleProvider.storageKey('NG_TRANSLATE_LANG_KEY');
});
