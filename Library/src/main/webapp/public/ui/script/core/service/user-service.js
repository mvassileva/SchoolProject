(function () {
    'use strict';

    angular.module('app.core').service('UserService', UserService);

    UserService.$inject = ['$resource', '$rootScope'];

    function UserService($resource, $rootScope) {
        return $resource('/library/api/user', {}, {
            get: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json',
                          'API-User': $rootScope.userName,
                          'API-Key':  $rootScope.userToken }
            },
            query: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json',
                          'API-User': $rootScope.userName,
                          'API-Key':  $rootScope.userToken }
            },
            save: {
                method: 'POST',
                isArray: false,
                headers: { Accept: 'application/json',
                          'API-User': $rootScope.userName,
                          'API-Key':  $rootScope.userToken }
            }
        });
    }
}());