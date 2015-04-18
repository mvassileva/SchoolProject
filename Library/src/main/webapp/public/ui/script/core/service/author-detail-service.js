(function () {
    'use strict';

    angular.module('app.core').service('AuthorDetailService', AuthorDetailService);

    AuthorDetailService.$inject = ['$resource', '$rootScope'];

    function AuthorDetailService($resource, $rootScope) {
        $rootScope.$on('userUpdate', function (event, userName, token, authLevel) {
            console.log("user Update Recieved" + userName + ", " + token + ", " + authLevel);
            $rootScope.userName = userName;
            $rootScope.userToken = token;
            $rootScope.userLevel = authLevel;
        });

        function getApiUserName() {
            return $rootScope.userName;
        }

        function getApiToken() {
            return $rootScope.userToken;
        }
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
                         'API-User': getApiUserName,
                         'API-Key':  getApiToken}
            },
            remove: {
                method: 'DELETE',
                isArray: false,
                headers: {Accept: 'application/json',
                         'API-User': getApiUserName,
                         'API-Key':  getApiToken}
            }
        });
    }
}());
