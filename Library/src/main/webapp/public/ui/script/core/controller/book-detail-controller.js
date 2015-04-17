(function () {
    'use strict';

    angular.module('app.core').controller('BookDetailController', BookDetailController);

    BookDetailController.$inject = ['$scope', '$routeParams', 'BookDetailService'];

    function BookDetailController($scope, $routeParams, BookDetailService) {
        BookDetailService.get({id: $routeParams.bookId}, function (data) {
            $scope.book = data;
        });
    }
}());
