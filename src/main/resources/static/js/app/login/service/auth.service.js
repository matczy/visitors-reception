'use strict';

angular.module('loginModule')
    .factory('Auth', function Auth($rootScope, $state, $q, Principal, AuthServerProvider, Restangular) {
        return {
            login: function (credentials, callback) {
                var cb = callback || angular.noop;
                var deferred = $q.defer();

                AuthServerProvider.login(credentials).then(function (data) {
                    // retrieve the logged account information
                    Principal.identity(true).then(function (account) {
                        deferred.resolve(data);
                    });
                    return cb();
                }).catch(function (err) {
                    this.logout();
                    deferred.reject(err);
                    return cb(err);
                }.bind(this));

                return deferred.promise;
            },

            logout: function () {
                AuthServerProvider.logout();
                Principal.authenticate(null);
                // Reset state memory
                $rootScope.previousStateName = undefined;
                $rootScope.previousStateNameParams = undefined;
            },

            authorize: function (force) {
                return Principal.identity(force)
                    .then(function () {
                        var isAuthenticated = Principal.isAuthenticated();

                        // an authenticated user can't access to login and register pages
                        if (isAuthenticated && ($rootScope.toState.name === 'login')) {
                            $state.go('registrationVisitor');
                        }

                        if ($rootScope.toState.data.authorities && $rootScope.toState.data.authorities.length > 0 && !Principal.hasAnyAuthority($rootScope.toState.data.authorities)) {
                            if (isAuthenticated) {
                                // user is signed in but not authorized for desired state
                                $state.go('accessdenied');
                            }
                            else {
                                // user is not authenticated. stow the state they wanted before you
                                // send them to the signin state, so you can return them when you're done
                                $rootScope.previousStateName = $rootScope.toState;
                                $rootScope.previousStateNameParams = $rootScope.toStateParams;

                                // now, send them to the signin state so they can log in
                                $state.go('registrationVisitor');
                            }
                        }
                    });
            },


            createAccount: function (account) {
                return Restangular.all('api/register').post(account)
            },

            resetPasswordInit: function (mail) {
                return Restangular.all('api/account/reset_password/init').post(mail)
               
            },

            resetPasswordFinish: function(keyAndPassword) {
                return Restangular.all('api/account/reset_password/finish').post(keyAndPassword)

            }
        };
    });
