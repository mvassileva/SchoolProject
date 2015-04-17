(function () {
    'use strict';

    angular.module('app.core').controller('UserController', UserController);

    UserController.$inject = ['$scope', 'UserService'];

    function UserController($scope, UserService) {
        $scope.users = UserService.query();

        $scope.user = {};

        $scope.submitUser = function submitUser() {
            var userServ = new UserService($scope.user);
            userServ.$save();
        };
        $scope.orderProperty = 'username';
    }
}());
