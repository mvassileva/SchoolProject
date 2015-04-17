(function () {
    'use strict';

    angular.module('app.core').service('BookStatusService', BookStatusService);

    BookStatusService.$inject = ['$resource'];

    function BookStatusService($resource) {
        return $resource('/library/api/bookStatus', {}, {
            get: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json'}
            }
        });
    }
}());