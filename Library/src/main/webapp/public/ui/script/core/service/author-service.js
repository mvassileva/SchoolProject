(function () {
    'use strict';

    angular.module('app.core').service('AuthorService', AuthorService);

    AuthorService.$inject = ['$resource', "$rootScope"];

    function AuthorService($resource, $rootScope) {
        return $resource('/library/api/author', {}, {
            query: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json'}
            },
            save: {
                method: 'POST',
                isArray: false,
                headers: {Accept: 'application/json',
                         'API-User': $rootScope.userName,
                         'API-Key':  $rootScope.userToken}
            }
        });
    }
}());
