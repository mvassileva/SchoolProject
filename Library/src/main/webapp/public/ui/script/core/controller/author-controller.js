(function () {
    'use strict';

    angular.module('app.core').controller('AuthorController', AuthorController);

    AuthorController.$inject = ['$scope', 'AuthorService'];

    function AuthorController($scope, AuthorService) {
        $scope.authors = AuthorService.query();

        $scope.author = {};

        $scope.submitAuthor = function submitAuthor() {
            var authorDAO = new AuthorService($scope.author);
            authorDAO.$save();
        };

        /* set default sort order */
        $scope.orderByField = 'lastName';
        $scope.reverseSort = false;
    }
}());
