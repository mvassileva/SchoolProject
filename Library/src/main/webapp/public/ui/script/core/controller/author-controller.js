(function () {
    'use strict';

    angular.module('app.core').controller('AuthorController', AuthorController);

    AuthorController.$inject = ['$scope', 'AuthorService',];

    function AuthorController($scope, AuthorService) {
        $scope.authors = AuthorService.query();

        $scope.author = {};
        $scope.submitAuthor = function submitAuthor() {
            var author = new AuthorService($scope.author);
            author.$save();
        };

        $scope.orderProp = 'lastName';

        //$scope.author = $scope.authors[1];
       
    }
}());
