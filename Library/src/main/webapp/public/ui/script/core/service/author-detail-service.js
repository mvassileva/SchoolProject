(function () {
    'use strict';

    angular.module('app.core').service('AuthorDetailService', AuthorDetailService);

    AuthorDetailService.$inject = ['$resource', '$rootScope'];

    function AuthorDetailService($resource, $rootScope) {
        return $resource('/library/api/author/:id', {}, {
            get: {
                method: 'GET',
                isArray: false,
                headers: {Accept: 'application/json'}
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
