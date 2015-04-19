(function () {
    'use strict';

    angular.module('app.core').controller('UserController', UserController);

    UserController.$inject = ['$scope', '$location', 'UserService', 'UserLevelService'];

    function UserController($scope, $location, UserService, UserLevelService) {
        $scope.userLevels = UserLevelService.query();

        $scope.users = UserService.query();

        $scope.user = {};

        $scope.submitUser = function submitUser() {
            var userServ = new UserService($scope.user);
            userServ.$save();
            $location.path('/users');
        };

        /* set default sort order */
        $scope.orderByField = 'title';
        $scope.reverseSort = false;
    }
}());
