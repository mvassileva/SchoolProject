(function () {
    'use strict';

    angular.module('app.core').controller('UserController', UserController);

    UserController.$inject = ['$scope', 'UserService'];

    function UserController($scope, UserService) {
        $scope.users = UserService.query();

        $scope.userData = {};

        $scope.newUser = function newUser() {
            var user = new UserService($scope.userData);
            user.$save();
        };
        $scope.orderProperty = 'username';
    }
}());
