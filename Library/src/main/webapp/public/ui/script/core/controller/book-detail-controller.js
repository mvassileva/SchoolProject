(function () {
    'use strict';

    angular.module('app.core').controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$routeParams', 'BookDetailService', 'BookStatusService', 'AuthorService'];

    function BookDetailController($scope, $routeParams, BookDetailService, BookStatusService, AuthorService) {
        $scope.bookStatus = BookStatusService.query();
        $scope.authors = AuthorService.query();
        
        $scope.submitBook = function submitBook() {
            var bookDAO = new BookDetailService($scope.book);
            bookDAO.$update({id: $scope.book.id});
        };
        
        BookDetailService.get({id: $routeParams.bookId}, function (data) {
            $scope.book = data;
        });
        
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
