(function () {
    'use strict';

    angular.module('app.core').service('BookService', BookService);

    BookService.$inject = ['$resource'];

    function BookService($resource) {
        return $resource('/library/api/book', {}, {
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
