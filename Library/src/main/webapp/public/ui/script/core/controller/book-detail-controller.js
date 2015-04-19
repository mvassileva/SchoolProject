(function () {
    'use strict';

    angular.module('app.core').controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$rootScope', '$routeParams', '$location', 'BookDetailService', 'BookStatusService', 'AuthorService', 'UserService'];

    function BookDetailController($scope, $rootScope, $routeParams, $location, BookDetailService, BookStatusService, AuthorService, UserService) {
        $scope.bookStatus = BookStatusService.query();
        $scope.authors = AuthorService.query();
        if ($rootScope.isLibrarian) {
             $scope.users = UserService.query();
        }
        
        BookDetailService.get({id: $routeParams.bookId}, function (data) {
            $scope.book = data;
            if ( $scope.book.checkedOutBy === null ) {
                $scope.book.checkedOutBy = {};
            }
        });

        $scope.submitBook = function submitBook() {
            var bookDAO = new BookDetailService($scope.book);
            bookDAO.$update({id: $scope.book.id});
            $location.path('/library/public/ui/#/books');
        };

        $scope.addAuthor = function () {
            $scope.author = $scope.bookData.author;
            $scope.book.authors.push($scope.author);
        };

        $scope.removeAuthor = function (author) {
            var index = $scope.book.authors.indexOf(author);

            if (index !== -1) {
                $scope.book.authors.splice(index, 1);
            }
        };
    }
}());
