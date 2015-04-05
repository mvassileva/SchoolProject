(function () {
    'use strict';

    angular.module('app.core').controller('BookController', BookController);

    BookController.$inject = ['$scope', 'BookService', 'AuthorService'];

    function BookController($scope, BookService, AuthorService) {
        $scope.books = BookService.query();

        $scope.bookData = {};

        $scope.newBook = function newBook() {
            var book = new BookService($scope.bookData);
            book.$save();
        };

        $scope.authors = AuthorService.query();
        $scope.orderProperty = 'title';
    }
}());
