(function () {
    'use strict';

    angular.module('app.core').controller('BookController', BookController);

    BookController.$inject = ['$scope', '$location', 'BookService', 'AuthorService', 'BookStatusService'];

    function BookController($scope, $location, BookService, AuthorService, BookStatusService) {
        $scope.bookStatus = BookStatusService.query();
        $scope.authors = AuthorService.query();
        $scope.books = BookService.query();

        $scope.book = {};
        $scope.book.authors = [];
        
        $scope.submitBook = function submitBook() {
            var bookDAO = new BookService($scope.book);
            bookDAO.$save();
            $location.path('/books');
        };

        $scope.addAuthor = function (i) {
            $scope.author = $scope.bookData.author;
            $scope.book.authors.push($scope.author);
            $scope.bookData[i] = false;
        };

        $scope.removeAuthor = function (author) {
            var index = $scope.book.authors.indexOf(author);

            if (index !== -1) {
                $scope.book.authors.splice(index, 1);
                $scope.authors.push(author);
            }
        };
        
        
        
        

        /* set default sort order */
        $scope.orderByField = 'title';
        $scope.reverseSort = false;
    }
}());
