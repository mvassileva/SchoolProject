/* global angular */

(function () {
    'use strict';

    angular.module('app.core').controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', 'AuthService'];

    function LoginController($scope, AuthService) {
        
        $scope.authData = {};
        
        $scope.login = function login() {
            var auth = new AuthService($scope.authData);
            auth.$save(function(){
                    console.log(arguments);
                    if (auth.token != "-1") {
                        $scope.userToken = auth.token;
                        $scope.userName = $scope.authData.userName;
                    }}
                );
              

            
        }
    }
}());
