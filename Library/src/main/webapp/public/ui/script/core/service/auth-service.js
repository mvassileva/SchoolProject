(function () {
    'use strict';

    angular.module('app.core').service('AuthService', AuthService);

    AuthService.$inject = ['$resource'];

    function AuthService($resource) {
        return $resource('/library/api/auth/login', {}, {
             save: {
                method: 'POST',
                isArray: false,
                headers: {Accept: 'application/json'}
            }
        });
    }
    
}());