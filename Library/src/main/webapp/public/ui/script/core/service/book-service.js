(function () {
    'use strict';

    angular.module('app.core').service('BookService', BookService);

    BookService.$inject = ['$resource', '$rootScope'];

    function BookService($resource, $rootScope) {
        return $resource('/library/api/book', {}, {
            query: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json'}
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



//                          