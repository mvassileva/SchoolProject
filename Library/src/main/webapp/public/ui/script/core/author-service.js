(function () {
    'use strict';

    angular.module('app.core').service('AuthorService', AuthorService);

    AuthorService.$inject = ['$resource'];

    function AuthorService($resource) {
        return $resource('/library/api/author', {}, {
            query: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json'}
            },
            add: {
                method: 'POST',
                isArray: false,
                headers: {Accept: 'application/json'}
            }
        });
    }
}());
