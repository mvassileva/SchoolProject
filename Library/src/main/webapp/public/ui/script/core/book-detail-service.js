(function () {
    'use strict';

    angular.module('app.core').service('BookDetailService', BookDetailService);

    BookDetailService.$inject = ['$resource'];

    function BookDetailService($resource) {
        return $resource('/library/api/book/:id', {}, {
            get: {
                    method: 'GET',
                    isArray: false,
                    headers: {Accept: 'application/json'}
            },
            update: {
                    method: 'PUT',
                    isArray: false,
                    headers: {Accept: 'application/json'}
            },
            remove: {
                    method: 'DELETE',
                    isArray: false,
                    headers: {Accept: 'application/json'}
            }
        });
    }
}());
