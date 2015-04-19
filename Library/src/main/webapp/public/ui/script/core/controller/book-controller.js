(function () {
    'use strict';

    angular.module('app.core').controller('BookController', BookController);

    BookController.$inject = ['$scope', '$location', 'BookService', 'AuthorService', 'BookStatusService'];

    function BookController($scope, $location, BookService, AuthorService, BookStatusService) {
        $scope.bookStatus = BookStatusService.query();
        $scope.authors = AuthorService.query();
        $scope.books = BookService.query();
        $scope.book = {};

        $scope.submitBook = function submitBook() {
            var bookDAO = new BookService($scope.book);
            bookDAO.$save();
            $location.path('/library/public/ui/#/books');
        };

        /*
         * addAuthor and removeAuthor only work on the book update page since
         * $scope.book is only an empty object when creating a book in this
         * controller.  See line 12 above.  Due to this
         * $scrop.book.authors.push() does not exist when it is called in the
         * function below.  Same goes for $scope.book.authros.splice().
         *
         * Compare this to how these are setup in the book-detail-controller to
         * see the differences.
         */
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

        /* set default sort order */
        $scope.orderByField = 'title';
        $scope.reverseSort = false;
    }
}());
