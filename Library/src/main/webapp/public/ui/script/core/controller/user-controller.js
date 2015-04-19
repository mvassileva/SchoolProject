(function () {
    'use strict';

    angular.module('app.core').controller('UserController', UserController);

    UserController.$inject = ['$scope', 'UserService', 'UserLevelService'];

    function UserController($scope, UserService, UserLevelService) {
        $scope.userLevels = UserLevelService.query();

        $scope.users = UserService.query();

        $scope.user = {};

        $scope.submitUser = function submitUser() {
            var userServ = new UserService($scope.user);
            userServ.$save();
        };

        /* set default sort order */
        $scope.orderByField = 'title';
        $scope.reverseSort = false;
    }
}());
