(function () {
    'use strict';

    angular.module('app.core').controller('UserDetailController', UserDetailController);

    UserDetailController.$inject = ['$scope', '$routeParams', '$location', 'UserDetailService', 'UserLevelService'];

    function UserDetailController($scope, $routeParams, $location, UserDetailService, UserLevelService) {
        $scope.userLevels = UserLevelService.query();

        UserDetailService.get({id: $routeParams.userId}, function (data) {
            $scope.user = data;
        });

        $scope.submitUser = function submitUser() {
            var userServ = new UserDetailService($scope.user);
            userServ.$update({id: $scope.user.id});
            $location.path('/users');
        };
    }
}());
