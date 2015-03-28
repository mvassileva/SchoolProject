(function () {
    'use strict';

    angular.module('app.core').controller('AuthorController', AuthorController);

    AuthorController.$inject = ['$scope', 'Author'];

    function AuthorController($scope, Author) {
        $scope.authors = Author.query();

        $scope.authorData = {};
        $scope.newAuthor = function () {
            var author = new Author($scope.authorData);
            author.$save();
        };

        $scope.orderProp = 'lastName';

        $scope.author = $scope.authors[1];
    }
}());
