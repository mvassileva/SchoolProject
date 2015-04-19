/* global angular */

(function () {
    'use strict';

    angular.module('app.core').controller('LogoutController', LogoutController);

    LogoutController.$inject = ['$rootScope', '$scope', 'AuthService', '$location'];

    function LogoutController($rootScope, $scope, AuthService, $location) {
        $rootScope.isAuthenticated = false;
        $rootScope.isAdmin = false;
        $rootScope.userLevel = null;
        $rootScope.isLibrarian = false;
        $rootScope.$broadcast('userUpdate', null, null, null);
        $location.path('/');

    }
}());
