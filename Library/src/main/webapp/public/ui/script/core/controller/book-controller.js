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
    }
}());
