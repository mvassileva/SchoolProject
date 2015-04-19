(function () {
    'use strict';

    angular.module('app.core').service('UserService', UserService);

    UserService.$inject = ['$resource', '$rootScope'];

    function UserService($resource, $rootScope) {
        $rootScope.$on('userUpdate', function (event, userName, token, authLevel) {
            $rootScope.userName = userName;
            $rootScope.userToken = token;
            $rootScope.userLevel = authLevel;
        });

        function getApiUserName() {
            return $rootScope.userName;
        }

        function getApiToken() {
            return $rootScope.userToken;
        }
        return $resource('/library/api/user', {}, {
            get: {
                method: 'GET',
                isArray: true,
                headers: {
                    Accept: 'application/json',
                    'API-User': getApiUserName,
                    'API-Key':  getApiToken
                }
            },
            query: {
                method: 'GET',
                isArray: true,
                headers: {
                    Accept: 'application/json',
                    'API-User': getApiUserName,
                    'API-Key':  getApiToken
                }
            },
            save: {
                method: 'POST',
                isArray: false,
                headers: {
                    Accept: 'application/json',
                    'API-User': getApiUserName,
                    'API-Key':  getApiToken
                }
            }
        });
    }
}());
