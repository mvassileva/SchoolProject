(function () {
    'use strict';

    angular.module('app.core').controller('AuthorController', AuthorController);

    AuthorController.$inject = ['$scope', '$location', 'AuthorService'];

    function AuthorController($scope, $location, AuthorService) {
        $scope.authors = AuthorService.query();

        $scope.author = {};

        $scope.submitAuthor = function submitAuthor() {
            var authorDAO = new AuthorService($scope.author);
            authorDAO.$save();
            $location.path('/author');
        };

        /* set default sort order */
        $scope.orderByField = 'lastName';
        $scope.reverseSort = false;
    }
}());
