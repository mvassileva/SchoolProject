(function () {
    'use strict';

    angular.module('app.core').service('UserDetailService', UserDetailService);

    UserDetailService.$inject = ['$resource', '$rootScope'];

    function UserDetailService($resource, $rootScope) {
        return $resource('/library/api/user/:id', {}, {
            get: {
                method: 'GET',
                isArray: false,
                headers: {Accept: 'application/json',
                         'API-User': $rootScope.userName,
                         'API-Key':  $rootScope.userToken}
            },
            update: {
                method: 'PUT',
                isArray: false,
                headers: {Accept: 'application/json',
                         'API-User': $rootScope.userName,
                         'API-Key':  $rootScope.userToken}
            },
            remove: {
                method: 'DELETE',
                isArray: false,
                headers: {Accept: 'application/json',
                         'API-User': $rootScope.userName,
                         'API-Key':  $rootScope.userToken}
            }
        });
    }
}());
