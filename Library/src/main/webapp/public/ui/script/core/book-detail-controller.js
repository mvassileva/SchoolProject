(function () {
    'use strict';

    angular.module('app.core').controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$routeParams', 'Book'];

    function BookDetailController($scope, $routeParams, Book) {
        Book.get({id: $routeParams.bookId}, function (data) {
            $scope.book = data;
        });
    }
}());
