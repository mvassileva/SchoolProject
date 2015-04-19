(function () {
    'use strict';

    angular.module('app.core').controller('LoginController', LoginController);

    LoginController.$inject = ['$rootScope', '$scope', '$location', 'AuthService'];

    function LoginController($rootScope, $scope, $location, AuthService) {

        $scope.authData = {};

        $scope.login = function login() {
            var auth = new AuthService($scope.authData);
            auth.$save(function () {
                    if (auth.error) {
                        $scope.error = true;
                    } else {
                        $scope.error = false;
                        $rootScope.userToken = auth.token;
                        $rootScope.userName = auth.userName;
                        $rootScope.isAuthenticated = true;
                        if (auth.level === 'ADMINISTRATOR') {
                            $rootScope.isAdmin = true;
                            $rootScope.isLibrarian = true;
                        }
                        if (auth.level === 'LIBRARIAN') {
                            $rootScope.isLibrarian = 1;
                        }
                        $rootScope.userLevel = auth.level;

                        $rootScope.$broadcast('userUpdate', auth.userName, auth.token, auth.level);

                        $location.path('/');
                    }
                });
        };
    }
}());
