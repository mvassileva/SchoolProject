(function () {
    'use strict';

    angular.module('app.core').controller('BookController', BookController);

    BookController.$inject = ['$scope', 'Book', 'Author'];

    function BookController($scope, Book, Author) {
        $scope.books = Book.query();

        $scope.bookData = {};

        $scope.newBook = function newBook() {
            var book = new Book($scope.bookData);
            book.$save();
        };

        $scope.authors = Author.query();
        $scope.orderProp = 'title';
    }
}());
