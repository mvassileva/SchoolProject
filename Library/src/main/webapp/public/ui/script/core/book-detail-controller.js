(function () {
    'use strict';

    angular.module('app.core').controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$routeParams', 'BookService'];

    function BookDetailController($scope, $routeParams, BookService) {
        BookService.get({id: $routeParams.bookId}, function (data) {
            $scope.book = data;
        });
    }
}());
