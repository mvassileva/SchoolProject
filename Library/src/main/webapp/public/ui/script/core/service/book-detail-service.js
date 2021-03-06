(function () {
    'use strict';

    angular.module('app.core').service('BookDetailService', BookDetailService);

    BookDetailService.$inject = ['$resource', '$rootScope'];

    function BookDetailService($resource, $rootScope) {

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

        return $resource('/library/api/book/:id', {}, {
            get: {
                method: 'GET',
                isArray: false,
                headers: {Accept: 'application/json',
                         'API-User': getApiUserName,
                         'API-Key':  getApiToken}
            },
            update: {
                method: 'PUT',
                isArray: false,
                headers: {
                    Accept: 'application/json',
                    'API-User': getApiUserName,
                    'API-Key':  getApiToken
                }
            },
            remove: {
                method: 'DELETE',
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
