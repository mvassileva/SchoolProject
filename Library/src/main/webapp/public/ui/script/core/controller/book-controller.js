(function () {
    'use strict';

    angular.module('app.core').controller('BookController', BookController);

    BookController.$inject = ['$scope', 'BookService', 'AuthorService', 'BookStatusService'];

    function BookController($scope, BookService, AuthorService, BookStatusService) {
        $scope.bookStatus = BookStatusService.query();

        $scope.books = BookService.query();

        $scope.book = {};

        $scope.submitBook = function submitBook() {
            var bookDAO = new BookService($scope.book);
            bookDAO.$save();
        };

        $scope.authors = AuthorService.query();
        $scope.orderProperty = 'title';

        $scope.addAuthor = function () {
            $scope.author = $scope.bookData.author;
            $scope.book.authors.push($scope.author);
        };

        $scope.removeAuthor = function (author) {
            var index = $scope.book.authors.indexOf(author);
            if (index != -1) {
                $scope.book.authors.splice(index, 1);
            }
        };
    }
}());
