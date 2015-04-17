(function () {
    'use strict';

    angular.module('app.core').service('UserLevelService', UserLevelService);

    UserLevelService.$inject = ['$resource'];

    function UserLevelService($resource) {
        return $resource('/library/api/userLevels', {}, {
            get: {
                method: 'GET',
                isArray: true,
                headers: {Accept: 'application/json'}
            }
        });
    }
}());