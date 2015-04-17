/* global angular */

(function () {
    'use strict';

    angular.module('app.core').controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$scope', 'AuthService', '$location'];

    function LoginController($rootScope, $scope, AuthService, $location) {
        
        $scope.authData = {};
        
        $scope.login = function login() {
            var auth = new AuthService($scope.authData);
            auth.$save(function(){
                    console.log(arguments);
                    if (auth.error) {
                        $scope.error = true;
                    } else {                    
                        $scope.error = false;
                        $rootScope.userToken = auth.token;
                        $rootScope.userName = auth.userName;
                        $rootScope.authenticated = true;
                        $location.path("/");
                    }
                });
        }
    }
}());
