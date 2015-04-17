(function () {
    'use strict';

    angular.module('app.core').controller('UserDetailController', UserDetailController);

    UserDetailController.$inject = ['$scope', '$routeParams', 'UserDetailService', 'UserLevelService'];

    function UserDetailController($scope, $routeParams, UserDetailService, UserLevelService) {
        $scope.userLevels = UserLevelService.query();
        
        UserDetailService.get({id: $routeParams.userId}, function (data) {
            $scope.user = data;
        });
        
        $scope.submitUser = function submitUser() {
            var userServ = new UserDetailService($scope.user);
            userServ.$update({id: $scope.user.id});
        };
    }
}());
