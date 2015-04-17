(function () {
    'use strict';

    angular.module('app.core').service('BookDetailService', BookDetailService);

    BookDetailService.$inject = ['$resource', '$rootScope'];

    function BookDetailService($resource, $rootScope) {
        return $resource('/library/api/book/:id', {}, {
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
